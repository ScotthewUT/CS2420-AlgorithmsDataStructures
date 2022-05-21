package assignment11;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

/**
 * Experimental class for timing the PriorityQueue.deleteMin() method.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 26 November 2018
 */
public class TimeRemoveMin {
	
	private static final int RUNS = 2500;

	public static void main(String[] args) {
		// Set the size range of the test (N).
		int N = 25000, N_max = 12_800_000;
		// Three clocks will be used to track run time.
		long clock1, clock2, clock3 = System.nanoTime();
		// Initialize a LocalTime and String for timestamps.
		LocalTime time = LocalTime.now();
		String timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
		// Declare a PriorityQueue.
		PriorityQueue<Integer> testPQ;
		// Initialize an RNG.
		Random rand = new Random(System.nanoTime());
		// Build a list of random ints to add to the PQ.
		ArrayList<Integer> intArr = new ArrayList<Integer>(N_max);
		for(int idx = 0; idx < N_max; idx++)
			intArr.add(rand.nextInt(10000));
		// Spin up for 5 seconds.
		while(System.nanoTime() - clock3 < 5_000_000_000L)
			continue;

		// Repeat timing experiment from initial N to N_max;
		while(N <= N_max) {
			
			// Print size of N and a timestamp.
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + "N = " + N);
			
			// Build a PriorityQueue of size N.
			testPQ = new PriorityQueue<Integer>();
			for(int idx = 0; idx < N; idx++)
				testPQ.add(intArr.get(idx));
			
			// Suggest a garbage collection.
			System.gc();
			
			
			/* DELETE MIN TIMING */
			clock1 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				testPQ.deleteMin();
			}
			clock2 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				continue;
			}
			clock3 = System.nanoTime();
			long deleteMinTime = clock2 - clock1;
			long loopTime = clock3 - clock2;
			deleteMinTime = (deleteMinTime - loopTime) / RUNS;
			
			
			// Save the data.
			try(FileWriter fout = new FileWriter(new File("time_deleteMin.tsv"), true)) {
				fout.append(N + "\t" + deleteMinTime + "\n");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			// Print the data.
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + deleteMinTime);
			
			// Double N.
			N = 2 * N;
		}
	}
}