import java.util.Iterator;
import java.util.NoSuchElementException;

public class KthIntList implements Iterator<Integer> {
    public int k;
    private IntList curList;
    private boolean hasNext;

    public KthIntList(IntList I, int k) {
        this.k = k;
        curList = I;
        hasNext = true;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public Integer next() {
        IntList now = curList;
        if (now == null) {
            throw new NoSuchElementException();
        }

        for (int i = 0; i < k; i += 1) {
            if (curList.next == null) {
                this.hasNext = false;
                break;
            } else  {
                curList = curList.next;
            }
        }
        return now.item;
    }

    public static void main(String[] args) {
        IntList list = new IntList(0, new IntList(1, new IntList(2, new IntList(3, new IntList(4, new IntList(5, new IntList(6, new IntList(7, null))))))));
        for (Iterator<Integer> p = new KthIntList(list, 2); p.hasNext(); ) {
            System.out.println(p.next());
        }
    }
}
