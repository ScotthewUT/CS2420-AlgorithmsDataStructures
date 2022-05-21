package assignment8;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;;

/**
 * This class represents a generic binary search tree ADT and implements the SortedSet
 * interface. It does NOT re-balance itself as Java's TreeSet does. A Node private class
 * is implemented within. Note that Node contains a reference to parent, which is not
 * normally the cast for tree ADTs.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 23 October 2018
 */
public class BinarySearchTree<T extends Comparable<? super T>> implements SortedSet<T> {
	
	private Node root;
	private int size;
	
	private class Node {
		public T data;
		public Node left, right, parent;
		
		public Node(T element) {
			data = element;
			left = null;
			right = null;
			parent = null;
		}
	}
	
	// Default constructor.
	public BinarySearchTree() {
		root = null;
		size = 0;
	}
	
	
	@Override
	public boolean add(T item) {
		// Edge case: BST was empty.
		if(root == null) {
			Node first = new Node(item);
			root = first;
			size++;
			return true;
		}
		// Call the recursive helper method.
		return addRec(item, root);
	}
	
	
	@Override
	public boolean addAll(Collection<? extends T> items) {
		int initial = size;
		for(T next : items)
			add(next);
		return (size > initial);
	}
	
	
	@Override
	public boolean remove(T item) {
		// Edge case: BST was empty.
		if(root == null)
			return false;
		// Call the recursive helper method.
		return findForRemove(item, root);
	}
	
	
	@Override
	public boolean removeAll(Collection<? extends T> items) {
		int initial = size;
		for(T next : items)
			remove(next);
		return (size < initial);
	}
	
	
	@Override
	public boolean contains(T item) {
		// Edge case: BST was empty.
		if(root == null)
			return false;
		// Call the recursive helper method.
		return containsRec(item, root);
	}
	
	
	@Override
	public boolean containsAll(Collection<? extends T> items) {
		for(T next : items)
			if(!this.contains(next))
				return false;
		return true;
	}
	
	
	@Override
	public T first() throws NoSuchElementException {
		// Empty tree error.
		if(this.size() < 1)
			throw new NoSuchElementException("ERROR: No elements contained in this BST.");
		// Call the recursive helper method.
		return getLeftMost(root).data;
	}
	
	
	@Override
	public T last() throws NoSuchElementException {
		// Empty tree error.
		if(this.size() < 1)
			throw new NoSuchElementException("ERROR: No elements contained in this BST.");
		// Call the recursive helper method.
		return getRightMost(root).data;
	}
	
	
	@Override
	public int size() {
		return size;
	}
	
	
	@Override
	public void clear() {
		root = null;
		size = 0;
	}
	
	
	@Override
	public boolean isEmpty() {
		return (this.size() == 0);
	}
	
	
	@Override
	public ArrayList<T> toArrayList() {
		ArrayList<T> result = new ArrayList<T>(size);
		inOrderDFTForList(result, root);
		return result;
	}
	
	
	/**
	 * This method creates a plain text file used to visualize the BST with
	 * the help of the tool found at:  http://www.webgraphviz.com/ .
	 * 
	 * @param filename - the name of the file to write to
	 * @throws FileNotFoundException
	 */
	public void writeDot(String filename) throws FileNotFoundException {
		try(FileWriter fout = new FileWriter(new File(filename), true)) {
			fout.append("digraph G {\n");
			writeDotDFT(fout, root);
			fout.append("}");
		}
		catch (IOException e) {
			System.out.println("ERROR: " + filename + " not found!");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Recursive helper method that adds an item to the BST.
	 * 
	 * @param item - item to be added
	 * @param node - starting point (root) of the tree or subtree
	 * @return true if the item was added; false if it was already in the BST
	 */
	private boolean addRec(T item, Node node) {
		// Base case: BST already contains item.
		if(item.compareTo(node.data) == 0)
			return false;
		// If argument is smaller...
		if(item.compareTo(node.data) < 0) {
			//...and left child exists, recurse with the left subtree.
			if(node.left != null)
				return addRec(item, node.left);
			//...and there is no left child (base case), add the item there.
			Node addLeft = new Node(item);
			addLeft.parent = node;
			node.left = addLeft;
			size++;
			return true;
		}
		// If argument is greater...
		if(item.compareTo(node.data) > 0) {
			//...and right child exists, recurse with the right subtree.
			if(node.right != null)
				return addRec(item, node.right);
			//...and there is no right child (base case), add the item there.
			Node addRight = new Node(item);
			addRight.parent = node;
			node.right = addRight;
			size++;
			return true;
		}
		return false;	// This return should never be reached.
	}
	
	
	/**
	 * Recursive helper method that locates an item in the BST for removal.
	 * 
	 * @param item - item to be removed
	 * @param node - starting point (root) of the tree or subtree
	 * @return true if the item was removed; false if it was not found in the BST
	 */
	private boolean findForRemove(T item, Node node) {
		// Base case: item is found.
		if(node.data.compareTo(item) == 0) {
			removeNode(node);
			return true;
		}
		// If argument is smaller & left child exists, recurse with the left subtree.
		if(node.left != null && item.compareTo(node.data) < 0)
			return findForRemove(item, node.left);
		// If argument is greater & right child exists, recurse with the right subtree.
		if(node.right != null && item.compareTo(node.data) > 0)
			return findForRemove(item, node.right);
		// Base case: item was not found.
		return false;
	}
	
	
	/**
	 * Helper method that removes a given node from the BST.
	 * 
	 * @param node - node to be removed
	 */
	private void removeNode(Node node) {
		// Target node has no children. Detach it.
		if(node.left == null && node.right == null) {
			if(node == node.parent.left)
				node.parent.left = null;
			else if(node == node.parent.right)
				node.parent.right = null;
			else
				root = null;
			size--;
			return;
		// Target node has a right child only. Connect child to grandparent.
		} else if(node.left == null) {
			if(node == node.parent.left)
				node.parent.left = node.right;
			else if(node == node.parent.right)
				node.parent.right = node.right;
			else
				root = node.right;
			size--;
			return;
		// Target node has a left child only. Connect child to grandparent.
		} else if(node.right == null) {
			if(node == node.parent.left)
				node.parent.left = node.left;
			else if(node == node.parent.right)
				node.parent.right = node.left;
			else
				root = node.left;
			size--;
			return;
		}
		// Target node has both left and right child. Feel free to cry.
		Node successor = getRightMost(node.left);
		node.data = successor.data;
		removeNode(successor);
	}
	
	
	/**
	 * Recursive helper method that searches for an item in the BST.
	 * 
	 * @param item - item to look for
	 * @param node - starting point (root) of the tree or subtree
	 * @return true if the item is contained in the BST; false otherwise
	 */
	private boolean containsRec(T item, Node node) {
		// Base case: item is found.
		if(node.data.compareTo(item) == 0)
			return true;
		// Base case: item was not found.
		if(node.left == null & node.right == null)
			return false;
		// If argument is smaller & left child exists, recurse with the left subtree.
		if(node.left != null && item.compareTo(node.data) < 0)
			return containsRec(item, node.left);
		// If argument is greater & right child exists, recurse with the right subtree.
		if(node.right != null && item.compareTo(node.data) > 0)
			return containsRec(item, node.right);
		return false;  	// This return should never be reached.
	}
	
	
	/**
	 * Recursive helper method that finds the left-most node in the BST.
	 * 
	 * @param node - starting point (root) of the tree or subtree
	 * @return the left-most node
	 */
	private Node getLeftMost(Node node) {
		// Base case: No left child.
		if(node.left == null)
			return node;
		// Recurse.
		return getLeftMost(node.left);
	}
	
	
	/**
	 * Recursive helper method that finds the right-most node in the BST.
	 * 
	 * @param node - starting point (root) of the tree or subtree
	 * @return the right-most node
	 */
	private Node getRightMost(Node node) {
		// Base case: No right child.
		if(node.right == null)
			return node;
		// Recurse.
		return getRightMost(node.right);
	}
	
	
	/**
	 * Recursive helper method that implements an in-order, depth-first
	 * traversal of the BST while adding all items contained in the BST
	 * to an ArrayList.
	 * 
	 * @param list - an ArrayList for copying items to
	 * @param node - starting point (root) of the tree or subtree
	 */
	private void inOrderDFTForList(ArrayList<T> list, Node node) {
		// Base case.
		if(node == null)
			return;
		// If left child exists, recurse.
		if(node.left != null)
			inOrderDFTForList(list, node.left);
		// Once there are no more left children, add to the list.
		list.add(node.data);
		// If right child exists, recurse.
		if(node.right != null)
			inOrderDFTForList(list, node.right);
	}
	
	
	/**
	 * Recursive helper method that implements a pre-order, depth-first
	 * traversal of the BST while writing to a file.
	 * 
	 * @param fout - a FileWriter opened in the driver method
	 * @param node - starting point (root) of the tree or subtree
	 */
	private void writeDotDFT(FileWriter fout, Node node) {
		// Base case.
		if(node == null)
			return;
		try {
			// If left child exists, append it to the file.
			if(node.left != null)
				fout.append("\t" + node.data + " -> " + node.left.data + "\n");
			// If right child exists, append it to the file.
			if(node.right != null)
				fout.append("\t" + node.data + " -> " + node.right.data + "\n");
			// If left child exists, recurse.
			if(node.left != null)
				writeDotDFT(fout, node.left);
			// If right child exists, recurse.
			if(node.right != null)
				writeDotDFT(fout, node.right);
		} catch (IOException e) {
			e.printStackTrace();	// NOTE: The driver method will catch the FileNotFoundException.
		}
	}
}
