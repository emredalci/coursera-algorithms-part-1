package percolation;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Utils {

    public static void processUnion(DynamicConnectivity dynamicConnectivity) {
        while (!StdIn.isEmpty()){
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (dynamicConnectivity.connected(p,q)) continue;
            dynamicConnectivity.union(p,q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(dynamicConnectivity.count + " components");
    }
}
