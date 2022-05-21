package assignment10;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Random;

/**
 * Provides unit tests for the QuadProbeHashTable class.
 * 
 * @author Bridger Peterson (u0971665) & Scott Crowley (u1178178)
 * @version 12 November 2018
 */
class UnitTestsForQuadProbeHashTable {

	static QuadProbeHashTable hashTable;
	static GoodHashFunctor goodFxn;
	static MediocreHashFunctor okFxn;
	static BadHashFunctor badFxn;
	static ArrayList<String> strList;
	static ArrayList<String> randList;
	
	@BeforeAll
	static void setUpBeforeClass() {
		goodFxn = new GoodHashFunctor();
		okFxn = new MediocreHashFunctor();
		badFxn = new BadHashFunctor();
		
		strList = new ArrayList<String>(7);
		strList.add("I am the very model of a modern Major-General.");
		strList.add("I've information vegetable,");
		strList.add("animal,");
		strList.add("and mineral.");
		strList.add("I know the kings of England,");
		strList.add("and I quote the fights historical");
		strList.add("From Marathon to Waterloo, in order categorical.");
		
		Random rand = new Random(1);
		randList = new ArrayList<String>(32000);
		int length = rand.nextInt(100) + 1;
		while(randList.size() < 32000) {
			String str = "";
			for(int j = 0; j < length; j++)
				str += (char) (48 + rand.nextInt(75));
			if(!randList.contains(str))
				randList.add(str);
		}
	}
	
	@Test
	void testAdd() {
		hashTable = new QuadProbeHashTable(10, okFxn);
		
		assertTrue(hashTable.isEmpty());
		assertTrue(hashTable.add("word"));
		assertFalse(hashTable.add("word"));
		assertEquals(1, hashTable.size());
		assertFalse(hashTable.isEmpty());
	}
	
	@Test
	void testAddAll() {
		hashTable = new QuadProbeHashTable(10, goodFxn);
		
		assertTrue(hashTable.addAll(strList));
		assertFalse(hashTable.addAll(strList));
		assertTrue(!hashTable.isEmpty());
	}
	
	@Test
	void testContains() {
		hashTable = new QuadProbeHashTable(10, badFxn);
		hashTable.add("cat");
		hashTable.add("please");
		hashTable.add("stop scratching me");
		
		assertTrue(hashTable.contains("cat"));
		assertTrue(hashTable.contains("please"));
		assertTrue(hashTable.contains("stop scratching me"));
		assertFalse(hashTable.contains("right meow"));
	}
	
	@Test
	void testContainsAll() {
		hashTable = new QuadProbeHashTable(10, goodFxn);
		hashTable.addAll(strList);
		
		assertTrue(hashTable.containsAll(strList));
	}
	
	@Test
	void testWithLargeList() {
		hashTable = new QuadProbeHashTable(10, goodFxn);
		
		assertTrue(hashTable.isEmpty());
		assertTrue(hashTable.addAll(randList));
		assertFalse(hashTable.isEmpty());
		assertEquals(randList.size(), hashTable.size());
		
		for(int i = 0; i < randList.size(); i += 100) {
			assertTrue(hashTable.contains(randList.get(i)));
			assertFalse(hashTable.add(randList.get(i)));
		}
		
		hashTable.clear();
		assertEquals(0, hashTable.size());
		
		Random rand = new Random(1);
		ArrayList<String> temp = new ArrayList<String>();
		for(int i = 0; i < randList.size(); i += 10) {
			int initialSize = hashTable.size();
			String next = randList.remove(rand.nextInt(randList.size()));
			assertTrue(hashTable.add(next));
			assertEquals(initialSize + 1, hashTable.size());
			temp.add(next);
		}
		randList = temp;
	}
}
