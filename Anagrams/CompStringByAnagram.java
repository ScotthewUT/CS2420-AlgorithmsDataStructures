package assignment4;

import java.util.Comparator;

/**
 * Comparator used to sort groups of anagrams. If two Strings are anagrams,
 * returns 0 (e.g. the arguments are equal). For our purposes the non-equal
 * cases which return a positive or negative integer are unimportant.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 17 September 2018
 *
 */
public class CompStringByAnagram implements Comparator<String> {

	public int compare(String _lhs, String _rhs) {
		String lhs = AnagramUtil.sort(_lhs.toLowerCase()).toLowerCase();
		String rhs = AnagramUtil.sort(_rhs.toLowerCase()).toLowerCase();
		return lhs.compareTo(rhs);
	}
}