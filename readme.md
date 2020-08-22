# project oop 

This is the project for exercising the lecture *object-oriented programming*.
The project is a code base in the Java programming language and 
contains several basic classes as example programs of OOP.
Those classes are declared in files located in the subdirectory `src/main/java/`.

To build the code of the project, 
you will needs to execute the following command after 
installing [Apache Maven](https://maven.apache.org).

   mvn package 

The command will produce the `oop-1.0-SNAPSHOT.jar` file in the `target` directory,
which contains compiled code of the classes.


For example, you can execute program with the jar, like the following command

   java -cp target/oop-1.0-SNAPSHOT.jar oop.nonmod.markdown.NonModMarkdown readme.md readme.html


This will execute the class from the file `src/main/java/oop/markdown/nonmod/NonModMarkdown.java`
and takes 2 arguments `readme.md` and `readme.html`.
The `NonModMarkdown` class reads the file specified as the first argument
and converts it to the file specified as the second argument.

The project currently contains the following programs

* `oop.sprite.*`          : tiny sprite example
* `oop.stack.*`           : stack example
* `oop.drawing.*`         : drawing figure apps
* `oop.markdown.*`        : simplified markdown processor
* `oop.nonmod.markdown.*` : non-modular version of simplified markdown processor
* `oop.nonmod.chat.*`     : non-modular version of chat server and client
* `oop.pattern.*`         : design-pattern examples
* `oop.tdd.*`             : TDD steps
* `oop.concurrent.*`      : concurrent and parallel examples

