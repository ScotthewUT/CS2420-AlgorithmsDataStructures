package assignment4;

import java.util.Comparator;

/**
 * Wraps Character.compareTo() in a Comparator for use in AnagramUtil.sort().
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 17 September 2018
 *
 */
public class CompCharByLex implements Comparator<Character> {

	public int compare(Character lhs, Character rhs) {
		return lhs.compareTo(rhs);
	}
}