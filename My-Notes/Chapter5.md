# Chapter 5

**Autoboxing and Unboxing** Autoboxing is the Java’s automatic conversion of between wrappers (Integer) to primitives (int). In most cases, if Java expects a wrapper class and gets a primitive instead, it autoboxes the primitive. Alternatively, if Java expects a primitive and gets a wrapper, it unboxes the wrapper.

**Drawbacks of Autoboxing and Unboxing** Though you can almost always interchange there are some things to the process.

- Autoboxing and unboxing can cause your program to slow down if you use it too much
- Wrappers require a lot more memory than primitives.
- If an array expects a wrapper and gets a primitive or vice versa, it will error. As in you cannot pass ints into an array whose type is Integer[] or the other way around.

**Immutability** Immutable data types are types that cannot change. To make sure that a variable does not change, use the `final` keyword. Once a variable is declared final, it can never change after initial assignment. An important note is that if an address is declared final it means that the address can’t change- it says nothing about its contents. For example the below syntax is valid:

```
final int[] arr = new int[1];
arr[0] = 1;
arr[0] = 3
```

But this one is not:

```
final int[] arr = new int[3];
arr = new int[4];
```

Because you are changing the address of the actual array the variable is pointing to.

**Generic Classes** To make it so that a class can have variables or methods that have a generic type, use the following syntax:

```
public class ArrayMap<K,V>{...}
```

Then when instantiating the class pass in some “real”, or known, types to the class

**Generic Methods** You can define a method that takes in generic parameters with the following syntax.

```
public static <Chow, Der> Chow get(ArrayMap<Chow, Der)> am, Chow key){...}
```

From left to right we have the declaration of the generics being used in this function then we have the return type. Finally, we have our arguments, the first being an ArrayMap with 2 generics and the latter being a generic type object.

To use a generic method use the following syntax

```
ArrayMap<Integer, String> ismap = new ArrayMap<Integer, String>();
System.out.println(MapHelper.get(ismap, 5));
```

**Comparing Objects with Generic Methods** Now we have the ability to put vague Objects into methods. However this lends itself to a bit of a problem- how do we compare these Objects? We cannot simply use ‘>’ because we aren’t sure if our object is a numerical primitive. We can get around this by using `.compareTo(Object O)`.

Now we have a new problem. How do we know if our generic has a compareTo method. To get around this, we can make sure that our generic must be a type of our `OurComparable`. How do we do this? Well take a gander below and check it out.

```
public static <K extends OurComparable, V> K maxKey(ArrayMap<K, V> am) {
  ...
  if (k.compareTo(largest) > 0) {
    ...
}
```

Basically what’s happening is that, in the header, we ensure that K needs to extend `OurComparable`.