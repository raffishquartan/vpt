# VoltDB Wrapper Generator

VoltDB stored procedures are great, but have some weaknesses. This package will:

- defines new abstract classes and annotations (to make implementation in an IDE easier)
- lint your code (to find common VoltDB runtime errors that are not detected at compile or test)
- implement a procedure-first code generator (to make procedure use easier in application code)

Together they'll help reduce the risk of unforced dev errors that are not detected until run time.

## Building this

Need to install server jar

mvn install:install-file -Dfile=~/tmp/voltdb-5.8.1.jar -DgroupId=org.voltdb -DartifactId=voltdb -Dversion=5.8.1 -Dpackaging=jar
