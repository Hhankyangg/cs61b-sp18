package disjoint_sets;

public class BalancedTreeSets extends TreeSets {

    public BalancedTreeSets(int num) {
        super(num);
    }

    @Override
    public void connect(int p, int q) {
        int pParent = findRoot(p);
        int qParent = findRoot(q);
        if (qParent == pParent) {
            return;
        }

        int pSize = -parent[pParent];
        int qSize = -parent[qParent];
        int negativeSize = -(pSize + qSize);
        if (qSize < pSize) {
            parent[qParent] = pParent;
            parent[pParent] = negativeSize;
        } else {
            parent[pParent] = qParent;
            parent[qParent] = negativeSize;
        }
    }
}
