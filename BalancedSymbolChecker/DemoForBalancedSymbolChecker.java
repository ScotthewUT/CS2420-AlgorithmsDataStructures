package assignment7;

import java.io.FileNotFoundException;

/**
 * Tests and demonstrates the use of BalancedSymbolChecker with the 14 class
 * files supplied.
 * 
 * EDIT: Removed the try-catches so we can observe if .checkFile throws the
 * FileNotFoundException as it should. Added a few more files for testing.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 7 November 2018
 */
public class DemoForBalancedSymbolChecker {

	public static void main(String[] args) throws FileNotFoundException {
		
//		String badFile = "Class0.java";
//		System.out.println("Class0.java:\t" + BalancedSymbolChecker.checkFile(badFile));
		
		String filename = "Class1.java";
		System.out.println("Class1.java:\t" + BalancedSymbolChecker.checkFile(filename));
		
		filename = "Class2.java";
		System.out.println("Class2.java:\t" + BalancedSymbolChecker.checkFile(filename));
		
		filename = "Class3.java";
		System.out.println("Class3.java:\t" + BalancedSymbolChecker.checkFile(filename));
		
		filename = "Class4.java";
		System.out.println("Class4.java:\t" + BalancedSymbolChecker.checkFile(filename));
		
		filename = "Class5.java";
		System.out.println("Class5.java:\t" + BalancedSymbolChecker.checkFile(filename));
		
		filename = "Class6.java";
		System.out.println("Class6.java:\t" + BalancedSymbolChecker.checkFile(filename));
		
		filename = "Class7.java";
		System.out.println("Class7.java:\t" + BalancedSymbolChecker.checkFile(filename));
		
		filename = "Class8.java";
		System.out.println("Class8.java:\t" + BalancedSymbolChecker.checkFile(filename));
		
		filename = "Class9.java";
		System.out.println("Class9.java:\t" + BalancedSymbolChecker.checkFile(filename));
		
		filename = "Class10.java";
		System.out.println("Class10.java:\t" + BalancedSymbolChecker.checkFile(filename));
		
		filename = "Class11.java";
		System.out.println("Class11.java:\t" + BalancedSymbolChecker.checkFile(filename));
		
		filename = "Class12.java";
		System.out.println("Class12.java:\t" + BalancedSymbolChecker.checkFile(filename));
		
		filename = "Class13.java";
		System.out.println("Class13.java:\t" + BalancedSymbolChecker.checkFile(filename));
		
		filename = "Class14.java";
		System.out.println("Class14.java:\t" + BalancedSymbolChecker.checkFile(filename));
		
		filename = "AnagramUtil.java";
		System.out.println("AnagramUtil.java:\t" + BalancedSymbolChecker.checkFile(filename));
		
		filename = "ArrayCollection.java";
		System.out.println("ArrayCollection.java:\t" + BalancedSymbolChecker.checkFile(filename));
		
		filename = "LibraryGeneric.java";
		System.out.println("LibraryGeneric.java:\t" + BalancedSymbolChecker.checkFile(filename));
		
		filename = "MyLinkedList.java";
		System.out.println("MyLinkedList.java:\t" + BalancedSymbolChecker.checkFile(filename));
		
		filename = "SortUtil.java";
		System.out.println("SortUtil.java:\t" + BalancedSymbolChecker.checkFile(filename));
	}
}
