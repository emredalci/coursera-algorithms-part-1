package percolation;

public abstract class DynamicConnectivity {

    protected int[] id;
    protected int count;

    protected DynamicConnectivity(int N) {
        count = N;
        id = new int[N];
        for (int i = 0; i < N ; i++) id[i] = i;
    }

    public int count(){
        return count;
    }

    public boolean connected(int p , int q){
        return find(p) == find(q);
    }

    abstract int find(int p);
    abstract void union(int p, int q);
}
