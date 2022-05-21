package assignment6;

/**
 * Test demo of the overloaded constructor that should make a clone linked list.
 * 
 * @author Scott Crowley (u1178178)
 * @version 2 October 2018
 */
public class TestCloneConstructor {

	public static void main(String[] args) {
		
		MyLinkedList<Integer> intList = new MyLinkedList<Integer>();
		for(int i = 1; i < 21; i++)
			intList.addLast(i);
		
		MyLinkedList<Integer> copiedList = new MyLinkedList<Integer>(intList);
		System.out.println("The size of the copied list is: " + copiedList.size());
		String copied = "It contains: ";
		for(int i = 0; i < copiedList.size() - 1; i++)
			copied += copiedList.get(i) + ", ";
		copied += "& " + copiedList.get(copiedList.size() - 1) + ".";
		System.out.println(copied);
	}
}
