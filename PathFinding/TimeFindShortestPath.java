package assignment9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Calculates the time to run MazeGraph.findShortestPath().
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 7 November 2018
 */
public class TimeFindShortestPath {

	private static final long RUNS = 5_000_000;	// Number of runs to average over.
	private static int height;					// The maze's height.
	private static int width;					// The maze's width.
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Three clocks will be used to track run time.
		long clock1, clock2, clock3 = System.nanoTime();
		// Enter the name of the input maze here.
		String inputFile = "massiveMaze.txt";
		// Create a graph from the input maze file.
		MazeGraph maze = mazeToGraph(inputFile);

		// Spin up for 4 seconds.
		while(System.nanoTime() - clock3 < 4_000_000_000L) {
			int cont = 0;
			continue;
		}

		// Record the time taken to run the .findShortestPath() method many times.
		clock1 = System.nanoTime();
		for(int iter = 0; iter < RUNS; iter++) {
			maze.findShortestPath();
		}
		clock2 = System.nanoTime();
		for(int iter = 0; iter < RUNS; iter++) {
			continue;
		}
		clock3 = System.nanoTime();

		// Subtract the time necessary to loop from the time to call
		// the .solveMaze() method. Average over the number of runs.
		long meanTime = ((clock2 - clock1) - (clock3 - clock2))/RUNS;

		// Print the results to console.
		System.out.println("Time to solve " + inputFile + ":  " + meanTime + " ns.");
	}
	
	
	/**
	 * Converts the input maze file to a graph of Node[][].
	 * 
	 * @param inputFile - the file path to the input maze
	 * @return graph representation of the maze backed by a 2D array of Nodes.
	 */
	private static MazeGraph mazeToGraph(String inputFile) {
		MazeGraph result = null;
		try {
			// Open a buffered file reader to read in the maze.
			BufferedReader fin = new BufferedReader(new FileReader(inputFile));
			// Get the maze's dimensions.
			String[] size = fin.readLine().split(" ");
			height = Integer.parseInt(size[0]);
			width  = Integer.parseInt(size[1]);
			result = new MazeGraph(height, width);
			// Read in one character at a time until EOF is reached.
			for(int j = 0; j < height; j++) {
				for(int i = 0; i < width; i++) {
					char next = (char) fin.read();
					// Ignore the new-line and carriage return "characters."
					if(next == '\n' || next == '\r') {
						i--;
						continue;
					}
					result.add(next, i, j);
				}
			}
			fin.close();
		} catch(IOException e) {
				System.out.println("Error encounterd with maze file: " + inputFile);
				e.printStackTrace();
		}
		return result;
	}
}