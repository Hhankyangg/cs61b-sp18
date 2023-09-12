public class LinkedListDeque<T> {
    private class TriNode {
        private TriNode prev;
        private T item;
        private TriNode next;

        private TriNode(TriNode p, T x, TriNode n) {
            prev = p;
            item = x;
            next = n;
        }

        private T getHelper(int index) {
            if (index == 0) {
                return item;
            }
            return this.next.getHelper(index - 1);
        }
    }

    private TriNode sentinel;
    private int size;

    /** Creates an empty linked list deque */
    public LinkedListDeque() {
        sentinel = new TriNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        TriNode newItem = new TriNode(null, item, null);
        sentinel.next.prev = newItem;
        newItem.next = sentinel.next;
        sentinel.next = newItem;
        newItem.prev = sentinel;
        size += 1;
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        TriNode newItem = new TriNode(null, item, null);
        sentinel.prev.next = newItem;
        newItem.prev = sentinel.prev;
        sentinel.prev = newItem;
        newItem.next = sentinel;
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space. */
    public void printDeque() {
        if (this.size() == 0) {
            return;
        }
        TriNode ptr = sentinel.next;
        while (ptr.next != sentinel) {
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }
        System.out.println(ptr.item);
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null. */
    public T removeFirst() {
        TriNode firstPtr = sentinel.next;
        if (firstPtr == sentinel) {
            return null;
        }
        firstPtr.next.prev = sentinel;
        sentinel.next = firstPtr.next;
        size -= 1;
        return firstPtr.item;
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    public T removeLast() {
        TriNode lastPtr = sentinel.prev;
        if (lastPtr == sentinel) {
            return null;
        }
        lastPtr.prev.next = sentinel;
        sentinel.prev = lastPtr.prev;
        size -= 1;
        return lastPtr.item;
    }

    /** Gets the item at the given index. */
    public T get(int index) {
        TriNode ptr = sentinel.next;
        if (index >= this.size() || index < 0) {
            return null;
        }
        while (index != 0) {
            index -= 1;
            ptr = ptr.next;
        }
        return ptr.item;
    }

    /** Same as get, but uses recursion. */
    public T getRecursive(int index) {
        if (index >= this.size() || index < 0) {
            return null;
        }
        return this.sentinel.next.getHelper(index);
    }
}
