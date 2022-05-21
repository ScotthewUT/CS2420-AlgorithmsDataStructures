package comprehensive;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Experiment class gathers run-time performance for RandomPhraseGenerator
 * where N is the number of non-terminal definitions.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 30 November 2018
 */
public class TimeRPGDepthN {
	
	private static final int RUNS = 100_000;
	
	public static void main(String[] args) throws IOException {
		// Three clocks will be used to track run time.
		long clock1, clock2, clock3 = System.nanoTime();
		// Spin up for 5 seconds.
		while(System.nanoTime() - clock3 < 5_000_000_000L)
			continue;

		// Repeat timing experiment for 10 grammar files.
		for(int cnt = 0; cnt < 10; cnt++) {
			
			// Array of filenames for test grammars.
			String[] filenames = new String[] {"1hundred.g", "2hundred.g", "3hundred.g",
											   "4hundred.g", "5hundred.g", "6hundred.g",
											   "7hundred.g", "8hundred.g", "9hundred.g",
											   "thousand.g"};
			// File path to the grammars.
			String filepath = "C:/Users/Scotthew/grammars/";
			filepath += filenames[cnt];
			
			// The two arguments to pass to RandomPhraseGenerator.main(String args[]).
			// Be sure to change the file path and name as necessary.
			String[] arguments = new String[] {filepath, "1"};
			
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
			try(FileWriter fout = new FileWriter(new File("time_RPGforNdepth.tsv"), true)) {
				fout.append((cnt + 1) * 100 + "\t" + rpgTime + "\n");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}