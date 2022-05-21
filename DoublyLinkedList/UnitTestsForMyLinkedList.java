package assignment6;

import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test cases for MyLinkedList data structure.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 2 October 2018
 */
class UnitTestsForMyLinkedList {
	
	MyLinkedList<Integer> intList;
	MyLinkedList<String> strList;
	MyLinkedList<Character> charList;
	MyLinkedList<Float> decList;
	MyLinkedList<Double> emptyList;
	MyLinkedList<Short> oneItemList;
	MyLinkedList<Short> twoItemList;
	
	@BeforeEach
	void setUp() {
		intList = new MyLinkedList<Integer>();
		for(int i = 1; intList.size() < 10; i++)
			intList.addLast(i);
		
		strList = new MyLinkedList<String>();
		strList.addFirst("worst of times, ");
		strList.addFirst("it was the ");
		strList.addFirst("best of times, ");
		strList.addFirst("It was the ");
		
		charList = new MyLinkedList<Character>();
		for(int i = 98; i < 122; i++)
			charList.addLast((char) i);
		
		decList = new MyLinkedList<Float>();
		for(float i = 1.0f; i < 10.0; i++)
			decList.addFirst(i / 9.0f);
		
		emptyList = new MyLinkedList<Double>();
		
		oneItemList = new MyLinkedList<Short>();
		oneItemList.add(0, (short) 1);
		
		twoItemList = new MyLinkedList<Short>();
		twoItemList.add(0, (short) 1);
		twoItemList.add(1, (short) 2);
	}
	
	@Test
	void testGetFirst() {
		assertEquals((Integer) 1, intList.getFirst());
		assertEquals((Float) 1.0f, decList.getFirst());
	}
	
	@Test
	void testGetFirstWithEmptyList() {
		Assertions.assertThrows(NoSuchElementException.class,
				() -> {
					emptyList.getFirst();
				});
	}
	
	@Test
	void testGetFirstWithOneItemList(){
		Short one = 1;
		assertEquals(one, oneItemList.getFirst());
	}
	
	@Test
	void testGetLast() {
		assertEquals((Integer) 10, intList.getLast());
		Float dec = 1.0f / 9.0f;
		assertEquals((Float) dec, decList.getLast());
	}
	
	@Test
	void testGetLastWithEmptyList() {
		Assertions.assertThrows(NoSuchElementException.class,
				() -> {
					emptyList.getLast();
				});
	}
	
	@Test
	void testGetLastWithOneItemList(){
		Short one = 1;
		assertEquals(one, oneItemList.getLast());
	}

	@Test
	void testAddFirstWithInt() {
		intList.addFirst(0);
		assertEquals(11, intList.size());
		assertEquals((Integer) 0, intList.get(0));
	}
	
	@Test
	void testAddFirstWithString() {
		strList.addFirst("Chapter 1:\t");
		assertEquals(5, strList.size());
		String cDickens = "";
		for(int i = 0; i < strList.size(); i++)
			cDickens += strList.get(i);
		System.out.println(cDickens);
	}
	
	@Test
	void testAddFirstWithEmptyList() {
		emptyList.addFirst(1.0);
		assertEquals(1, emptyList.size());
		assertEquals((Double) 1.0, emptyList.get(0));
		assertEquals((Double) 1.0, emptyList.removeLast());
	}
	
	@Test
	void testAddFirstWithOneItemList() {
		Short zero = 0, one = 1;
		oneItemList.addFirst(zero);
		assertEquals(2, oneItemList.size());
		assertEquals(zero, oneItemList.get(0));
		assertEquals(one, oneItemList.removeLast());
	}
	
	@Test
	void testRemoveFirstWithInt() {
		assertEquals((Integer) 1, intList.removeFirst());
		assertEquals(9, intList.size());
		assertEquals((Integer) 2, intList.get(0));
	}
	
	@Test
	void testRemoveFirstWithChar() {
		int size = charList.size();
		assertEquals((Character) 'b', charList.removeFirst());
		assertEquals(size - 1, charList.size());
		assertEquals((Character) 'c', charList.get(0));
	}
	
	@Test
	void testRemoveFirstWithEmptyList() {
		Assertions.assertThrows(NoSuchElementException.class,
				() -> {
					emptyList.removeFirst();
				});
	}
	
	@Test
	void testRemoveFirstWithOneItemList() {
		Short one = 1;
		assertEquals(one, oneItemList.removeFirst());
		assertTrue(oneItemList.isEmpty());
	}
	
	@Test
	void testRemoveFirstWithTwoItemList() {
		Short one = 1, two = 2;
		assertEquals(one,twoItemList.removeFirst());
		assertEquals(1, twoItemList.size());
		assertEquals(two, twoItemList.get(0));
	}
	
	@Test
	void testAddLastWithInt() {
		intList.addLast(11);
		assertEquals(11, intList.size());
		assertEquals((Integer) 11, intList.get(10));
	}
	
	@Test
	void testAddLastWithString() {
		strList.addLast("it was the age of wisdom, ");
		strList.addLast("it was the age of foolishness, ");
		assertEquals(6, strList.size());
		String cDickens = "";
		for(int i = 0; i < strList.size(); i++)
			cDickens += strList.get(i);
		System.out.println(cDickens);
	}
	
	@Test
	void testAddLastWithEmptyList() {
		emptyList.addLast(1.0);
		assertEquals(1, emptyList.size());
		assertEquals((Double) 1.0, emptyList.get(0));
		assertEquals((Double) 1.0, emptyList.removeFirst());
	}
	
	@Test
	void testAddLastWithOneItemList() {
		Short one = 1, two = 2;
		oneItemList.addLast(two);
		assertEquals(2, oneItemList.size());
		assertEquals(two, oneItemList.get(1));
		assertEquals(one, oneItemList.removeFirst());
	}
	
	@Test
	void testRemoveLastWithInt() {
		assertEquals((Integer) 10, intList.removeLast());
		assertEquals(9, intList.size());
		assertEquals((Integer) 9, intList.get(8));
	}
	
	@Test
	void testRemoveLastWithChar() {
		int size = charList.size();
		assertEquals((Character) 'y', charList.removeLast());
		assertEquals(size - 1, charList.size());
		assertEquals((Character) 'x', charList.getLast());
	}
	
	@Test
	void testRemoveLastWithEmptyList() {
		Assertions.assertThrows(NoSuchElementException.class,
				() -> {
					emptyList.removeLast();
				});
	}
	
	@Test
	void testRemoveLastWithOneItemList() {
		Short one = 1;
		assertEquals(one, oneItemList.removeLast());
		assertTrue(oneItemList.isEmpty());
	}
	
	@Test
	void testRemoveLastWithTwoItemList() {
		Short one = 1, two = 2;
		assertEquals(two,twoItemList.removeLast());
		assertEquals(1, twoItemList.size());
		assertEquals(one, twoItemList.get(0));
	}
	
	@Test
	void testAddAndGetWithInt() {
		intList.add(0, 42);
		assertEquals(11, intList.size());
		assertEquals((Integer) 42, intList.get(0));
		
		intList.add(3, 42);
		assertEquals(12, intList.size());
		assertEquals((Integer) 42, intList.get(3));
		
		intList.add(7, 42);
		assertEquals(13, intList.size());
		assertEquals((Integer) 42, intList.get(7));
		
		intList.add(13, 42);
		assertEquals(14, intList.size());
		assertEquals((Integer) 42, intList.get(13));
	}
	
	@Test
	void testAddAndGetWithChar() {
		int size = charList.size();
		charList.add(0, 'a');
		assertEquals(size + 1, charList.size());
		assertEquals((Character) 'a', charList.get(0));
		
		charList.add(7, 'X');
		assertEquals(size + 2, charList.size());
		assertEquals((Character) 'X', charList.get(7));
		
		charList.add(19, 'X');
		assertEquals(size + 3, charList.size());
		assertEquals((Character) 'X', charList.get(19));
		
		charList.add(charList.size(), 'z');
		assertEquals(size + 4, charList.size());
		assertEquals((Character) 'z', charList.get(charList.size() - 1));
	}
	
	@Test
	void testAddWithEmptyList() {
		emptyList.add(0, 1.0);
		assertEquals(1, emptyList.size());
		assertEquals((Double) 1.0, emptyList.get(0));
	}
	
	@Test
	void testAddForOutOfBoundsExceptions() {
		Assertions.assertThrows(IndexOutOfBoundsException.class,
				() -> {
					oneItemList.add(2, (short) 3);
				});
		Assertions.assertThrows(IndexOutOfBoundsException.class,
				() -> {
					oneItemList.add(-1, (short) -1);
				});
	}
	
	@Test
	void testGetForOutOfBoundsExceptions() {
		Assertions.assertThrows(IndexOutOfBoundsException.class,
				() -> {
					emptyList.get(0);
				});
		Assertions.assertThrows(IndexOutOfBoundsException.class,
				() -> {
					twoItemList.get(-1);
				});
		Assertions.assertThrows(IndexOutOfBoundsException.class,
				() -> {
					twoItemList.get(2);
				});
	}
	
	@Test
	void testRemoveWithInt() {
		assertEquals((Integer) 1, intList.remove(0));
		assertEquals(9, intList.size());
		assertEquals((Integer) 2, intList.get(0));
		intList.addFirst(1);
		
		assertEquals((Integer) 10, intList.remove(9));
		assertEquals(9, intList.size());
		assertEquals((Integer) 9, intList.get(8));
		intList.addLast(10);
		
		assertEquals((Integer) 3, intList.remove(2));
		assertEquals(9, intList.size());
		assertEquals((Integer) 4, intList.get(2));
		intList.add(2, 3);
		
		assertEquals((Integer) 7, intList.remove(6));
		assertEquals(9, intList.size());
		assertEquals((Integer) 8, intList.get(6));
	}
	
	@Test
	void testRemoveWithSingleItemList() {
		Short one = 1;
		assertEquals(one, oneItemList.remove(0));
		assertTrue(oneItemList.isEmpty());
	}
	
	@Test
	void testRemoveWithTwoItemList() {
		Short one = 1, two = 2;
		assertEquals(one, twoItemList.remove(0));
		assertEquals(1, twoItemList.size());
		assertEquals(two, twoItemList.remove(0));
		assertTrue(twoItemList.isEmpty());
	}
	
	@Test
	void testIndexOf() {
		charList = new MyLinkedList<Character>();
		Random rand = new Random(3);
		String charStr = "", idxStr = "";
		for(int i = 0; i < 15; i++) {
			char next = (char)(rand.nextInt(6) + 97);
			charList.addLast(next);
		}
		for(int i = 0; i < 15; i++) {
			charStr += charList.get(i) + "\t";
			idxStr += i + "\t";
		}
		System.out.println(charStr);
		System.out.println(idxStr);
		
		assertEquals( 2, charList.indexOf('a'));
		assertEquals( 3, charList.indexOf('b'));
		assertEquals( 0, charList.indexOf('c'));
		assertEquals( 6, charList.indexOf('d'));
		assertEquals( 7, charList.indexOf('e'));
		assertEquals(12, charList.indexOf('f'));
		assertEquals(-1, charList.indexOf('g'));
	}
	
	@Test
	void testLastIndexOf() {
		charList = new MyLinkedList<Character>();
		Random rand = new Random(3);
		for(int i = 0; i < 15; i++) {
			char next = (char)(rand.nextInt(6) + 97);
			charList.addLast(next);
		}
		
		assertEquals( 5, charList.lastIndexOf('a'));
		assertEquals( 9, charList.lastIndexOf('b'));
		assertEquals( 1, charList.lastIndexOf('c'));
		assertEquals(14, charList.lastIndexOf('d'));
		assertEquals(13, charList.lastIndexOf('e'));
		assertEquals(12, charList.lastIndexOf('f'));
		assertEquals(-1, charList.lastIndexOf('g'));
	}
	
	@Test
	void testSize() {
		assertEquals(0, emptyList.size());
		assertEquals(1, oneItemList.size());
		assertEquals(2, twoItemList.size());
		assertEquals(10, intList.size());
	}
	
	@Test
	void testIsEmpty() {
		assertTrue(emptyList.isEmpty());
		assertFalse(oneItemList.isEmpty());
	}
	
	@Test
	void testClear() {
		emptyList.clear();
		assertTrue(emptyList.isEmpty());
		oneItemList.clear();
		assertTrue(oneItemList.isEmpty());
		decList.clear();
		assertTrue(decList.isEmpty());
		Assertions.assertThrows(IndexOutOfBoundsException.class,
				() -> {
					decList.get(0);
				});
	}
	
	@Test
	void testToArrayWithFloat() {
		Object[] decArr = decList.toArray();
		String decStr = "[";
		for(int i = 0; i < decArr.length - 1; i++)
			decStr += decArr[i] + ", ";
		decStr += decArr[decArr.length - 1] + "]";
		System.out.println(decStr);
		for(int i = 0; i < decList.size(); i++)
			assertEquals(decList.get(i), decArr[i]);
	}
}
