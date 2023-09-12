public class ArrayDeque<T> {
    private static final int INIT = 8;
    private T[] items;
    private int size;
    private int nextFirst, nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[INIT];
        size = 0;
        nextFirst = 0;
        nextLast = INIT - 1;
    }

    private void resizeLarger(int goalSize) {
        int halfSize = goalSize / 2;
        T[] a = (T[]) new Object[goalSize];
        System.arraycopy(items, 0, a, 0, nextFirst);
        System.arraycopy(items, nextFirst, a, halfSize + nextFirst, halfSize - nextFirst);
        items = a;
        nextLast = halfSize + nextFirst - 1;
    }

    public void addFirst(T item) {
        items[nextFirst] = item;
        nextFirst += 1;
        if (nextFirst > items.length - 1) {
            nextFirst = 0;
        }

        size += 1;

        if (size == items.length) {
            this.resizeLarger(size * 2);
        }
    }

    public void addLast(T item) {
        items[nextLast] = item;
        nextLast -= 1;
        if (nextLast < 0) {
            nextLast = items.length - 1;
        }

        size += 1;

        if (size == items.length) {
            this.resizeLarger(size * 2);
        }
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int totalSize = items.length;
        int ptr;

        if (size == 0) {
            return;
        }

        if (nextFirst < nextLast) {   // 两端分布
            if (nextFirst > 0) {  // 前面有东西
                ptr = nextFirst - 1;
                while (ptr > 0) {
                    System.out.print(items[ptr] + " ");
                    ptr -= 1;
                }
                if (nextLast == totalSize - 1) {  // 后面没东西
                    System.out.println(items[ptr]);
                } else if (nextLast < totalSize - 1) { // 后面有东西
                    System.out.print(items[ptr] + " ");
                }
            }
            ptr = totalSize - 1;
            if (nextLast < totalSize - 1) {  //后面有东西
                while (ptr > nextLast + 1) {
                    System.out.print(items[ptr] + " ");
                    ptr -= 1;
                }
                System.out.println(items[ptr]);
            }
        } else {                         // 一段分布
            ptr = nextFirst - 1;
            while (ptr > nextLast + 1) {
                System.out.print(items[ptr] + " ");
                ptr -= 1;
            }
            System.out.println(items[ptr]);
        }
    }

    private void resizeSmaller() {
        int goalSize = items.length / 2;
        T[] a = (T[]) new Object[goalSize];
        if (nextLast > nextFirst) {   // 两端分布
            System.arraycopy(items, 0, a, 0, nextFirst);
            System.arraycopy(items, nextLast + 1, a, goalSize - size + nextFirst, size - nextFirst);
            items = a;
            nextLast = goalSize - size + nextFirst - 1;
        } else {                         // 一段分布
            System.arraycopy(items, nextLast + 1, a, 0, size);
            items = a;
            nextFirst = size;
            nextLast = goalSize - 1;
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst -= 1;
        }
        T a = items[nextFirst];
        items[nextFirst] = null;

        size -= 1;
        if ((double) size / items.length <= 0.25) {
            this.resizeSmaller();
        }
        return a;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }

        if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }
        T a = items[nextLast];
        items[nextLast] = null;

        size -= 1;
        if ((double) size / items.length <= 0.25) {
            this.resizeSmaller();
        }
        return a;
    }

    public T get(int index) {
        if (index < size) {
            if (nextFirst < nextLast) {
                int numLeft = nextFirst;
                if (index < numLeft) {
                    return items[numLeft - 1 - index];
                } else {
                    int totalSize = items.length;
                    return items[totalSize - 1 - (index - numLeft)];
                }
            }
            return items[nextLast + size - index];
        }
        return null;
    }
}
