package assignment3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.IllegalStateException;

/**
 * Implements the Collection interface using an array as storage.
 * The array must grow as needed.
 * An ArrayCollection can not contain duplicates.
 * All methods should be implemented as defined by the Java API, unless otherwise specified.
 *
 * @author Daniel Kopta, Scott Crowley (u1178178), & Kressa Fox (u0620628)
 * @version 10 September 2018
 * @param <T> - generic type placeholder
 */
public class ArrayCollection<T> implements Collection<T> {

	private T data[];	// Storage for the items in the collection
	private int size;	// Keep track of how many items this collection holds

	// There is no clean way to convert between T and Object, so we suppress the warning.
	@SuppressWarnings("unchecked")  
	public ArrayCollection()
	{
		size = 0;
		// We can't instantiate an array of unknown type T, so we must create an Object array, and typecast
		data = (T[]) new Object[10];	// Start with an initial capacity of 10
	}
	
	
	/**
	 * Adds the argument to to the collection at the first empty index. If the collection already
	 * contains that item, it is not added. When the backing array's capacity is reached, make a
	 * call to ArrayCollection.grow().
	 * 
	 * @param arg0 - A generic-typed element to be added to the 
	 * @return false if the argument is found in the collection, otherwise true
	 */
	public boolean add(T arg0) {
		if(this.contains(arg0))		// If the item is found in the collection, return false.
			return false;
		if(size >= data.length)		// If data's capacity has been reached, grow it.
			this.grow();
		data[size] = arg0;
		size++;
		return true;
	}
	
	
	/**
	 * Adds all items from the argument Collection to the ArrayCollection that
	 * aren't already contained in the ArrayCollection.
	 * 
	 * @param arg0 - a collection of generic items
	 * @return true if at least one item was added, false otherwise
	 */
	public boolean addAll(Collection<? extends T> arg0) {
		int startSize = size;
		Iterator<? extends T> iter = arg0.iterator();
		while(iter.hasNext()) {
			this.add(iter.next());
		}
		return(size > startSize);
	}
	
	
	/**
	 * Clears all data from the collection by setting its size to 0 allowing all elements
	 * to be overwritten. We've opted to allow the backing array's capacity to remain grown
	 * (if applicable) as this collection will likely be used for similarly-sized purposes.
	 */
	public void clear() {
		size = 0;
	}
	
	
	/**
	 * Searches the collection for the specified Object by calling its .equals() method.
	 * 
	 * @param arg0 - a generic Object to search the collection for
	 * @return true if the collection contains the specified Object, false otherwise
	 */
	public boolean contains(Object arg0) {
		for(int idx = 0; idx < size; idx++) {
			if(arg0.equals(data[idx]))
				return true;
		}
		return false;
	}
	
	
	/**
	 * Checks each item in the argument Collection for matches in the ArrayCollection.
	 * If any item isn't found, return false.
	 * 
	 * @param arg0 - a Collection of generic items
	 * @return true if all items are contained in ArrayCollection, otherwise false
	 */
	public boolean containsAll(Collection<?> arg0) {
		Iterator<?> iter = arg0.iterator();
		while(iter.hasNext()) {
			if(!this.contains(iter.next()))
				return false;
		}
		return true;
	}
	
	
	/**
	 * @return true if size is 0, false otherwise
	 */
	public boolean isEmpty() {
		return(size == 0);
	}
	
	
	/**
	 * Gives the user access to initialize an iterator for the collection.
	 * 
	 * @return an iterator for the ArrayCollection
	 */
	public Iterator<T> iterator() {
		return new ArrayCollectionIterator();
	}
	
	
	/**
	 * Removes the specified item from the ArrayCollection.
	 * 
	 * @param arg0 - the item to remove
	 * @return true as long as the item was found and removed, otherwise false
	 */
	public boolean remove(Object arg0) {
		int startSize = size;
		int idx;
		for(idx = 0; idx < size; idx++) {
			if(arg0.equals(data[idx])) {
				size--;
				break;
			}
		}
		while(idx < size) {
			data[idx] = data[idx + 1];
			idx++;
		}
		return(size < startSize);
	}
	
	
	/**
	 * Checks each item in the argument Collection and removes it from the
	 * ArrayCollection (if found).
	 * 
	 * @param arg0 - a Collection of generic items to remove
	 * @return true if at least one item was removed from the ArrayCollection
	 */
	public boolean removeAll(Collection<?> arg0) {
		int startSize = size;
		Iterator<?> iter = arg0.iterator();
		while(iter.hasNext()) {
			this.remove(iter.next());
		}
		return(size < startSize);
	}
	
	
	/**
	 * TODO
	 * 
	 * @param arg0
	 * @return
	 */
	public boolean retainAll(Collection<?> arg0) {
		int startSize = size;
		Iterator<T> iter = this.iterator();
		while(iter.hasNext()) {
			if(!arg0.contains(iter.next()))
				iter.remove();
		}
		return (size < startSize);
	}
	
	
	/**
	 * @return the size of the collection
	 */
	public int size() {
		return size;
	}
	
	
	/**
	 * Creates an Object array the same size as the collection and copies all the
	 * items into it.
	 * 
	 * @return an Object array of the items contained in the collection 
	 */
	public Object[] toArray() {
		Object[] objArr = new Object[size];
		for(int idx = 0; idx < size; idx++) {
			objArr[idx] = data[idx];
		}
		return objArr;
	}
	
	
	/**
	 * Allows the user to create an array from the collection that isn't necessarily Object-typed.
	 * For example: an ArrayCollection<Circles> could be copied to Circles[] instead of Object[].
	 * NOTE: If the argument is large enough, it will be overwritten with the collection's data.
	 * Otherwise, a new array will be created and returned.
	 * 
	 * @param arg0 - an array of user-specified type
	 * @return an array of the items contained in the collection of the same type as the argument
	 */
	@SuppressWarnings({ "unchecked", "hiding" })
	public <T> T[] toArray(T[] arg0) {
		
		if(arg0.length >= size) {
			for(int idx = 0; idx < size; idx++) 			// If the array passed in is large enough,
				arg0[idx] = (T) data[idx];					// just reuse it by copying in all of data's
			for(int idx = size; idx < arg0.length; idx++) 	// items and setting any remaining indices
				arg0[idx] = null;							// to null.
			return arg0;
		}
		T[] genericArr = (T[]) new Object[size];			// Create a new array of the specified type
		for(int idx = 0; idx < size; idx++) {				// just large enough to hold all of the
			genericArr[idx] = (T) data[idx];				// collection's data and copy it over before
		}													// returning the new array.
		return genericArr;
	}
	
	
	/**
	 * Creates a sorted ArrayList from the collection by use of Selection Sort.
	 * NOTE: We suspect this method has O(n^2 + n) time complexity. Gross.
	 * 
	 * @param cmp - the Comparator to sort by
	 * @return a sorted ArrayList
	 */
	public ArrayList<T> toSortedList(Comparator<? super T> cmp)
	{
		ArrayList<T> sortedList = new ArrayList<T>();
		for(int idx = 0; idx < size; idx++)					// Create an ArrayList copy of the collection.
			sortedList.add(data[idx]);
		
		/* SELECTION SORT */
		for(int idxL = 0; idxL < size - 1; idxL++) {
			int idxR, idxMin;
			// Call the first item in the unsorted portion of the list the minimum and compare against each
			// item to its right, updating minimum when found.
			for(idxR = idxL + 1, idxMin = idxL; idxR < size; idxR++) {
				if(cmp.compare(sortedList.get(idxMin), sortedList.get(idxR)) > 0)
					idxMin = idxR;
			}
			// When the end of the list is reached, swap the minimum with the first item and call it sorted.
			T min = sortedList.get(idxMin);
			sortedList.set(idxMin, sortedList.get(idxL));
			sortedList.set(idxL, min);
			// The item to the right of the swap is now the first item in the unsorted portion of the list.
		}
		return sortedList;
	}
	
	
	/**
	 * This is a helper function specific to ArrayCollection
	 * Doubles the size of the data storage array, retaining its current contents.
	 */
	@SuppressWarnings("unchecked")
	private void grow()
	{
		T[] grownArr = (T[]) new Object[size * 2];	// Initialize a new generic array with twice the current capacity.
		for(int idx = 0; idx < size; idx++) {
			grownArr[idx] = data[idx];				// Copy the current data into the larger array.
		}
		data = grownArr;							// Reassign the member array to the grown copy.
	}

	
	/**
	 * Private class implements an iterator for ArrayCollection.
	 * ArrayCollection.iterator() must be called to initialize.
	 */
	private class ArrayCollectionIterator implements Iterator<T>
	{
		int loc;				// The location of the iterator.
		boolean nextCalled;		// Tracks if .next() has been called.
		
		/**
		 * Default constructor.
		 */
		public ArrayCollectionIterator()
		{
			loc = 0;
			nextCalled = false;
		}
		
		/**
		 * @return true as long as the iterator's location hasn't reached the end of the collection
		 */
		public boolean hasNext() {
			return(loc < size);
		}
		
		/**
		 * @return the next item in the collection
		 */
		public T next() throws NoSuchElementException {
			if(this.hasNext()) {
				nextCalled = true;
				loc++;
				return data[loc - 1];
			}
			throw new NoSuchElementException("hasNext() returned false");
		}
		
		/**
		 * Removes that last item returned by the iterator. May only be called after a call to .next().
		 */
		public void remove() throws IllegalStateException {
			if(!nextCalled)
				throw new IllegalStateException("next() has not been called");
			nextCalled = false;
			ArrayCollection.this.remove(data[loc - 1]);
			loc--;
		}
	}
}
