package assignment10;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

/**
 * Experimental class for collecting performance data for the three hash
 * functors included in the project. Records the time necessary to search
 * for a String  contained in a QuadProbeHashTable of size N. Writes the
 * data to a file named "time_hashFunctorsContains.tsv".
 * 
 * @author Bridger Peterson (u0971665) & Scott Crowley (u1178178)
 * @version 15 November 2018
 */
public class TimeHashFunctorsWithContains {

	private static final int RUNS = 500;
	private static final int STRING_MAX_LENGTH = 50;
	private static final int CAP = 500;

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Set the size range of the test (N).
		int N = 500, N_max = 50000;
		// Five clocks will be used to track run time.
		long clock1, clock2, clock3, clock4, clock5 = System.nanoTime();
		// Initialize a LocalTime and String for timestamps.
		LocalTime time = LocalTime.now();
		String timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
		// Initialize the three HashFunctors and declare a hash table.
		GoodHashFunctor goodFxn = new GoodHashFunctor();
		MediocreHashFunctor okFxn   = new MediocreHashFunctor();
		BadHashFunctor badFxn  = new BadHashFunctor();
		QuadProbeHashTable testHash;
		// Build an ArrayLists used to fill the hash tables.
		ArrayList<String> randStrArr = new ArrayList<String>();
		while(randStrArr.size() < N_max) {
			String randStr = generateString();
			if(!randStrArr.contains(randStr))
				randStrArr.add(randStr);
		}
		// Spin up for 3 seconds.
		while(System.nanoTime() - clock5 < 3_000_000_000L) {
			int cont = 0;
			continue;
		}

		// Repeat timing experiment from initial N to N_max;
		while(N <= N_max) {
			
			// Reset collisions.
			int goodCol = 0, okCol = 0, badCol = 0, loop = 0;
			
			// Initialize the hash table with the good functor.
			testHash = new QuadProbeHashTable(CAP, goodFxn);
			
			// Fill the GoodHashFunctor table.
			for(int idx = 0; idx < N; idx++)
				testHash.add(randStrArr.get(idx));
			// Get the number of collisions.
			goodCol = testHash.getCollisions();
			
			// Print size of N and timestamp.
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + "N = " + N);
			
			/* GOOD HASH FUNCTOR TIMING */
			clock1 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				for(int idx = 0; idx < N; idx++) {
					String str = randStrArr.get(idx);
					testHash.contains(str);
				}
			}
			clock2 = System.nanoTime();
			long goodHashTime = clock2 - clock1;
			
			// Reset the hash table and initialize with the mediocre functor.
			testHash = new QuadProbeHashTable(CAP, okFxn);
			
			// Fill the MediocreHashFunctor table.
			for(int idx = 0; idx < N; idx++)
				testHash.add(randStrArr.get(idx));
			// Get the number of collisions.
			okCol = testHash.getCollisions();
			
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + goodCol + " collisions for GoodHashFunctor.");
			
			/* MEDIOCRE HASH FUNCTOR TIMING */
			clock2 = System.nanoTime();
			for(int i = 0; i < RUNS; i++) {
				for(int idx = 0; idx < N; idx++) {
					String str = randStrArr.get(idx);
					testHash.contains(str);
				}
			}
			clock3 = System.nanoTime();
			long okHashTime = clock3 - clock2;
			
			// Reset the hash table and initialize with the bad functor.
			testHash = new QuadProbeHashTable(CAP, badFxn);
			
			// Fill the BadHashFunctor table.
			for(int idx = 0; idx < N; idx++)
				testHash.add(randStrArr.get(idx));
			// Get the number of collisions.
			badCol = testHash.getCollisions();
			
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + okCol + " collisions for MediocreHashFunctor.");
			
			/* BAD HASH FUNCTOR TIMING */
			clock3 = System.nanoTime();
			for(int i = 0; i < RUNS; i++) {
				for(int idx = 0; idx < N; idx++) {
					String str = randStrArr.get(idx);
					testHash.contains(str);
				}
			}
			clock4 = System.nanoTime();
			long badHashTime = clock4 - clock3;
			
			// Nullify the hash table.
			testHash = null;
			
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + badCol + " collisions for BadHashFunctor.");
			
			/* SUBRTRACTION LOOP */
			clock4 = System.nanoTime();
			for(int i = 0; i < RUNS; i++) {
				for(int idx = 0; idx < N; idx++) {
					String str = randStrArr.get(idx);
				}
			}
			clock5 = System.nanoTime();
			long loopTime = clock5 - clock4;
			
			// Calculate the run times.
			goodHashTime = (goodHashTime - loopTime) / (RUNS * N);
			okHashTime = (okHashTime - loopTime) / (RUNS * N);
			badHashTime = (badHashTime - loopTime) / (RUNS * N);
			
			// Save the data.
			try(FileWriter fout = new FileWriter(new File("time_hashFunctorsContains.tsv"), true)) {
				fout.append(N + "\t" + goodHashTime + "\t" + okHashTime + "\t" + badHashTime + "\t"
													+ goodCol + "\t" + okCol + "\t" + badCol + "\n");
			}
			catch (IOException e) {
				System.out.println("ERROR: time_hashFunctorsContains.tsv not found!");
				e.printStackTrace();
			}
			// Print the data.
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + goodHashTime + "\t" + okHashTime + "\t" + badHashTime + "\t"
				 	   					 + goodCol + "\t" + okCol + "\t" + badCol);
			// Increment N.
			N += 1500;
		}
	}
	
	
	/**
	 * Helper method generates a random alphanumeric String
	 * of length 1 to STRING_MAX_LENGTH.
	 * 
	 * @return a randomized alphanumeric String
	 */
	private static String generateString() {
		char[] charArr = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					  'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
					  'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'w', 'v', 'x', 'y', 'z',
					  'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
					  'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		
		Random rand = new Random(System.nanoTime());
		int length = rand.nextInt(STRING_MAX_LENGTH) + 1;
		
		String result = "";
		for(int i = 0; i < length; i++)
			result += charArr[rand.nextInt(charArr.length)];
		
		return result;
	}
}