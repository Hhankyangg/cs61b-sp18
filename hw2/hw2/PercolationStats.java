package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int size;
    private int times;
    private PercolationFactory pf;
    private double[] thresholds;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (T <= 0 || N <= 0) {
            throw new java.lang.IllegalArgumentException("T or N is too small!");
        }
        size = N;
        times = T;
        this.pf = pf;
        thresholds = new double[T];
        doExperiments();
    }

    private void doExperiments() {
        for (int time = 0; time < times; time += 1) {
            doExperiment(time);
        }
    }

    private void doExperiment(int time) {
        Percolation newTest = pf.make(size);
        int count = 0;
        int total = size * size;
        while (!newTest.percolates()) {
            int randomRow = edu.princeton.cs.introcs.StdRandom.uniform(size);
            int randomCol = StdRandom.uniform(size);
            if (!newTest.isOpen(randomRow, randomCol)) {
                newTest.open(randomRow, randomCol);
                count++;
            }
        }
        thresholds[time] = 1.0 * count / total;
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }
    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        double mean = mean();
        double stddevSqrted = Math.sqrt(stddev());
        return mean - 1.96 * stddevSqrted / Math.sqrt(times);
    }
    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        double mean = mean();
        double stddevSqrted = Math.sqrt(stddev());
        return mean + 1.96 * stddevSqrted / Math.sqrt(times);
    }
}
