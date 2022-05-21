package assignment2;

import java.util.GregorianCalendar;

/**
 * Class representation of a library book that inherits from Book.
 * Adds holder and due date members that update with check in/out methods.
 * ISBN, author, and title cannot be changed once a LibraryBook is created.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 31 August 2018
 */
public class LibraryBook extends Book {
	
	private String holder;
	private GregorianCalendar dueDate;

	public LibraryBook(long _isbn, String _author, String _title) {
		super(_isbn, _author, _title);
		this.holder = null;
		this.dueDate = null;
	}
	
	
	/**
	 * Getter method for LibraryBook's holder.
	 * 
	 * @return LibraryBook's holder
	 */
	public String getHolder() {
		return this.holder;
	}
	
	
	/**
	 * Getter method for LibraryBook's due date.
	 * 
	 * @return LibraryBook's due date
	 */
	public GregorianCalendar getDueDate() {
		return this.dueDate;
	}
	
	
	/**
	 * Checks LibraryBook in by setting holder and due date to null.
	 * 
	 * @return false if book is already checked in
	 */
	public boolean checkIn() {
		if(this.holder == null)
			return false;
		this.holder = null;
		this.dueDate = null;
		return true;
	}
	
	
	/**
	 * Checks LibraryBook out by assigning new holder and due date.
	 * 
	 * @param _holder	-- book's new holder
	 * @param month		-- month of due date
	 * @param day		-- day of month due date
	 * @param year		-- year of due date
	 * 
	 * @return false if book is already checked out; true if it's available
	 */
	public boolean checkOut(String _holder, int month, int day, int year) {
		if(this.holder != null || this.dueDate != null)
			return false;
		this.holder = _holder;
		this.dueDate = new GregorianCalendar(year, month, day);
		return true;
	}
}
