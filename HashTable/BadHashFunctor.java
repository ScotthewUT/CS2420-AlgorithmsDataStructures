package assignment10;

/**
 * Provides a hash function that converts a String to an int by taking
 * the sum of the chars contained in the String and dividing by the
 * String's length (i.e. returns the average char value).
 * 
 * NOTE: This provides very poor hashes. Tested on 50,000 alphanumeric
 * Strings of length 1-50, the hashes ranged from 48 to 122 with ~99%
 * collision rate.
 * 
 * @author Bridger Peterson (u0971665) & Scott Crowley (u1178178)
 * @version 14 November 2018
 */
public class BadHashFunctor implements HashFunctor {

	@Override
	public int hash(String item) {
		int hash = 0;
		for(int i = 0; i < item.length(); i++)
			hash += (int) item.charAt(i);
		hash /= item.length();
		return hash;
	}
}
