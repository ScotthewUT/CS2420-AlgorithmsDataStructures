package assignment3;

import java.util.ArrayList;

/**
 * Demonstration of Object.equals() & Collection.contains() for
 * our own understanding.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u06206
 * @version 6 September 2018
 */

public class ObjectEqualsMethodTest {

	public static void main(String[] args) {
		
		ArrayList<Integer> intVec = new ArrayList<Integer>();
		Long x = 7L;
		Integer y = 7;
		
		for(int idx = 1; idx < 10; idx++) {
			intVec.add(idx);
		}
		
		if(intVec.get(6).equals(x))
			System.out.println("Integer 7 = Long 7");
		if(intVec.get(6).equals((Object) x))
			System.out.println("Integer 7 = (Object) Long 7");
		if(intVec.contains(x))
			System.out.println("List conatains Long 7");
		if(intVec.contains((Object) x))
			System.out.println("List contains (Object) Long 7");
		
		if(intVec.get(6).equals(y))
			System.out.println("Integer 7 = Integer 7");
		if(intVec.get(6).equals((Object) y))
			System.out.println("Integer 7 = (Object) Integer 7");
		if(intVec.contains(y))
			System.out.println("List conatains Integer 7");
		if(intVec.contains((Object) y))
			System.out.println("List contains (Object) Integer 7");
	}
}
