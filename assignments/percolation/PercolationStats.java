import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] results;
    private int trials;
    private final double gridSize;


    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException("Some argument is <= 0");
        }

        this.results = new double[trials];
        this.trials = trials;
        this.gridSize = n * n;

        int i = -1;
        while (++i < trials) {
            Percolation p = new Percolation(n);

            while (!p.percolates()) {
                p.open(randInRange(n), randInRange(n));
            }

            results[i] = openSites(p.state) / gridSize;
        }
        // perform trials independent experiments on an n-by-n grid
    }


    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(results);
    }


    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(results);
    }


    public double confidenceLo() {
        // low  endpoint of 95% confidence interval
        return mean() - (1.96d * stddev()) / Math.sqrt(trials);
    }


    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return mean() + (1.96d * stddev()) / Math.sqrt(trials);
    }


    private int randInRange(int n) {
        return StdRandom.uniform(1, n + 1);
    }


    private int openSites(boolean[] state) {
        int openSites = 0;
        for (int i = 1; i < state.length - 1; i++) {
            if (state[i]) {
                openSites++;
            }
        }

        return openSites;
    }


    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Invalid arguments passed. 2 arguments should present");
        }
        final int n = Integer.parseInt(args[0]);
        final int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);

        StdOut.println("mean                      = " + stats.mean());
        StdOut.println("stddev                    = " + stats.stddev());
        StdOut.println("95% confidence interval   = " + stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}