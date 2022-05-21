package comprehensive;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Experiment class gathers run-time performance for RandomPhraseGenerator
 * where N is the branching factor of a tree-like grammar.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 5 December 2018
 */
public class TimeRPGWidthN {
	
	private static final String FILEPATH = "C:/Users/Scotthew/grammars/";
	private static final int HEIGHT = 4;
	private static final int RUNS = 100;
	
	public static void main(String[] args) throws IOException {
		// Three clocks will be used to track run time.
		long clock1, clock2, clock3 = System.nanoTime();
		// Spin up for 5 seconds.
		while(System.nanoTime() - clock3 < 5_000_000_000L)
			continue;

		// Repeat timing experiment for 20 grammar files.
		for(int width = 1; width <= 20; width++) {
			
			// Build a new file name.
			String filename = FILEPATH + "treegram" + width +".g";
			
			// Generate a new grammar file.
			GenerateGrammarTree.main(new String[] {filename, Integer.toString(HEIGHT), Integer.toString(width)});
			
			// The two arguments to pass to RandomPhraseGenerator.main(String args[]).
			// Be sure to change the file path as necessary.
			String[] arguments = new String[] {filename, "1"};
			
			// Suggest a garbage collection.
			System.gc();
			
			
			/* BEGIN TIMING */
			clock1 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				RandomPhraseGenerator.main(arguments);
			}
			clock2 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				continue;
			}
			clock3 = System.nanoTime();
			long rpgTime = clock2 - clock1;
			long loopTime = clock3 - clock2;
			rpgTime = (rpgTime - loopTime) / RUNS;
			
			
			// Save the data.
			try(FileWriter fout = new FileWriter(new File("time_RPGforNwidth.tsv"), true)) {
				fout.append(width + "\t" + rpgTime + "\n");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			
			// Delete the grammar file.
			File grammar = new File(filename);
			grammar.delete();
		}
	}
}