package comprehensive;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

/**
 * Experiment class gathers run-time performance for RandomPhraseGenerator
 * where N is the number of phrases to generate.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 30 November 2018
 */
public class TimeRPGPhrasesN {
	
	private static final int RUNS = 10000;
	
	public static void main(String[] args) throws IOException {
		// Set the size range of the test (N).
		int N = 10, N_max = 100;
		// Three clocks will be used to track run time.
		long clock1, clock2, clock3 = System.nanoTime();
		// Initialize a LocalTime and String for timestamps.
		LocalTime time = LocalTime.now();
		String timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
		// Spin up for 5 seconds.
		while(System.nanoTime() - clock3 < 5_000_000_000L)
			continue;

		// Repeat timing experiment from initial N to N_max;
		while(N <= N_max) {
			
			// Print size of N and a timestamp.
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + "N = " + N);
			
			// The two arguments to pass to RandomPhraseGenerator.main(String args[]).
			// Be sure to change the file path and name as necessary.
			String[] arguments = new String[] {"C:/Users/Scotthew/grammars/testgrammar.g", Integer.toString(N)};
			
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
			try(FileWriter fout = new FileWriter(new File("time_RPGforNphrases.tsv"), true)) {
				fout.append(N + "\t" + rpgTime + "\n");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			// Print the data.
			time = LocalTime.now();
			timestamp = "[" + time.getHour() + ":" + time.getMinute() + "]  ";
			System.out.println(timestamp + rpgTime);
			
			// Increment N.
			N += 10;
		}
	}
}