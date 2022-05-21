package assignment10;

import java.util.Collection;

/**
 * Represents a hash table that resolves collisions with quadratic probing.
 * 
 * @author Bridger Peterson (u0971665) & Scott Crowley (u1178178)
 * @version 15 November 2018
 */
public class QuadProbeHashTable implements Set<String> {
	
	private HashFunctor fxn;
	private String[] hashTable;
	private int size;
	private int collisions;

	/** Constructs a hash table of the given capacity that uses the hashing function
	  * specified by the given functor.
	  */
	public QuadProbeHashTable(int capacity, HashFunctor functor) {
		fxn = functor;
		hashTable = new String[getNextPrime(capacity)];
		size = 0;
		collisions = 0;
	}

	
	@Override
	public boolean add(String item) {
		// If the load factor nears 50%, grow the hash table.
		if(size >= (hashTable.length / 2) - 1)
			grow();
		// Call private helper method to locate the correct hash table index.
		int index = quadraticProbe(hashTable, item);
		// If quadraticProbe() returned -1, the String already appears in the table.
		if(index < 0)
			return false;
		// Place the String in the correct index, increment size, and return true;
		hashTable[index] = item;
		size++;
		return true;
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
		// Collisions only apply to the add method.
		int col = collisions;
		// If the String is found in the table, quadraticProbe will return -1.
		boolean contained = (quadraticProbe(hashTable, item) == -1);
		// Reset collision counter.
		collisions = col;
		return contained;
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
		int cap = hashTable.length;
		hashTable = new String[cap];
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
	 * Implements a quadratic probing method to locate a valid index for the
	 * supplied String.
	 * 
	 * @param table - the hash table to probe
	 * @param item - String to find an index for
	 * @return the index where the String may be placed or -1 if it is already
	 * 		   contained in the table
	 */
	private int quadraticProbe(String[] table, String item) {
		// Hash the String and "unsign" it by casting to long and adding 2.15 billion.
		long unsigned = fxn.hash(item) + 2_147_483_648L;
		// Apply the table length modulus.
		int initial = (int) (unsigned % table.length);
		// Check if the initial index is valid.
		if(table[initial] == null)
			return initial;
		// Both the initial index & the one that is currently being checked are needed.
		int idx = initial;
		// While the current index is not null, increase the index by the quadratic
		// method:  initial index + iteration^2.
		for(int i = 1; table[idx] != null; i++) {
			// If the current index contains the same String, break & return -1.
			if(table[idx].equals(item))
				return -1;
			idx = initial;
			int quad = i * i;
			idx = (idx + quad) % table.length;
		}
		collisions++;
		return idx;
	}
	
	
	/**
	 * Helper method doubles the capacity of the current hash table and copies
	 * the contents over while rehashing.
	 */
	private void grow() {
		// Double the capacity of the hash table and find the next prime to
		// use as the new capacity. Create a temporary hash table to copy to.
		String[] grownTable = new String[getNextPrime(2 * hashTable.length)];
		// For each String in the current hash table, rehash it to the temp.
		for(String next : hashTable) {
			if(next == null)
				continue;
			grownTable[quadraticProbe(grownTable, next)] = next;
		}
		hashTable = grownTable;
	}
	
	
	/**
	 * Finds the next largest prime number. This helper method is necessary
	 * because quadratic probing relies on the hash table capacity being a
	 * prime number to prevent infinite loops.
	 * 
	 * @param num - an int to start from
	 * @return the next prime number greater than the parameter
	 */
	private int getNextPrime(int num) {
		boolean prime = false;
		int result = num;
		if(num % 2 == 0)
			result--;
		while(!prime && result < num * 2) {
			result += 2;
			prime = true;
			for(int i = 2; i < result; i++) {
				if(result % i == 0) {
					prime = false;
					break;
				}
			}
		}
		return result;
	}
}
