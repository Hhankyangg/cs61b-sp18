package disjoint_sets;

public class BalancedTreeSetsWithPathCom extends BalancedTreeSets {
    public BalancedTreeSetsWithPathCom(int num) {
        super(num);
    }

    @Override
    protected int findRoot(int index) {
        int p = index;
        while (parent[p] > 0) {
            p = parent[p];
        }
        while (index != p) {
            parent[index] = p;
            index = parent[index];
        }
        return p;
    }
}
