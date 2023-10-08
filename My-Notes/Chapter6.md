# Chapter 6

## Throwing Exceptions



## Iteration

```java
Set<String> s = new HashSet<>();
...
for (String city : s) {
    ...
}
```

等价于

```java
Set<String> s = new HashSet<>();
...
Iterator<String> seer = s.iterator();
while (seer.hasNext()) {
    String city = seer.next();
    ...
}
```

### 让自己的数据类型也可以被这样遍历:

```java
public class ArraySet<T> implements Iterable {
    @Override	
    public Iterator<T> iterator() {
        return new ArraySetIterator();
    }
    
    private class ArraySetIterator implements Iterator<T> {
        private int pos;

        public ArraySetIterator() {
            pos = 0;
        }

        @Override
        public boolean hasNext() {
            return pos < size;
        }

        @Override
        public T next() {
            T thisItem = array[pos];
            pos += 1;
            return thisItem;
        }
    }
}
```