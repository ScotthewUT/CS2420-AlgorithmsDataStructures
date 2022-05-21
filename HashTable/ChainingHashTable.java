package assignment10;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Represents a hash table that resolves collisions with separate chaining.
 * 
 * @author Bridger Peterson (u0971665) & Scott Crowley (u1178178)
 * @version 15 November 2018
 */
@SuppressWarnings("unchecked")
public class ChainingHashTable implements Set<String> {
	
	private static final double MAX_LOAD_FACTOR = 1.0;
	
	private HashFunctor fxn;
	private LinkedList<String>[] hashTable;
	private int size;
	private int cap;
	private int collisions;
	
	/** Constructs a hash table of the given capacity that uses the hashing function
	  * specified by the given functor.
	  */
	public ChainingHashTable(int capacity, HashFunctor functor) {
		fxn = functor;
		hashTable = new LinkedList[capacity];
		size = 0;
		cap = capacity;
		collisions = 0;
	}
	
	
	@Override
	public boolean add(String item) {
		// If the load factor crosses its threshold, or the largest LinkedList gets
		// too long, grow the hash table.
		if((double)size / cap >= MAX_LOAD_FACTOR)
			grow();
		// Call the helper method to add the String to the hash table.
		return addToHashTable(hashTable, item);
	}
	
	
	@Override
	public boolean addAll(Collection<? extends String> items) {
		int initialSize = size;
		for(String next: items)
			add(next);
		return (size > initialSize);
	}
	
	
	@Override
	public boolean contains(String item) {
		// Hash the String and "unsign" it by casting to long and adding 2.15 billion.
		long unsigned = fxn.hash(item) + 2_147_483_648L;
		// Apply the table length modulus.
		int index = (int) (unsigned % cap);
		// Get the linked list from the hashed index.
		LinkedList<String> list = hashTable[index];
		// If the list is null, the items isn't contained in the hash table. Return false.
		if(list == null)
			return false;
		// Iterate through each item in the linked list until a match is found.
		for(String next : list) {
			if(next.equals(item))
				return true;
		}
		return false;		// Item wasn't in the linked list. Return false.
	}
	
	
	@Override
	public boolean containsAll(Collection<? extends String> items) {
		for(String next : items)
			if(!contains(next))
				return false;
		return true;
	}
	
	
	@Override
	public void clear() {
		hashTable = new LinkedList[cap];
		size = 0;
		collisions = 0;
	}
	
	
	@Override
	public boolean isEmpty() {
		return(size == 0);
	}
	
	
	@Override
	public int size() {
		return size;
	}
	
	
	/**
	 * Gives the current number of collisions encountered while adding to
	 * the hash table.
	 * 
	 * @return number of collisions
	 */
	public int getCollisions() {
		return collisions;
	}
	
	/**
	 * Helper method adds the String to the provided hash table as long as it does
	 * not already contain it.
	 * 
	 * @param table - a hash table backed by an array of linked lists of Strings
	 * @param item - the String to add
	 * @return true if the String was added to the hash table; false if the table
	 * 		   already contained the String
	 */
	private boolean addToHashTable(LinkedList<String>[] table, String item) {
		// Hash the String and "unsign" it by casting to long and adding 2.15 billion.
 		long unsigned = fxn.hash(item) + 2_147_483_648L;
		// Apply the table length modulus.
		int index = (int) (unsigned % cap);
		// If no linked list exists at index, create linked list and add item.
		if(table[index] == null) {
			LinkedList<String> list = new LinkedList<String>();
			list.add(item);
			table[index] = list;
			size++;
			return true;
		}
		// Get the linked list at the hashed index.
		LinkedList<String> list = table[index];
		if(list.contains(item))			// If the linked list contains the String,
			return false;				// return false.
		collisions++;					// Increment collisions.
		list.add(item);					// Add the String.
		size++;							// Increment size.
		return true;
	}
	
	
	/**
	 * Helper method doubles the capacity of the current hash table and copies
	 * the contents over while rehashing.
	 */
	private void grow() {
		int initialSize = size;
		cap = 2 * cap;
		// Double the capacity of the hash table & create a temporary hash table to copy to.
		LinkedList<String>[] grownTable = new LinkedList[cap];
		// For each String in the current hash table, rehash it to the temp.
		for(LinkedList<String> nextLL : hashTable) {
			if(nextLL == null)
				continue;
			for(String next : nextLL) {
				addToHashTable(grownTable, next);
			}
		}
		hashTable = grownTable;
		size = initialSize;
	}
}
