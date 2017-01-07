# VoltDB WrapGen

tl;dr: Use WrapGen for safe, statically-typed invocation of VoltDB stored procedures.

## The problem with client-first stored procedure invocation

VoltDB is great. VoltDB stored procedures are great. But VoltDB stored procedures are executed using a client-first API with varargs and dynamic typing. So it's all too easy to make mistakes that are only detected at run time.

What do I mean? Well, here's a stored procedure definition:

````java
public class MyStoredProcedure extends VoltProcedure {
  public VoltTable[] run(String argOne, long argTwo) {
    // ...
  }
}
````

And here's me executing it in my application:

````java
client.callProcedure("MyStoredProcedure", 0L, "that data I talked about");
````

This will fail when executed. The problem is simple: I've transposed argOne and argTwo. But no errors or warnings are raised on compilation. My unit tests will all pass. So will my integration tests, which do correctly invoke the stored procedure. It is not until this code is run against a live database, perhaps in production itself, that this error will be discovered. Sad face.

There are also many other errors I could have made, and these would also fail to generate compile-time errors or warnings. I could mistype the stored procedure's name at invocation ("MyStoredProecdure"). I could specify an invalid return type to the run function, or I could specify an invalid parameter (javac will compile anything, but VoltDB will only allow [a few types](https://docs.voltdb.com/UsingVoltDB/DesignProcAnatomy.php#DesignPassArgs) as arguments to stored procedures).

And of course there's no code completion of vararg methods in any IDE so even just writing the code which uses the stored procedure is more painful than it needs to be.

## A solution: Procedure-first stored procedure invocation

WrapGen generates wrappers, one per stored procedure class, that are procedure-first and not client-first. Compare:

````java
CompletableFuture<ClientResponse> = MyStoredProcedure.run(client, "that data I talked about", 0L);
````

With:

````java
// synchronous - ugh
ClientResponse response = client.callProcedure("MyStoredProcedure", "that data", 0L);

// asynchronous, but callbacks - ugh
ClientResponse response = client.callProcedure(someCallbackDefinedElsewhere, "MyStoredProcedure", "that data", 0L);
````

Because there is one class per stored procedure, avoiding type errors in the parameters and typos in the stored procedure name is automatic, and code completion is a doddle. Does WrapGen solve all the world's problems? No. But it is a first step in making VoltDB even easier to use. See the issues, future features and road map for next steps :)

## But code generation is smelly

Yes, it can be. But so are run time errors in production. Smellier even.

There's an important point here though: WrapGen is intended for use in environments where DB correctness is critical. The "smelliness" of code generation ultimately comes down to two factors:

- It adds complexity (additional build steps)
- It's tempting to manually modify the generated code,  

WrapGen tries to work around these two issues:

- By making it as easy and as safe as possible to generate the runners. Ultimately, whether you do it on the console, in your IDE or at build time, the same code will be generated
 - That means you can generate it in Eclipse and in your build process, which also eliminates the risk of manual modifications making it into production
 - In the future, WrapGen will backup the code and warn when the generated code is different to any code being overwritten, so you also won't lose them
- Making the runners very simple. You give it the parameters, it gives you a future for the ClientResponse. You do whatever you like with it

Also it's [bad practice](https://docs.voltdb.com/UsingVoltDB/DesignAppAsync.php#DesignAppAsynCallback) to do complex computation in a VoltDB stored procedure or its callback. That said, WrapGen might not be right for your needs. Feedback and discussion is very welcome :)

## Building WrapGen

WrapGen is built using gradle: `./gradlew build`

Important note: WrapGen depends on the VoltDB client library, which is not available in any public repo. If it is not already present in your local repo you will need to install it before you can build WrapGen: 

`mvn install:install-file -Dfile=/path/to/voltdb-x.y.z.jar -DgroupId=org.voltdb -DartifactId=voltdb -Dversion=5.8.1 -Dpackaging=jar`

## console-wrapgen Usage

**To be finalised as development is completed**

console-wrapgen takes four command line arguments. All of them must be specified and no others are allowed:
- `-s`, `--source` - The root directory to crawl recursively for Java files, builders will be created for any valid VoltDB procedure found
- `-d`, `--destination` - The root directory to write the results to (package folders will be created under this as needed)
- `-p`, `--packagebase` - explained jointly below
- `-r`, `--regexsuffix` - explained jointly below

The root of each runner's package will be the `--packagebase`, e.g.: `--packagebase=voltdb.myrunners`. The regex suffix is then applied to the stored procedure's package and the first matching group is appended to the package base.

For example, if the VoltDB stored procedure MyStoredProcedure class was defined in the package  `uk.co.bristlecone.voltdb.datamanip` and `--regexsuffix=uk.co.bristlecone.voltdb.datamanip.(.*)` then the runner's fully qualified classname would be `voltdb.myrunners.datamanip.MyStoredProcedure`. Remember to escape globbing characters in the command line parameter.

If the suffix regex does not match then the runner is placed in `--packagebase`. Runner names clashing in this situation is not handled specially. 

## WrapGen Issues and Future Features

- Create runners for indirect subclasses of VoltProcedure (need to use java-symbol-solver here)
- Stored procedures defined in a nested class haven't been tested and probably won't be correctly parsed
- Enable the automatic annotation of runners with dependency injection annotations
- Enable generation of singleton runners (and make runner generation more configurable)
- Group source files into "is", "is not" and "maybe" contain stored procedure's, for clearer logging
- Make backup copies and log loudly if any file being overwritten is different to the generated code (manual modification warnings) 
- Build runners that use the run-everywhere pattern for appropriate stored procedures...
- ...with customisable, per-`ClientResponseWithPartitionKey`, join strategies
- Add WrapGenClientInterface, replicating VoltDB's and overload runners to allow either to be used (lets you define a custom client, e.g. with more logging)  
- Add some ClientResponse helper methods
  - E.g. for getting the string value of any column without manually specifying its type

## Roadmap

- Address the above issues and implement those future features
- gradle-wrapgen plugin
- maven-wrapgen plugin
- eclipse-wrapgen plugin
- intellij-wrapgen plugin
- An independent tool to check stored procedure's for correctness: VoltLint
  - Do smarter linting here to detect more issues - non-final `SQLStmt's`, use of non-final statics, ...

## Changelog

- 0.0.1 - first released version - wrapgen-core, console-wrapgen - **NOT YET RELEASED, COMING SOON**
