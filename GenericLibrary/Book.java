package assignment2;

/**
 * Class representation of a book. The ISBN, author, and title can never change
 * once the book is created.
 * 
 * Note that ISBNs are unique.
 *
 * @author [CS 2420 Faculty], Scott Crowley (u1178178), & Kressa Fox (u0620628)
 * @version 31 August 2018
 */
public class Book {

  private long isbn;

  private String author;

  private String title;

  public Book(long _isbn, String _author, String _title) {
    this.isbn = _isbn;
    this.author = _author;
    this.title = _title;
  }

  /**
   * @return the author
   */
  public String getAuthor() {
    return this.author;
  }

  /**
   * @return the ISBN
   */
  public long getIsbn() {
    return this.isbn;
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Two books are considered equal if they have the same ISBN, author, and
   * title.
   * 
   * @param other --
   *          the object begin compared with "this"
   * @return true if "other" is a Book and is equal to "this", false otherwise
   */
  public boolean equals(Object other) {
    
	if(!(other instanceof Book))				// Parameter, other, must be a Book.
		return false;
	  
	Book bookOther = (Book)other;				// Casting other to Book from Object.
	
	if(bookOther.getIsbn() == 0 || bookOther.getAuthor() == null || bookOther.getTitle() == null)
		return false;							// Check for an uninitialized Book.
	
	if(this.isbn != bookOther.getIsbn())
		return false;
	if(this.author != bookOther.getAuthor())	// ISBN, Author, & Title must match for the books to be equal.
		return false;
	if(this.title != bookOther.getTitle())
		return false;
	
    return true;
  }
  
  /**
   * Returns a string representation of the book.
   */
  public String toString() {
    return isbn + ", " + author + ", \"" + title + "\"";
  }
}
