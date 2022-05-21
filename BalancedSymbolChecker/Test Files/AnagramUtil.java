package assignment4;

import java.util.Arrays;
import java.util.Comparator;

/**
 * This utility class provides methods to determine if Strings are anagrams
 * (e.g. nameless and salesman). Also included are generic implementations of
 * insertion sort and Shellsort.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 17 September 2018
 */
public class AnagramUtil {
	
	// Comparators used to sort characters and Strings for anagrams.
	private static CompCharByLex compChar = new CompCharByLex();
	private static CompStringByAnagram compAna = new CompStringByAnagram();
	
	
	/**
	 * Takes in a String and sorts it lexicographically such that:
	 * "Nameless" returns "Naeelmss". Uses an insertion sort method.
	 * 
	 * @param str - a String to sort
	 * @return a sorted version of the argument String
	 */
	public static String sort(String str) {
		Character[] charArr = new Character[str.length()];
		
		for(int idx = 0; idx < charArr.length; idx++) {
			charArr[idx] = str.charAt(idx);
		}
		insertionSort(charArr, compChar);
		String sorted = "";
		for(Character next : charArr) {
			sorted += next;
		}
		return sorted;
	}
	
	
	/**
	 * Implements an iterative insertion sort on a generic array.
	 * 
	 * @param arr - an array of generic items
	 * @param comp - a Comparator to sort by
	 */
	public static <T> void insertionSort(T[] arr, Comparator<? super T> comp) {
		
		if(arr.length < 2)	// Arrays of 0-1 items is already sorted.
			return;
		
		for(int i = 1; i < arr.length; i++) {
			// Pick up the first element in the unsorted portion of the array.
			T next = arr[i];
			int j = i;
			// Compare it to each element to the left (the sorted portion of the array)
			// while shifting each element to the right until swaps are no longer needed
			// or the beginning of the array is reached.
			while(j > 0 && comp.compare(arr[j - 1], next) > 0) {
				arr[j] = arr[j - 1];
				j--;
			}
			// Set the element down in the sorted portion of the array.
			arr[j] = next;
		}
	}
	
	
	/**
	 * Implements an iterative Shellsort on an generic array. Uses Marcin Ciura's gap sequence.
	 * 
	 * @param arr - an array of generic items
	 * @param comp - a Comparator to sort by
	 */
	public static <T> void shellSort(T[] arr, Comparator<? super T> comp) {
		
		if(arr.length < 2)	// Arrays of 0-1 items is already sorted.
			return;
		
		// Marcin Ciura's gap sequence found experimentally.
		int[] gapCiura = new int[] {701, 301, 132, 57, 23, 10, 4, 1};
		
		for(int gap : gapCiura) {
			//Begin index at one gap away from the first item.
			for(int i = gap; i < arr.length; i++) {
				// Pick up the element at gap, gap+1, gap+2...
				T held = arr[i];
				int j;
				// Compare all items that are a gap size apart and swap when appropriate.
				for(j = i; j >= gap && comp.compare(arr[j - gap], held) > 0; j = j - gap) {
					arr[j] = arr[j - gap];
				}
				arr[j] = held;
			}
		}
	}
	
	
	/**
	 * Checks if two Strings are anagrams of each other (e.g. eastern & nearest).
	 * Ignores case. Returns true for empty strings and false for null strings.
	 * 
	 * @param lhs - a String to compare
	 * @param rhs - a String to compare
	 * @return true if Strings are anagrams, false if not or null
	 */
	public static boolean areAnagrams(String lhs, String rhs) {
		
		if(lhs == null || rhs == null)
			return false;
		if(lhs.length() != rhs.length())
			return false;
		
		String alphaL = lhs.toLowerCase();
		String alphaR = rhs.toLowerCase();
		alphaL = sort(alphaL);
		alphaR = sort(alphaR);
		return alphaL.equalsIgnoreCase(alphaR);
	}
	
	
	/**
	 * Searches a String array and creates a new array containing the argument's
	 * members that are part of the largest group of anagrams. In case of tie,
	 * only a single group of anagrams is returned. If there are no anagrams
	 * present, an empty array is returned. Uses a Shellsort algorithm.
	 * NOTE: This method throws a NullPointerException if passed a null array.
	 * 
	 * @param strArr - an array of Strings to search
	 * @return an array of Strings composed of largest group of anagrams,
	 * 		   or an empty array if no anagrams are found
	 */
	public static String[] getLargestAnagramGroup(String[] strArr) {
		
		// Sort the array such that all anagrams are placed next to each other.
		shellSort(strArr, compAna);
		
		int idxGroup = 0, cntCurrent = 0, cntLargest = 0;
		// Iterate through the sorted array and track the largest group of anagrams.
		for(int idx = 1; idx < strArr.length; idx++) {
			if(areAnagrams(strArr[idx - 1], strArr[idx]))
				cntCurrent++;						// If the previous and current Strings are anagrams,
			else {									// increment the current count.
				if(cntCurrent > cntLargest) {
					cntLargest = cntCurrent;		// If the current count exceeds the largest count,
					idxGroup = idx - cntCurrent;	// it is the new largest count.
				}
				cntCurrent = 0;						// If the previous and current Strings are NOT anagrams,
			}										// reset the current count.
			if(cntCurrent > cntLargest) {
				cntLargest = cntCurrent;			// If the largest count is still finding more anagrams,
				idxGroup = idx - cntCurrent;		// keep incrementing largest count as well.
			}
		}
		if(cntLargest == 0) {						// If no anagrams were found, return and empty array.
			String[] emptyArr = new String[0];
			return emptyArr;
		}
		String[] largestGroup = Arrays.copyOfRange(strArr, idxGroup, idxGroup + cntLargest + 1);
		return largestGroup;						// Return a subarray containing the largest group of anagrams.
	}
}

