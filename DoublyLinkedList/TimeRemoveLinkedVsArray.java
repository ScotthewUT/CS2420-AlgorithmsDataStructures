package assignment6;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TimeRemoveLinkedVsArray {
	
	private static final int RUNS = 1000000;

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Size of the Lists to test, N.
		int n = 2000;
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
		
		// Repeat timing experiment for N = 2000 to 10000
		while(n < 10001) {
			
			//Generate two identical lists of each type.
			for(int i = 0; i < n; i++) {
				linkedList.addLast(0);
				arrayList.add(0);
			}
			//Create copies to work on.
			MyLinkedList<Integer> linkedTest = new MyLinkedList<Integer>(linkedList);
			ArrayList<Integer> arrayTest = new ArrayList<Integer>(arrayList);
			
			//Create a list of random indices to use.
			int[] idx = new int[RUNS];
			for(int i = 0; i < RUNS; i++)
				idx[i] = rand.nextInt(n);
			
			
			/* MyLinkedList.remove() Timing */
			clock1 = System.nanoTime();
			System.out.println("Timing MyLinkedList.remove() with size N = " + n + " . . .");
			for(int iter = 0; iter < RUNS; iter++) {
				int i = idx[iter];
				linkedTest.remove(i);
				linkedTest = new MyLinkedList<Integer>(linkedList);
			}
			clock2 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				int i = idx[iter];
				linkedTest = new MyLinkedList<Integer>(linkedList);
			}
			clock3 = System.nanoTime();
			
			// Subtract the time necessary to loop, pick the index, and copy from
			// the time to call the get() method. Average over the number of runs.
			long removeMeanTime = ((clock2 - clock1) - (clock3 - clock2))/RUNS;
			
			// Save the data.
			try(FileWriter fout = new FileWriter(new File("time_remove.tsv"), true)) {
				fout.append(n + "\t" + removeMeanTime + "\t");
			}
			catch (IOException e) {
				System.out.println("ERROR: time_remove.tsv not found!");
			}
			
			
			/* ArrayList.get() Timing */
			clock1 = System.nanoTime();
			System.out.println("Timing ArrayList.remove() with size N = " + n + " . . .");
			for(int iter = 0; iter < RUNS; iter++) {
				int i = idx[iter];
				arrayTest.remove(i);
				arrayTest = new ArrayList<Integer>(arrayList);
			}
			clock2 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				int i = idx[iter];
				arrayTest = new ArrayList<Integer>(arrayList);
			}
			clock3 = System.nanoTime();
			
			// Subtract the time necessary to loop, pick the index, and copy from
			// the time to call the get() method. Average over the number of runs..
			removeMeanTime = ((clock2 - clock1) - (clock3 - clock2))/RUNS;
			
			// Save the data.
			try(FileWriter fout = new FileWriter(new File("time_remove.tsv"), true)) {
				fout.append(removeMeanTime + "\n");
			}
			catch (IOException e) {
				System.out.println("ERROR: time_remove.tsv not found!");
			}
			
			//Increment N.
			n += 1000;
		}
	}
}