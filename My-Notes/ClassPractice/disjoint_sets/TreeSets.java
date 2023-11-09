package disjoint_sets;

public class TreeSets implements DisjointSets {
    protected int[] parent;

    public TreeSets(int num) {
        parent = new int[num];
        for (int i = 0; i < num; i += 1) {
            parent[i] = -1;
        }
    }

    protected int findRoot(int index) {
        int p = index;
        while (parent[p] > 0) {
            p = parent[p];
        }
        return p;
    }


    @Override
    public void connect(int p, int q) {
        int pParent = findRoot(p);
        int qParent = findRoot(q);
        if (qParent == pParent) {
            return;
        }
        parent[qParent] = pParent;
    }

    @Override
    public boolean isConnected(int p, int q) {
        return findRoot(p) == findRoot(q);
    }
}
