package disjoint_sets;

public class ArraySets implements DisjointSets{
    protected final int[] id;

    public ArraySets(int num) {
        id = new int[num];
        for (int i = 0; i < num; i += 1) {
            id[i] = i;
        }
    }

    @Override
    public void connect(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        for (int i= 0; i< id.length; i += 1) {
            if (id[i] == qid) {
                id[i] = pid;
            }
        }
    }

    @Override
    public boolean isConnected(int p, int q) {
        return id[p] == id[q];
    }
}
