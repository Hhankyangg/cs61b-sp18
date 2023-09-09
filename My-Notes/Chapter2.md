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