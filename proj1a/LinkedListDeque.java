public class LinkedListDeque<Genshin> {
    private class TriNode {
        public TriNode prev;
        public Genshin item;
        public TriNode next;

        public TriNode(TriNode p, Genshin x, TriNode n) {
            prev = p;
            item = x;
            next = n;
        }

        public Genshin getHelper(int index) {
            if (index == 0) {
                return item;
            }
            return this.next.getHelper(index-1);
        }
    }

    public TriNode sentinel;
    public int size;

    /** Creates an empty linked list deque */
    public LinkedListDeque() {
        sentinel = new TriNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /** Creates a linked list with only one element: item */
    public LinkedListDeque(Genshin item) {
        sentinel = new TriNode(null, null, null);
        sentinel.next = new TriNode(sentinel, item, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    /** Adds an item of type Genshin to the front of the deque. */
    public void addFirst(Genshin item) {
        TriNode new_item = new TriNode(null, item, null);
        sentinel.next.prev = new_item;
        new_item.next = sentinel.next;
        sentinel.next = new_item;
        new_item.prev = sentinel;
        size += 1;
    }

    /** Adds an item of type Genshin to the back of the deque. */
    public void addLast(Genshin item) {
        TriNode new_item = new TriNode(null, item, null);
        sentinel.prev.next = new_item;
        new_item.prev = sentinel.prev;
        sentinel.prev = new_item;
        new_item.next = sentinel;
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

    /** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public Genshin removeFirst() {
        TriNode first_ptr = sentinel.next;
        if (first_ptr == sentinel) {
            return null;
        }
        first_ptr.next.prev = sentinel;
        sentinel.next = first_ptr.next;
        size -= 1;
        return first_ptr.item;
    }

    /** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    public Genshin removeLast() {
        TriNode last_ptr = sentinel.prev;
        if (last_ptr == sentinel) {
            return null;
        }
        last_ptr.prev.next = sentinel;
        sentinel.prev = last_ptr.prev;
        size -= 1;
        return last_ptr.item;
    }

    /** Gets the item at the given index. */
    public Genshin get(int index) {
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
    public Genshin getRecursive(int index) {
        if (index >= this.size() || index < 0) {
            return null;
        }
        return this.sentinel.next.getHelper(index);
    }

}