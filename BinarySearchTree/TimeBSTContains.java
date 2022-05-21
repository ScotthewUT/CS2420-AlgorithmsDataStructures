package assignment8;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

/**
 * Experiment class that collects timing data from the .contains()
 * method for both our BST and Java's TreeSet.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 24 October 2018
 */
public class TimeBSTContains {

	private static final long RUNS = 8_000_000;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Initial size of the BSTs.
		int n = 256;
		// Four clocks will be used to track run time.
		long clock1, clock2, clock3, clock4 = System.nanoTime();
		// Initialize a BST and TreeSet
		BinarySearchTree<Integer> testBST = new BinarySearchTree<Integer>();
		TreeSet<Integer> testTreeSet = new TreeSet<Integer>();
		// Declare an ArrayList used to fill the BSTs.
		ArrayList<Integer> randArr;
		// Declare an array for picking items to search for.
		int[] find = new int[256];
		// Initialize an RNG.
		Random rand = new Random(System.nanoTime());
		// Initialize a LocalTime and String for timestamps.
		LocalTime time = LocalTime.now();
		String timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";

		// Spin up for 4 seconds.
		while(System.nanoTime() - clock4 < 4_000_000_000L) {
			int cont = 0;
			continue;
		}

		// Repeat timing experiment for N = 256 to 4,194,304.
		while(n < 5_000_000) {
			//Print the size of N.
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + "N = " + n);

			// Initialize the ArrayList, fill it from 1 to N, & shuffle it.
			randArr = new ArrayList<Integer>(n);
			for(int i = 1; i <= n; i++)
				randArr.add(i);
			for(int i = 0; i < n; i++)
				randArr.set(i, randArr.get(rand.nextInt(n)));

			//Pick 128 items from 1 to N to search for.
			for(int i = 0; i < 256; i++)
				find[i] = 1 + rand.nextInt(n);

			// Fill the BSTs.
			testBST.addAll(randArr);
			testTreeSet.addAll(randArr);

			/* BinarySearchTree.contains() Timing */
			clock1 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				boolean found = testBST.contains(find[iter % 256]);
			}
			clock2 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				boolean found = testTreeSet.contains(find[iter % 256]);
			}
			clock3 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				boolean found = true;
			}
			clock4 = System.nanoTime();
			
			// Calculate the average time to call .contains() for the BSTs.
			long timeBST = (clock2 - clock1) / RUNS;
			long timeTreeSet = (clock3 - clock2) / RUNS;
			long timeLoop = (clock4 - clock3) / RUNS;
			timeBST -= timeLoop;
			timeTreeSet -= timeLoop;

			// Save the data.
			try(FileWriter fout = new FileWriter(new File("time_Contains.tsv"), true)) {
				fout.append(n + "\t" + timeBST + "\t" + timeTreeSet + "\n");
			}
			catch (IOException e) {
				System.out.println("ERROR: time_Contains.tsv not found!");
				e.printStackTrace();
			}
			// Print the data.
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + timeBST + " ns to call BinarySearchTree.contains().");
			System.out.println(timestamp + timeTreeSet + " ns to call TreeSet.contains().");
			
			//Clear the BSTs.
			testBST.clear();
			testTreeSet.clear();
			
			//Double the size of N.
			n = 2 * n;
		}
	}
}
