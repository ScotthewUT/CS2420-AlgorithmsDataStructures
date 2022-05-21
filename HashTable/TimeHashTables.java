package assignment10;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

/**
 * Experimental class for collecting performance data for the two hash
 * tables, QuadProbeHashTable & ChainingHashTable.. Records the time
 * necessary to search for a String contained in a table of size N.
 * Writes the data to a file named "time_hashtables.tsv".
 * 
 * @author Bridger Peterson (u0971665) & Scott Crowley (u1178178)
 * @version 15 November 2018
 */
public class TimeHashTables {

	private static final int RUNS = 1000;
	private static final int COLL_RUNS = 100;
	private static final int STRING_MAX_LENGTH = 50;
	private static final int CAP = 500;

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Set the size range of the test (N).
		int N = 500, N_max = 50000;
		// Four clocks will be used to track run time.
		long clock1, clock2, clock3, clock4 = System.nanoTime();
		// Initialize a LocalTime and String for timestamps.
		LocalTime time = LocalTime.now();
		String timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
		// Initialize a good hash function and the two hash table types.
		GoodHashFunctor goodFxn = new GoodHashFunctor();
		QuadProbeHashTable quadProbeHash = new QuadProbeHashTable(CAP, goodFxn);
		ChainingHashTable chainingHash = new ChainingHashTable(CAP, goodFxn);
		// Build an ArrayLists used to fill the hash tables.
		ArrayList<String> randStrArr = new ArrayList<String>();
		while(randStrArr.size() < N_max) {
			String randStr = generateString();
			if(!randStrArr.contains(randStr))
				randStrArr.add(randStr);
		}
		// Spin up for 3 seconds.
		while(System.nanoTime() - clock4 < 3_000_000_000L) {
			int cont = 0;
			continue;
		}

		// Repeat timing experiment from initial N to N_max;
		while(N <= N_max) {
			
			// Print size of N and a timestamp.
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + "N = " + N);

			// Reset collisions.
			int quadCol = 0, chainCol = 0;
			
			
			// Determine the average collision rate for QuadProbeHashTable.
			for(int iter = 0; iter < COLL_RUNS; iter++) {
				// Reset the quadratic probing hash table.
				quadProbeHash = new QuadProbeHashTable(CAP, goodFxn);
				// Fill the quadratic probing table.
				for(int idx = 0; idx < N; idx++)
					quadProbeHash.add(randStrArr.get(idx));
				// Count the number of collisions.
				quadCol += quadProbeHash.getCollisions();
			}	// Average the collisions over number of loops.
			quadCol = quadCol / COLL_RUNS;
			
			
			/* QUAD-PROBE HASH TABLE TIMING */
			clock1 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				for(int idx = 0; idx < N; idx++) {
					String str = randStrArr.get(idx);
					quadProbeHash.contains(str);
				}
			}
			clock2 = System.nanoTime();
			long quadHashTime = clock2 - clock1;
			//Reset the quad-probe hash table.
			quadProbeHash = new QuadProbeHashTable(CAP, goodFxn);
			
			
			// Determine the average collision rate for ChainingHashTable.
			for(int iter = 0; iter < COLL_RUNS; iter++) {
				// Reset the separate chaining hash table.
				chainingHash = new ChainingHashTable(CAP, goodFxn);
				// Fill the chaining hash table.
				for(int idx = 0; idx < N; idx++)
					chainingHash.add(randStrArr.get(idx));
				// Count the number of collisions.
				chainCol += chainingHash.getCollisions();
			}	// Average the collisions over number of loops.
			chainCol = chainCol / COLL_RUNS;
			
			
			/* CHAINING HASH TABLE TIMING */
			clock2 = System.nanoTime();
			for(int i = 0; i < RUNS; i++) {
				for(int idx = 0; idx < N; idx++) {
					String str = randStrArr.get(idx);
					chainingHash.contains(str);
				}
			}
			clock3 = System.nanoTime();
			long chainHashTime = clock3 - clock2;
			//Reset the chaining hash table.
			chainingHash = new ChainingHashTable(CAP, goodFxn);
			
			
			/* SUBRTRACTION LOOP */
			clock3 = System.nanoTime();
			for(int i = 0; i < RUNS; i++) {
				for(int idx = 0; idx < N; idx++) {
					String str = randStrArr.get(idx);
				}
			}
			clock4 = System.nanoTime();
			long loopTime = clock4 - clock3;
			
			// Calculate the run times.
			quadHashTime = (quadHashTime - loopTime) / (RUNS * N);
			chainHashTime = (chainHashTime - loopTime) / (RUNS * N);
			
			// Save the data.
			try(FileWriter fout = new FileWriter(new File("time_hashtables.tsv"), true)) {
				fout.append(N + "\t" + quadHashTime + "\t" + chainHashTime +  "\t" + quadCol + "\t" + chainCol + "\n");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			// Print the data.
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + quadHashTime + "\t" + chainHashTime +  "\t" + quadCol + "\t" + chainCol);
			
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