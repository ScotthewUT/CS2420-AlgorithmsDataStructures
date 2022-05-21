package assignment6;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Experiment class for collecting runtime performance of MyLinkedList.get().
 * NOTE: Requires a file named "time_get.tsv" in the working directory.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 3 October 2018
 */
public class TimeGetLinkedVsArray {
	
	private static final int RUNS = 1000000;

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Size of the Lists to test, N.
		int n = 5000;
		// Three clocks will be used to track run time.
		long clock1, clock2, clock3 = System.nanoTime();
		// Initialize and seed Random for picking indices.
		Random rand = new Random(System.currentTimeMillis());
		// Initialize a doubly-linked list and an array list for testing.
		MyLinkedList<Integer> linkedList = new MyLinkedList<Integer>();
		ArrayList<Integer> arrayList = new ArrayList<Integer>();

		// Spin up for 3 seconds.
		while(System.nanoTime() - clock3 < 3_000_000_000L) {
			@SuppressWarnings("unused")
			int cont = 0;
			continue;
		}
		
		// Repeat timing experiment for N = 5000 to 50000
		while(n < 50001) {
			
			//Generate two identical lists of each type.
			for(int i = 0; i < n; i++) {
				linkedList.addLast(0);
				arrayList.add(0);
			}
			//Create a list of random indices to use.
			int[] idx = new int[RUNS];
			for(int i = 0; i < RUNS; i++)
				idx[i] = rand.nextInt(n);
			
			
			/* MyLinkedList.get() Timing */
			clock1 = System.nanoTime();
			System.out.println("Timing MyLinkedList.get() with size N = " + n + " . . .");
			for(int iter = 0; iter < RUNS; iter++) {
				int i = idx[iter];
				linkedList.get(i);
			}
			clock2 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				int i = idx[iter];
			}
			clock3 = System.nanoTime();
			
			// Subtract the time necessary to loop and pick the index from the
			// time to call the get() method. Average over the number of runs.
			long getMeanTimeLinked = ((clock2 - clock1) - (clock3 - clock2))/RUNS;
			
			// Save the data.
			try(FileWriter fout = new FileWriter(new File("time_get.tsv"), true)) {
				fout.append(n + "\t" + getMeanTimeLinked + "\t");
			}
			catch (IOException e) {
				System.out.println("ERROR: time_get.tsv not found!");
			}
			
			/* ArrayList.get() Timing */
			clock1 = System.nanoTime();
			System.out.println("Timing ArrayList.get() with size N = " + n + " . . .");
			for(int iter = 0; iter < RUNS; iter++) {
				int i = idx[iter];
				arrayList.get(i);
			}
			clock2 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				int i = idx[iter];
			}
			clock3 = System.nanoTime();
			
			// Subtract the time necessary to loop and pick an index from the
			// time to call the get() method. Average over the number of runs.
			Long getMeanTimeArray = ((clock2 - clock1) - (clock3 - clock2))/RUNS;
			
			// Save the data.
			try(FileWriter fout = new FileWriter(new File("time_get.tsv"), true)) {
				fout.append(getMeanTimeArray + "\n");
			}
			catch (IOException e) {
				System.out.println("ERROR: time_get.tsv not found!");
			}
			
			//Increment N.
			n += 5000;
		}
	}
}
