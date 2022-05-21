package assignment4;

import java.util.Comparator;

/**
 * Implements Comparator to compare Characters lexicographically, ignoring case.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 14 September 2018
 *
 */
public class CompCharByLexIgnoreCase implements Comparator<Character> {

	public int compare(Character _lhs, Character _rhs) {
		Character lhs = Character.toLowerCase(_lhs);
		Character rhs = Character.toLowerCase(_rhs);
		return lhs.compareTo(rhs);
	}
}

