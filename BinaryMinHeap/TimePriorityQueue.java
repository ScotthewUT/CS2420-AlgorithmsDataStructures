package assignment11;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Random;

/**
 * Experimental class for timing PriorityQueue operations.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 26 November 2018
 */
public class TimePriorityQueue {
	
	private static final int RUNS = 50000;

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Set the size range of the test (N).
		int N = 500, N_max = 50000;
		// Three clocks will be used to track run time.
		long clock1, clock2, clock3 = System.nanoTime();
		// Initialize a LocalTime and String for timestamps.
		LocalTime time = LocalTime.now();
		String timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
		// Declare a PriorityQueue.
		PriorityQueue<Integer> testPQ;
		// Initialize an RNG.
		Random rand = new Random(System.nanoTime());
		// Spin up for 4 seconds.
		while(System.nanoTime() - clock3 < 4_000_000_000L)
			continue;

		// Repeat timing experiment from initial N to N_max;
		while(N <= N_max) {
			
			// Print size of N and a timestamp.
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + "N = " + N);
			
			// Suggest a garbage collection.
			System.gc();
			
			
			/* ADD METHOD TIMING */
			clock1 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				testPQ = new PriorityQueue<Integer>();
				for(int i = 0; i < N; i++) {
					int next = rand.nextInt(2 * N);
					testPQ.add(next);
				}
			}
			clock2 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				testPQ = new PriorityQueue<Integer>();
				for(int i = 0; i < N; i++) {
					int next = rand.nextInt(2 * N);
				}
			}
			clock3 = System.nanoTime();
			long addTime = clock2 - clock1;
			long loopTime = clock3 - clock2;
			addTime = (addTime - loopTime) / (RUNS * N);
			
			
			// Fill the PriorityQueue with N items.
			testPQ = new PriorityQueue<Integer>();
			for(int i = 0; i < N; i++) {
				int next = rand.nextInt(2 * N);
				testPQ.add(next);
			}
			
			// Suggest a garbage collection.
			System.gc();
			
			
			/* FIND MIN TIMING */
			clock1 = System.nanoTime();
			for(int iter = 0; iter < 1000 * RUNS; iter++) {
				testPQ.findMin();
			}
			clock2 = System.nanoTime();
			for(int iter = 0; iter < 1000 * RUNS; iter++) {
				continue;
			}
			clock3 = System.nanoTime();
			long findMinTime = clock2 - clock1;
			loopTime = clock3 - clock2;
			findMinTime = (findMinTime - loopTime) / (1000 * RUNS);
			
			
			// Suggest a garbage collection.
			System.gc();
			
			
			/* DELETE MIN TIMING */
			clock1 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				testPQ = new PriorityQueue<Integer>();
				for(int j = 0; j < N; j++) {
					int next = rand.nextInt(2 * N);
					testPQ.add(next);
				}
				for(int i = 0; i < N; i++) {
					testPQ.deleteMin();
				}
			}
			clock2 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				testPQ = new PriorityQueue<Integer>();
				for(int j = 0; j < N; j++) {
					int next = rand.nextInt(2 * N);
					testPQ.add(next);
				}
				for(int i = 0; i < N; i++) {
					continue;
				}
			}
			clock3 = System.nanoTime();
			long deleteMinTime = clock2 - clock1;
			loopTime = clock3 - clock2;
			deleteMinTime = (deleteMinTime - loopTime) / (RUNS * N);
			
			
			// Save the data.
			try(FileWriter fout = new FileWriter(new File("time_PriorityQueue.tsv"), true)) {
				fout.append(N + "\t" + addTime + "\t" + findMinTime +  "\t" + deleteMinTime + "\n");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			// Print the data.
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + addTime + "\t" + findMinTime +  "\t" + deleteMinTime);
			
			// Increment N.
			N += 1500;
		}
	}
}