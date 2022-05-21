package assignment3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * Experiment class for collecting timing data on ArrayCollection.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 10 September 2018
 */
public class TimeArrayCollection {
	private static Random rand;
	public static void main(String[] args) {
		// Set up an ArrayCollection of 10,000 integers, unsorted.
		rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		ArrayCollection<Integer> randArrCol = new ArrayCollection<Integer>();
		while(randArrCol.size() < 10000) {
			randArrCol.add(rand.nextInt(19999) + 1);
		}
		
		// Three clocks will be used to track run time.
		long clock1, clock2, clock3 = System.nanoTime();
		// We'll average results over 100 test runs.
		int samples = 1000;
		// Create a Comparator for sorting and comparing.
		IntegerComparator comp = new IntegerComparator();
		
		// Spin up for 1 second.
		while(System.nanoTime() - clock3 < 1_000_000_000) {
			int cont = 0;
			continue;
		}
		
		//TODO: Write code to time your toSortedList, contains, and SearchUtil.binarySearch methods so you can plot the results.
		
		ArrayList<Integer> sortedList = new ArrayList<Integer>();
		clock1 = System.nanoTime();
		for(int iter = 0; iter < samples; iter++) {
		sortedList = randArrCol.toSortedList(comp);
		sortedList = new ArrayList<Integer>();
		}
		clock2 = System.nanoTime();
		for(int iter = 0; iter < samples; iter++) {
			sortedList = new ArrayList<Integer>();
		}
		clock3 = System.nanoTime();
		
		double toSortedListMeanTime = ((clock2 - clock1) - (clock3 - clock2))/(samples * 1000.0);
		
		System.out.println("It took ArrayCollection.toSortedList() an average of " + toSortedListMeanTime + " microseconds to sort 10000 integers.");
		
		
		clock1 = System.nanoTime();
		for(int iter = 0; iter < samples; iter++) {
			int randInt = 1 + rand.nextInt(19999);
			randArrCol.contains(randInt);
		}
		clock2 = System.nanoTime();
		for(int iter = 0; iter < samples; iter++) {
			int randInt = 1 + rand.nextInt(19999);
		}
		clock3 = System.nanoTime();
		
		double containsMeanTime = ((clock2 - clock1) - (clock3 - clock2))/(samples * 1000.0);
		
		System.out.println("It took ArrayCollection.contains() an average of " + containsMeanTime + " microseconds to search 10000 integers.");
		
		
		sortedList = randArrCol.toSortedList(comp);
		clock1 = System.nanoTime();
		for(int iter = 0; iter < samples; iter++) {
			int randInt = 1 + rand.nextInt(19999);
			SearchUtil.binarySearch(sortedList, randInt, comp);
		}
		clock2 = System.nanoTime();
		for(int iter = 0; iter < samples; iter++) {
			int randInt = 1 + rand.nextInt(19999);
		}
		clock3 = System.nanoTime();
		
		double bindarySearchMeanTime = ((clock2 - clock1) - (clock3 - clock2))/(samples * 1000.0);
		
		System.out.println("It took SearchUtil.binarySearch() an average of " + bindarySearchMeanTime + " microseconds to search 10000 integers.");
		
	}
}
