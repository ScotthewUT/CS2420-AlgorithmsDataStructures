package assignment8;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit test cases for the BinarySearchTree ADT.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 19 October 2018
 */
class UnitTestsForBST {
	
	BinarySearchTree<Integer> testBST;
	LinkedList<Integer> testLL;
	Random rand1, rand2;

	@BeforeEach
	void setUp() {
		rand1 = new Random(1);
		rand2 = new Random(1);
		testBST = new BinarySearchTree<Integer>();
		testLL = new LinkedList<Integer>();
		for(int i = 0; i < 10; i++)
			testLL.push(rand2.nextInt(100));
	}

	@Test
	void testAddToBST() {
		for(int i = 0; i < 10; i++) {
			assertTrue(testBST.add(rand1.nextInt(100)));
		}
		assertEquals(10, testBST.size());
	}
	
	@Test
	void testAddAlltoBST() {
		assertTrue(testBST.addAll(testLL));
		assertEquals(10, testBST.size());
	}
	
	@Test
	void testContains() {
		for(int i = 0; i < 10; i++)
			testBST.add(rand1.nextInt(100));
		assertTrue(testBST.contains(6));
		assertTrue(testBST.contains(88));
		assertTrue(testBST.contains(85));
		assertTrue(testBST.contains(78));
		assertFalse(testBST.contains(0));
	}
	
	@Test
	void testContainsAll() {
		for(int i = 0; i < 10; i++)
			testBST.add(rand1.nextInt(100));
		assertTrue(testBST.containsAll(testLL));
		testLL = new LinkedList<Integer>();
		testLL.push(6);
		testLL.push(88);
		testLL.push(0);
		assertFalse(testBST.containsAll(testLL));
	}
	
	@Test
	void testRemoveWithNoChildren() throws FileNotFoundException {
		for(int i = 0; i < 10; i++)
			testBST.add(rand1.nextInt(100));
		assertTrue(testBST.remove(6));
		assertTrue(testBST.remove(78));
		assertFalse(testBST.remove(0));
//		String filename = "BST.txt";
//		testBST.writeDot(filename);
	}
	
	@Test
	void testRemoveWithOneChild() throws FileNotFoundException {
		for(int i = 0; i < 10; i++)
			testBST.add(rand1.nextInt(100));
		assertTrue(testBST.remove(4));
//		String filename = "BST.txt";
//		testBST.writeDot(filename);
	}
	
	@Test
	void testRemoveWithTwoChildren() throws FileNotFoundException {
		for(int i = 0; i < 10; i++)
			testBST.add(rand1.nextInt(100));
		assertTrue(testBST.remove(85));
		assertTrue(testBST.remove(13));
//		String filename = "BST.txt";
//		testBST.writeDot(filename);
	}
	
	@Test
	void testRemoveAll() throws FileNotFoundException {
		for(int i = 0; i < 10; i++)
			testBST.add(rand1.nextInt(100));
		testLL = new LinkedList<Integer>();
		testLL.push(47);
		testLL.push(48);
		testLL.push(4);
		testLL.push(0);
		assertTrue(testBST.removeAll(testLL));
		assertFalse(testBST.removeAll(testLL));
//		String filename = "BST.txt";
//		testBST.writeDot(filename);
	}
	
	@Test
	void testFirst() {
		for(int i = 0; i < 10; i++)
			testBST.add(rand1.nextInt(100));
		assertEquals((Integer) 4, testBST.first());
	}
	
	@Test
	void testLast() {
		for(int i = 0; i < 10; i++)
			testBST.add(rand1.nextInt(100));
		assertEquals((Integer) 88, testBST.last());
	}
	
	@Test
	void testClearSizeAndIsEmpty() {
		for(int i = 0; i < 10; i++)
			testBST.add(rand1.nextInt(100));
		assertEquals(10, testBST.size());
		testBST.clear();
		assertEquals(0, testBST.size());
		assertTrue(testBST.isEmpty());
	}
	
	@Test
	void testToArrayList() {
		for(int i = 0; i < 10; i++)
			testBST.add(rand1.nextInt(100));
		ArrayList<Integer> testArr = testBST.toArrayList();
		assertEquals(10, testArr.size());
		for(int i = 1; i < 10; i++) {
			assertTrue(testArr.get(i) > testArr.get(i - 1));
		}
	}
	
	@Test
	void testWriteDotFile() throws FileNotFoundException {
		for(int i = 0; i < 10; i++)
			testBST.add(rand1.nextInt(100));
		String filename = "BST.txt";
		testBST.writeDot(filename);
	}
}
