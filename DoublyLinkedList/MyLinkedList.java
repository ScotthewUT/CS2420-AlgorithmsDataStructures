package assignment6;

import java.util.NoSuchElementException;
@SuppressWarnings("unchecked")

/**
 * Class representation of a generic, doubly-linked listed. Implements the List interface.
 * The list is composed of nodes which can hold generic types and also contain pointers to
 * the preceding and succeeding nodes.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 2 October 2018
 */
public class MyLinkedList<T> implements List<T> {
	// Internal private class serves both as a container for our data as well as a pointer
	// to the node it follows and the next node.
	private class Node {
		public T data;
		public Node next;
		public Node prev;
		
		public Node(T element) {
			data = element;
			next = null;
			prev = null;
		}
	}
	
	private Node head;	// The first node in the list.
	private Node tail;	// The second node in the list.
	private int size;	// The number of elements in the list.
	
	public MyLinkedList() {
		head = null;
		tail = null;
		size = 0;
	}
	
	// Overloaded constructor for creating a clone of another MyLinkedList.
	public MyLinkedList(MyLinkedList<T> other) {
		head = null;
		tail = null;
		size = 0;
		Node temp = other.head;
		while(temp.next != null) {
			T element = temp.data;
			this.addLast(element);
			temp = temp.next;
		}
		T element = temp.data;
		this.addLast(element);
	}
	

	/**
	 * Inserts the specified element at the beginning of the list.
	 * O(1) for a doubly-linked list.
	 * 
	 * @param element - the item to add
	 */
	@Override
	public void addFirst(T element) {
		Node added = new Node(element);
		if(size > 0) {
			added.next = head;
			head.prev = added;
		}
		if(size == 0)
			tail = added;
		head = added;
		size++;
	}

	
	/**
	 * Inserts the specified element at the end of the list.
	 * O(1) for a doubly-linked list.
	 * 
	 * @param element - the item to add
	 */
	@Override
	public void addLast(T element) {
		Node added = new Node( element);
		if(size > 0) {
			added.prev = tail;
			tail.next = added;
		}
		if(size == 0)
			head = added;
		tail = added;
		size++;
	}
	
	
	/**
	 * Inserts the specified element at the specified position in the list.
	 * Throws IndexOutOfBoundsException if index is out of range (index < 0 || index > size())
	 * O(N) for a doubly-linked list.
	 * 
	 * @param index - the location to insert the item
	 * @param element - the item to add
	 */
	@Override
	public void add(int index, T element) throws IndexOutOfBoundsException {
		
		if(index == 0) {
			this.addFirst(element);
			return;
		} else if(index == size) {
			this.addLast(element);
			return;
		} else if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException("ERROR: Attempted to place element at index "
								  				+ index + ". Valid range is 0-" + (size - 1) + ".");
		}
		
		Node insert = new Node(element);
		if(index <= size / 2) {
			insert.next = head.next;
			for(int pnt = 1; pnt < index; pnt++){
				insert.next = insert.next.next;
			}
			insert.prev = insert.next.prev;
		} else if(index > size / 2) {
			insert.prev = tail.prev;
			for(int pnt = size - 1; pnt > index; pnt--) {
				insert.prev = insert.prev.prev;
			}
			insert.next = insert.prev.next;
		} 
		insert.prev.next = insert;
		insert.next.prev = insert;
		size++;
	}
	
	
	/**
	 * Returns the first element in the list.
	 * Throws NoSuchElementException if the list is empty.
	 * O(1) for a doubly-linked list.
	 * 
	 * @return the first element in the list
	 */
	@Override
	public T getFirst() throws NoSuchElementException {
		if(size < 1)
			throw new NoSuchElementException("ERROR: No such element.");
		return head.data;
	}
	
	
	/**
	 * Returns the last element in the list.
	 * Throws NoSuchElementException if the list is empty.
	 * O(1) for a doubly-linked list.
	 * 
	 * @return the last element in the list
	 */
	@Override
	public T getLast() throws NoSuchElementException {
		if(size < 1)
			throw new NoSuchElementException("ERROR: No such element.");
		return tail.data;
	}
	
	
	/**
	 * Returns the element at the specified position in the list.
	 * Throws IndexOutOfBoundsException if index is out of range (index < 0 || index >= size())
	 * O(N) for a doubly-linked list.
	 * 
	 * @param index - the location of desired element
	 * @return the element at the index specified
	 */
	@Override
	public T get(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index > size - 1)
			throw new IndexOutOfBoundsException("ERROR: Attempted to place element at index "
								  				+ index + ". Valid range is 0-" + (size - 1) + ".");
		Node getter = new Node(null);
		if(index <= size / 2) {
			getter.next = head;
			for(int pnt = 0; pnt < index; pnt++){
				getter.next = getter.next.next;
			}
			return getter.next.data;
		} else if(index > size / 2) {
			getter.prev = tail;
			for(int pnt = size - 1; pnt > index; pnt--) {
				getter.prev = getter.prev.prev;
			}
			return getter.prev.data;
		}
		System.out.println("This null return shouldn't have been reached!");
		return null;
	}
	
	
	/**
	 * Removes and returns the first element from the list.
	 * Throws NoSuchElementException if the list is empty.
	 * O(1) for a doubly-linked list.
	 * 
	 * @return the first element in the list
	 */
	@Override
	public T removeFirst() throws NoSuchElementException {
		if(size < 1)
			throw new NoSuchElementException("ERROR: No such element.");
		T result = head.data;
		if(size == 1) {
			head = null;
			tail = null;
		} else {
			head = head.next;
			head.prev = null;
		}
		size--;
		return result;
	}
	
	
	/**
	 * Removes and returns the last element from the list.
	 * Throws NoSuchElementException if the list is empty.
	 * O(1) for a doubly-linked list.
	 * 
	 * @return the last element in the list
	 */
	@Override
	public T removeLast() throws NoSuchElementException {
		if(size < 1)
			throw new NoSuchElementException("ERROR: No such element.");
		T result = tail.data;
		if(size == 1) {
			head = null;
			tail = null;
		} else {
			tail = tail.prev;
			tail.next = null;
		}
		size--;
		return result;
	}
	
	
	/**
	 * Removes and returns the element at the specified position in the list.
	 * Throws IndexOutOfBoundsException if index is out of range (index < 0 || index >= size())
	 * O(N) for a doubly-linked list.
	 * 
	 * @param index - the location of desired element
	 * @return the element at the index specified
	 */
	@Override
	public T remove(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index > size - 1)
			throw new IndexOutOfBoundsException("ERROR: Attempted to place element at index "
								  				+ index + ". Valid range is 0-" + (size - 1) + ".");
		if(index == 0)
			return this.removeFirst();
		if(index == size - 1) 
			return this.removeLast();

		Node getter = new Node(null);
		if(index <= size / 2) {
			getter.next = head.next;
			for(int pnt = 1; pnt < index; pnt++){
				getter.next = getter.next.next;
			}
			T result = getter.next.data;
			getter.next.prev.next = getter.next.next;
			getter.next.next.prev = getter.next.prev;
			size--;
			return result;
		} else if(index > size / 2) {
			getter.prev = tail.prev;
			for(int pnt = size - 2; pnt > index; pnt--) {
				getter.prev = getter.prev.prev;
			}
			T result = getter.prev.data;
			getter.prev.prev.next = getter.prev.next;
			getter.prev.next.prev = getter.prev.prev;
			size--;
			return result;
		}
		System.out.println("This null return shouldn't have been reached!");
		return null;
	}
	
	
	/**
	 * Returns the index of the first occurrence of the specified element in the list, 
	 * or -1 if this list does not contain the element.
	 * O(N) for a doubly-linked list.
	 * 
	 * @param element - an element to search for
	 * @return the first index of the specified element
	 */
	@Override
	public int indexOf(T element) {
		Node getter = new Node(null);
		getter.next = head;
		for(int pnt = 0; pnt < size; pnt++){
			if(getter.next.data.equals(element))   //TODO Ask about .equals
				return pnt;
			getter.next = getter.next.next;
		}
		return -1;
	}
	
	
	/**
	 * Returns the index of the last occurrence of the specified element in this list, 
	 * or -1 if this list does not contain the element.
	 * O(N) for a doubly-linked list.
	 * 
	 * @param element - an element to search for
	 * @return the last index of the specified element
	 */
	@Override
	public int lastIndexOf(T element) {
		Node getter = new Node(null);
		getter.prev = tail;
		for(int pnt = size - 1; pnt >= 0; pnt--){
			if(getter.prev.data.equals(element))   //TODO Ask about .equals
				return pnt;
			getter.prev = getter.prev.prev;
		}
		return -1;
	}
	
	
	/**
	 * Returns the number of elements in this list.
	 * O(1) for a doubly-linked list.
	 * 
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return size;
	}
	
	
	/**
	 * Returns true if this collection contains no elements.
	 * O(1) for a doubly-linked list.
	 * 
	 * @return true if the list is empty; otherwise false
	 */
	@Override
	public boolean isEmpty() {
		return(size == 0);
	}
	
	
	/**
	 * Removes all of the elements from this list.
	 * O(1) for a doubly-linked list.
	 */
	@Override
	public void clear() {
		head = null;
		tail = null;
		size = 0;
	}
	
	
	/**
	 * Returns an array containing all of the elements in this list in proper sequence 
	 * (from first to last element).
	 * O(N) for a doubly-linked list.
	 * 
	 * @return an array representation of the list
	 */
	@Override
	public Object[] toArray() {
		Object[] arr = new Object[size];
		Node getter = new Node(null);
		getter.next = head;
		for(int idx = 0; idx < size; idx++) {
			arr[idx] = getter.next.data;
			getter.next = getter.next.next;
		}
		return arr;
	}
}
