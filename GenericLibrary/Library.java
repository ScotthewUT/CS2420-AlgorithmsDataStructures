package assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class representation of a library (a collection of library books).
 * 
 * @author [CS 2420 Faculty], Scott Crowley (u1178178), & Kressa Fox (u0620628)
 * @version 31 August 2018
 */
public class Library {

  private ArrayList<LibraryBook> library;

  public Library() {
    library = new ArrayList<LibraryBook>();
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
    library.add(new LibraryBook(isbn, author, title));
  }
  
  
  /**
   * Add the list of library books to the library, assume no duplicates.
   * 
   * @param list --
   *          list of library books to be added
   */
  public void addAll(ArrayList<LibraryBook> list) {
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
    ArrayList<LibraryBook> toBeAdded = new ArrayList<LibraryBook>();

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

        toBeAdded.add(new LibraryBook(isbn, author, title));

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
  public String lookup(long isbn) {
	 for(LibraryBook next : library) {		// Check each book in the library
		 if(next.getIsbn() == isbn)			// and if the ISBNs match,
			 return next.getHolder();		// return that book's holder.
	 }
    return null;							// Return null if ISBN isn't found.
  }
  
  
  /**
   * Returns the list of library books checked out to the specified holder.
   * 
   * If the specified holder has no books checked out, returns an empty list.
   * 
   * @param holder -- the holder to search for assigned books
   * @return an ArrayList of LibaryBooks checked out by specified holder
   */
  public ArrayList<LibraryBook> lookup(String holder) {
	ArrayList<LibraryBook> heldBooks = new ArrayList<LibraryBook>();
	for(LibraryBook next : library) {
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
  public boolean checkout(long isbn, String holder, int month, int day, int year) {
    for(LibraryBook next : library) {							// Check each book in the library
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
    for(LibraryBook next : library) {		// Check each book in the library
    	if(next.getIsbn() == isbn)			// if ISBNs match,
    		return next.checkIn();			// call LibraryBook.checkIn().
    }
    return false;							// ISBN wasn't found.
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
  public boolean checkin(String holder) {
	  ArrayList<LibraryBook> heldBooks = this.lookup(holder);	// Use Library.lookup() to create list of holder's books
	  if(heldBooks.size() < 1)									// If that list is empty, return false.
		  return false;
	  for(LibraryBook next : heldBooks) {						// For each book in the list
		  next.checkIn();  										// call LibraryBook.checkIn().
	  }
	  return true;
  }
}
