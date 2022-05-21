package assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * Class representation of a library (a collection of library books).
 * 
 * @author [CS 2420 Faculty], Scott Crowley (u1178178), & Kressa Fox (u0620628)
 * @version 2 September 2018
 */
public class LibraryGeneric<Type> {

	private ArrayList<LibraryBookGeneric<Type>> library;

	public LibraryGeneric() {
		library = new ArrayList<LibraryBookGeneric<Type>>();
	}


	/**
	 * Add the specified book to the library, assume no duplicates.
	 * 
	 * @param isbn --
	 *          ISBN of the book to be added
	 * @param author --
	 *          author of the book to be added
	 * @param title --
	 *          title of the book to be added
	 */
	public void add(long isbn, String author, String title) {
		library.add(new LibraryBookGeneric<Type>(isbn, author, title));
	}


	/**
	 * Add the list of library books to the library, assume no duplicates.
	 * 
	 * @param list --
	 *          list of library books to be added
	 */
	public void addAll(ArrayList<LibraryBookGeneric<Type>> list) {
		library.addAll(list);
	}


	/**
	 * Add books specified by the input file. One book per line with ISBN, author,
	 * and title separated by tabs.
	 * 
	 * If file does not exist or format is violated, do nothing.
	 * 
	 * @param filename
	 */
	@SuppressWarnings("resource")
	public void addAll(String filename) {
		ArrayList<LibraryBookGeneric<Type>> toBeAdded = new ArrayList<LibraryBookGeneric<Type>>();

		try {
			Scanner fileIn = new Scanner(new File(filename));
			int lineNum = 1;

			while (fileIn.hasNextLine()) {
				String line = fileIn.nextLine();

				Scanner lineIn = new Scanner(line);
				lineIn.useDelimiter("\\t");

				if (!lineIn.hasNextLong())
					throw new ParseException("ISBN", lineNum);
				long isbn = lineIn.nextLong();

				if (!lineIn.hasNext())
					throw new ParseException("Author", lineNum);
				String author = lineIn.next();

				if (!lineIn.hasNext())
					throw new ParseException("Title", lineNum);
				String title = lineIn.next();

				toBeAdded.add(new LibraryBookGeneric<Type>(isbn, author, title));

				lineNum++;
			}
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage() + " Nothing added to the library.");
			return;
		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage()
					+ " formatted incorrectly at line " + e.getErrorOffset()
					+ ". Nothing added to the library.");
			return;
		}

		library.addAll(toBeAdded);
	}


	/**
	 * Returns the holder of the library book with the specified ISBN.

	 * If no book with the specified ISBN is in the library, returns null.
	 * 
	 * @param isbn -- ISBN of the book to be looked up
	 * @return the library book's holder or null, if not checked out or not found
	 */
	public Type lookup(long isbn) {
		for(LibraryBookGeneric<Type> next : library) {		// Check each book in the library
			if(next.getIsbn() == isbn)						// and if the ISBNs match,
				return next.getHolder();					// return that book's holder.
		}
		return null;										// Return null if ISBN isn't found.
	}


	/**
	 * Returns the list of library books checked out to the specified holder.
	 * 
	 * If the specified holder has no books checked out, returns an empty list.
	 * 
	 * @param holder -- the holder to search for assigned books
	 * @return an ArrayList of LibaryBooks checked out by specified holder
	 */
	public ArrayList<LibraryBookGeneric<Type>> lookup(Type holder) {
		ArrayList<LibraryBookGeneric<Type>> heldBooks = new ArrayList<LibraryBookGeneric<Type>>();
		for(LibraryBookGeneric<Type> next : library) {
			if(next.getHolder() == holder && next.getHolder() != null)
				heldBooks.add(next);
		}
		return heldBooks;
	}


	/**
	 * Sets the holder and due date of the library book with the specified ISBN.
	 * 
	 * If no book with the specified ISBN is in the library, returns false.
	 * 
	 * If the book with the specified ISBN is already checked out, returns false.
	 * 
	 * Otherwise, returns true.
	 * 
	 * @param isbn --
	 *          ISBN of the library book to be checked out
	 * @param holder --
	 *          new holder of the library book
	 * @param month --
	 *          month of the new due date of the library book
	 * @param day --
	 *          day of the new due date of the library book
	 * @param year --
	 *          year of the new due date of the library book
	 * @return false is book not found or held; true otherwise
	 */
	public boolean checkout(long isbn, Type holder, int month, int day, int year) {
		for(LibraryBookGeneric<Type> next : library) {				// Check each book in the library
			if(next.getIsbn() == isbn)								// if ISBNs match,
				return next.checkOut(holder, month, day, year);		// call LibraryBook.checkOut().
		}
		return false;												// ISBN wasn't found.
	}


	/**
	 * Unsets the holder and due date of the library book.
	 * 
	 * If no book with the specified ISBN is in the library, returns false.
	 * 
	 * If the book with the specified ISBN is already checked in, returns false.
	 * 
	 * Otherwise, returns true.
	 * 
	 * @param isbn --
	 *          ISBN of the library book to be checked in
	 */
	public boolean checkin(long isbn) {
		for(LibraryBookGeneric<Type> next : library) {		// Check each book in the library
			if(next.getIsbn() == isbn)						// if ISBNs match,
				return next.checkIn();						// call LibraryBook.checkIn().
		}
		return false;										// ISBN wasn't found.
	}


	/**
	 * Unsets the holder and due date for all library books checked out by the
	 * specified holder.
	 * 
	 * If no books with the specified holder are in the library, returns false;
	 * 
	 * Otherwise, returns true.
	 * 
	 * @param holder --
	 *          holder of the library books to be checked in
	 */
	public boolean checkin(Type holder) {
		ArrayList<LibraryBookGeneric<Type>> heldBooks = this.lookup(holder);	// Use Library.lookup() to create list of holder's books
		if(heldBooks.size() < 1)												// If that list is empty, return false.
			return false;
		for(LibraryBookGeneric<Type> next : heldBooks) {						// For each book in the list
			next.checkIn();  													// call LibraryBook.checkIn().
		}
		return true;
	}


	/**
	 * Returns the list of library books, sorted by ISBN (smallest ISBN first).
	 */
	public ArrayList<LibraryBookGeneric<Type>> getInventoryList() {
		ArrayList<LibraryBookGeneric<Type>> libraryCopy = new ArrayList<LibraryBookGeneric<Type>>();
		libraryCopy.addAll(library);

		OrderByIsbn comparator = new OrderByIsbn();

		sort(libraryCopy, comparator);

		return libraryCopy;
	}
	
	
	/**
	 * Returns the list of library books, sorted by author
	 */
	public ArrayList<LibraryBookGeneric<Type>> getOrderedByAuthor() {
		ArrayList<LibraryBookGeneric<Type>> libraryCopy = new ArrayList<LibraryBookGeneric<Type>>();
		libraryCopy.addAll(library);

		OrderByAuthor comparator = new OrderByAuthor();

		sort(libraryCopy, comparator);

		return libraryCopy;
	}
	
	
	/**
	 * Returns the list of library books whose due date is older than the input
	 * date. The list is sorted by date (oldest first).
	 *
	 * If no library books are overdue, returns an empty list.
	 * @param month -- number of month to check against
	 * @param day -- day of the month to check against
	 * @param year -- year to check against
	 * @return a list of library books that are overdue.
	 */
	public ArrayList<LibraryBookGeneric<Type>> getOverdueList(int month, int day, int year) {
		// Create a Date object from the primitive parameters to use for comparison.
		GregorianCalendar today = new GregorianCalendar(year, month, day);
		// Initialize an list that will hold all the library's overdue books.
		ArrayList<LibraryBookGeneric<Type>> overdueList = new ArrayList<LibraryBookGeneric<Type>>();
		// For each book in the library check if they're overdue.
		for(LibraryBookGeneric<Type> next : library) {
			// Books that aren't checked out can be skipped.
			if(next.getHolder() == null)
				continue;
			// If the book's due date is earlier than "today," it's overdue.
			if(next.getDueDate().compareTo(today) < 0) {
				// Add the overdue book to the list in sorted order.
				for(int idx = 0; idx < overdueList.size() + 1; idx++) {
					if(idx == overdueList.size()) {
						overdueList.add(next);
						break;
					}
					if(overdueList.get(idx).getDueDate().compareTo(next.getDueDate()) > 0) {
						overdueList.add(idx, next);
						break;
					}
				}
			}
		}
		return overdueList;
	}
	

	/**
	 * Performs a SELECTION SORT on the input ArrayList. 
	 * 1. Find the smallest item in the list. 
	 * 2. Swap the smallest item with the first item in the list. 
	 * 3. Now let the list be the remaining unsorted portion 
	 * (second item to Nth item) and repeat steps 1, 2, and 3.
	 */
	private static <ListType> void sort(ArrayList<ListType> list, Comparator<ListType> c) {
		for (int i = 0; i < list.size() - 1; i++) {
			int j, minIndex;
			for (j = i + 1, minIndex = i; j < list.size(); j++)
				if (c.compare(list.get(j), list.get(minIndex)) < 0)
					minIndex = j;
			ListType temp = list.get(i);
			list.set(i, list.get(minIndex));
			list.set(minIndex, temp);
		}
	}

	
	/**
	 * Comparator that defines an ordering among library books using the ISBN.
	 */
	protected class OrderByIsbn implements Comparator<LibraryBookGeneric<Type>> {

		/**
		 * Returns a negative value if lhs is smaller than rhs. Returns a positive
		 * value if lhs is larger than rhs. Returns 0 if lhs and rhs are equal.
		 */
		public int compare(LibraryBookGeneric<Type> lhs,
				LibraryBookGeneric<Type> rhs) {
			return (int) (lhs.getIsbn() - rhs.getIsbn());
		}
	}

	
	/**
	 * Comparator that defines an ordering among library books using the author, and book title as a tie-breaker.
	 */
	protected class OrderByAuthor implements Comparator<LibraryBookGeneric<Type>> {
		
		public int compare(LibraryBookGeneric<Type> lhs, LibraryBookGeneric<Type> rhs) {
			if(lhs.getAuthor() == rhs.getAuthor())
				return (int) lhs.getTitle().compareTo(rhs.getTitle());
			return (int) lhs.getAuthor().compareTo(rhs.getAuthor());
		}
	}

	
	/**
	 * Comparator that defines an ordering among library books using the due date.
	 */
	protected class OrderByDueDate implements Comparator<LibraryBookGeneric<Type>> {
		
		public int compare(LibraryBookGeneric<Type> lhs, LibraryBookGeneric<Type> rhs) {
			return (int) (lhs.getDueDate().compareTo(rhs.getDueDate()));
		}
	}
}
