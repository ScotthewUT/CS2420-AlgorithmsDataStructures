package assignment6;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Experiment class for collecting runtime performance of MyLinkedList.addFirst().
 * NOTE: Requires a file named "time_addFirst.tsv" in the working directory.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 2 October 2018
 */
public class TimeAddFirstLinkedVsArray {
	
	private static final int RUNS = 250000;

	public static void main(String[] args) {
		// Size of the Lists to test, N.
		int n = 20000;
		// Three clocks will be used to track run time.
		long clock1, clock2, clock3 = System.nanoTime();
		// Initialize a doubly-linked list and an array list for testing.
		MyLinkedList<Character> linkedList = new MyLinkedList<Character>();
		ArrayList<Character> arrayList = new ArrayList<Character>();

		// Spin up for 3 seconds.
		while(System.nanoTime() - clock3 < 3_000_000_000L) {
			@SuppressWarnings("unused")
			int cont = 0;
			continue;
		}
		
		// Repeat timing experiment for N = 20000 to 100000
		while(n < 100001) {
			
			//Generate two identical lists of each type.
			for(int i = 0; i < n; i++) {
				char next = (char)((i % 26) + 97);
				linkedList.addLast(next);
				arrayList.add(next);
			}
			//Create copies to work on.
			MyLinkedList<Character> linkedTest = new MyLinkedList<Character>(linkedList);
			ArrayList<Character> arrayTest = new ArrayList<Character>(arrayList);
			
			/* MyLinkedList.addFirst() Timing */
			clock1 = System.nanoTime();
			System.out.println("Timing MyLinkedList.addFirst() with size N = " + n + " . . .");
			for(int iter = 0; iter < RUNS; iter++) {
				linkedTest.addFirst('X');
				linkedTest = new MyLinkedList<Character>(linkedList);
			}
			clock2 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				linkedTest = new MyLinkedList<Character>(linkedList);
			}
			clock3 = System.nanoTime();
			
			// Subtract the time necessary to loop and make another copy from the
			// time to call the addFirst() method. Average over the number of runs.
			long addFirstMeanTime = ((clock2 - clock1) - (clock3 - clock2))/RUNS;
			
			// Save the data.
			try(FileWriter fout = new FileWriter(new File("time_addFirst.tsv"), true)) {
				fout.append(n + "\t" + addFirstMeanTime + "\t");
			}
			catch (IOException e) {
				System.out.println("ERROR: time_addFirst.tsv not found!");
			}
			
			/* ArrayList.add(0, X) Timing */
			clock1 = System.nanoTime();
			System.out.println("Timing ArrayList.add(0, X) with size N = " + n + " . . .");
			for(int iter = 0; iter < RUNS; iter++) {
				arrayTest.add(0, 'X');
				arrayTest = new ArrayList<Character>(arrayList);
			}
			clock2 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				arrayTest = new ArrayList<Character>(arrayList);
			}
			clock3 = System.nanoTime();
			
			// Subtract the time necessary to loop and make another copy from the
			// time to call the add(0, X) method. Average over the number of runs.
			long addMeanTime = ((clock2 - clock1) - (clock3 - clock2))/RUNS;
			
			// Save the data.
			try(FileWriter fout = new FileWriter(new File("time_addFirst.tsv"), true)) {
				fout.append(addMeanTime + "\n");
			}
			catch (IOException e) {
				System.out.println("ERROR: time_addFirst.tsv not found!");
			}
			
			//Increment N.
			n += 2500;
		}
	}
}
