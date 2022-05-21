package assignment11;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Represents a priority queue of generically-typed items. 
 * The queue is implemented as a min heap. 
 * The min heap is implemented implicitly as an array.
 * 
 * @author Erin Parker, Scott Crowley (u1178178), & Kressa Fox (u0620628)
 * @version 16 November 2018
 */
public class PriorityQueue<T> {
	
	private T[] minHeap;
	private int size;
	private Comparator<? super T> comp;

	/**
	 * Constructs an empty priority queue. Orders elements according
	 * to their natural ordering (i.e., T is expected to be Comparable)
	 * T is not forced to be Comparable.
	 */
	@SuppressWarnings("unchecked")
	public PriorityQueue() {
		minHeap = (T[]) new Object[10]; // safe to ignore warning
		size = 0;
		comp = null;
	}

	/**
	 * Construct an empty priority queue with a specified comparator.
	 * Orders elements according to the input Comparator (i.e., T need not
	 * be Comparable).
	 */
	@SuppressWarnings("unchecked")
	public PriorityQueue(Comparator<? super T> c) {
		minHeap = (T[]) new Object[10]; // safe to ignore warning
		size = 0;
		comp = c;
	}
	
	
	/**
	 * Adds an item to this priority queue.
	 * 
	 * (Runs in constant time.)
	 * 
	 * @param item -- the item to be inserted
	 */
	public void add(T item) {
		// If backing array has reached capacity, grow the array.
		if(size == minHeap.length)
			grow();
		// Place the new item in the next available location.
		minHeap[size] = item;
		// Increment size.
		size++;
		// Percolate up.
		percolateUp(size - 1);
	}
	
	/**
	 * @return the minimum item in this priority queue.
	 * @throws NoSuchElementException if this priority queue is empty.
	 * 
	 * (Runs in constant time.)
	 */
	public T findMin() throws NoSuchElementException {
		if(size == 0)
			throw new NoSuchElementException();
		return minHeap[0];
	}
	
	
	/**
	 * Removes and returns the minimum item in this priority queue.
	 * 
	 * @throws NoSuchElementException if this priority queue is empty.
	 * 
	 * (Runs in logarithmic time.)
	 */
	public T deleteMin() throws NoSuchElementException {
		if(size == 0)
			throw new NoSuchElementException();
		// Store the item at root (the min) in a temporary reference.
		T min = minHeap[0];
		// Move the item at the last index to the root.
		minHeap[0] = minHeap[size - 1];
		// Decrement size.
		size--;
		// Percolate down.
		percolateDown(0);
		// Return the minimum item.
		return min;
	}
	
	
	/**
	 * @return the number of items in this priority queue.
	 */
	public int size() {
		return size;
	}
	
	
	/**
	 * Makes this priority queue empty.
	 */
	public void clear() {
		size = 0;
	}
	
	
	/**
	 * Generates a DOT file for visualizing the binary heap.
	 */
	public void generateDotFile(String filename) {
		try {
			PrintWriter out = new PrintWriter(filename);
			out.println("digraph Heap {\n\tnode [shape=record]\n");

			for(int i = 0; i < size; i++) {
				out.println("\tnode" + i + " [label = \"<f0> |<f1> " + minHeap[i] + "|<f2> \"]");
				if(((i*2) + 1) < size)
					out.println("\tnode" + i + ":f0 -> node" + ((i*2) + 1) + ":f1");
				if(((i*2) + 2) < size)
					out.println("\tnode" + i + ":f2 -> node" + ((i*2) + 2) + ":f1");
			}

			out.println("}");
			out.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	
	/**
	 * Helper method doubles the capacity of the min heap.
	 */
	@SuppressWarnings("unchecked")
	private void grow() {
		T[] newHeap = (T[]) new Object[2 * minHeap.length];
		for(int idx = 0; idx < minHeap.length; idx++)
			newHeap[idx] = minHeap[idx];
		minHeap = newHeap;
	}
	
	
	/**
	 * Private helper method percolates an item up recursively until
	 * a valid location is found.
	 * 
	 * @param idx - the index of the item being moved upward
	 */
	private void percolateUp(int idx) {
		// Index math to find parent.
		int parent = (idx - 1) / 2;
		// Base case: item is at root.
		if(idx == 0)
			return;
		// Base case: Parent is less than or equal to child.
		if(compare(minHeap[idx], minHeap[parent]) >= 0)
			return;
		// Child is less than parent. Swap them.
		T temp = minHeap[idx];
		minHeap[idx] = minHeap[parent];
		minHeap[parent] = temp;
		// Recurse.
		percolateUp(parent);
	}
	
	
	/**
	 * Private helper method percolates an item down recursively until
	 * a valid location is found.
	 * 
	 * @param idx - the index of the item being moved downward
	 */
	private void percolateDown(int idx) {
		int leftChild = 2 * idx + 1;	// Index of the left child.
		int rightChild = 2 * idx + 2;	// Index of the right child.
		
		// Base Case: item is a leaf node.
		if(leftChild >= size)
			return;
		// Base Case: item's children are less than or equal to it.
		if(compare(minHeap[idx], minHeap[leftChild]) <= 0 && compare(minHeap[idx], minHeap[rightChild]) <= 0)
			return;
		// Compare which of the two children is smallest.
		int tempIdx;
		if(compare(minHeap[leftChild], minHeap[rightChild]) <= 0)
			tempIdx = leftChild;
		else
			tempIdx = rightChild;
		// Swap the parent with the smaller of its two children.
		T temp = minHeap[tempIdx];
		minHeap[tempIdx] = minHeap[idx];
		minHeap[idx] = temp;
		// Recurse.
		percolateDown(tempIdx);
	}
	
	
	/**
	 * Internal method for comparing lhs and rhs using Comparator if provided by the
	 * user at construction time, or Comparable, if no Comparator was provided.
	 */
	@SuppressWarnings("unchecked")
	private int compare(T lhs, T rhs) {
		if (comp == null)
			return ((Comparable<? super T>) lhs).compareTo(rhs); // safe to ignore warning
		// We won't test your code on non-Comparable types if we didn't supply a Comparator

		return comp.compare(lhs, rhs);
	}
	
	
	
	
	//LEAVE IN for grading purposes
	public Object[] toArray() {    
		Object[] ret = new Object[size];
		for(int i = 0; i < size; i++)
			ret[i] = minHeap[i];
		return ret;
	}
}
