public class DMSList {
    public IntNode sentinel;
    public DMSList() {
        sentinel = new IntNode(-1000, new LastNode());
    }
    public class IntNode {
        public int item;
        public IntNode next;
        public IntNode(int i, IntNode h) {
            item = i;
            next = h;
        }
        public int max() {
            return Math.max(item, next.max());
        }
    }
    public class LastNode extends IntNode {
        public LastNode() {
            super(0, null);
        }
        @Override
        public int max() {
            return 0;
        }

    }
    public int max() {
        return sentinel.next.max();
    }
}