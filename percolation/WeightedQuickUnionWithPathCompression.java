package percolation;

public class WeightedQuickUnionWithPathCompression extends WeightedQuickUnion{

    public WeightedQuickUnionWithPathCompression(int N) {
        super(N);
    }

    @Override
    public int find(int p){
        int root = p;
        while (root != id[root]){
            root = id[root];
        }
        while (p != root){
            int newP = id[p];
            id[p] = root;
            p = newP;
        }
        return root;
    }
}
