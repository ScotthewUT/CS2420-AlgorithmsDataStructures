package assignment4;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * JUnit Testing Class for AnagramUtil.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 17 September 2018
 */
class JUnitTestsForAnagramUtil {
	
	@Test
	void testSortStringsLexicographically() {
		String str = "Gfedcba";
		str = AnagramUtil.sort(str);
		assertEquals("Gabcdef", str);
		str = "spoilage!";
		str = AnagramUtil.sort(str);
		assertEquals("!aegilops", str);
	}
	
	@Test
	void testSortWithSingleCharStrings() {
		String str = "1";
		str = AnagramUtil.sort(str);
		assertEquals("1", str);
		str = "X";
		str = AnagramUtil.sort(str);
		assertEquals("X", str);
	}
	
	@Test
	void testSortWithEmptyString() {
		String str = "";
		str = AnagramUtil.sort(str);
		assertTrue(str.isEmpty());
	}
	
	@Test
	void testInsertionSortWithModerateSize() {
		Random rand = new Random(System.currentTimeMillis());
		CompIntByAbsValue comp = new CompIntByAbsValue();
		Integer[] intArr = new Integer[10000];
		for(int i = 0; i < intArr.length; i++) {
			intArr[i] =  rand.nextInt(1001) - 500;
		}
		
		AnagramUtil.insertionSort(intArr, comp);
		
		for(int idx = 1; idx < intArr.length; idx++) {
			int lhs = intArr[idx - 1];
			int rhs = intArr[idx];
			assertTrue(comp.compare(lhs, rhs) <= 0);
		}
	}
	
	@Test
	void testInsertionSortWithSingleItemArray() {
		CompStringByAnagram comp = new CompStringByAnagram();
		String[] strArr = new String[] {"single"};
		AnagramUtil.insertionSort(strArr, comp);
		assertEquals(1, strArr.length);
		assertEquals("single", strArr[0]);
	}
	
	@Test
	void testInsertionSortWithEmptyArray() {
		CompStringByAnagram comp = new CompStringByAnagram();
		String[] strArr = new String[0];
		AnagramUtil.insertionSort(strArr, comp);
		assertEquals(0, strArr.length);
	}
	
	@Test
	void testInsertionSortWithLargeSize() {
		Random rand = new Random(System.currentTimeMillis());
		CompCharByLex comp = new CompCharByLex();
		Character[] charArr = new Character[1_000_000];
		for(int idx = 0; idx < charArr.length; idx++) {
			for(int j = 65; j < 91; j++)
				charArr[idx] = (char)j;
			for(int j = 97; j < 123; j++)
				charArr[idx] = (char)j;
			for(int j = 48; j < 58; j++)
				charArr[idx] = (char)j;
		}
		for(int idx = 0; idx < charArr.length; idx++)
			charArr[idx] = charArr[rand.nextInt(1_000_000)];
		
		AnagramUtil.insertionSort(charArr, comp);
		
		for(int idx = 1; idx < charArr.length; idx++) {
			Character lhs = charArr[idx - 1];
			Character rhs = charArr[idx];
			assertTrue(comp.compare(lhs, rhs) <= 0);
		}
	}
	
	@Test
	void testShellsortWithCiuraGapsAndModerateSize() {
		Random rand = new Random(System.currentTimeMillis());
		CompIntByAbsValue comp = new CompIntByAbsValue();
		Integer[] intArr = new Integer[10000];
		for(int i = 0; i < intArr.length; i++) {
			intArr[i] =  rand.nextInt(1001) - 500;
		}
		
		AnagramUtil.shellSort(intArr, comp);
		
		for(int idx = 1; idx < intArr.length; idx++) {
			int lhs = intArr[idx - 1];
			int rhs = intArr[idx];
			assertTrue(comp.compare(lhs, rhs) <= 0);
		}
	}
	
	@Test
	void testShellsortWithCiuraGapsAndSingleItemArray() {
		CompStringByAnagram comp = new CompStringByAnagram();
		String[] strArr = new String[] {"single"};
		AnagramUtil.shellSort(strArr, comp);
		assertEquals(1, strArr.length);
		assertEquals("single", strArr[0]);
	}
	
	@Test
	void testShellsortWithCiuraGapsAndEmptyArray() {
		CompStringByAnagram comp = new CompStringByAnagram();
		String[] strArr = new String[0];
		AnagramUtil.shellSort(strArr, comp);
		assertEquals(0, strArr.length);
	}
	
	@Test
	void testShellsortWithCiuraGapsAndLargeSize() {
		Random rand = new Random(System.currentTimeMillis());
		CompCharByLex comp = new CompCharByLex();
		Character[] charArr = new Character[1_000_000];
		for(int idx = 0; idx < charArr.length; idx++) {
			for(int j = 65; j < 91; j++)
				charArr[idx] = (char)j;
			for(int j = 97; j < 123; j++)
				charArr[idx] = (char)j;
			for(int j = 48; j < 58; j++)
				charArr[idx] = (char)j;
		}
		for(int idx = 0; idx < charArr.length; idx++)
			charArr[idx] = charArr[rand.nextInt(1_000_000)];
		
		AnagramUtil.shellSort(charArr, comp);
		
		for(int idx = 1; idx < charArr.length; idx++) {
			Character lhs = charArr[idx - 1];
			Character rhs = charArr[idx];
			assertTrue(comp.compare(lhs, rhs) <= 0);
		}
	}
	
	@Test
	void testFiveLetterWordsThatShouldBeAnagrams() {
		String bared = "bared";
		String beard = "beard";
		String bread = "Bread";
		String debar = "DEBAR";
		assertTrue(AnagramUtil.areAnagrams(bared, beard));
		assertTrue(AnagramUtil.areAnagrams(beard, bread));
		assertTrue(AnagramUtil.areAnagrams(bread, debar));
		assertTrue(AnagramUtil.areAnagrams(debar, bared));
	}
	
	@Test
	void testOneLetterStringsAsAnagrams() {
		String a1 = "A";
		String a2 = "a";
		String and1 = "&";
		String and2 = "&";
		assertTrue(AnagramUtil.areAnagrams(a1, a2));
		assertTrue(AnagramUtil.areAnagrams(and1, and2));
		assertFalse(AnagramUtil.areAnagrams(a1, and1));
	}
	
	@Test
	void testAreEmptyStringsAnagrams() {
		String empty1 = "", empty2 = "";
		assertTrue(AnagramUtil.areAnagrams(empty1, empty2));
	}
	
	@Test
	void testAreNullStringsAnagrams() {
		String null1 = null, null2 = null;
		assertFalse(AnagramUtil.areAnagrams(null1, null2));
	}
	
	/**
	 * This unit test makes no assertions, but prints to console.
	 */
	@Test
	void testGroupAnagramsTogetherInStringArray() {
		CompStringByAnagram comp = new CompStringByAnagram();
		String[] strArr = new String[] { "bared", "asp", "begin", "apt",
										 "beard", "pas", "being", "paT",
										 "Bread", "sAp", "binge", "tap",
										 "debar", "spa", "abcde", "APS"};
		AnagramUtil.shellSort(strArr, comp);
		for(String next : strArr)
			System.out.println(next);
	}
	
	@Test
	void testGetLargestAnagramGroup() {
		String[] strArr = new String[] { "bared", "asp", "begin", "apt",
										 "beard", "pas", "being", "paT",
										 "Bread", "sAp", "binge", "tap",
										 "debar", "spa", "abcde", "APS"};
		strArr = AnagramUtil.getLargestAnagramGroup(strArr);
		assertEquals(5, strArr.length);
		ArrayList<String> strVec = new ArrayList<String>();
		for(String next : strArr)
			strVec.add(next);
		String[] check = new String[] {"asp", "pas", "sAp", "spa", "APS"};
		for(String next : check)
			assertTrue(strVec.contains(next));
	}
	
	@Test
	void testGetLargestAnagramGroupWithZeroAnagrams() {
		String[] strArr = new String[] {"one", "two", "three", "four", "five"};
		strArr = AnagramUtil.getLargestAnagramGroup(strArr);
		assertEquals(0, strArr.length);
	}
	
	@Test
	void testGetLargestAnagramGroupWithEmptyArray() {
		String[] strArr = new String[0];
		strArr = AnagramUtil.getLargestAnagramGroup(strArr);
		assertEquals(0, strArr.length);
	}
	
	@Test
	void testGetLargestAnagramGroupWithNullStrings() {
		String null1 = null, null2 = null, null3 = null, null4 = null;
		String[] nullArr = new String[] {null1, null2, null3, null4};
		assertThrows(NullPointerException.class, () -> {
			AnagramUtil.getLargestAnagramGroup(nullArr);
		});
	}
	
	@Test
	void testGetLargestAnagramGroupWithNullArray() {
		String[] nullArr = new String[2];
		assertThrows(NullPointerException.class, () -> {
			AnagramUtil.getLargestAnagramGroup(nullArr);
		});
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
