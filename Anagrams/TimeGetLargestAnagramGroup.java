package assignment4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
* Experiment class for collecting run time performance of AnagramUtil.getLargestAnagramGroup().
* Averages performance over 1000 runs from N = 1000 to N = 50000.
* NOTE: Create a file in the working directory named "time_getLargestAnagramGroup.tsv".
* 
* @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
* @version 20 September 2018
*/
public class TimeGetLargestAnagramGroup {
	
	private static final int RUNS = 500;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		// Size of the array to test, N.
		int n = 1000;
		// Three clocks will be used to track run time.
		long clock1, clock2, clock3 = System.nanoTime();
		// Initialize a Random and set seed.
		Random rand = new Random(System.currentTimeMillis());
		
		// Read in a list of words to a String array.
		String wordList = "test_word_list.txt";
		String[] wordArr = AnagramTester.readFile(wordList);
		// Shuffle the word list array.
		int size = wordArr.length;
		for(int i = 0; i < size; i++) {
			int swap = rand.nextInt(size);
			String held = wordArr[i];
			wordArr[i] = wordArr[swap];
			wordArr[swap] = held;
		}
				
		// Spin up for 1 second.
		while(System.nanoTime() - clock3 < 1_000_000_000) {
			int cont = 0;
			continue;
		}
		
		// Repeat timing experiment for N = 500, 1000, 1500, ... 50000.
		while(n < 50001) {
			
			String[] testArr = Arrays.copyOf(wordArr, n);
			String[] result = null;
			
			/* AnagramUtil.getLargestAnagramGroup Timing */
			clock1 = System.nanoTime();
			System.out.println("Timing getLargestAnagramGroup() with size N = " + n + " . . .");
			for(int iter = 0; iter < RUNS; iter++) {
				result = AnagramUtil.getLargestAnagramGroup(testArr);
				for(int i = 0; i < n; i++) {
					int swap = rand.nextInt(n);
					String held = testArr[i];
					testArr[i] = testArr[swap];
					testArr[swap] = held;
				}
			}
			clock2 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				String[] subract = new String[n];
				for(int i = 0; i < n; i++) {
					int swap = rand.nextInt(n);
					String held = testArr[i];
					testArr[i] = testArr[swap];
					testArr[swap] = held;
				}
			}
			clock3 = System.nanoTime();
			
			// Subtract the time necessary to loop, initialize, and shuffle the array from the time
			// to call the getLargestAnagramGroup() method. Average over the number of runs.
			long getLargestAnagramGroupMeanTime = ((clock2 - clock1) - (clock3 - clock2))/RUNS;
			
			// Save the data.
			System.out.println("It took getLargestAnagramGroup() " + getLargestAnagramGroupMeanTime + " ns to search an array of size N = " + n + ".");
			try(FileWriter fout = new FileWriter(new File("time_getLargestAnagramGroup.tsv"), true)) {
				fout.append(n + "\t" + getLargestAnagramGroupMeanTime + "\n");
			}
			catch (IOException e) {
				System.out.println("ERROR: time_getLargestAnagramGroup.tsv not found!");
			}
			String largestGroup = "Most anagrams: ";
			for(String next : result) {
				largestGroup += next + " ";
			}
			System.out.println(largestGroup);
			
			// Increment N by 500.
			n += 500;
		}
	}
}
