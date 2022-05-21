package comprehensive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Uses a provided grammar to produce random phrases.
 * 
 * Supports command-line arguments for specifying the location of the input grammar file
 * well as the number of random phrases to output.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 29 November 2018
 */
public class RandomPhraseGenerator {
	
	private static ArrayList<String> grammar;					// ArrayList representation of the grammar.
	private static HashMap<String, ArrayList<String>> gramMap;	// HashMap representation of the grammar.

	public static void main(String[] args) throws IOException {
		
		String filepath = "testgrammar.g";
		int count = 3;
		
		// TODO Is it OK to put our command-line arguments behind if-statements?
		// The 1st command-line argument is the file path to the grammar.
		if(args.length > 0)
			filepath = args[0];
		// The 2nd command-line argument is the number of phrases to generate.
		if(args.length > 1)
			count = Integer.parseInt(args[1]);
		
		// Read in the grammar file.
		grammarReader(filepath);
		// Convert the grammar to a map.
		grammarToMap();
		// Initialize an RNG.
		Random rand = new Random(System.nanoTime());
		// Get grammar's <start>.
		ArrayList<String> start = gramMap.get("<start>");
		// Generate as many random phrases as specified and print them.
		for(int iter = 0; iter < count; iter++) {
			String str = start.get(rand.nextInt(start.size()));
			String phrase = generatePhrase(str);
			System.out.println(phrase);
		}
		
		// Uncomment this for testing purposes.
//		FileWriter fout = new FileWriter(new File("random_phrase_result.txt"));
//		for(int iter = 0; iter < count; iter++) {
//			String phrase = generatePhrase(str);
//			fout.append(phrase + "\n\n");
//		}
//		fout.close();
		
	}
	
	
	/**
	 * Private helper method converts a grammar file into an an array of
	 * Strings.
	 * 
	 * @param filename - the grammar file
	 * @throws IOException
	 */
	private static void grammarReader(String filename) throws IOException {
		
		// Open a buffered file reader to read in the maze.
		BufferedReader fin = new BufferedReader(new FileReader(new File(filename)));
		// Initialize the grammar array.
		grammar = new ArrayList<String>();
		// Declare a String to store new lines.
		String line;
		// Read in each line until EOF is reached.
		while((line=fin.readLine()) != null){
		    grammar.add(line);
		}
		// Close the file reader.
		fin.close();
	}
	
	
	/**
	 * Private helper method converts the grammar from an ArrayList of Strings to a HashMap
	 * whose keys are all the non-terminals and values are each key's list of production rules
	 * held in an ArrayList of Strings.
	 */
	private static void grammarToMap() {
		
		gramMap = new HashMap<String, ArrayList<String>>();
		
		boolean inDefinition = false;		// Tracks when inside of { }.
		boolean newKey = false;				// Tracks when starting a new non-terminal definition.
		String key = "";					// Holds non-terminal names used as keys for the hash map.
		ArrayList<String> values = null;	// Holds production rules for each non-terminal.
		
		for(String next : grammar) {				// For each line in the grammar file...
			
			if(next.length() == 0)					// Empty lines are skipped.
				continue;
			
			char ch = next.charAt(0);				// The 1st char of the line. Looking for { or }.
			
			if(!inDefinition && ch != '{')			// Comment area outside of non-terminal definitions
				continue;							// is ignored.
			
			if(!inDefinition && ch == '{') {		// When the beginning of a non-terminal definition
				inDefinition = true;				// is found, enable both booleans.
				newKey = true;
				continue;
			}
			if(inDefinition && ch == '}') {			// When the end of a non-terminal definition is
				gramMap.put(key, values);			// found, place the key-values pair in the map and
				inDefinition = false;				// disable the definition boolean.
				continue;
			}
			if(newKey) {							// The next line after the beginning of a new
				key = next;							// definition is the key name, so save it.
				values = new ArrayList<String>();	// Initialize a new array to hold production rules.
				newKey = false;						// Disable the new key boolean.
				continue;
			}
			values.add(next);						// Add each production rule to the list of values.
		
		}
	}
	
	
	/**
	 * Private helper method generates a random phrase using the grammar HashMap, recursive calls,
	 * and a StringBuilder.
	 * 
	 * @param str - a partial phrase that may contain a combination of terminals and non-terminals
	 * @return a partial phrase that does not contain non-terminals
	 */
	private static String generatePhrase(String str) {
		
		// Initialize a StringBuilder to concatenate the random phrase.
		StringBuilder phrase = new StringBuilder();
		// Another StringBuilder will hold non-terminals as keys for the map.
		StringBuilder key = new StringBuilder();
		// Tracks when a new key is being built.
		boolean nonTerminal = false;
		// Initialize an RNG.
		Random rand = new Random(System.nanoTime());
		
		for(int idx = 0; idx < str.length(); idx++) {
		// For each character in the current sub-phrase...
			char next = str.charAt(idx);
			
			if(!nonTerminal && next == '<') {		// '<' begins a new non-terminal.
				key.append(next);					// Append it to key.
				nonTerminal = true;					// Enable the non-terminal tracker.
				
			} else if(nonTerminal && next == '>') {	// '>' marks the end of the non-terminal.
				key.append('>');					// Append it to key.
				
				// Recurse by using key to get the non-terminal's production rules from the map...
				ArrayList<String> values = gramMap.get(key.toString());
				// picking a random production rule...
				String value = values.get(rand.nextInt(values.size()));
				// and appending the production rule to the phrase after calling this method on it.
				phrase.append(generatePhrase(value));
				
				key = new StringBuilder();			// Clear the key/non-terminal.
				nonTerminal = false;				// Disable the non-terminal tracker.
				
			} else if(nonTerminal) {				// Continue concatenating to key until a
				key.append(next);					// '>' is found.
				
			} else {								// Continue concatenating to phrase until a
			phrase.append(next);					// non-terminal is found or the end of the 
			}										// sub-phrase is reached.
		}
		return phrase.toString();					// Return the sub-phrase which has no non-terminals.
	}
}