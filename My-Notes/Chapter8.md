# Chapter 8

## 8.1 

**Encapsulation** 
- Module: A set of methods that work together as a whole to perform some task or set of related tasks.
- Encapsulated: A module is said to be encapsulated if its implementation is completely hidden, and it can be accessed only through a documented interface.

**Delegation vs Extension**

```java
// Extension
public class ExtensionStack<Item> extends LinkedList<Item> {
    public void push(Item x) {
        add(x);
    }
}

// Delegation
public class DelegationStack<Item> {
    private LinkedList<Item> L = new LinkedList<Item>();
    public void push(Item x) {
        L.add(x);
    }
}
```

Extension tends to be used when you know what is going on in the parent class. In other words, you know how the methods are implemented. Additionally, with extension, you are basically saying that the class you are extending from acts similarly to the one that is doing the extending. On the other hand, Delegation is when you do not want to consider your current class to be a version of the class that you are pulling the method from.

**Views**
Views are an alternative representation of an existed object. Views essentially limit the access that the user has to the underlying object. However, changes done through the views will affect the actual object.

```java
public static void main(String[] args) {
    /** Create an ArrayList. */
    List<Integer> L = new ArrayList<>();

    L.add(0); L.add(1); L.add(2);
    L.add(3); L.add(4);

    List sl = L.subList(1, 4);
    sl.add(9999);
    sl.set(0, 1000);
    System.out.println(L);
}
```

```shell
[0, 1000, 2, 3, 9999, 4]
```