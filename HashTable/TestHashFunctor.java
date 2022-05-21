package assignment10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Provides performance testing for HashFunctors.
 * 
 * @author Bridger Peterson (u0971665) & Scott Crowley (u1178178)
 * @version 12 November 2018
 */
public class TestHashFunctor {
	
	final static HashFunctor fxn = new GoodHashFunctor();	// Initialize the hash function to test.
	final static int N = 50000;								// Number of Strings to test on.
	final static int STRING_MAX_LENGTH = 50;				// Max length of test Strings.

	public static void main(String[] args) {
		// Create a list of random Strings with no duplicates.
		ArrayList<String> strList = new ArrayList<String>(N);
		while(strList.size() < N) {
			String randStr = generateString();
			if(!strList.contains(randStr))
				strList.add(randStr);
		}
		// Initialize a list to store the hashes.
		ArrayList<Integer> hashes = new ArrayList<Integer>(N);
		int collisions = 0;		// Collision counter.
		long sum = 0L;			// Sum of all hashes (for calculating mean).
		// Find the hash of each String and store it.
		for(int i = 0; i < N; i++) {
			int hash = fxn.hash(strList.get(i));
			if(hashes.contains(hash))
				collisions++;	// If a duplicate hash is found, increment collisions.
			hashes.add(hash);
			sum += hash;
		}
		// Uncomment this to print the hashes.
//		for(int next : hashes)
//			System.out.println(next);
		
		// Sort the list of hashes.
		Collections.sort(hashes);
		// Find the minimum hash value.
		long min = (long)hashes.get(0);
		// Find the maximum hash value.
		long max = (long)hashes.get(hashes.size() - 1);
		// Find the positive and negative hash values nearest to zero.
		long[] nearZero = closestToZero(hashes);
		// Estimate over what percent of int's range (-2.14B to 2.14B) the hashes are scattered.
		double range = ( (max - min - 0.0) - (nearZero[1] - nearZero[0] - 0.0) ) / 4_294_967_295L;
		
		// Print the findings.
		System.out.println("Size of N:\t" + N);
		System.out.println("Collisions:\t" + collisions + " (" + collisions * 100 / N + "%)");
		System.out.println("Minimum:\t" + min);
		System.out.println("Maximum:\t" + max);
		System.out.println("Nearest 0:\t" + nearZero[0] + " & " + nearZero[1]);
		System.out.println("Range:\t\t" + range * 100 + "%");
		System.out.println("Mean:\t\t" + sum / N);
		System.out.println("Median:\t\t" + hashes.get(hashes.size()/2));
		printMode(hashes);	// Prints the mode of the hashes list.
		// Verify hashes are reproducible.
		String testStr = "This string should hash to the same int always: ";
		for(int i = 0; i < 3; i++) {
			System.out.println(testStr + fxn.hash(testStr));
		}
	}
	
	
	/**
	 * Helper method finds the negative and positive integers nearest
	 * to zero in a given sorted list.
	 * 
	 * @param list - a sorted ArrayList of ints
	 * @return a 2-item array where the 1st entry is the negative
	 * 		   integer nearest zero, and the 2nd is the positive
	 */
	private static long[] closestToZero(ArrayList<Integer> list) {
		
		int absMinNeg = list.get(0);
		int absMinPos = list.get(list.size() - 1);
		
		if(absMinNeg >= 0)
			return new long[] {list.get(0), list.get(0)};
		if(absMinPos <= 0)
			return new long[] {list.get(list.size() - 1), list.get(list.size() - 1)};

		
		for(int idx = 0; idx < list.size() && list.get(idx) <= 0; idx++) {
			int current = Math.abs(absMinNeg);
			int absNext = Math.abs(list.get(idx));
			if(absNext < current)
				absMinNeg = list.get(idx);
		}
		
		for(int idx = list.size() - 1; idx > 0 && list.get(idx) >= 0; idx--) {
			int current = Math.abs(absMinPos);
			int absNext = Math.abs(list.get(idx));
			if(absNext < current)
				absMinPos = list.get(idx);
		}
		
		return new long[] {(long)absMinNeg, (long)absMinPos};
	}
	
	
	/**
	 * Helper method that prints the mode (most common) integer
	 * found in a given list.
	 * 
	 * @param list - an ArrayList of ints
	 */
	private static void printMode(ArrayList<Integer> list) {
		int mode = list.get(0);
		int modeCnt = 1;
		
		int currentMode = mode;
		int currentCnt = 1;
		
		for(int i = 1; i < list.size(); i++) {
			if(list.get(i) == currentMode) {
				currentCnt++;
				continue;
			}
			if(currentCnt > modeCnt) {
				modeCnt = currentCnt;
				mode = currentMode;
				currentMode = list.get(i);
				continue;
			}
			currentMode = list.get(i);
			currentCnt = 1;
		}
		
		if(modeCnt < 2)
			System.out.println("Mode:\t\tnone");
		else
			System.out.println("Mode:\t\t" + mode + " was seen " + modeCnt + " times.");
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
