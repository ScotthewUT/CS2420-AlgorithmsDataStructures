package assignment9;

import java.util.LinkedList;

/**
 * Represents a maze as a graph by creating a 2D array of Nodes.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 6 November 2018
 */
public class MazeGraph {
	
	private Node start;			// The starting point of the maze.
	private Node goal;			// The maze's goal.
	private Node[][] maze;		// A 2D array of Nodes that represents the maze.

	private class Node {
		
		public char data;		// Holds the character found at that Node.
		int x, y;				// The location of the Node.
		public Node visitedFrom;// During BFS, which Node preceded this one?
		boolean visited;		// Has the Node been visited during BFS?
		
		public Node(char element, int _x, int _y) {
			data = element;
			x = _x;
			y = _y;
			visitedFrom = null;
			visited = false;
		}
	}
	
	// Default constructor. Requires the mazes height & width.
	public MazeGraph(int height, int width) {
		start = null;
		goal = null;
		maze = new Node[height][width];
	}
	
	
	/**
	 * Adds a new Node to the MazeGraph.
	 * NOTE: X, S, G, & space are the only acceptable chars.
	 * 
	 * @param ch - char contained at that point of the maze
	 * @param x - horizontal position of the char
	 * @param y - vertical position of the char
	 */
	public void add(char ch, int x, int y) {
		Node node = new Node(ch, x, y);
		maze[y][x] = node;
		if(ch == 'S')
			start = node;	// When an 'S' is found, assign the start Node.
	}
	
	
	/**
	 * Returns the char found at the specified location in the MazeGraph.
	 * 
	 * @param x - horizontal position of the char
	 * @param y - vertical position of the char
	 * @return the X, S, G, or space found at the argument's location
	 */
	public char get(int x, int y) {
		return maze[y][x].data;
	}
	
	
	/**
	 * Implements a breadth-first search of the MazeGraph to find one of the
	 * shortest routes from 'S' to 'G' as long as one exists.
	 */
	public void findShortestPath() {
		// Create a queue to store Nodes.
		LinkedList<Node> queue = new LinkedList<Node>();
		// Begin at the starting node and mark it visited.
		queue.offer(start);
		start.visited = true;
		// As long as the queue is not empty and the goal hasn't been reached
		// continue with the BFS.
		while(!queue.isEmpty()) {
			// Take the first Node off the queue and store it in a temp Node
			// as well as its neighbors.
			Node temp = queue.poll();
			Node N = maze[temp.y - 1][temp.x];	// The only valid neighbors of
			Node W = maze[temp.y][temp.x - 1];	// any Node in the MazeGraph
			Node S = maze[temp.y + 1][temp.x];	// are the four that border it.
			Node E = maze[temp.y][temp.x + 1];
			if(N.data == 'G') {
				N.visitedFrom = temp;			// If any of temp's neighbors
				goal = N;						// contain 'G', then assign
				break;							// assign goal and mark it as
			}									// visited from temp.
			if(W.data == 'G') {
				W.visitedFrom = temp;
				goal = W;
				break;
			}
			if(S.data == 'G') {
				S.visitedFrom = temp;
				goal = S;
				break;
			}
			if(E.data == 'G') {
				E.visitedFrom = temp;
				goal = E;
				break;
			}
			if(!N.visited && N.data == ' ') {
				queue.offer(N);					// Any of temp's neighbors that
				N.visited = true;				// that have not yet been visited
				N.visitedFrom = temp;			// are marked so and placed on
			}									// the queue.
			if(!W.visited && W.data == ' ') {
				queue.offer(W);
				W.visited = true;
				W.visitedFrom = temp;
			}
			if(!S.visited && S.data == ' ') {
				queue.offer(S);
				S.visited = true;
				S.visitedFrom = temp;
			}
			if(!E.visited && E.data == ' ') {
				queue.offer(E);
				E.visited = true;
				E.visitedFrom = temp;
			}
		}	// If the queue empties before 'G' is found, then there is no valid path.
												
	}
	
	
	/**
	 * Once the BFS has completed, if a path between start and goal was found, then
	 * this method will traverse the Nodes in reverse switching the spaces to periods.
	 */
	public void dotsBack() {
		if(goal == null)
			return;
		Node temp = goal.visitedFrom;
		while(temp.data != 'S') {
			temp.data = '.';
			temp = temp.visitedFrom;
		}
	}
}
