import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

// Estimates percolation threshold for an N-by-N percolation system.
public class PercolationStats {
    private int numTrials=0;//numTrials makes the number of trials available for class-wide use,
    // totalPercols = total amount of times experiments were run until percolation
    private double[] openFrac;// holds fraction of open sites in a computational experiment

    // Perform T independent experiments (Monte Carlo simulations) on an
    // N-by-N grid.
    public PercolationStats(int N, int T) {
        if(N<=0||T<=0)
            throw new IllegalArgumentException();
        numTrials = T;
        openFrac = new double[T];
        for(int i = 0; i<T; i++) {
            Percolation p = new Percolation(N);
            double count = 0;
            while (!p.percolates()) {
                int randRow = StdRandom.uniform(N);
                int randCol = StdRandom.uniform(N);
                p.open(randRow, randCol);
                count++;//p.numberOfOpenSites() returns a value but it can't be added to the openFrac array
                // so count variable will serve the same purpose and hold the number of open sites in this scope
            }
            openFrac[i] = (count/(N * N));//each element in openFrac = # of sites opened until experiment percolates/total # of sites
        }
    }

    // Sample mean of percolation threshold.
    public double mean() {
        return StdStats.mean(openFrac);
    }

    // Sample standard deviation of percolation threshold.
    public double stddev() {
        return StdStats.stddevp(openFrac);
    }

    // Low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        assert numTrials>=30 : "T isn't sufficiently large (i.e. T<30)";
        return mean()-(1.96 * Math.sqrt(stddev())/ Math.sqrt(numTrials));
    }

    // High endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        assert numTrials>=30 : "T isn't sufficiently large (i.e. T<30)";
        return mean()+(1.96 * Math.sqrt(stddev())/ Math.sqrt(numTrials));
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(N, T);
        StdOut.printf("mean           = %f\n", stats.mean());
        StdOut.printf("stddev         = %f\n", stats.stddev());
        StdOut.printf("confidenceLow  = %f\n", stats.confidenceLow());
        StdOut.printf("confidenceHigh = %f\n", stats.confidenceHigh());
    }
}
