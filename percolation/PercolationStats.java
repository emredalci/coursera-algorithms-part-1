package percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final String ERROR_MESSAGE = "Values are must be greater than 0";
    private static final double CONFIDENCE_95 = 1.96;
    private final int trials;
    private final double[] results;

    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        this.trials = trials;
        this.results = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (Boolean.FALSE.equals(percolation.percolates())) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                if (Boolean.FALSE.equals(percolation.isOpen(row, col))) {
                    percolation.open(row, col);
                }
            }
            int openSite = percolation.numberOfOpenSites();
            results[i] = (double) (openSite) / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev()) / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev()) / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int gridSize = 10;
        int totalTrials = 10;
        if (args.length >= 2) {
            gridSize = Integer.parseInt(args[0]);
            totalTrials = Integer.parseInt(args[1]);
        }
        PercolationStats ps = new PercolationStats(gridSize, totalTrials);

        String confidence = "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]";
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}
