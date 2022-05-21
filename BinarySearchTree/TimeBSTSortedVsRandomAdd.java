package assignment8;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

/**
 * Experiment class that collects timing data on BinarySearchTree
 * and compares adding items in a sorted order (unbalanced) vs a
 * random order (balanced).
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 24 October 2018
 */
public class TimeBSTSortedVsRandomAdd {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Initial size of the BST.
		int n = 512;
		// Three clocks will be used to track run time.
		long clock1, clock2, clock3 = System.nanoTime();
		// Initialize a BST.
		BinarySearchTree<Integer> testBST = new BinarySearchTree<Integer>();
		// Declare two ArrayLists used to fill the BST.
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
			// Fill the BST in sorted order.
			testBST.addAll(sortArr);
			
			
			/* BST TIMING WITH SORTED ITEMS */
			int runs = 10000;
			clock1 = System.nanoTime();
			for(Integer next : sortArr) {
				for(int iter = 0; iter < runs; iter++) {
					boolean found = testBST.contains(next);
				}
			}
			clock2 = System.nanoTime();
			for(Integer next : sortArr) {
				for(int iter = 0; iter < runs; iter++) {
					boolean cont = true;
				}
			}
			clock3 = System.nanoTime();
			
			// Subtract the time necessary to loop from the time to call the
			// .contains() method. Average over the number of runs times N.
			long meanTime = ((clock2 - clock1) - (clock3 - clock2))/(runs * n);
			
			// Save the data.
			try(FileWriter fout = new FileWriter(new File("time_BST.tsv"), true)) {
				fout.append(n + "\t" + meanTime + "\t");
			}
			catch (IOException e) {
				System.out.println("ERROR: time_BST.tsv not found!");
				e.printStackTrace();
			}
			// Print the data.
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + meanTime + " ns to call .contains() on the sorted BST.");
			
			// Clear the BST then fill it with unsorted items.
			testBST.clear();
			testBST.addAll(randArr);
			
			
			/* BST TIMING WITH SHUFFLED ITEMS */
			runs = 100000;
			clock1 = System.nanoTime();
			for(Integer next : sortArr) {
				for(int iter = 0; iter < runs; iter++) {
					boolean found = testBST.contains(next);
				}
			}
			clock2 = System.nanoTime();
			for(Integer next : sortArr) {
				for(int iter = 0; iter < runs; iter++) {
					boolean cont = true;
				}
			}
			clock3 = System.nanoTime();
			
			// Subtract the time necessary to loop from the time to call the
			// .contains() method. Average over the number of runs times N.
			meanTime = ((clock2 - clock1) - (clock3 - clock2))/(runs * n);

			// Save the data.
			try(FileWriter fout = new FileWriter(new File("time_BST.tsv"), true)) {
				fout.append(meanTime + "\n");
			}
			catch (IOException e) {
				System.out.println("ERROR: time_BST.tsv not found!");
				e.printStackTrace();
			}
			// Print the data.
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + meanTime + " ns to call .contains() on the shuffled BST.");
			
			// Clear the BST and increment N.
			testBST.clear();
			n += 512;
		}
	}
}
