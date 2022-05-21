package assignment3;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Utility class for implementing searches, specifically a binary search.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 10 September 2018
 */
public class SearchUtil {

	/**
	 * @param <T>	The type of elements contained in the list
	 * @param list	An ArrayList to search through. 
	 * 				This ArrayList is assumed to be sorted according 
	 * 				to the supplied comparator. This method has
	 * 				undefined behavior if the list is not sorted. 
	 * @param item	The item to search for
	 * @param cmp	Comparator for comparing Ts or a super class of T
	 * @return		true if "item" exists in "list", false otherwise
	 */
	public static <T> boolean binarySearch(ArrayList<T> list, T item, Comparator<? super T> cmp)
	{
		int maxIdx = list.size() - 1;
		return binarySearchRecurse(list, item, cmp, 0, maxIdx);
	}
	
	
	/**
	 * Private helper method that uses recursion for a binary search.
	 * 
	 * @param list - an ArrayList to search
	 * @param item - target of the search
	 * @param cmp - Comparator for comparing items
	 * @param l - the left most index of the sub array
	 * @param r - the right most index of the sub array
	 * @return true if the item is found in the ArrayList, false otherwise
	 */
	private static <T> boolean binarySearchRecurse(ArrayList<T> list, T item, Comparator<? super T> cmp, int l, int r) {
		
		if(l > r)
			return false;

		int mid = l + (r - l)/2;
		if(cmp.compare(item, list.get(mid)) == 0)
			return true;
		
		if(cmp.compare(item, list.get(mid)) < 0) 
			return binarySearchRecurse(list, item, cmp, l, mid - 1);
		
		if(cmp.compare(item, list.get(mid)) > 0) 
			return binarySearchRecurse(list, item, cmp, mid + 1, r);
		
		return false;
	}
}