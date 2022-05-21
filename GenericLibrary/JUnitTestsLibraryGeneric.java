package assignment2;

import java.util.ArrayList;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for the LibraryGeneric class.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 2 September 2018
 */
class JUnitTestsLibraryGeneric {
	
	LibraryGeneric<String> stringLib;
	LibraryGeneric<Integer> numberLib;
	LibraryGeneric<PhoneNumber> phoneLib;

	@BeforeEach
	void setUp() {
		stringLib = new LibraryGeneric<String>();
		stringLib.addAll("Mushroom_Publishing.txt");
		numberLib = new LibraryGeneric<Integer>();
	    numberLib.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
	    numberLib.add(9780330351690L, "Jon Krakauer", "Into the Wild");
	    numberLib.add(9780446580342L, "David Baldacci", "Simple Genius");
	    phoneLib = new LibraryGeneric<PhoneNumber>();
	    phoneLib.add(9780486275437L, "Lewis Carroll", "Alice's Adventures in Wonderland");
	    phoneLib.add(9781840227864L, "Antoine de Saint-Exupéry", "The Little Prince");
	    phoneLib.add(9780062484390L, "Agatha Christie", "And Then There Were None");
	    phoneLib.add(9780804840965L, "Cao Xueqin", "Dream of the Red Chamber");
	    phoneLib.add(9780007581146L, "J. R. R. Tolkien", "The Lord of the Rings");
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void testCheckOutStrings() {
		Assert.assertTrue(stringLib.checkout(9781843190004L, "Bookworm Jim", 10, 31, 2019));
		Assert.assertFalse(stringLib.checkout(9999999999999L, "Bookworm Jim", 10, 31, 2019));
		Assert.assertFalse(stringLib.checkout(9781843190004L, "Jane Doe", 10, 31, 2019));
	}
	
	@Test
	void testCheckInString() {
		stringLib.checkout(9781843190004L, "Bookworm Jim", 10, 31, 2019);
		stringLib.checkout(9781843190011L, "Bookworm Jim", 10, 31, 2019);
		stringLib.checkout(9781843190028L, "Bookworm Jim", 10, 31, 2019);
		stringLib.checkout(9781843190042L, "Bookworm Jim", 10, 31, 2019);
		Assert.assertTrue(stringLib.checkin(9781843190004L));
		Assert.assertFalse(stringLib.checkin(9999999999999L));
		Assert.assertFalse(stringLib.checkin(9781843190004L));
		Assert.assertTrue(stringLib.checkin("Bookworm Jim"));
		Assert.assertFalse(stringLib.checkin(9781843190042L));
	}
	
	@Test
	void testCheckOutNumbers() {
		Assert.assertTrue(numberLib.checkout(9780374292799L, 8675309, 10, 31, 2019));
		Assert.assertFalse(numberLib.checkout(9999999999999L, 8675309, 10, 31, 2019));
		Assert.assertFalse(numberLib.checkout(9780374292799L, 9999999, 10, 31, 2019));
	}
	
	@Test
	void testCheckInNumbers() {
		Integer holder = 8675309;
		numberLib.checkout(9780374292799L, holder, 10, 31, 2019);
		numberLib.checkout(9780330351690L, holder, 10, 31, 2019);
		numberLib.checkout(9780446580342L, holder, 10, 31, 2019);
		Assert.assertTrue(numberLib.checkin(9780374292799L));
		Assert.assertFalse(numberLib.checkin(9999999999999L));
		Assert.assertFalse(numberLib.checkin(9780374292799L));
		Assert.assertTrue(numberLib.checkin(holder));
		Assert.assertFalse(numberLib.checkin(9780446580342L));
	}
	
	@Test
	void testLookupString() {
		stringLib.checkout(9781843190004L, "Bookworm Jim", 10, 31, 2019);
		stringLib.checkout(9781843190011L, "Bookworm Jim", 10, 31, 2019);
		stringLib.checkout(9781843190028L, "Bookworm Jim", 10, 31, 2019);
		stringLib.checkout(9781843190042L, "Bookworm Jim", 10, 31, 2019);
		Assert.assertTrue(stringLib.lookup(9781843190004L).equals("Bookworm Jim"));
		Assert.assertNull(stringLib.lookup(9999999999999L));
		Assert.assertNull(stringLib.lookup(9781843193319L));
		ArrayList<LibraryBookGeneric<String>> heldBooks = stringLib.lookup("Bookworm Jim");
		Assert.assertTrue(heldBooks.get(0).getIsbn() == 9781843190004L);
		Assert.assertTrue(heldBooks.size() == 4);
		heldBooks = stringLib.lookup("Jane Doe");
		Assert.assertTrue(heldBooks.size() == 0);
	}
	
	@Test
	void testLookupNumber() {
		Integer holder = 8675309;
		numberLib.checkout(9780374292799L, holder, 10, 31, 2019);
		numberLib.checkout(9780330351690L, holder, 10, 31, 2019);
		numberLib.checkout(9780446580342L, holder, 10, 31, 2019);
		Assert.assertTrue(numberLib.lookup(9780374292799L).equals(8675309));
		Assert.assertNull(numberLib.lookup(9999999999999L));
		Assert.assertNull(numberLib.lookup(9781843193319L));
		ArrayList<LibraryBookGeneric<Integer>> heldBooks = numberLib.lookup(holder);
		Assert.assertTrue(heldBooks.get(0).getIsbn() == 9780374292799L);
		Assert.assertTrue(heldBooks.size() == 3);
		heldBooks = numberLib.lookup((Integer) 777777);
		Assert.assertTrue(heldBooks.size() == 0);
	}
	
	@Test
	void testPhoneNumber() {
		PhoneNumber holder = new PhoneNumber("(415) 867-5309");
		
	    Assert.assertTrue(phoneLib.checkout(9780486275437L, holder, 3, 2, 2019));
	    Assert.assertTrue(phoneLib.checkout(9781840227864L, holder, 10, 2, 2018));
	    Assert.assertTrue(phoneLib.checkout(9780804840965L, holder, 1, 1, 2020));
	    Assert.assertTrue(phoneLib.checkout(9780062484390L, holder, 9, 2, 2019));
	    Assert.assertFalse(phoneLib.checkout(9780062484390L, holder, 9, 2, 2019));
	    Assert.assertEquals(holder, phoneLib.lookup(9780804840965L));
	    Assert.assertEquals(4, phoneLib.lookup(holder).size());
	    
	    Assert.assertTrue(phoneLib.checkin(9780804840965L));
	    Assert.assertFalse(phoneLib.checkin(9780804840965L));
	    Assert.assertTrue(phoneLib.checkin(holder));
	    Assert.assertNull(phoneLib.lookup(9780804840965L));
	    Assert.assertEquals(0, phoneLib.lookup(holder).size()); 
	}
	
	@Test
	void testInventoryList() {
		ArrayList<LibraryBookGeneric<PhoneNumber>> inventoryList = phoneLib.getInventoryList();
		for(int idx = 0; idx + 1 < inventoryList.size(); idx++) {
			Assert.assertTrue(inventoryList.get(idx).getIsbn() <= inventoryList.get(idx + 1).getIsbn());
		}
	}
	
	@Test
	void testOrderedByAuthor() {
		ArrayList<LibraryBookGeneric<PhoneNumber>> authorSorted = phoneLib.getOrderedByAuthor();
		for(int idx = 0; idx + 1 < authorSorted.size(); idx++) {
			Assert.assertTrue(authorSorted.get(idx).getAuthor().compareTo(authorSorted.get(idx + 1).getAuthor()) <= 0);
		}
	}
	
	@Test
	void testOverdueList() {
		PhoneNumber holder = new PhoneNumber("(415) 867-5309");
		phoneLib.checkout(9780486275437L, holder, 3, 2, 2019);
		phoneLib.checkout(9781840227864L, holder, 10, 2, 2018);
		phoneLib.checkout(9780062484390L, holder, 1, 1, 2020);
		phoneLib.checkout(9780804840965L, holder, 9, 2, 2019);
		phoneLib.checkout(9780007581146L, holder, 1, 1, 2019);
		ArrayList<LibraryBookGeneric<PhoneNumber>> overdueList = phoneLib.getOverdueList(12, 31, 2019);
		for(int idx = 0; idx + 1 < overdueList.size(); idx++) {
			Assert.assertTrue(overdueList.get(idx).getDueDate().compareTo(overdueList.get(idx + 1).getDueDate()) <= 0);
		}
		Assert.assertEquals(4, overdueList.size());
	}
}
