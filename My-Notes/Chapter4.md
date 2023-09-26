# Chapter 4

## 4.1

**Method Overloading** In Java, methods in a class can have the same name, but different parameters. For example, a `Math` class can have an `add(int a, int b)` method and an `add(float a, float b)` method as well. The Java compiler is smart enough to choose the correct method depending on the parameters that you pass in. Methods with the same name but different parameters are said to be overloaded.

**Making Code General** Consider a `largestNumber` method that only takes an AList as a parameter. The drawback is that the logic for `largestNumber` is the same regardless of if we take an `AList` or `SLList`. We just operate on a different type of list. If we use our previous idea of method overriding, we result in a very long Java file with many similar methods. This code is hard to maintain; if we fix a bug in one method, we have to duplicate this fix manually to all the other methods.

The solution to the above problem is to define a new reference type that represents both `AList` and `SLList`. We will call it a `List`. Next, we specify an “is-a” relationship: An `AList` is a `List`. We do the same for `SLList`. Let’s formalize this into code.

**Interfaces** We will use the keyword `interface` instead of `class` to create our `List`. More explicitly, we write:

```java
public interface List<Item> { ... }
```

The key idea is that interfaces specify what this `List` can do, not how to do it. Since all lists have a `get` method, we add the following method signature to the interface class:

```java
public Item get(int i);
```

Notice we did not define this method. We simply stated that this method should exist as long as we are working with a `List` interface.

Now, we want to specify that an `AList` is a `List`. We will change our class declaration of `AList` to:

```java
public AList<Item> implements List<Item> { ... }
```

We can do the same for `SLList`. Now, going back to our `largestNumber` method, instead of creating one method for each type of list, we can simply create one method that takes in a `List`. As long as our actual object implements the `List` interface, then this method will work properly!

**Overriding** For each method in `AList` that we also defined in `List`, we will add an @Override right above the method signature. As an example:

```java
@Override
public Item get(int i) { ... }
```

This is not necessary, but is good style and thus we will require it. Also, it allows us to check against typos. If we mistype our method name, the compiler will prevent our compilation if we have the @Override tag.

**Interface Inheritance** Formally, we say that subclasses inherit from the superclass. Interfaces contain all the method signatures, and each subclass must implement every single signature; think of it as a contract. In addition, relationships can span multiple generations. For example, C can inherit from B, which can inherit from A.

**Default Methods** Interfaces can have default methods. We define this via:

```java
default public void method() { ... }
```

We can actually implement these methods inside the interface. Note that there are no instance variables to use, but we can freely use the methods that are defined in the interface, without worrying about the implementation. Default methods should work for any type of object that implements the interface! The subclasses do not have to re-implement the default method anywhere; they can simply call it for free. However, we can still override default methods, and re-define the method in our subclass.

**Static vs. Dynamic Type** Every variable in Java has a static type. This is the type specified when the variable is declared, and is checked at compile time. Every variable also has a dynamic type; this type is specified when the variable is instantiated, and is checked at runtime. As an example:

```java
Thing a;
a = new Fox();
```

Here, `Thing` is the static type, and `Fox` is the dynamic type. This is fine because all foxes are things. We can also do:

```java
Animal b = a;
```

This is fine, because all foxes are animals too. We can do:

```java
Fox c = b;
```

This is fine, because `b` points to a `Fox`. Finally, we can do:

```java
a = new Squid()
```

This is fine, because the static type of `a` is a `Thing`, and `Squid` is a thing.

**Dynamic Method Selection** The rule is, if we have a static type `X`, and a dynamic type `Y`, then if `Y` overrides the method from `X`, then on runtime, we use the method in `Y` instead. Student often confuse overloading and overriding.

**Overloading and Dynamic Method Selection** Dynamic method selection plays no role when it comes to overloaded methods. Consider the following piece of code, where `Fox extends Animal`.

```java
1  Fox f = new Fox();
2  Animal a = f;
3  define(f);
4  define(a);
```

Let’s assume we have the following overloaded methods in the same class:

```java
public static void define(Fox f) { ... }
public static void define(Animal a) { ... }
```

Line 3 will execute `define(Fox f)`, while line 4 will execute `define(Animal a)`. Dynamic method selection only applies when we have overridden methods. There is no overriding here, and therefore dynamic method selection does not apply.

## 4.2

**The Interface and implements.** Earlier we went classes and interfaces and we realized that when writing classes, we can sometimes write a lot of redundant code. This leads us to Inheritance, the idea that some object does not need to redefine all of its qualities of its parent. We can inherit from both interfaces and classes and the syntax is slightly different. For classes to inherit the qualities of an interface the syntax is as follows (where SLList is a class and List61B is an interface):

```java
SLList<Blorp> implements List61B<Blorp>
```

Similarly, the way for a class to implement the qualities of another class the syntax is as follows:

```java
Class_Name extends Class_Name
```

**Usage of Inheritance.** Say we wanted to make a special type of `SLList` called `RotatingSLList`. `RotatingSLList` should be able to do everyhthing that SLList can; however, it should also be able to rotate to the right. How can we do this? Well this is just an application of Inheritance! Doing the following will allow for RotatingSLList to have all the qualities of SLList as well as its own method `rotateRight`.

```java
public class RotatingSLList<Blorp> extends SLList<Blorp>{
  public void rotateRight() {...}
}
```

**What is Inherited?** We have a powerful tool in Inheritance now; however, we will define a few rules. For now, we will say that we can inherit:

- instance and static variables
- all methods
- all nested classes This changes a little bit with the introduction of private variables but don’t worry about that right now. The one item that is not inherited is a class’s constructor.

**The Special Case of the Constructor?** Even though constructor’s are not inherited, we still use them. We can call the constructor explicitly by using the keyword `super()`. At the start of every constructor, there is already an implicit call to its super class`s constructor. As a result

```java
public VengefulSLList() {
  deletedItems = new SLList<Item>();
}
```

is equivalent to

```java
public VengefulSLList() {
  super();
  deletedItems = new SLList<Item>();
}
```

However, constructor`s with arguments are not implicitly called. This means that.

```java
public VengefulSLList() {
    super(x);
    deletedItems = new SLList<Item>();
  }
```

is not equivalent to

```java
public VengefulSLList() {
    deletedItems = new SLList<Item>();
  }
```

This is because only the empty argument `super()` is called.

**Is A.** When a class inherits from another, we know that it must have all the qualities of it. This means that `VengefulSLList` is a `SLList` because it has all the qualities of an `SLList`- it just has a few additional ones too.

Every single class is a descendent on the Object class, meaning they are all Objects.

**Abstraction** As you’ll learn later in this class, programs can get a tad confusing when they are really large. A way to make programs easier to handle is to use abstraction. Basically abstraction is hiding components of programs that people do not need to see. The user of the hidden methods should be able to use them without knowing how they work.

An intuitive way to realize the motivation of abstraction is to look at yourself. You are a human (unless some robot is looking at this in which case I am sorry for offending you) and humans can eat food and convert it to energy. You do not need to know how you convert food to energy you just know that it works. In this case think of your conversion of food to energy as a method and the input is food and the output is energy.

**Casting** In Java, every object has a static type (defined at compile-time) and a dynamic type (defined at run-time). Our code may rely on the fact that some variable may be a more specific type than the static type. For example if we had the below definitions:

```java
Poodle frank  = new Poodle("Frank", 5);
Poodle frankJr = new Poodle("Frank Jr.", 15);
```

This statement would be valid

```java
Dog largerDog = maxDog(frank, frankJr);
```

But this one would not be

```java
Poodle largerPoodle = maxDog(frank, frankJr);
```

The reason the former statement is valid is because the compilers knows for a fact that anything that is returned from a `maxDog` function call is a `Dog`. However, in the latter case, the compiler does not know for a fact that the return value of `maxDog` would result in a `Poodle` even though both `Dog` arguments are `Poodle`s.

Instead of being happy with just having a generic `Dog`, we can be a bit risky and use a technique called casting. Casting allows us to force the static type of a variable, basically tricking the compiler into letting us force the static type of am expression. To make `largerPoodle` into a static type `Poodle` we will use the following:

```java
Poodle largerPoodle = (Poodle) maxDog(frank, frankJr);
```

Note that we are not changing the actual dynamic type of maxDog- we are just telling the compiler what is coming out of maxDog will be a `Poodle`. This means that any reference to `largerPoodle` will have a static type of `Poodle` associated with it.

Casting, while powerful is also quite dangerous. You need to ensure that what you are casting to can and will actually happen. There are a few rules that can be used:

- You can always cast up (to a more generic version of a class) without fear of ruining anything because we know the more specific version is a version of the generic class. For example you can always cast a Poodle to a Dog because all Poodles are Dog’s.
- You can also cast down (to a more specific version of a class) with caution as you need to make sure that, during runtime, nothing is passed in that violates your cast. For example, sometimes Dog’s are Poodle’s but not always.
- Finally, you cannot ever cast to a class that is above or below the class being cast. For an example, you cannot cast a Dog to a Monkey because a Monkey is not in the direct lineage of a Dog- it is a child of animal so a bit more distant.

## 4.3

**Review: Typing Rules**

- Compiler allows the memory box to hold any subtype.
- Compiler allows calls based on static type.
- Overridden non-static methods are selected at runtime based on dynamic type.
- For overloaded methods, the method is selected at compile time.

**Subtype Polymorphism** Consider a variable of static type `Deque`. The behavior of calling `deque.method()` depends on the dynamic type. Thus, we could have many subclasses the implement the `Deque` interface, all of which will be able to call `deque.method()`.

**Subtype Polymorphism Example** Suppose we want to write a function `max()` that returns the max of any array regardless of type. If we write a method `max(Object[] items)`, where we use the ‘>’ operator to compare each element in the array, this will not work! Why is this the case?

Well, this makes the assumption that all objects can be compared. But some objects cannot! Alternatively, we could write a `max()` function inside the Dog class, but now we have to write a `max()` function for each class that we want to compare! Remember, our goal is to write a “one true max method” that works for all comparable objects.

**Solution: OurComparable Interface** The solution is to create an interface that contains a `compareTo(Object)` method; let’s call this interface `OurComparable`. Now, our `max()` method can take a `OurComparable[]` parameter, and since we guarantee that any object which extends the interface has all the methods inside the interface, we guarantee that we will always be able to call a `compareTo` method, and that this method will correctly return some ordering of the objects.

Now, we can specify a “one true max method”. Of course, any object that needs to be compared must implement the `compareTo` method. However, instead of re-implementing the `max` logic in every class, we only need to implement the logic for picking the ordering of the objects, given two objects.

**Even Better: Java’s In-Built Comparable** Java has an in-built `Comparable` interface that uses generics to avoid any weird casting issues. Plus, Comparable already works for things like `Integer`, `Character`, and `String`; moreover, these objects have already implemented a `max`, `min`, etc. method for you. Thus you do not need to re-do work that’s already been done!

**Comparators** The term “Natural Order” is used to refer to the ordering implied by a `Comparable`’s `compareTo` method. However, what if we want to order our `Dog` objects by something other than `size`? We will instead pass in a `Comparator<T>` interface, which demands a `compare()` method. We can then implement the `compare()` method anyway we want to achieve our ordering.

## 4.4

**Abstract Data Types** Previously, we went over interfaces which, in a traditional sense (disregarding default methods which will be defined a bit lower), requires certain methods to be implemented in a class if it is said a type of that interface. Abstract Data Types follow this philosophy, and are defined to be some sort of Object that is defined by some set of operations rather than the implementation of these operations.

**Interfaces** There are 2 types of inheritance that we have gone over in previous lectures:

- Interface inheritance: What (the class can do).
- Implementation inheritance: How (the class does it).

**Default Methods** The way we have dealt with interfaces, there is no content in them. We only define a certain set of operations that need to be fulfilled by anything that implements the interface. However, we can create `default` methods that take the following form:

```java
default void methodName(){...}
```

Normal interface methods have no body and only state what needs to be defined. Default methods on the other hand provide how a method by providing a method body.

Here are some bullet points about interfaces

- variables can exist in interfaces but they are public static final.
- classes can extend more than 1 interface.
- methods are public unless stated otherwise
- interfaces cannot be instantiated.

**Abstract Classes** Abstract classes can be thought of as a hybrid of a normal class and an interface. Abstract classes are like interfaces in that they cannot be instantiated. All methods in an Abstract class are like normal methods in classes unless they have word `abstract` in front of them. If that is the case then they are treated like normal methods in interfaces and do not need to have a body and can instead have an implementation in whatever class extends them. A very important difference between abstract classes and interfaces is that a class can only extend one abstract class but can implement more than one interface.

**Packages** A namespace is a region that can be used to organize code. Packages are a specific type of namespace that is used to organize classes and interfaces. To use a class from a different package use the following syntax:

```java
package_name.classname.subclassname a = new package_name.classname.subclassname();
```

To make your life easier while typing out code, you can simply import the class following the syntax below:

```java
import package_name.classname.subclassname;
```

Replace the subclassname with a * if you want to important everything from the class.
