package assignment3;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

/**
 * JUnit tests for the ArrayCollection class.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 10 September 2018
 */
class ArrayCollectionJUnitTest {

	ArrayCollection<Integer> intList;
	ArrayCollection<String> strList;
	ArrayCollection<GregorianCalendar> dateList;
	ArrayCollection<Integer> randList;
	Random rand;
	
	@BeforeEach
	void setUp() {
		intList = new ArrayCollection<Integer>();
		strList = new ArrayCollection<String>();
		dateList = new ArrayCollection<GregorianCalendar>();
		
		randList = new ArrayCollection<Integer>();
		rand = new Random(System.currentTimeMillis());
	}
	
	@Test
	void testAddAllWithIntStack() {
		Stack<Integer> intStk = new Stack<Integer>();
		for(int itr = 1; itr < 10; itr++) {
			intStk.push(itr);
		}
		intList.add(0);
		Assert.assertEquals(1, intList.size());
		intList.addAll(intStk);
		Assert.assertEquals(10, intList.size());
		
		Object[] intArr =  new Object[] {0,1,2,3,4,5,6,7,8,9};
		Assert.assertArrayEquals(intArr, intList.toArray());
	}
	
	@Test
	void testGrowOnLargeList() {
		rand = new Random(System.currentTimeMillis());
		int prevSize = randList.size();
		while(randList.size() < 20000) {
			if(randList.add(1 + rand.nextInt(100000))) {
				Assert.assertEquals(prevSize + 1, randList.size());
				prevSize = randList.size();
			}
		}
	}
	
	@Test
	void testRemoveAllWithDateVector() {
		Vector<GregorianCalendar> dateVec = new Vector<GregorianCalendar>();
		for(int i = 1; i < 12; i++) {
			GregorianCalendar date = new GregorianCalendar(1969 + i, i, 29 - i);
			dateList.add(date);
			dateVec.add(date);
		}
		Assert.assertTrue(dateList.remove(new GregorianCalendar(1970, 1, 28)));
		Assert.assertFalse(dateList.remove(new GregorianCalendar(1970, 1, 28)));
		Assert.assertTrue(dateList.removeAll(dateVec));
		Assert.assertTrue(dateList.isEmpty());
		Assert.assertEquals(0, dateList.size());
	}
	
	@Test
	void testContainsAllWithIntLinkedList() {
		LinkedList<Integer> intLink = new LinkedList<Integer>();
		for(int i = 1; i < 10; i++) {
			intList.add(i);
		}
		intLink.add(2);
		intLink.add(8);
		intLink.add(5);
		Assert.assertTrue(intList.contains(8));
		Assert.assertFalse(intList.contains(10));
		Assert.assertTrue(intList.containsAll(intLink));
		intLink.add(0);
		Assert.assertFalse(intList.containsAll(intLink));
	}

	@Test
	void testRetainAllForIntArrayList() {
		for(int idx = 0; idx < 10; idx++) {
		intList.add(idx);
		}
		ArrayList<Integer> intVector = new ArrayList<Integer>();
		intVector.add(7);
		intVector.add(3);
		intVector.add(11);
		intVector.add(2);
		
		intList.retainAll(intVector);
		
		Assert.assertTrue(intList.contains(7));
		Assert.assertTrue(intList.contains(3));
		Assert.assertFalse(intList.contains(0));
		Assert.assertFalse(intList.contains(8));
		Assert.assertEquals(3, intList.size());
	}
	
	@Test
	void testBinarySearchWithStrings() {
		strList.add("apple");
		strList.add("bananas");
		strList.add("cucumbers");
		strList.add("dragonfruit");
		strList.add("eggplant");
		strList.add("fennel");
		
		CompStringByLength cmp = new CompStringByLength();
		
		ArrayList<String> sortedList = strList.toSortedList(cmp);
		
		Assert.assertEquals("fennel", sortedList.get(1));
		Assert.assertTrue(SearchUtil.binarySearch(sortedList, "dragonfruit", cmp));
	}
	
	@Test
	void testBinarySearchWithInts(){
		while(randList.size() < 20) {
			randList.add(1 + rand.nextInt(20));
		}
		Assert.assertEquals(20, randList.size());
		
		CompIntByAbsValue comp = new CompIntByAbsValue();
		ArrayList<Integer> sortedList = randList.toSortedList(comp);
		for(Integer next : sortedList) {
			Assert.assertTrue(SearchUtil.binarySearch(sortedList, next, comp));
		}
		Assert.assertFalse(SearchUtil.binarySearch(sortedList,  0, comp));
		Assert.assertFalse(SearchUtil.binarySearch(sortedList, 21, comp));
	}
	
	@Test
	void testBinarySearchOnLargeList() {
		rand = new Random(System.currentTimeMillis());
		while(randList.size() < 999) {
			randList.add(rand.nextInt(2001) - 1000);
		}
		randList.add(-333);
		CompIntByAbsValue comp = new CompIntByAbsValue();
		ArrayList<Integer> sortedList = randList.toSortedList(comp);
		Assert.assertTrue(SearchUtil.binarySearch(sortedList,  -333, comp));
		Assert.assertFalse(SearchUtil.binarySearch(sortedList, 3000, comp));
	}
		
	@Test
	void testToArray() {
		
		
	}
	
	/**
	 * Comparator defines an ordering of Strings based on length.
	 */
	protected class CompStringByLength implements Comparator<String> {

		public int compare(String left, String right) {
			return left.length() - right.length();
		}
	}
	
	/**
	 * Comparator defines an ordering of Integers by absolute value.
	 */
	protected class CompIntByAbsValue implements Comparator<Integer> {

		public int compare(Integer left, Integer right) {
			return Math.abs(left) - Math.abs(right);
		}
	}

}
