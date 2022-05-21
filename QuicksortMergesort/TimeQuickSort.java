package assignment5;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Experiment class for collecting run time performance of SortUtil.quicksort().
 * Averages performance over 100000 runs from N = 1000 to N = 124000.
 * NOTE: Create a file in the working directory named "time_quicksort.tsv".
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 23 September 2018
 */
public class TimeQuickSort {
	
private static final int RUNS = 100000;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		// Size of the Strings to test, N.
		int n = 72000;
		// Three clocks will be used to track run time.
		long clock1, clock2, clock3 = System.nanoTime();
		// Initialize a Random and set seed.
		Random rand = new Random(System.currentTimeMillis());
		// Declare ArrayLists for testing.
		ArrayList<Integer> randList, testList;
		// Create a comparator for sorting Integers.
		IntegerComparator comp = new IntegerComparator();
		
		// Spin up for 3 seconds.
		while(System.nanoTime() - clock3 < 3_000_000_000L) {
			@SuppressWarnings("unused")
			int cont = 0;
			continue;
		}
		
		// Repeat timing experiment for N = 1000 to 124000
		while(n <= 124000) {
			
			// Get a randomized list of size N and make a copy.
			randList = SortUtil.generateAverageCase(n);
			testList = new ArrayList<Integer>(randList);
			
			/* SortUtil.mergesort() Timing */
			clock1 = System.nanoTime();
			System.out.println("Timing quicksort() with size N = " + n + " . . .");
			for(int iter = 0; iter < RUNS; iter++) {
				SortUtil.quicksort(testList, comp);
				testList = new ArrayList<Integer>(randList);
			}
			clock2 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				testList = new ArrayList<Integer>(randList);
			}
			clock3 = System.nanoTime();
			
			// Subtract the time necessary to loop and make a copy of the randomized list from
			// the time to call the quicksort() method. Average over the number of runs.
			long quicksortMeanTime = ((clock2 - clock1) - (clock3 - clock2))/RUNS;
			
			// Save the data.
			System.out.println("It took quicksort() " + quicksortMeanTime + " ns to sort a list of size N = " + n + ".");
			try(FileWriter fout = new FileWriter(new File("time_quicksort.tsv"), true)) {
				fout.append(n + "\t" + quicksortMeanTime + "\n");
			}
			catch (IOException e) {
				System.out.println("ERROR: time_quicksort.tsv not found!");
			}
			// Increment N.
			if(n < 8001)
				n = 2 * n;
			else
				n += 12000;
		}
	}
}
