package assignment9;

/**
 * Calculates the time to run PathFinder.solveMaze().
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 6 November 2018
 */
public class TimePathFinder {

	private static final int RUNS = 250_000;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Three clocks will be used to track run time.
		long clock1, clock2, clock3 = System.nanoTime();
		
		String inputFile = "switchbacksMaze.txt";
		String outputFile = "switchbacksMazeOut.txt";
		
		// Spin up for 4 seconds.
		while(System.nanoTime() - clock3 < 4_000_000_000L) {
			int cont = 0;
			continue;
		}
		
		// Record the time taken to run the .solveMaze method many times.
		clock1 = System.nanoTime();
		for(int iter = 0; iter < RUNS; iter++) {
			PathFinder.solveMaze(inputFile, outputFile);
		}
		clock2 = System.nanoTime();
		for(int iter = 0; iter < RUNS; iter++) {
			continue;
		}
		clock3 = System.nanoTime();
		
		// Subtract the time necessary to loop from the time to call
		// the .solveMaze() method. Average over the number of runs.
		long meanTime = ((clock2 - clock1) - (clock3 - clock2))/RUNS;
		
		// Convert ns to ms.
		Double meanTimeMilli = meanTime / 1_000_000.0;
		
		// Print the results to console.
		System.out.println("Time to solve " + inputFile + ":  " + meanTimeMilli + " ms.");
	}
}
