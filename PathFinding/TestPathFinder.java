package assignment9;

/**
 * Demonstration of PathFinder's maze solving method.
 * 
 * @author [CS 2420 Staff], Scott Crowley (u1178178), & Kressa Fox (u0620628)
 * @version 6 November 2018
 */
public class TestPathFinder {
	
	public static void main(String[] args) {
		
		/*
		 * The below code assumes you have the following file in
		 * your project folder:
		 *     tinyMaze.txt
		 *     turn.txt
		 *     unsolvable.txt
		 *     mediumMaze.txt
		 *     randMaze1.txt
		 *     randMaze2.txt
		 *     randMaze3.txt
		 *     multipathMaze.txt
		 *     switchbacksMaze.txt
		 *     
		 * If PathFinder.solveMaze is implemented, it will create 
		 * the file "___________Out.txt" in your project folder.
		 * You will have to browse to that folder to view the 
		 * output, it will not automatically show up in Eclipse.
		 */
		PathFinder.solveMaze("tinyMaze.txt", "tinyMazeOut.txt");
		PathFinder.solveMaze("turn.txt", "turnOut.txt");
		PathFinder.solveMaze("unsolvable.txt", "unsolvableOut.txt");
		PathFinder.solveMaze("mediumMaze.txt", "mediumMazeOut.txt");
		PathFinder.solveMaze("randMaze1.txt", "randMaze1Out.txt");
		PathFinder.solveMaze("randMaze2.txt", "randMaze2Out.txt");
		PathFinder.solveMaze("randMaze3.txt", "randMaze3Out.txt");
		PathFinder.solveMaze("multipathMaze.txt", "multipathMazeOut.txt");
		PathFinder.solveMaze("switchbacksMaze.txt", "switchbacksMazeOut.txt");
		PathFinder.solveMaze("massiveMaze.txt", "massiveMazeOut.txt");
	}
}
