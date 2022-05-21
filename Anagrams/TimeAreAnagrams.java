package assignment4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Experiment class for collecting run time performance of AnagramUtil.areAnagrams().
 * Averages performance over 1000 runs from N = 500 to N = 5000. 10% of the runs
 * test with Strings that are anagrams; 90% are not anagrams.
 * 
 * NOTE: Create a file in the working directory named "time_areAnagrams.tsv".
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 19 September 2018
 */
public class TimeAreAnagrams {

	private static final int RUNS = 1000;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		// Size of the Strings to test, N.
		int n = 500;
		// Three clocks will be used to track run time.
		long clock1, clock2, clock3 = System.nanoTime();
		// Initialize a Random and set seed.
		Random rand = new Random(System.currentTimeMillis());
		// Strings to check as anagrams.
		String randStr1, randStr2, anagStr1;
		
		// Spin up for 2 seconds.
		while(System.nanoTime() - clock3 < 2_000_000_000) {
			int cont = 0;
			continue;
		}
		
		// Repeat timing experiment for N = 500, 1000, 1500, ... 5000.
		while(n < 5001) {
			// Get a randomized String of size N.
			randStr1 = AnagramTester.randomString(n);
			// Copy the first random String and shuffle it.
			char[] copy = randStr1.toCharArray();
			for(int i = 0; i < n; i++) {
				int swap = rand.nextInt(n);
				char held = copy[i];
				copy[i] = copy[swap];
				copy[swap] = held;
			}
			anagStr1 = new String(copy);
			// Get a second randomized String of size N.
			randStr2 = AnagramTester.randomString(n);
			
			/* AnagramUtil.areAnagrams() Timing */
			clock1 = System.nanoTime();
			System.out.println("Timing areAnagrams() with size N = " + n + " . . .");
			for(int iter = 0; iter < RUNS; iter++) {
				boolean check;
				if(iter % 10 == 9) {
					check = AnagramUtil.areAnagrams(randStr1, anagStr1);
					continue;
				}
				check = AnagramUtil.areAnagrams(randStr1, randStr2);
			}
			clock2 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				boolean check;
				if(iter % 10 == 9) {
					check = true;
					continue;
				}
				check = false;
			}
			clock3 = System.nanoTime();
			
			// Subtract the time necessary to loop, check the if-statement, and assign a boolean
			// from the time to call areAnagrams() method. Average over the number of runs.
			long areAnagramsMeanTime = ((clock2 - clock1) - (clock3 - clock2))/RUNS;
			
			// Save the data.
			System.out.println("It took areAnagrams() " + areAnagramsMeanTime + " ns to compare Strings of size N = " + n + ".");
			try(FileWriter fout = new FileWriter(new File("time_areAnagrams.tsv"), true)) {
				fout.append(n + "\t" + areAnagramsMeanTime + "\n");
			}
			catch (IOException e) {
				System.out.println("ERROR: time_areAnagrams.tsv not found!");
			}
			// Increment N by 500.
			n += 500;
		}
	}
}
