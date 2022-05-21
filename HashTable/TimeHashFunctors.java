package assignment10;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

/**
 * Experimental class for collecting performance data for the three
 * hash functors included in the project. Records the time necessary
 * to add N items to QuadProbeHashTable and writes it to a file called
 * "time_hashFunctors.tsv" & also tracks collision count.
 * 
 * @author Bridger Peterson (u0971665) & Scott Crowley (u1178178)
 * @version 14 November 2018
 */
public class TimeHashFunctors {
	
	private static final int RUNS = 10000;
	private static final int STRING_MAX_LENGTH = 25;
	private static final int CAP = 500;
	private static final HashFunctor goodFxn = new GoodHashFunctor();
	private static final HashFunctor okFxn   = new MediocreHashFunctor();
	private static final HashFunctor badFxn  = new BadHashFunctor();

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Set the size range of the test (N).
		int N = 500, N_max = 50000;
		// Five clocks will be used to track run time.
		long clock1, clock2, clock3, clock4, clock5 = System.nanoTime();
		// Initialize a LocalTime and String for timestamps.
		LocalTime time = LocalTime.now();
		String timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
		// Declare three hash tables.
		ChainingHashTable goodHash;
		ChainingHashTable okHash;
		ChainingHashTable badHash;
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
			
			// Initialize the hash tables.
			goodHash = new ChainingHashTable(CAP, goodFxn);
			okHash = new ChainingHashTable(CAP, okFxn);
			badHash = new ChainingHashTable(CAP, badFxn);
			
			
			// Reset collisions.
			int goodCol = 0, okCol = 0, badCol = 0, loop = 0;
			
			// Print size of N and timestamp.
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + "N = " + N + "\t\t\t\t\tTesting GoodHashFunctor...");
			
			/* GOOD HASH FUNCTOR TIMING */
			clock1 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				for(int idx = 0; idx < N; idx++)
					goodHash.add(randStrArr.get(idx));
				goodCol += goodHash.getCollisions();
				goodHash = new ChainingHashTable(CAP, goodFxn);
			}
			clock2 = System.nanoTime();
			long goodHashTime = clock2 - clock1;
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + goodCol / RUNS + " collisions for GoodHashFunctor.\t\tTesting MediocreHashFunctor...");
			
			/* MEDIOCRE HASH FUNCTOR TIMING */
			clock2 = System.nanoTime();
			for(int i = 0; i < RUNS; i++) {
				for(int idx = 0; idx < N; idx++)
					okHash.add(randStrArr.get(idx));
				okCol += okHash.getCollisions();
				okHash = new ChainingHashTable(CAP, okFxn);
			}
			clock3 = System.nanoTime();
			long okHashTime = clock3 - clock2;
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + okCol / RUNS + " collisions for MediocreHashFunctor.\tTesting BadHashFunctor...");
			
			/* BAD HASH FUNCTOR TIMING */
			clock3 = System.nanoTime();
			for(int i = 0; i < RUNS; i++) {
				for(int idx = 0; idx < N; idx++)
					badHash.add(randStrArr.get(idx));
				badCol += badHash.getCollisions();
				badHash = new ChainingHashTable(CAP, badFxn);
			}
			clock4 = System.nanoTime();
			long badHashTime = clock4 - clock3;
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + badCol / RUNS + " collisions for BadHashFunctor.");
			
			/* SUBRTRACTION LOOP */
			clock4 = System.nanoTime();
			for(int i = 0; i < RUNS; i++) {
				goodHash = new ChainingHashTable(CAP, goodFxn);
				loop = goodHash.getCollisions();
			}
			clock5 = System.nanoTime();
			long loopTime = clock5 - clock4;
			
			// Calculate the run times.
			goodHashTime = (goodHashTime - loopTime) / (RUNS * N);
			okHashTime = (okHashTime - loopTime) / (RUNS * N);
			badHashTime = (badHashTime - loopTime) / (RUNS * N);
			
			// Save the data.
			try(FileWriter fout = new FileWriter(new File("time_HashFunctors.tsv"), true)) {
				fout.append(N + "\t" + goodHashTime + "\t" + okHashTime + "\t" + badHashTime + "\t"
						 	   + goodCol / RUNS + "\t" + okCol / RUNS + "\t" + badCol / RUNS + "\n");
			}
			catch (IOException e) {
				System.out.println("ERROR: time_hashFunctors.tsv not found!");
				e.printStackTrace();
			}
			// Print the data.
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + goodHashTime + "\t" + okHashTime + "\t" + badHashTime + "\t"
				 	   					 + goodCol / RUNS + "\t" + okCol / RUNS + "\t" + badCol / RUNS );
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