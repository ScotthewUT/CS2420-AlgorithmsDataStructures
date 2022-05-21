package assignment5;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for the SortUtil class.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 23 September 2018
 */
class UnitTestsforSortUtil {
	
	ArrayList<Integer> intList;
	ArrayList<String> strList;
	ArrayList<Short> shortList;
	ArrayList<Short> emptyList;
	CompIntByAbsValue compInt;
	CompStringByLength compStr;
	CompShortByNatural compShort;
	Random rand;
	
	@BeforeEach
	void setUp() {
		intList = new ArrayList<Integer>();
		compInt = new CompIntByAbsValue();
		rand = new Random(System.currentTimeMillis());
		for(int i = 0; i < 1024; i++) {
			intList.add(rand.nextInt(1001) - 500);
		}
		
		strList = new ArrayList<String>();
		compStr = new CompStringByLength();
		for(int i = 0; i < 16; i++) {
			String next = "";
			int size = 1 + rand.nextInt(8);
			for(int j = 0; j < size; j++)
				next += (char)(rand.nextInt(26) + 97);
			strList.add(next);
		}
		
		shortList = new ArrayList<Short>();
		compShort = new CompShortByNatural();
		shortList.add((short)16);
		
		emptyList = new ArrayList<Short>();
	}
	
	
	@Test
	void testMergeSortWithManyInts() {
		SortUtil.mergesort(intList, compInt);
		for(int i = 1; i < intList.size(); i++) 
			assertTrue(compInt.compare(intList.get(i - 1), intList.get(i)) <= 0);
	}
	
	@Test
	void testMergeSortStringsByLength() {
		SortUtil.mergesort(strList, compStr);
		System.out.println("MERGESORT STRING BY LENGTH:");
		for(String next : strList)
			System.out.println("    " + next);
		for(int i = 1; i < strList.size(); i++)
			assertTrue(compStr.compare(strList.get(i - 1), strList.get(i)) <= 0);
	}
	
	@Test
	void testMergeSortWithSingleElement() {
		SortUtil.mergesort(shortList, compShort);
		assertEquals(1, shortList.size());
		assertTrue(shortList.get(0) == 16);
	}
	
	@Test
	void testMergeSortWithEmptyList() {
		SortUtil.mergesort(emptyList, compShort);
		assertTrue(emptyList.isEmpty());
	}
	
	@Test
	void testQuickSortWithManyInts() {
		SortUtil.quicksort(intList, compInt);
		for(int i = 1; i < intList.size(); i++) 
			assertTrue(compInt.compare(intList.get(i - 1), intList.get(i)) <= 0);
	}
	
	@Test
	void testQuickSortStringsByLength() {
		SortUtil.quicksort(strList, compStr);
		System.out.println("QUICKSORT STRING BY LENGTH:");
		for(String next : strList)
			System.out.println("    " + next);
		for(int i = 1; i < strList.size(); i++)
			assertTrue(compStr.compare(strList.get(i - 1), strList.get(i)) <= 0);
	}
	
	@Test
	void testQuickSortWithSingleElement() {
		SortUtil.quicksort(shortList, compShort);
		assertEquals(1, shortList.size());
		assertTrue(shortList.get(0) == 16);
	}
	
	@Test
	void testQuickSortWithEmptyList() {
		SortUtil.quicksort(emptyList, compShort);
		assertTrue(emptyList.isEmpty());
	}
	
	@Test
	void testInsertionSortWithManyInts() {
		SortUtil.insertionSort(intList, compInt);
		for(int i = 1; i < intList.size(); i++) 
			assertTrue(compInt.compare(intList.get(i - 1), intList.get(i)) <= 0);
	}
	
	@Test
	void testInsertionSortStringsByLength() {
		SortUtil.insertionSort(strList, compStr);
		System.out.println("INSERTION SORT STRING BY LENGTH:");
		for(String next : strList)
			System.out.println("    " + next);
		for(int i = 1; i < strList.size(); i++)
			assertTrue(compStr.compare(strList.get(i - 1), strList.get(i)) <= 0);
	}
	
	@Test
	void testInsertionSortWithSingleElement() {
		SortUtil.insertionSort(shortList, compShort);
		assertEquals(1, shortList.size());
		assertTrue(shortList.get(0) == 16);
	}
	
	@Test
	void testInsertionSortWithEmptyList() {
		SortUtil.insertionSort(emptyList, compShort);
		assertTrue(emptyList.isEmpty());
	}
	
	@Test
	void testGenerateBestCaseForAscending() {
		ArrayList<Integer> intListAscend = SortUtil.generateBestCase(1000);
		for(int i = 1; i < 1000; i++)
			assertTrue(intListAscend.get(i - 1) <= intListAscend.get(i));
	}
	
	@Test
	void testGenerateAverageCaseForRandomness() {
		ArrayList<Integer> intListRand = SortUtil.generateAverageCase(1000);
		
		boolean ascending = true;
		for(int i = 1; i < 1000; i++) {
			if(intListRand.get(i - 1) <= intListRand.get(i)) {
				ascending = false;
				break;
			}
		}
		assertFalse(ascending);
		
		boolean descending = true;
		for(int i = 1; i < 1000; i++) {
			if(intListRand.get(i - 1) >= intListRand.get(i)) {
				descending = false;
				break;
			}
		}
		assertFalse(descending);
		
		for(int i = 1; i < 1000; i++) {
			assertTrue(intListRand.contains(i));
		}
	}
	
	@Test
	void testGenerateWorstCaseForDescending() {
		ArrayList<Integer> intListDesc = SortUtil.generateWorstCase(1000);
		for(int i = 1; i < 1000; i++)
			assertTrue(intListDesc.get(i - 1) >= intListDesc.get(i));
	}
	
	
	/**
	 * Comparator defines an ordering of Integers by absolute value.
	 */
	protected class CompIntByAbsValue implements Comparator<Integer> {

		public int compare(Integer left, Integer right) {
			return Math.abs(left) - Math.abs(right);
		}
	}
	
	/**
	 * Comparator defines an ordering of Shorts naturally.
	 */
	protected class CompShortByNatural implements Comparator<Short> {
		
		public int compare(Short left, Short right) {
			return left - right;
		}
	}
	
	/**
	 * Comparator defines an ordering of Strings based on length.
	 */
	protected class CompStringByLength implements Comparator<String> {

		public int compare(String left, String right) {
			return left.length() - right.length();
		}
	}
}
