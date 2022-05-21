package assignment10;

/**
 * Provides a hash function that converts a String to an int. The method
 * takes advantage of prime numbers and bitwise shifting.
 * 
 * NOTE: This provides good hashes. Tested on 50,000 alphanumeric Strings
 * of length 1-50, the hashes ranged from -2.1B to 2.1B (~100% of total int
 * range) with 0-3 collisions.
 * 
 * @author Bridger Peterson (u0971665) & Scott Crowley (u1178178)
 * @version 9 November 2018
 */
public class GoodHashFunctor implements HashFunctor {

	@Override
	public int hash(String item) {
		int hash = 4999;
		for(int i = 0; i < item.length(); i++)
			hash += (hash << (item.charAt(i) % 31)) + item.charAt(i) * 11;
		hash *= (int) Math.pow(-1, item.length());
		hash -= 727;
		return hash;
	}
}
