# VoltProcTools

VoltDB is great and it's stored procedures are even better. But distributed systems [can be hard](https://qualocustech.wordpress.com/2016/09/28/whos-afraid-of-the-big-bad-distributed-wolf/). VPT is a collection of tools that might make a developer's life in a distributed world just a little bit easier.  

## More on: vpt-lib

This library of common classes and utilities should be made available as a dependency at compile-time and run-time to client applications that use VPT (e.g. runners from runner-builders). It is not needed if the client application does not use VPT tools at run-time.

## More on: vpt-core

This library of common classes and utilities is used internally by VPT tools.

## More on: voltlint

Some linting is done by the runner-builder's, but more could be done and it should all be decoupled from runner-building. Coming soon!

## More on: runner-builder's

tl;dr: Use a runner-builder for safe, statically-typed, IDE-friendly invocation of VoltDB stored procedures 

### The problem with client-first stored procedure invocation

VoltDB stored procedures are executed using a client-first API with varargs and no static typing. This means there isn't much IDE support.

For example, stored procedure names are string constants, not types, and there's no static checking of arguments at the point of invocation.

This makes development slower and code harder to test. Bugs are discovered later at greater cost. This is a general problem bemoaned by dev's everywhere - JDBC I'm looking at you - but that doesn't mean it should be ignored.

For example, here's a VoltDB stored procedure definition:

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

This will compile without errors because `callProcedure` takes an `Object...` for the parameters, but it will fail when executed because I've transposed argOne and argTwo. If argOne and argTwo had compatible types the problem could be worse: corrupted data, a lurking cancer that would cause a catastrophe sometime in the future. 

I might discover this problem during integration testing (there are some great tools for Linux and Mac dev's [here](https://github.com/VoltDB/app-debug-and-test)), but if this procedure invocation was buried in an obscure code path or my testing was poor then there's a good chance it will only appear in production. Sad face.

This is one type of error. There are many others. I could mistype the stored procedure's name at invocation ("MyStoredProecdure"). I could specify an invalid return type to the run function, or I could specify a parameter type not passable to a VoltDB stored procedure. See [here](https://docs.voltdb.com/UsingVoltDB/DesignProcAnatomy.php#DesignPassArgs) for more and [here](https://qualocustech.wordpress.com/2017/02/02/voltdb-how-to-write-high-performance-stored-procedures-that-work-the-checklist/) for some of the other errors.

### A solution: Linting + procedure-first stored procedure invocation

A runner-builder walks your code base and generates stored procedure runners, one per stored procedure. These runners have an API that is procedure-first and not client-first. Compare the old:

````java
// synchronous - ugh
ClientResponse response = client.callProcedure("MyStoredProcedure", "that data", 0L);

// asynchronous, but no use of types - ugh
ClientResponse response = client.callProcedure(someCallbackDefinedElsewhere, "MyStoredProcedure", "that data", 0L);
````

With the new:

````java
MyStoredProcedure mspProc = new MyStoredProcedure();
CompletableFuture<ClientResponse> = mspProc.run(client, "that data I talked about", 0L);
````
Because there is now one class per stored procedure, type errors in the parameters and typos in the stored procedure name are automatically avoided and code completion is a doddle. The runner classes can be injected, or created on demand, they have no fields so construction is cheap and fast.

Stored procedure definitions are also linted during generation and some (soon: more) other errors are identified then.

Does doing this solve all the world's problems? No. But it's a first step in making VoltDB even easier to use. See the issues, future features and road map for next steps :)

### But code generation is smelly

Yes, it can be. But so are database errors in production.

There's an important point here though: vpt's runner-builder tools are intended for use where DB correctness and avoiding runtime errors is critical, your requirements might be different and it might not be appropriate for you.

Ultimately, the "smelliness" of code generation comes down to a couple of factors:

- It adds complexity
  - generated code isn't available in the IDE
  - additional build steps
- It's tempting to manually modify the generated code
  - Manual modifications get lost
  - If it needs modification you might as well write it from scratch

vpt and the runner-builder roadmap tries to work around these two issues:

- By making runner creation consistent across multiple tools
  - The output will be the same whether they are built in the IDE, by Maven or Gradle or a command line tool
- By flagging, not silently overwriting, differences
  - console-runner-builder backs up any existing files it finds, instead of just overwriting them
  - future versions will diff files, backup existing files as needed and prominently warn the developer
- By making the runner's configurable
  - allow automatic annotation of the runner with JEE annotations
  - use annotations of the stored procedure to specify which distributed execution patterns should be used (e.g. [run-everywhere](https://dzone.com/articles/voltdb-beyond-multi-partition))
- By making the runners very simple, reducing the need for modification that isn't configurable
  - You give the runners the parameters, it gives you a CompletableFuture for the ClientResponse, you do whatever you like with it
  - Doing as little as possible during stored procedure execution is also VoltDB [best practice](https://docs.voltdb.com/UsingVoltDB/DesignAppAsync.php#DesignAppAsynCallback)
  
Input and discussion is very welcome, please raise an issue if you have any questions :)

### console-runner-builder usage

When building runner's, console-runner-builder requires four command line arguments:
- `-s`, `--source` - The root directory to crawl recursively for valid procedures, builders will be created for each one found
- `-d`, `--destination` - The root directory to (runner package folders will be created under this)
- `-p`, `--packagebase` - explained with regexsuffix in the following paragraph
- `-r`, `--regexsuffix` - explained with packagebase in the following paragraph

The root of each runner's package will be the `--packagebase`, e.g.: `--packagebase=voltdb.myrunners`. The regex suffix is then applied to the stored procedure's package and the first matching group is appended to the package base.

For example, if the VoltDB stored procedure MyStoredProcedure class was defined in the package  `uk.co.bristlecone.voltdb.datamanip.foo.bar` and `--regexsuffix=uk.co.bristlecone.voltdb.datamanip.(.*)` then the runner's fully qualified classname would be `voltdb.myrunners.datamanip.foo.bar.MyStoredProcedure`. Remember to escape globbing characters in the regexsuffix command line parameter.

If regexsuffix does not match the procedure's package name then an error is raised.

console-runner-builder also accepts the `--version` and `--help` options.

### How do I use runner's in my application?

1. Execute console-runner-builder against the code base
2. Add vpt-lib as a dependency
3. ???
4. Profit 

### console-runner-builder, vpt-core issues and future features

- Only make backup copies of an existing file if it is different to the generated runner
- Create runners for indirect subclasses of VoltProcedure (need to use java-symbol-solver here)
- Stored procedures defined in a nested class haven't been tested and probably won't be correctly parsed
- Enable the automatic annotation of runners with dependency injection annotations (`@Inject` etc)
- Enable generation of singleton runners (make runner generation more configurable in general)
- Use the run-everywhere execution pattern in runners for configuration-specified stored procedures ...
- ... with customisable, per-`ClientResponseWithPartitionKey`, join strategies
- Add WrapGenClientInterface, replicating VoltDB's and overload runners to allow either to be used
  - This would let you define a custom client, e.g. with profiling, more logging or a custom thread pool
- Add new run method to runner's
  - runAndIgnoreResult (to be the equivalent of VDB's NullCallback)
  - runWithCallback (accepts a ProcedureCallback implementation and applies it to the ClientResponse future, to better support transitioning of existing code)
- Allow custom prefix and suffix to runner names (instead of just using the procedure name)

## Building vpt

The VPT tools are built using gradle: `./gradlew build`

Important note: VPT depends on the VoltDB client library, which is not available in any public repo. If it is not already present in your local repo you will need to install it before you can build VPT: 

`mvn install:install-file -Dfile=/path/to/voltdb-x.y.z.jar -DgroupId=org.voltdb -DartifactId=voltdb -Dversion=x.y.z -Dpackaging=jar`

## VPT roadmap

- An independent tool to check stored procedure's for correctness: voltlint
  - vpt-core does some for runner-builder's already, but more could be done - e.g. detect non-final `SQLStmt's`, use of non-final statics, ... 
  - Linting should be decoupled from runner-building 
- Address the remaining console-runner-builder issues (in vpt-core) 
- gradle-runner-builder plugin
- maven-runner-builder plugin
- eclipse-runner-builder plugin
- intellij-runner-builder plugin
- Add some ClientResponse helper methods to make working with stored procedure results a little easier
  - E.g. for getting the string value of any column without manually specifying its type

## Changelog

- 0.0.1 - first released version of vpt - vpt-lib, vpt-core, console-runner-builder
