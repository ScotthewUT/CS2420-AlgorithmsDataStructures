package assignment9;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;

/**
 * 
 * @author Daniel Kopta
 * This program is a helper utility to generate random mazes.
 * See the assignment instructions for more details.
 *
 */
public class MazeGen {

	public static void main(String[] args) 
	{
		// Example usage:
		// Creates a 2048x2048 maze with about 25% walls
		randomMaze("massiveMaze.txt", 2048, 0.25);
		
	}

	/**
	 * 
	 * @param filename - name of file to output random maze to
	 * @param dimensions - size of the square maze (NxN)
	 * @param density - density of walls in the maze (0 - 1.0)
	 * 					1.0 will be all walls, 0.5 will be 50% walls
	 * 
	 * NOTE: An extremely dense maze will not display properly in the
	 * pacman tool. It is recommended that density be < 0.5
	 * 
	 */
	public static void randomMaze(String filename, int dimensions, double density)
	{
		int numWalls = 0;
		Random rand = new Random(System.currentTimeMillis());
		int startX = rand.nextInt(dimensions - 2) + 1;
		int startY = rand.nextInt(dimensions - 2) + 1;
		int goalX, goalY;
		do
		{
			goalX = rand.nextInt(dimensions - 2) + 1;
			goalY = rand.nextInt(dimensions - 2) + 1;
		}
		while(startX == goalX && startY == goalY);
		try
		{
			PrintWriter output = new PrintWriter(new FileWriter(filename));
			output.println(dimensions + " " + dimensions);
			for(int i=0; i < dimensions; i++)
				output.print("X");
			output.println();
			for(int i=1; i < dimensions - 1; i++)
			{
				output.print("X");
				for(int j=1; j < dimensions - 1; j++)
				{
					if(i==startX && j==startY)
					{
						output.print("S");
						continue;
					}
					if(i==goalX && j==goalY)
					{
						output.print("G");
						continue;
					}
					if(rand.nextDouble() < density)
					{
						numWalls++;
						output.print("X");
					}
					else
						output.print(" ");
				}
				output.println("X");
			}
			for(int i=0; i < dimensions; i++)
				output.print("X");
			output.println();
			output.close();
		}
		catch(Exception e){e.printStackTrace();}
		System.out.println("created " + dimensions + "x" + dimensions + 
				" maze with " + numWalls + " walls");
	}
	
}
