package assignment11;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Unit tests for PriorityQueue class.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 23 November 2018
 */
class UnitTestsForPriorityQueue {
	
	static ArrayList<Integer> randIntArr;
	static ArrayList<String> strArr;
	static PriorityQueue<Integer> randIntPQ;
	static PriorityQueue<Character> charPQ;
	static PriorityQueue<String> strPQAlpha;
	static PriorityQueue<String> strPQSize;
	static CompCharNoCase comp;
	static CompStringLength comp2;
	
	@BeforeEach
	void setUp() {
		Random rand = new Random(1);
		randIntArr = new ArrayList<Integer>();
		for(int i = 0; i < 10000; i++)
			randIntArr.add(rand.nextInt(1000));
		
		String  str = "IamtheverymodelofamodernMajorGeneral";
		char[] charArr = str.toCharArray();
		comp = new CompCharNoCase();
		charPQ = new PriorityQueue<Character>(comp);
		for(int i = 0; i < charArr.length; i++)
			charPQ.add(charArr[i]);
		
		strArr = new ArrayList<String>();
		strArr.add("Mercury");
		strArr.add("Venus");
		strArr.add("Earth");
		strArr.add("Mars");
		strArr.add("Jupiter");
		strArr.add("Saturn");
		strArr.add("Uranus");
		strArr.add("Neptune");
		strArr.add("Pluto");
	}
	
	@Test
	void testFindMin() {
		Random rand = new Random(1);
		randIntPQ = new PriorityQueue<Integer>();
		for(int i = 0; i < 1000; i++)
			randIntPQ.add(rand.nextInt(100));
		assertEquals(0, (int)randIntPQ.findMin());
	}
	
	@Test
	void testFindMinThrowsExceptionWhenEmpty() {
		randIntPQ = new PriorityQueue<Integer>();
		assertThrows(NoSuchElementException.class, () -> {randIntPQ.findMin();});
		randIntPQ.add(4);
		randIntPQ.add(8);
		randIntPQ.add(15);
		randIntPQ.add(16);
		randIntPQ.add(23);
		randIntPQ.add(42);
		assertEquals(4, (int)randIntPQ.deleteMin());
		assertEquals(8, (int)randIntPQ.deleteMin());
		assertEquals(15, (int)randIntPQ.deleteMin());
		assertEquals(16, (int)randIntPQ.deleteMin());
		assertEquals(23, (int)randIntPQ.deleteMin());
		assertEquals(42, (int)randIntPQ.deleteMin());
		assertThrows(NoSuchElementException.class, () -> {randIntPQ.findMin();});
		randIntPQ.add(4);
		randIntPQ.add(8);
		randIntPQ.add(15);
		randIntPQ.add(16);
		randIntPQ.add(23);
		randIntPQ.add(42);
		randIntPQ.clear();
		assertThrows(NoSuchElementException.class, () -> {randIntPQ.findMin();});
	}
	
	@Test
	void testDeleteMin() {
		strPQAlpha = new PriorityQueue<String>();
		for(String next : strArr)
			strPQAlpha.add(next);
		assertEquals("Earth", strPQAlpha.deleteMin());
	}
	
	@Test
	void testDeleteMinThrowsExceptionWhenEmpty() {
		strPQAlpha = new PriorityQueue<String>();
		assertThrows(NoSuchElementException.class, () -> {strPQAlpha.deleteMin();});
		for(String next : strArr)
			strPQAlpha.add(next);
		while(strPQAlpha.size() > 0)
			strPQAlpha.deleteMin();
		assertThrows(NoSuchElementException.class, () -> {randIntPQ.deleteMin();});
		for(String next : strArr)
			strPQAlpha.add(next);
		strPQAlpha.clear();
		assertThrows(NoSuchElementException.class, () -> {randIntPQ.deleteMin();});
	}
	
	@Test
	void testWithManyInts() {
		randIntPQ = new PriorityQueue<Integer>();
		for(int next : randIntArr) {
			int initialSize = randIntPQ.size();
			randIntPQ.add(next);
			assertEquals(initialSize + 1, randIntPQ.size());
		}
		
		int prev = randIntPQ.deleteMin();
		while(randIntPQ.size() > 0) {
			int next = randIntPQ.deleteMin();
			assertTrue(prev <= next);
			prev = next;
		}
		
		randIntPQ.clear();
		assertEquals(0, randIntPQ.size());
	}

	@SuppressWarnings("unused")
	@Test
	void testWithCharsAndComparator() {
		char prevChar = charPQ.deleteMin();
		String charStr = "";
		charStr += prevChar;
		while(charPQ.size() > 0) {
			char nextChar = charPQ.deleteMin();
			charStr += nextChar;
			assertTrue(comp.compare(prevChar, nextChar) <= 0);
			prevChar = nextChar;
		}
		//System.out.println(charStr);
	}
	
	@Test
	void testWithStrings() {
		strPQAlpha = new PriorityQueue<String>();
		for(String next : strArr) {
			int initialSize = strPQAlpha.size();
			strPQAlpha.add(next);
			assertEquals(initialSize + 1, strPQAlpha.size());
		}
		String prev = strPQAlpha.deleteMin();
		while(strPQAlpha.size() > 0) {
			String next = strPQAlpha.deleteMin();
			assertTrue(prev.compareTo(next) <= 0);
			prev = next;
		}
	}
	
	@Test
	void testWithStringsAndComparator() {
		comp2 = new CompStringLength();
		strPQSize = new PriorityQueue<String>(comp2);
		for(String next : strArr) {
			int initialSize = strPQSize.size();
			strPQSize.add(next);
			assertEquals(initialSize + 1, strPQSize.size());
		}
		String prev = strPQSize.deleteMin();
		while(strPQSize.size() > 0) {
			//System.out.println(prev);
			String next = strPQSize.deleteMin();
			assertTrue(prev.length() <= next.length());
			prev = next;
		}
	}
	
	
	
	private class CompCharNoCase implements Comparator<Character> {
		@Override
		public int compare(Character lhs, Character rhs) {
			lhs = Character.toLowerCase(lhs);
			rhs = Character.toLowerCase(rhs);
			return lhs.compareTo(rhs);
		}
	}
	
	private class CompStringLength implements Comparator<String> {
		@Override
		public int compare(String lhs, String rhs) {
			return lhs.length() - rhs.length();
		}
	}

	
}
	

