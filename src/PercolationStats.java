import java.util.*;

/**
 * Compute statistics on Percolation after performing T independent experiments on an N-by-N grid.
 * Compute 95% confidence interval for the percolation threshold, and  mean and std. deviation
 * Compute and print timings
 * 
 * @author Jeff Forbes
 * @author Owen Astrachan
 */

public class PercolationStats {
	public static int RANDOM_SEED = 1234;
	public static Random ourRandom = new Random(RANDOM_SEED);
	
	 private IPercolate getPercolator(int size) {
		 //return new PercolationDefault(size);
		 return new PercolationBFS(size);
         //return new PercolationDFS(size); 
		 //IUnionFind uf = new QuickUWPC();
         //return new PercolationUF(uf,size);
	 }
	 
	 private ArrayList<int[]> getRandomSites(int size){
         ArrayList<int[]> list = new ArrayList<>();
         for(int row=0; row < size; row++) {
                 for(int col=0; col < size; col++) {
                         int[] cell = new int[2];
                         cell[0] = row;
                         cell[1] = col;
                         list.add(cell);
                 }
         }
         Collections.shuffle(list,ourRandom);
         return list;
	 }

	 public double[] simulate(int size, int trials) {
		 double[] steps = new double[trials];
		 double start = System.nanoTime();
		 for(int k=0; k < trials; k++) {			 
			 IPercolate perc = getPercolator(size);
			 ArrayList<int[]> list = getRandomSites(size);
			 int index = 0;
			 while (! perc.percolates()) {
				 int[] cr = list.get(index);
				 //System.out.printf("%d\t%d %d %d\n",size,index,cr[0],cr[1]);
				 perc.open(cr[0],cr[1]);
				 index += 1;
				 
			 }
             steps[k] = index*1.0/(size*size);
		 } 
		 double end = System.nanoTime();
		 double mean = StdStats.mean(steps);
		 double sdev = StdStats.stddev(steps);
		 return new double[] {mean,sdev,(end-start)/1e9};
	 }
	
	public static void main(String[] args) {
		PercolationStats ps = new PercolationStats();
		int trials = 20;
		int first = 100;
		int last = 3200;
		System.out.printf("simulation data for %d trials\n",trials);
		System.out.println("grid\tmean\tstddev\ttotal time");
		for(int size = first; size <= last; size *= 2) {
			double[] data = ps.simulate(size, trials);
			System.out.printf("%d\t%1.3f\t%1.3f\t%1.3f\n",size,data[0],data[1],data[2]);
		}
	}
}
