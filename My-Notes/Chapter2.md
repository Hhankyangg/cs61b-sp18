# Chapter 2

## 2.1

**Bits** The computer stores information as memory, and represents this information using sequences of bits, which are either 0 or 1.

**Primitives** Primitives are representations of information. There are 8 primitive types in Java: byte, short, int, long, float, double, boolean, and char. Each primitive is represented by a certain number of bits. For example, ints are 32 bit primitives, while bytes are 8 bit primitives.

**Declaring Primitives** When we declare a variable to be a primitive (i.e. `int x;`), we set aside enough memory space to hold the bits (in this case, 32). We can think of this as a box holding the bits. Java then maps the variable name to this box. Say we have a line of code `int y = x;` where `x` was defined before. Java will copy the bits inside the `x` box into the bits in the `y` box.

**Creating Objects** When we create an instance of a class using the `new` keyword, Java creates boxes of bits for each field, where the size of each box is defined by the type of each field. For example, if a Walrus object has an `int` variable and a `double` variable, then Java will allocate two boxes totaling 96 bits (32 + 64) to hold both variables. These will be set to a default value like 0. The constructor then comes in and fills in these bits to their appropriate values. The return value of the constructor will return the location in memory where the boxes live, usually an address of 64 bits. This address can then be stored in a variable with a “reference type.”

**Reference Types** If a variable is not a primitive type, then it is a reference type. When we declare object variables, we use reference type variables to store the location in memory of where an object is located. Remember this is what the constructor returns. A reference type is always a box of size 64 bits. Note that the variable does not store the entire object itself!

**Golden Rule of Equals** For primitives, the line `int y = x` copies the bits inside the `x` box into the `y` box. For reference types, we do the exact same thing. In the line `Walrus newWalrus = oldWalrus;`, we copy the 64 bit address in the `oldWalrus` box into the `newWalrus` box. So we can think of this golden rule of equals (GroE) as: when we assign a value with equals, we are just copying the bits from one memory box to another!

**Parameter Passing** Say we have a method `average(double a, double b)`. This method takes two doubles as parameters. Parameter passing also follows the GRoE, i.e. when we call this method and pass in two doubles, we copy the bits from those variables into the parameter variables.

**Array Instantiation.** Arrays are also Objects, and are also instantiated using the `new` keyword. This means declaring an array variable (i.e. `int[] x;`) will create a 64-bit reference type variable that will hold the location of this array. Of course, right now, this box contains the value null, as we have not created the array yet. The `new` keyword for arrays will create the array and return the location of this array in memory. So by saying `int[] x = new int[]{0, 1, 2, 3, 4};`, we set the location of this newly created array to the variable x. Note that the size of the array was specified when the array was created, and cannot be changed!

**IntLists.** Using references, we recursively defined the `IntList` class. `IntLists` are lists of integers that can change size (unlike arrays), and store an arbitrarily large number of integers. Writing a `size` helper method can be done with either recursion or iteration.

## 2.2

**Naked Data Structures** `IntLists` are hard to use. In order to use an `IntList` correctly, the programmer must understand and utilize recursion even for simple list related tasks.

**Adding Clothes** First, we will turn the `IntList` class into an `IntNode` class. Then, we will delete any methods in the `IntNode` class. Next, we will create a new class called `SLList`, which contains the instance variable `first`, and this variable should be of type `IntNode`. In essence, we have “wrapped” our `IntNode` with an `SLList`.

**Using SLList** As a user, to create a list, I call the constructor for `SLList`, and pass in the number I wish to fill my list with. The `SLList` constructor will then call the `IntList` constructor with that number, and set `first` to point to the `IntList` it just created.

**Improvement** Notice that when creating a list with one value, we wrote `SLList list = new SLList(1)`. We did not have to worry about passing in a null value like we did with our `IntList`. Essentially, the SLList class acts as a middleman between the list user and the naked `IntList`.

**Public vs. Private** We want users to modify our list via `SLList` methods only, and not by directly modifying `first`. We can prevent other users from doing so by setting our variable access to `private`. Writing `private IntNode first;` prevents code in other classes from accessing and modifying `first` (while the code inside the class can still do so).

**Nested Classes** We can also move classes into classes to make nested classes! You can also declare the nested classes to be private as well; this way, other classes can never use this nested class.

**Static Nested Classes** If the `IntNode` class never uses any variable or method of the `SLList` class, we can turn this class static by adding the “static” keyword.

**Recursive Helper Methods** If we want to write a recursive method in `SLList`, how would we go about doing that? After all, the `SLList` is not a naturally recursive data structure like the `IntNode`. A common idea is to write an outer method that users can call. This method calls a private helper method that takes `IntNode` as a parameter. This helper method will then perform the recursion, and return the answer back to the outer method.

**Caching** Previously, we calculated the size of our `IntList` recursively by returning 1 + the size of the rest of our list. This becomes really slow if our list becomes really big, and we repeatedly call our size method. Now that we have an `SLList`, lets simply cache the size of our list as an instance variable! Note that we could not do this before with out `IntList`.

**Empty Lists** With an`SLList`, we can now represent an empty list. We simply set `first` to `null` and `size` to `0`. However, we have introduced some bugs; namely, because `first` is now `null`, any method that tries to access a property of `first` (like `first.item`) will return a `NullPointerException`. Of course, we can fix this bug by writing code that handles this special case. But there may be many special cases. Is there a better solution?

**Sentinel Nodes** Lets make all `SLList` objects, even empty lists, the same. To do this, lets give each SLList a sentinel node, a node that is always there. Actual elements go after the sentinel node, and all of our methods should respect the idea that sentinel is always the first element in our list.

**Invariants** An invariant is a fact about a data structure that is guaranteed to be true (assuming there are no bugs in your code). This gives us a convenient checklist every time we add a feature to our data structure. Users are also guaranteed certain properties that they trust will be maintained. For example, an `SLList` with a sentinel node has at least the following invariants:

- The sentinel reference always points to a sentinel node.
- The front item (if it exists), is always at sentinel.next.item.
- The size variable is always the total number of items that have been added.

## 2.3 & 2.4

**SLList Drawbacks** `addLast()` is slow! We can’t add to the middle of our list. In addition, if our list is really large, we have to start at the front, and loop all the way to the back of our list before adding our element.

**A Naive Solution** Recall that we cached the size of our list as an instance variable of `SLList`. What if we cached the `last` element in our list as well? All of a sudden, `addLast()` is fast again; we access the last element immediately, then add our element in. But `removeLast()` is still slow. In `removeLast()`, we have to know what our second-to-last element is, so we can point our cached `last` variable to it. We could then cache a `second-to-last` variable, but now if I ever want to remove the second-to-last element, I need to know where our third-to-last element is. How to solve this problem?

**DLList** The solution is to give each `IntNode` a `prev` pointer, pointing to the previous item. This creates a doubly-linked list, or `DLList`. With this modification, adding and removing from the front and back of our list becomes fast (although adding/removing from the middle remains slow).

**Incorporating the Sentinel** Recall that we added a sentinel node to our `SLList`. For `DLList`, we can either have two sentinels (one for the front, and one for the back), or we can use a circular sentinel. A `DLList` using a circular sentinel has one sentinel. The sentinel points to the first element of the list with `next` and the last element of the list with `prev`. In addition, the last element of the list’s `next` points to the sentinel and the first element of the list’s `prev` points to the sentinel. For an empty list, the sentinel points to itself in both directions.

**Generic DLList** How can we modify our `DLList` so that it can be a list of whatever objects we choose? Recall that our class definition looks like this:

```java
public class DLList { ... }
```

We will change this to

```java
public class DLList<T> { ... }
```

where `T` is a placeholder object type. Notice the angle bracket syntax. Also note that we don’t have to use `T`; any variable name is fine. In our `DLList`, our item is now of type `T`, and our methods now take `T` instances as parameters. We can also rename our `IntNode` class to `TNode` for accuracy.

**Using Generic DLList** Recall that to create a `DLList`, we typed:

```java
DLList list = new DLList(10);
```

If we now want to create a `DLList` holding `String` objects, then we must say:

```java
DLList<String> list = new DLList<>("bone");
```

On list creation, the compiler replaces all instances of `T` with `String`! We will cover generic typing in more detail in later lectures.

**Arrays** Recall that variables are just boxes of bits. For example, `int x;` gives us a memory box of 32 bits. Arrays are a special object which consists of a numbered sequence of memory boxes! To get the ith item of array `A`, use `A[i]`. The length of an array cannot change, and all the elements of the array must be of the same type (this is different from a Python list). The boxes are zero-indexed, meaning that for a list with N elements, the first element is at `A[0]` and the last element is at `A[N - 1]`. Unlike regular classes, **arrays do not have methods!** Arrays do have a `length` variable though.

**Instantiating Arrays** There are three valid notations for creating arrays. The first way specifies the size of the array, and fills the array with default values:

```java
int[] y = new int[3];
```

The second and third ways fill up the array with specific values.

```java
int[] x = new int[]{1, 2, 3, 4, 5};
int[] w = {1, 2, 3, 4, 5};
```

We can set a value in an array by using array indexing. For example, we can say `A[3] = 4;`. This will access the **fourth** element of array `A` and sets the value at that box to 4.

**Arraycopy** In order to make a copy of an array, we can use `System.arraycopy`. It takes 5 parameters; the syntax is hard to memorize, so we suggest using various references online such as [this](https://www.tutorialspoint.com/java/lang/system_arraycopy.htm).

**2D Arrays** We can declare multidimensional arrays. For 2D integer arrays, we use the syntax:

```java
int[][] array = new int[4][];
```

This creates an array that holds integer arrays. Note that we have to manually create the inner arrays like follows:

```java
array[0] = new int[]{0, 1, 2, 3};
```

Java can also create multidemensional arrays with the inner arrays created automatically. To do this, use the syntax:

```java
int[][] array = new int[4][4];
```

We can also use the notation:

```java
int[][] array = new int[][]{{1}, {1, 2}, {1, 2, 3}}
```

to get arrays with specific values.

**Arrays vs. Classes**

- Both are used to organize a bunch of memory.
- Both have a fixed number of “boxes”.
- Arrays are accessed via square bracket notation. Classes are accessed via dot notation.
- Elements in the array must be all be the same type. Elements in a class may be of different types.
- Array indices are computed at runtime. We cannot compute class member variable names.

## 2.5

**Lists vs. Arrays** Our `DLList` has a drawback. Getting the ith item is slow; we have to scan through each item in the list, starting from the beginning or the end, until we reach the ith item. For an array named `A`, however, we can quickly access the ith item using bracket notation, `A[i]`. Thus, our goal is to implement a list with an array.

**AList** The `AList` will have the same API has our `DLList`, meaning it will have the same methods as `DLList` (`addLast()`, `getLast()`, `removeLast()`, and `get(int i)`). The `AList` will also have a `size` variable that tracks its size.

**AList Invariants** There are a few invariants for our `AList`.

- `addLast`: The next item we want to add, will go into position `size`.
- `getLast`: The item we want to return is in position `size - 1`.
- `size`: The number of items in the list should be `size`.

**Implementing AList** Each `AList` has an `int[]` called `items`.

- For `addLast`, we place our item in `items[size]`.
- For `getLast`, we simply return `items[size - 1]`.
- For `removeLast`, we simply decrement `size` (we don’t need to change `items`). Thus, if `addLast` is called next, it simply overwrites the old value, because size was decremented. **However, it is good practice to null out objects when they are removed, as this will save memory.** Notice how closely these methods were related to the invariants.

**Abstraction** One key idea of this course is that the implementation details can be hidden away from the users. For example, a user may want to use a list, but we, as implementers, can give them any implementation of a list, as long as it meets their specifications. A user should have no knowledge of the inner workings of our list.

**Array Resizing** When the array gets too full, we can resize the array. However, we have learned that array size cannot change. The solution is, instead, to create a new array of a larger size, then copy our old array values to the new array. Now, we have all of our old values, but we have more space to add items.

**Resizing Speed** In the lecture video, we started off resizing the array by one more each time we hit our array size limit. This turns out to be extremely slow, because copying the array over to the new array means we have to perform the copy operation for each item. The worst part is, since we only resized by one extra box, if we choose to add another item, we have to do this again each time we add to the array.

**Improving Resize Performance** Instead of adding by an extra box, we can instead create a new array with `size * FACTOR` items, where `FACTOR` could be any number, like 2 for example. We will discuss why this is fast later in the course.

**Downsizing Array Size** What happens if we have a 1 million length array, but we remove 990,000 elements of the array? Well, similarly, we can downsize our array by creating an array of half the size, if we reach 250,000 elements, for example. Again, we will discuss this more rigorously later in the course.

**Aside: Breaking Code Up** Sometimes, we write large methods that do multiple things. A better way is to break our large methods up into many smaller methods. One advantage of this is that we can test our code in parts.

**Generic AList** Last time, we discussed how to make a generic `DLList`. We can do something similar for `AList`. But we find that we error out on array creation. Our problem is that generic arrays are not allowed in Java. Instead, we will change the line:

```java
items = new Item[100];
```

to:

```java
items = (Item[]) new Object[100];
```

This is called a cast, and we will learn about it in the future.