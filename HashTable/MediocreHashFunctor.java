package assignment10;

/**
 * Provides a hash function that converts a String to an int by taking
 * the product of the chars contained in the String, dividing by 11, &
 * flipping odd-length strings to negatives.
 * 
 * NOTE: This provides OK hashes. Tested on 50,000 alphanumeric Strings
 * of length 1-50, the hashes ranged from -195M to 195M (9% of total int
 * range) with ~29% collision rate.
 * 
 * @author Bridger Peterson (u0971665) & Scott Crowley (u1178178)
 * @version 12 November 2018
 */
public class MediocreHashFunctor implements HashFunctor {

	@Override
	public int hash(String item) {
		int hash = (int) Math.pow(-1, item.length());
		int product = 1;
		for(int i = 0; i < item.length(); i++) {
			product *= (int) item.charAt(i);
			if(product == 0)
				product = (int) item.charAt(i);
		}
		hash *= product;
		hash /= 11;
		return hash;
	}
}
