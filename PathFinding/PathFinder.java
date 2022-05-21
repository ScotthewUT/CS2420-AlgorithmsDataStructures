package assignment9;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Provides a method for finding a shortest path in a rectangular
 * maze where Xs are walls, spaces are paths, S is the start, and
 * G is the goal.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 3 November 2018
 */
public class PathFinder {
	
	private static MazeGraph maze;	// A graph used to represent the maze.
	private static int height;		// The maze's height.
	private static int width;		// The maze's width.

	/**
	 * Finds the shortest path from the start to the goal in the
	 * input maze file.
	 * The output file should contain the same maze with the shortest 
	 * path marked. See the assignment instructions for details.
	 * @param inputFile - the file path to the input maze
	 * @param outputFile - the file path to the output maze
	 */
	public static void solveMaze(String inputFile, String outputFile)
	{
		// Create a graph from the input maze file.
		maze = mazeToGraph(inputFile);
		// Use a BFS to find the shortest path to the goal.
		maze.findShortestPath();
		// Traverse back marking the path with '.'.
		maze.dotsBack();
		// Create an output maze file from the graph.
		printSolution(outputFile);
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
	
	
	/**
	 * Converts a MazeGraph to an text file version of the maze.
	 * 
	 * @param outputFile - the file path to the output maze
	 */
	private static void printSolution(String outputFile) {
		try {
			// Open a buffered file writers to print out the maze.
			BufferedWriter fout = new BufferedWriter(new FileWriter(outputFile));
			// The first line of the file is the maze's dimensions.
			fout.append(height + " " + width + "\n");
			// Print each Node of the MazeGraph to the file.
			for(int j = 0; j < height; j++) {
				for(int i = 0; i < width; i++)
					fout.append(maze.get(i, j));
				fout.append('\n');
			}
			fout.close();
			
		} catch (IOException e) {
			System.out.println("Error encounterd with maze file: " + outputFile);
			e.printStackTrace();
		}
	}
}