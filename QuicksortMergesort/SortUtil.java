package assignment5;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Utility class for implementing sorting algorithms on generic ArrayLists,
 * including recursive versions of merge sort and quick sort and an
 * iterative insertion sort. The public methods are drivers that call private
 * helper methods.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 27 September 2018
 */
public class SortUtil {
	
	// The size of sublist where merge sort will switch to insertion sort.
	private static final int THRESHOLD = 30;
	// Select a pivot method: 1) middle index, 2) random index, 3) median of three,
	// 4) median of five, or 5) median of bot, mid, & top is the pivot.
	private static int quicksortPivot = 5;
	
	/**
	 * Driver method that calls a recursive merge sort.
	 * 
	 * @param list - a generic ArrayList to sort
	 * @param comp - a Comparator to sort by
	 */
	public static <T> void mergesort(ArrayList<T> list, Comparator<? super T> comp) {
		// Lists of 0-1 items are already sorted.
		if(list.size() < 2)
			return;
		// Create a storage list to temporarily hold items while merging.
		ArrayList<T> temp = new ArrayList<T>(list.size());
		for(int i = 0; i < list.size(); i++)
			temp.add(null);
		
		int bot = 0, top = list.size() - 1;
		
		mergesortRecurse(temp, bot, top, list, comp);
	}
	
	
	/**
	 * Driver method that calls a recursive quicksort.
	 * 
	 * @param list - a generic ArrayList to sort
	 * @param comp - a Comparator to sort by
	 */
	public static <T> void quicksort(ArrayList<T> list, Comparator<? super T> comp) {
		// Lists of 0-1 items are already sorted.
		if(list.size() < 2)
			return;
		
		int bot = 0, top = list.size() - 1;
		
		// Pick a pivot method and call its quicksort recursion.
		switch(quicksortPivot) {
		case 1:									// 1) The item at the middle is the pivot.
			quicksortRecursePivotMid(list, bot, top, comp);
			break;
		case 2:									// 2) A random item is the pivot.
			quicksortRecursePivotRand(list, bot, top, comp);
			break;
		case 3:									// 3) The median of three random items is the pivot.
			quicksortRecursePivotBestOfThree(list, bot, top, comp);
			break;
		case 4:									// 4) The median of five sorted items is the pivot.
			quicksortRecursePivotBestOfFive(list, bot, top, comp);
			break;
		case 5:									// 5) The median of bot, mid, & top is the pivot.
			quicksortRecursePivotBestOfThreeV2(list, bot, top, comp);
			break;
		default:
			System.out.println("ERROR: Invalid quicksortPivot value. Must be 1, 2, or 3.");
			return;
		}
	}
	
	
	/**
	 * Driver method that calls an iterative insertion sort.
	 * 
	 * @param list - a generic ArrayList to sort
	 * @param comp - a Comparator to sort by
	 */
	public static <T> void insertionSort(ArrayList<T> list, Comparator<? super T> comp) {
		// Lists of 0-1 items are already sorted.
		if(list.size() < 2)
			return;
		
		int bot = 0, top = list.size() - 1;
		
		insertionSortIterative(list, bot, top, comp);
	}
	
	
	/**
	 * Creates an ArrayList of Integers from 1 to N, ascending.
	 * 
	 * @param size - the size of the list
	 * @return an ArrayList of sorted Integers
	 */
	public static ArrayList<Integer> generateBestCase(int size){
		ArrayList<Integer> intListAscend = new ArrayList<Integer>(size);
		for(int idx = 0; idx < size; idx++)
			intListAscend.add(idx + 1);
		return intListAscend;
	}
	
	
	/**
	 * Creates an ArrayList of Integers from 1 to N, shuffled.
	 * 
	 * @param size - the size of the list
	 * @return and ArrayList of shuffled Integers
	 */
	public static ArrayList<Integer> generateAverageCase(int size) {
		Random rand = new Random(1);
		ArrayList<Integer> intListRand = generateBestCase(size);
		for(int idx = 0; idx < size; idx++) {
			int swap = rand.nextInt(size);
			int hold = intListRand.get(idx);
			intListRand.set(idx, intListRand.get(swap));
			intListRand.set(swap, hold);
		}
		return intListRand;
	}
	
	
	/**
	 * Creates an ArrayList of Integers from N to 1, descending.
	 * 
	 * @param size - the size of the list
	 * @return and ArrayList of reverse sorted Integers
	 */
	public static ArrayList<Integer> generateWorstCase(int size) {
		ArrayList<Integer> intListDesc = new ArrayList<Integer>(size);
		for(int i = size; i > 0; i--)
			intListDesc.add(i);
		return intListDesc;
	}

	
	/**
	 * Implements a recursive merge sort. Switches to an iterative insertion sort at a specified threshold.
	 * 
	 * @param temp - a temporary ArrayList for overhead storage
	 * @param bot - the bottom index of the sublist
	 * @param top - the top index of the sublist
	 * @param list - a generic ArrayList to sort
	 * @param comp - a Comparator to sort by
	 */
	private static <T> void mergesortRecurse(ArrayList<T> temp, int bot, int top, ArrayList<T> list, Comparator<? super T> comp) {
		// Base case. A list of 1 item is sorted. Begin merging.
		if(bot >= top)
			return;
		// When threshold is reached, call insertion sort on the remaining sublist.
		if(top - bot < THRESHOLD) {
			insertionSortIterative(list, bot, top, comp);
			return;
		}
		
		int mid = 1 + (top + bot) / 2;
		// Split the list into two sublists and recurse.
		mergesortRecurse(temp, bot, mid - 1, list, comp);
		mergesortRecurse(temp, mid, top, list, comp);
		
		merge(temp, bot, mid, top, list, comp);
	}
	
	
	/**
	 * Private helper method that merges two sorted sublists together while sorting.
	 * It is called by mergesortRecurse().
	 * 
	 * @param temp - a temporary ArrayList for overhead storage
	 * @param bot - the bottom index of the left-hand sublist
	 * @param mid - the middle index, also the bottom index of the right-hand sublist
	 * @param top - the top index of the right-hand sublist
	 * @param list - a generic ArrayList to sort
	 * @param comp - a Comparator to sort by
	 */
	private static <T> void merge(ArrayList<T> temp, int bot, int mid, int top, ArrayList<T> list, Comparator<? super T> comp) {
		int lhsListIdx = bot, rhsListIdx = mid, tempListIdx = bot;
		// Until one of the sublists has been iterated through completely, compare items
		// from the left-hand sublist to the right-hand placing the lesser of each into
		// the temp list. Once either sublist has been fully compared, break.
		while(lhsListIdx < mid && rhsListIdx <= top) {
			if(comp.compare(list.get(lhsListIdx), list.get(rhsListIdx)) < 0) {
				temp.set(tempListIdx, list.get(lhsListIdx));
				tempListIdx++;
				lhsListIdx++;
			} else {
				temp.set(tempListIdx, list.get(rhsListIdx));
				tempListIdx++;
				rhsListIdx++;
			}	
		}
		// If the left-hand sublist hadn't been fully compared above, copy the remaining
		// items to the temp list.
		if(lhsListIdx < mid) {
			while(lhsListIdx < mid) {
				temp.set(tempListIdx, list.get(lhsListIdx));
				tempListIdx++;
				lhsListIdx++;
			}
		// If the right-hand sublist hadn't been fully compared above, copy the remaining
		// items to the temp list.
		} else if(rhsListIdx <= top) {
			while(rhsListIdx <= top) {
				temp.set(tempListIdx, list.get(rhsListIdx));
				tempListIdx++;
				rhsListIdx++;
			}
		}
		// Move the items in temp back to the primary array list.
		for(int idx = bot; idx <= top; idx++) 
			list.set(idx,  temp.get(idx));
	}
	
	
	/**
	 * Implements a recursive quicksort. The middle item of the list is the pivot.
	 * Switches to an iterative insertion sort at a specified threshold.
	 * 
	 * @param list - a generic ArrayList to sort
	 * @param bot - the bottom index of the sublist
	 * @param top - the top index of the sublist
	 * @param comp - a Comparator to sort by
	 */
	private static <T> void quicksortRecursePivotMid(ArrayList<T> list, int bot, int top, Comparator<? super T> comp) {
		// Base case. A list of 1 item is already sorted.
		if(bot >= top)
			return;
		// When threshold is reached, call insertion sort on the remaining sublist.
		if(top - bot < THRESHOLD) {
			insertionSortIterative(list, bot, top, comp);
			return;
		}
		
		int pivot = (bot + top) / 2;
		// Put the pivot at the top, out of the way.
		Collections.swap(list, pivot, top);
		pivot = top;
		
		int lhsListIdx = bot, rhsListIdx = top - 1;
		// Until the left-hand and right-hand indices meet...
		while(lhsListIdx <= rhsListIdx) {
			// Move the left-hand index right until an a swap candidate is found.
			while(lhsListIdx <= rhsListIdx && comp.compare(list.get(lhsListIdx), list.get(pivot)) <= 0)
				lhsListIdx++;
			// Move the right-hand index to the left until a swap candidate is found.
			while(rhsListIdx >= lhsListIdx && comp.compare(list.get(rhsListIdx), list.get(pivot)) >= 0)
				rhsListIdx--;
			// Swap the candidates.
			if(lhsListIdx <= rhsListIdx) {
				Collections.swap(list, lhsListIdx, rhsListIdx);
				lhsListIdx++;
				rhsListIdx--;
			}
		}	// Move the pivot to its final location.
		Collections.swap(list, lhsListIdx, pivot);
		pivot = lhsListIdx;
		
		quicksortRecursePivotMid(list, bot, pivot - 1, comp);
		quicksortRecursePivotMid(list, pivot + 1, top, comp);
	}
	
	
	/**
	 * Implements a recursive quicksort. A random item is the pivot.
	 * Switches to an iterative insertion sort at a specified threshold.
	 * 
	 * @param list - a generic ArrayList to sort
	 * @param bot - the bottom index of the sublist
	 * @param top - the top index of the sublist
	 * @param comp - a Comparator to sort by
	 */
	private static <T> void quicksortRecursePivotRand(ArrayList<T> list, int bot, int top, Comparator<? super T> comp) {
		// Base case. A list of 1 item is already sorted.
		if(bot >= top)
			return;
		// When threshold is reached, call insertion sort on the remaining sublist.
		if(top - bot < THRESHOLD) {
			insertionSortIterative(list, bot, top, comp);
			return;
		}
		// Set the pivot as a random item from the sublist.
		Random rand = new Random(System.currentTimeMillis());
		int pivot = rand.nextInt(top - bot + 1) + bot;
		// Put the pivot at the top, out of the way.
		Collections.swap(list, pivot, top);
		pivot = top;

		int lhsListIdx = bot, rhsListIdx = top - 1;
		// Until the left-hand and right-hand indices meet...
		while(lhsListIdx <= rhsListIdx) {
			// Move the left-hand index right until an a swap candidate is found.
			while(lhsListIdx <= rhsListIdx && comp.compare(list.get(lhsListIdx), list.get(pivot)) <= 0)
				lhsListIdx++;
			// Move the right-hand index to the left until a swap candidate is found.
			while(rhsListIdx >= lhsListIdx && comp.compare(list.get(rhsListIdx), list.get(pivot)) >= 0)
				rhsListIdx--;
			// Swap the candidates.
			if(lhsListIdx <= rhsListIdx) {
				Collections.swap(list, lhsListIdx, rhsListIdx);
				lhsListIdx++;
				rhsListIdx--;
			}
		}	// Move the pivot to its final location.
		Collections.swap(list, lhsListIdx, pivot);
		pivot = lhsListIdx;
		
		quicksortRecursePivotRand(list, bot, pivot - 1, comp);
		quicksortRecursePivotRand(list, pivot + 1, top, comp);
	}
	
	
	/**
	 * Implements a recursive quicksort. The median of three random items is the pivot.
	 * Switches to an iterative insertion sort at a specified threshold.
	 * 
	 * @param list - a generic ArrayList to sort
	 * @param bot - the bottom index of the sublist
	 * @param top - the top index of the sublist
	 * @param comp - a Comparator to sort by
	 */
	private static <T> void quicksortRecursePivotBestOfThree(ArrayList<T> list, int bot, int top, Comparator<? super T> comp) {
		// Base case. A list of 1 item is already sorted.
		if(bot >= top)
			return;
		// When threshold is reached, call insertion sort on the remaining sublist.
		if(top - bot < THRESHOLD) {
			insertionSortIterative(list, bot, top, comp);
			return;
		}
		int pivot = medianOfThree(list, bot, top, comp);
		// Put the pivot at the top, out of the way.
		Collections.swap(list, pivot, top);
		pivot = top;

		int lhsListIdx = bot, rhsListIdx = top - 1;
		// Until the left-hand and right-hand indices meet...
		while(lhsListIdx <= rhsListIdx) {
			// Move the left-hand index right until an a swap candidate is found.
			while(lhsListIdx <= rhsListIdx && comp.compare(list.get(lhsListIdx), list.get(pivot)) <= 0)
				lhsListIdx++;
			// Move the right-hand index to the left until a swap candidate is found.
			while(rhsListIdx >= lhsListIdx && comp.compare(list.get(rhsListIdx), list.get(pivot)) >= 0)
				rhsListIdx--;
			// Swap the candidates.
			if(lhsListIdx <= rhsListIdx) {
				Collections.swap(list, lhsListIdx, rhsListIdx);
				lhsListIdx++;
				rhsListIdx--;
			}
		}	// Move the pivot to its final location.
		Collections.swap(list, lhsListIdx, pivot);
		pivot = lhsListIdx;
		
		quicksortRecursePivotBestOfThree(list, bot, pivot - 1, comp);
		quicksortRecursePivotBestOfThree(list, pivot + 1, top, comp);
	}
	
	
	/**
	 * Private helper method for quicksortRecursePivotBestOfThree(). Picks three random
	 * indices in the sublist, finds the median, and returns its index to use as a pivot.
	 * 
	 * NOTE: This implementation feels bad. It seems expensive to make three calls to
	 * Random.nextInt(), build an ArrayList, call ArrayList.get() up to seven times,
	 * run an insertion sort on three items, and make a couple more comparisons. Plus,
	 * this happens every single recursion.
	 * 
	 * @param list - the generic ArrayList that quicksort is working on
	 * @param bot - bottom index of the sublist
	 * @param top - top index of the sublist
	 * @param comp - a Comparator to sort by
	 * @return the index to use as a pivot
	 */
	private static <T> int medianOfThree(ArrayList<T> list, int bot, int top, Comparator<? super T> comp) {
		Random rand = new Random(System.currentTimeMillis());
		
		// Pick three indices from the sublist at random.
		int idxA = rand.nextInt(top - bot + 1) + bot;
		int idxB = rand.nextInt(top - bot + 1) + bot;
		int idxC = rand.nextInt(top - bot + 1) + bot;
		
		// Create a 3-item list with items from the selected indices then sort it.
		ArrayList<T> medianList = new ArrayList<T>();
		medianList.add(list.get(idxA));
		medianList.add(list.get(idxB));
		medianList.add(list.get(idxC));
		insertionSortIterative(medianList, 0, 2, comp);
		
		// Determine which index corresponds to the median and return it.
		if(comp.compare(medianList.get(1), list.get(idxA)) == 0) {
			return idxA;
		} else if(comp.compare(medianList.get(1), list.get(idxB)) == 0) {
			return idxB;
		} else {
			return idxC;
		}
	}
	
	
	/**
	 * Implements a recursive quicksort. The median of five items from a sublist is the pivot.
	 * Picking the pivot sorts that sublist. Switches to an iterative insertion sort at a
	 * specified threshold.
	 * 
	 * @param list - a generic ArrayList to sort
	 * @param bot - the bottom index of the sublist
	 * @param top - the top index of the sublist
	 * @param comp - a Comparator to sort by
	 */
	private static <T> void quicksortRecursePivotBestOfFive(ArrayList<T> list, int bot, int top, Comparator<? super T> comp) {
		// Base case. A list of 1 item is already sorted.
		if(bot >= top)
			return;
		// When threshold is reached, call insertion sort on the remaining sublist.
		if(top - bot < THRESHOLD) {
			insertionSortIterative(list, bot, top, comp);
			return;
		}
		int pivot = medianOfFive(list, bot, top, comp);
		// Put the pivot at the top, out of the way.
		Collections.swap(list, pivot, top);
		pivot = top;

		int lhsListIdx = bot, rhsListIdx = top - 1;
		// Until the left-hand and right-hand indices meet...
		while(lhsListIdx <= rhsListIdx) {
			// Move the left-hand index right until an a swap candidate is found.
			while(lhsListIdx <= rhsListIdx && comp.compare(list.get(lhsListIdx), list.get(pivot)) <= 0)
				lhsListIdx++;
			// Move the right-hand index to the left until a swap candidate is found.
			while(rhsListIdx >= lhsListIdx && comp.compare(list.get(rhsListIdx), list.get(pivot)) >= 0)
				rhsListIdx--;
			// Swap the candidates.
			if(lhsListIdx <= rhsListIdx) {
				Collections.swap(list, lhsListIdx, rhsListIdx);
				lhsListIdx++;
				rhsListIdx--;
			}
		}	// Move the pivot to its final location.
		Collections.swap(list, lhsListIdx, pivot);
		pivot = lhsListIdx;
		
		quicksortRecursePivotBestOfThree(list, bot, pivot - 1, comp);
		quicksortRecursePivotBestOfThree(list, pivot + 1, top, comp);
	}
	
	
	/**
	 * Private helper method for quicksortRecursePivotBestOfFive(). Picks a random set of
	 * five items in a row from a sublist, sorts them, and returns the index of the median.
	 * 
	 * @param list - the generic ArrayList that quicksort is working on
	 * @param bot - bottom index of the sublist
	 * @param top - top index of the sublist
	 * @param comp - a Comparator to sort by
	 * @return the index to use as a pivot
	 */
	private static <T> int medianOfFive(ArrayList<T> list, int bot, int top, Comparator<? super T> comp) {
		Random rand = new Random(System.currentTimeMillis());
		// Pick a random index which will mark the location of the sublist.
		int idxOfFive = rand.nextInt((top - bot) - 4) + bot;
		// Sort the sublist.
		insertionSortIterative(list, idxOfFive, idxOfFive + 4, comp);
		// The middle item is the median and the new pivot.
		return idxOfFive + 2;
	}
	
	
	/**
	 * Implements a recursive quicksort. The median of the top, middle, and bottom
	 * is the pivot. Switches to an iterative insertion sort at a specified threshold.
	 * 
	 * @param list - a generic ArrayList to sort
	 * @param bot - the bottom index of the sublist
	 * @param top - the top index of the sublist
	 * @param comp - a Comparator to sort by
	 */
	private static <T> void quicksortRecursePivotBestOfThreeV2(ArrayList<T> list, int bot, int top, Comparator<? super T> comp) {
		// Base case. A list of 1 item is already sorted.
		if(bot >= top)
			return;
		// When threshold is reached, call insertion sort on the remaining sublist.
		if(top - bot < THRESHOLD) {
			insertionSortIterative(list, bot, top, comp);
			return;
		}
		// Find the median of bot, mid, & top.
		int mid = (bot + top) / 2;
		if(comp.compare(list.get(bot), list.get(top)) > 0)
			Collections.swap(list, bot, top);
		if(comp.compare(list.get(bot), list.get(mid)) > 0)
			Collections.swap(list, bot, mid);
		if(comp.compare(list.get(mid), list.get(top)) > 0)
			Collections.swap(list, mid, top);
		int pivot = top;
				
		int lhsListIdx = bot, rhsListIdx = top - 1;
		// Until the left-hand and right-hand indices meet...
		while(lhsListIdx <= rhsListIdx) {
			// Move the left-hand index right until an a swap candidate is found.
			while(lhsListIdx <= rhsListIdx && comp.compare(list.get(lhsListIdx), list.get(pivot)) <= 0)
				lhsListIdx++;
			// Move the right-hand index to the left until a swap candidate is found.
			while(rhsListIdx >= lhsListIdx && comp.compare(list.get(rhsListIdx), list.get(pivot)) >= 0)
				rhsListIdx--;
			// Swap the candidates.
			if(lhsListIdx <= rhsListIdx) {
				Collections.swap(list, lhsListIdx, rhsListIdx);
				lhsListIdx++;
				rhsListIdx--;
			}
		}	// Move the pivot to its final location.
		Collections.swap(list, lhsListIdx, pivot);
		pivot = lhsListIdx;
		
		quicksortRecursePivotBestOfThreeV2(list, bot, pivot - 1, comp);
		quicksortRecursePivotBestOfThreeV2(list, pivot + 1, top, comp);
	}
	
	
	/**
	 * Implements an iterative insertion sort. Used as a switch-over for merge sort and
	 * quicksort when a specified threshold is reached.
	 * 
	 * @param list - a generic ArrayList to sort
	 * @param bot - the bottom index of the sublist
	 * @param top - the top index of the sublist
	 * @param comp - a Comparator to sort by
	 */
	private static <T> void insertionSortIterative(ArrayList<T> list, int bot, int top, Comparator<? super T> comp) {
		
		for(int i = bot + 1; i <= top; i++) {
			// Pick up the first element in the unsorted portion of the list.
			T next = list.get(i);
			int j = i;
			// Compare it to each element to the left (the sorted portion of the list)
			// while shifting each element to the right until swaps are no longer needed
			// or the beginning of the list is reached.
			while(j > bot && comp.compare(list.get(j - 1), next) > 0) {
				list.set(j, list.get(j - 1));
				j--;
			}
			// Set the element down in the sorted portion of the array.
			list.set(j, next);
		}
	}
}
