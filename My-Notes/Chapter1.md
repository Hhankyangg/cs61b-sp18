# Chapter 1

**Our First Java Program.** Printing Hello World is as easy as:

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
```

**Key Syntax Features.** Our first programs reveal several important syntax features of Java:

- All code lives inside a class.
- The code that is executed is inside a function, a.k.a. method, called `main`.
- Curly braces are used to denote the beginning and end of a section of code, e.g. a class or method declaration.
- Statements end with semi-colons.
- Variables have declared types, also called their “static type”.
- Variables must be declared before use.
- Functions must have a return type. If a function does not return anything, we use void,
- The compiler ensures type consistency. If types are inconsistent, the program will not compile.

**Static Typing.** Static typing is (in my opinion) one of the best features of Java. It gives us a number of important advantages over languages without static typing:

- Types are checked before the program is even run, allowing developers to catch type errors with ease.
- If you write a program and distribute the compiled version, it is (mostly) guaranteed to be free of any type errors. This makes your code more reliable.
- Every variable, parameter, and function has a declared type, making it easier for a programmer to understand and reason about code.

There are downside of static typing, to be discussed later.

**Coding Style.** Coding style is very important in 61B and the real world. Code should be appropriately commented as described in the textbook and lectures.

**Command line compilation and execution.** `javac` is used to compile programs. `java` is used to execute programs. We must always compile before execution.

**Client Programs and Main Methods.** A Java program without a main method cannot be run using the `java` command. However, methods from one class can be invoked using the `main` method of another class. A java class that uses another class is called a client of that class.

**Class Declaration.** Java classes can contain methods and/or variables. We say that such methods and variables are “members” of the calss. Members can be *instance* members or *static* members. Static members are declared with the `static` keyword. Instance members are any members without the `static` keyword.

**Class Instantiation.** Instantiating a class is almost always done using the new keyword, e.g. `Dog d = new Dog()`. An instance of a class in Java is also called an `Object`.

**Dot Notation.** We access members of a class using dot notation, e.g. `d.bark()`. Class members can be accessed from within the same class or from other classes.

**Constructors.** Constructors tell Java what to do when a program tries to create an instance of a class, e.g. what it should do when it executes `Dog d = new Dog()`.

**Array Instantiation.** Arrays are also instantiated using the `new` keyword. If we have an array of Objects, e.g. `Dog[] dogarray`, then each element of the array must also be instantiated separately.

**Static vs. Instance methods.** The distinction between static and instance methods is incredibly important. Instance methods are actions that can only be taken by an instance of the class (i.e. a specific object), whereas static methods are taken by the class itself. An instance method is invoked using a reference to a specific instance, e.g. `d.bark()`, whereas static methods should be invoked using the class name, e.g. `Math.sqrt()`. Know when to use each.

**Static variables.** Variables can also be static. Static variables should be accessed using the class name, e.g. `Dog.binomen` as opposed to `d.binomen`. Technically Java allows you to access using a specific instance, but we strongly encourage you not to do this to avoid confusion.

**void methods.** A method which does not return anything should be given a void return type.

**The `this` keyword.** Inside a method, we can use the `this` keyword to refer to the current instance.

**public static void main(String[] args).** We now know what each of these things means:

- public: So far, all of our methods start with this keyword.
- static: It is a static method, not associated with any particular instance.
- void: It has no return type.
- main: This is the name of the method.
- String[] args: This is a parameter that is passed to the main method.

**Command Line Arguments.** Arguments can be provided by the operating system to your program as “command line arguments,” and can be accessed using the `args` parameter in `main`. For example if we call our program from the command line like this `java ArgsDemo these are command line arguments`, then the `main` method of `ArgsDemo` will have an array containing the Strings “these”, “are”, “command”, “line”, and “arguments”.

**Using Libraries.** There’s no need in the year 2017 to build everything yourself from scratch. In our course, you are allowed to and highly encouraged to use Java’s built-in libraries, as well as libraries that we provide, e.g. the Princeton standard library. You should not use libraries other than those provided or built into Java because it may render some of the assignments moot, and also our autograder won’t have access to these libraries and your code won’t work.

**Getting Help from the Internet.** You’re welcome to seek help online. However, you should always cite your sources, and you should not seek help on specific homework problems or projects. For example, googling “how convert String Java” or “how read file Java” are fine, but you should not be searching “project 2 61b java berkeley”.