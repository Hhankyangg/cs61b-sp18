public class ArrayDeque<Genshin> {
    private static final int INIT = 8;
    private Genshin[] items;
    private int size;
    private int next_first, next_last;

    public ArrayDeque() {
        items = (Genshin[])new Object[INIT];
        size = 0;
        next_first = 0;
        next_last = INIT - 1;
    }

    private void resize_larger(int goal_size) {
        int half_size = goal_size / 2;
        Genshin[] a = (Genshin[]) new Object[goal_size];
            System.arraycopy(items, 0, a, 0, next_first);
            System.arraycopy(items, next_first, a, half_size + next_first, half_size - next_first);
            items = a;
            next_last = half_size + next_first - 1;
    }

    public void addFirst(Genshin item) {
        items[next_first] = item;
        next_first += 1;
        if (next_first > items.length - 1) {
            next_first = 0;
        }

        size += 1;

        if (size == items.length) {
            this.resize_larger(size * 2);
        }
    }

    public void addLast(Genshin item) {
        items[next_last] = item;
        next_last -= 1;
        if (next_last < 0) {
            next_last = items.length - 1;
        }

        size += 1;

        if (size == items.length) {
            this.resize_larger(size * 2);
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
        int total_size = items.length;
        int ptr;

        if (size == 0) {
            return;
        }

        if (next_first < next_last) {   // 两端分布
            if (next_first > 0) {  // 前面有东西
                ptr = next_first - 1;
                while (ptr > 0) {
                    System.out.print(items[ptr] + " ");
                    ptr -= 1;
                }
                if (next_last == total_size - 1) {  // 后面没东西
                    System.out.println(items[ptr]);
                }else if (next_last < total_size - 1) { // 后面有东西
                    System.out.print(items[ptr] + " ");
                }
            }
            ptr = total_size - 1;
            if (next_last < total_size - 1) {  //后面有东西
                while (ptr > next_last + 1) {
                    System.out.print(items[ptr] + " ");
                    ptr -= 1;
                }
                System.out.println(items[ptr]);
            }
        }else {                         // 一段分布
            ptr = next_first - 1;
            while (ptr > next_last + 1) {
                System.out.print(items[ptr] + " ");
                ptr -= 1;
            }
            System.out.println(items[ptr]);
        }
    }

    private void resize_smaller() {
        int goal_size = items.length / 2;
        Genshin[] a = (Genshin[]) new Object[goal_size];
        if (next_last > next_first) {   // 两端分布
            System.arraycopy(items, 0, a, 0, next_first);
            System.arraycopy(items, next_last + 1, a, goal_size - size + next_first, size - next_first);
            items = a;
            next_last = goal_size - size + next_first - 1;
        }else {                         // 一段分布
            System.arraycopy(items, next_last + 1, a, 0, size);
            items = a;
            next_first = size;
            next_last = goal_size - 1;
        }
    }

    public Genshin removeFirst() {
        if (size == 0) {
            return null;
        }

        if (next_first == 0) {
            next_first = items.length - 1;
        }else {
            next_first -= 1;
        }
        Genshin a = items[next_first];
        items[next_first] = null;

        size -= 1;
        if ((double)size/ items.length <= 0.25) {
            this.resize_smaller();
        }
        return a;
    }

    public Genshin removeLast() {
        if (size == 0) {
            return null;
        }

        if (next_last == items.length - 1) {
            next_last = 0;
        }else {
            next_last += 1;
        }
        Genshin a = items[next_last];
        items[next_last] = null;

        size -= 1;
        if ((double)size/ items.length <= 0.25) {
            this.resize_smaller();
        }
        return a;
    }

    public Genshin get(int index) {
        if (next_first < next_last) {
            int num_left = next_first;
            int num_right = size - num_left;
            if (index < num_left) {
                return items[num_left - 1 - index];
            }else {
                int total_size = items.length;
                return items[total_size - 1 - (index - num_left)];
            }
        }
        return items[next_last + size - index];
    }
}
