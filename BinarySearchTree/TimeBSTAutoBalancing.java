package assignment8;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

/**
 * Experiment class that collects timing data for BinarySearchTree
 * vs TreeSet to compare the cost-benefit of auto-balancing.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 24 October 2018
 */
public class TimeBSTAutoBalancing {
	
	private static final int RUNS = 250000;

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Initial size of the BSTs.
		int n = 512;
		// Three clocks will be used to track run time.
		long clock1, clock2, clock3 = System.nanoTime();
		// Initialize a BST and TreeSet
		BinarySearchTree<Integer> testBST = new BinarySearchTree<Integer>();
		TreeSet<Integer> testTreeSet = new TreeSet<Integer>();
		// Declare two ArrayLists used to fill the BSTs.
		ArrayList<Integer> sortArr;
		ArrayList<Integer> randArr;
		// Initialize an RNG to shuffle randArr.
		Random rand = new Random(System.nanoTime());
		// Initialize a LocalTime and String for timestamps.
		LocalTime time = LocalTime.now();
		String timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";

		// Spin up for 4 seconds.
		while(System.nanoTime() - clock3 < 4_000_000_000L) {
			int cont = 0;
			continue;
		}

		// Repeat timing experiment for N = 512 to 20,480.
		while(n <= 20480) {
			//Print the size of N.
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + "N = " + n);

			// Initialize the ArrayLists.
			sortArr = new ArrayList<Integer>(n);
			randArr = new ArrayList<Integer>(n);
			// Fill them with 1 to N.
			for(int i = 1; i <= n; i++) {
				sortArr.add(i);
				randArr.add(i);
			}
			// Shuffle randArr.
			for(int i = 0; i < n; i++)
				randArr.set(i, randArr.get(rand.nextInt(n)));
			
			
			/* BinarySearchTree.addAll() Timing. */
			clock1 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				testBST.addAll(randArr);
				testBST.clear();
			}
			clock2 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				testBST.clear();
			}
			clock3 = System.nanoTime();
			
			// Subtract the time necessary to loop and clear the BST from the time to
			// call the .addAll() method. Average over the number of runs.
			long meanTime = ((clock2 - clock1) - (clock3 - clock2))/RUNS;

			// Save the data.
			try(FileWriter fout = new FileWriter(new File("time_BSTvsTreeSet.tsv"), true)) {
				fout.append(n + "\t" + meanTime + "\t");
			}
			catch (IOException e) {
				System.out.println("ERROR: time_BSTvsTreeSet.tsv not found!");
				e.printStackTrace();
			}
			// Print the data.
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + meanTime + " ns to call BinarySearchTree.addAll().");
			
			
			// Refill the BST.
			testBST.addAll(randArr);
			
			
			/* BinarySearchTree.contains() Timing */
			clock1 = System.nanoTime();
			for(Integer next : sortArr) {
				for(int iter = 0; iter < RUNS; iter++) {
					boolean found = testBST.contains(next);
				}
			}
			clock2 = System.nanoTime();
			for(Integer next : sortArr) {
				for(int iter = 0; iter < RUNS; iter++) {
					boolean cont = true;
				}
			}
			clock3 = System.nanoTime();

			// Subtract the time necessary to loop from the time to call the
			// .contains() method. Average over the number of runs times N.
			meanTime = ((clock2 - clock1) - (clock3 - clock2))/(RUNS * n);

			// Save the data.
			try(FileWriter fout = new FileWriter(new File("time_BSTvsTreeSet.tsv"), true)) {
				fout.append(meanTime + "\t");
			}
			catch (IOException e) {
				System.out.println("ERROR: time_BSTvsTreeSet.tsv not found!");
				e.printStackTrace();
			}
			// Print the data.
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + meanTime + " ns to call BinarySearchTree.contains().");
			
			
			// Clear the BST.
			testBST.clear();


			/* Java's TreeSet.addAll() Timing. */
			clock1 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				testTreeSet.addAll(randArr);
				testTreeSet.clear();
			}
			clock2 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				testTreeSet.clear();
			}
			clock3 = System.nanoTime();
			
			// Subtract the time necessary to loop and clear the TreeSet from the time
			// to call the .addAll() method. Average over the number of runs.
			meanTime = ((clock2 - clock1) - (clock3 - clock2))/RUNS;

			// Save the data.
			try(FileWriter fout = new FileWriter(new File("time_BSTvsTreeSet.tsv"), true)) {
				fout.append(meanTime + "\t");
			}
			catch (IOException e) {
				System.out.println("ERROR: time_BSTvsTreeSet.tsv not found!");
				e.printStackTrace();
			}
			// Print the data.
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + meanTime + " ns to call TreeSet.addAll().");
			
			
			// Refill the TreeSet.
			testTreeSet.addAll(randArr);
			
			
			/* TreeSet.contains() Timing */
			clock1 = System.nanoTime();
			for(Integer next : sortArr) {
				for(int iter = 0; iter < RUNS; iter++) {
					boolean found = testTreeSet.contains(next);
				}
			}
			clock2 = System.nanoTime();
			for(Integer next : sortArr) {
				for(int iter = 0; iter < RUNS; iter++) {
					boolean cont = true;
				}
			}
			clock3 = System.nanoTime();

			// Subtract the time necessary to loop from the time to call the
			// .contains() method. Average over the number of runs times N.
			meanTime = ((clock2 - clock1) - (clock3 - clock2))/(RUNS * n);

			// Save the data.
			try(FileWriter fout = new FileWriter(new File("time_BSTvsTreeSet.tsv"), true)) {
				fout.append(meanTime + "\n");
			}
			catch (IOException e) {
				System.out.println("ERROR: time_BSTvsTreeSet.tsv not found!");
				e.printStackTrace();
			}
			// Print the data.
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + meanTime + " ns to call TreeSet.contains().");


			// Clear the TreeSet.
			testTreeSet.clear();
			// Increment N.
			n += 512;
		}
	}
}
