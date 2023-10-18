public class AltList<X, Y> {
    private X item;
    private AltList<Y, X> next;

    public AltList(X item, AltList<Y, X> next) {
        this.item = item;
        this.next = next;
    }

    public AltList<Y, X> pairSwapped() {
        if (next.next == null) {
            return new AltList<Y, X>(next.item, new AltList<X,Y>(item, null));
        }
        return new AltList<Y, X>(next.item, new AltList<X,Y>(item, next.next.pairSwapped()));
    }

    @Override
    public String toString() {
        if (next.next == null) {
            return item.toString() + " " + next.item.toString() + "]";
        }
        return "[" + item.toString() + " " + next.item.toString() + " " + next.next.toString();
    }
    public static void main(String[] args) {
        AltList<Integer, String> list =
                new AltList<>(5,
                        new AltList<>("cat",
                                new AltList<>(10,
                                        new AltList<>("dog", null))));
        AltList<String, Integer> l = list.pairSwapped();
        System.out.println(l);
    }
}
