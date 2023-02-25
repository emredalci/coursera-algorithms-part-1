package percolation;

public class WeightedQuickUnion extends DynamicConnectivity{

    private int[] size;

    public WeightedQuickUnion(int N) {
        super(N);
        size = new int[N];
        for (int i = 0 ; i < N; i++) size[i] = 1;
    }

    public int getCount(){
        return count;
    }

    public boolean connected(int p, int q){
        return find(p) == find(q);
    }

    @Override
    public int find(int p){
        while (p != id[p])
            p = id[p];
        return p;
    }

    @Override
    public void union(int p, int q){
        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot == qRoot) return;

        if (size[p] < size[q]){
            id[pRoot] = qRoot;
            size[qRoot] += size[pRoot];
        }else {
            id[qRoot] = pRoot;
            size[pRoot] += size[qRoot];
        }
        count--;
    }
}
