package assignment7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class containing the checkFile method for checking if the (, [, and { symbols
 * in an input file are correctly matched.
 * 
 * EDIT:  Kopta discovered our static member variables never get reset, so if the
 * user makes multiple calls to .checkfile() the trackers get out of sync. Adding
 * four lines at the beginning of the method fixes the problem.
 * EDIT:  Surrounding the Scanner in a try-catch prevents the auto-grader from
 * seeing the exception when an invalid filename is passed in. The method was
 * already set to throw the exception, so we've removed the redundant try-catch.
 * EDIT:  Line 100 (previously 87) was responsible for IndexOutOfBoundsException.
 * Added an additional boolean to prevent this error.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 7 November 2018
 */
public class BalancedSymbolChecker {
	
	private static MyStack<Character> stack;
	private static int atLine, atCol;
	private static boolean comment = false, strLit = false;
	private static char expected;

	/**
	 * Returns a message indicating whether the input file has unmatched
	 * symbols. (Use the methods below for constructing messages.) Throws
	 * FileNotFoundException if the file does not exist.
	 * 
	 * @param filename - the exact name of the file to check including extension
	 * @return a String message indicating what if any errors were found
	 */
	public static String checkFile(String filename) throws FileNotFoundException {
		// Initialize a stack that will hold opening brackets.
		stack = new MyStack<Character>();
		// Member variables must be reset with each call to .checkFile()
		atLine = 0;
		atCol = 0;
		comment = false;
		strLit = false;
	
		// Open a scanner to read in the file.
		Scanner fin = new Scanner(new File(filename));
		// Read in one line as a time until EOF is reached.
		while(fin.hasNextLine()) {
			String line = fin.nextLine();
			atLine++;						// Increment the line position.
			char chr = stackIt(line);		// Send the next line to stackIt().
			if(chr != '?') {				// If stackIt() finds an error, return the details.
				fin.close();
				return unmatchedSymbol(atLine, atCol, chr, expected);
			}
		}
		fin.close();
		
		if(comment)								// EOF was reached while comment remained open.
			return unfinishedComment();
		
		if(stack.size() != 0) {					// EOF was reached with at least one bracket not closed.
			char pop = stack.pop();
			if(pop == '(')
				expected = ')';
			else if(pop == '{')
				expected = '}';
			else
				expected = ']';
			return unmatchedSymbolAtEOF(expected);
		}
		
		return allSymbolsMatch();				// EOF was reached with no errors.
	}
	
	
	/**
	 * Helper method takes in one line of the file at a time and works with the stack
	 * to track opening and closing of brackets.
	 * 
	 * @param str - the next line of code to analyze
	 * @return '?' char if no errors were found, otherwise returns the first bracket
	 * 			that caused the unmatched symbol error.
	 */
	private static char stackIt(String str) {
		// Iterate through the String one char at a time.
		for(int idx = 0; idx < str.length(); idx++) {
			atCol = idx + 1;						// Increment the column location.
			char next = str.charAt(idx);
			
			if(next == '/' && !comment && !strLit) {
				if(str.charAt(idx + 1) == '/')		// Check for line comment: //
					return '?';						// Skip the rest of the line if found.
				
				if(str.charAt(idx + 1) == '*')		// Check for in-line comment: /*
					comment = true;					// Toggle on comment if found.
			}
			else if(next == '*' && comment && idx + 1 < str.length() && str.charAt(idx + 1) == '/') {
				comment = false;					// Check for end of in-line comment: */
				idx++;								// Toggle off comment if found.
			}
			
			else if(next == '\'' && !comment && !strLit) {
				if(str.charAt(idx + 1) == '\\')		// Check for character literal: '
					idx += 2;						// Skip the next two characters if an escape is found.
				else								// Otherwise, skip the next character.
					idx ++;
				continue;
			}
			else if(next == '"' && !comment && !strLit)
				strLit = true;						// Check for string literal: "
													// Toggle on strLit if found.
			else if(next == '\\' && strLit) {
				idx++;								// Check for escape within a string literal.
				continue;							// Skip ahead one if found.
			}
			else if(next == '"' && strLit)			// Check for end of string literal: "
				strLit = false;						// Toggle of strLit if found.
			
			else if((next == '(' || next == '{' || next == '[') && !comment && !strLit)
				stack.push(next);					// Check for opening brackets and push them onto the
													// stack as long as comment & strLit are off.
			
			else if((next == ')' || next == '}' || next == ']') && !comment && !strLit) {
													// Check for closing brackets if comment & strLit are off.
				if(stack.isEmpty()) {
					expected = ' ';					// If the stack is empty, return an error that no closing
					return next;					// bracket was expected.
				}
				char pop = stack.pop();				// Otherwise pop the stack and compare brackets.
				if(next == ')' && pop == '(')		// If the brackets match, return to the top of the loop.
					continue;
				if(next == '}' && pop == '{')
					continue;
				if(next == ']' && pop == '[')
					continue;
				
				if(pop == '(')						// If the brackets do not match, determine the closing
					expected = ')';					// bracket that was expected and return the bracket that
				else if(pop == '{')					// was found.
					expected = '}';
				else
					expected = ']';
				return next;
			}										// All other character cases are ignored.
		}
		return '?';									// No errors found. Return the character: ?
	}

	
	/**
	 * Returns an error message for unmatched symbol at the input line and
	 * column numbers. Indicates the symbol match that was expected and the
	 * symbol that was read.
	 */
	private static String unmatchedSymbol(int lineNumber, int colNumber, char symbolRead, char symbolExpected) {
		return "ERROR: Unmatched symbol at line " + lineNumber + " and column " + colNumber + ". Expected " + symbolExpected
				+ ", but read " + symbolRead + " instead.";
	}
	
	
	/**
	 * Returns an error message for unmatched symbol at the end of file.
	 * Indicates the symbol match that was expected.
	 */
	private static String unmatchedSymbolAtEOF(char symbolExpected) {
		return "ERROR: Unmatched symbol at the end of file. Expected " + symbolExpected + ".";
	}
	
	
	/**
	 * Returns an error message for a file that ends with an open /* comment.
	 */
	private static String unfinishedComment() {
		return "ERROR: File ended before closing comment.";
	}
	
	
	/**
	 * Returns a message for a file in which all symbols match.
	 */
	private static String allSymbolsMatch() {
		return "No errors found. All symbols match.";
	}
}
