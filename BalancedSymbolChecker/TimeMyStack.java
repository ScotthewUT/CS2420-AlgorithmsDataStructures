package assignment7;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Experiment class for timing MyStack methods to confirm constant
 * time complexity.
 * 
 * @author Scott Crowley (u1178178)
 * @version 17 October 2018
 */
public class TimeMyStack {

	private static final long RUNS = 16_000_000;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Initial size of the stack.
		int n = 200;
		// Three clocks will be used to track run time.
		long clock1, clock2, clock3 = System.nanoTime();
		// Initialize a the stack.
		MyStack<Character> stack = new MyStack<Character>();

		// Spin up for 4 seconds.
		while(System.nanoTime() - clock3 < 4_000_000_000L) {
			int cont = 0;
			continue;
		}

		// Repeat timing experiment for N = 200 to 15,000.
		while(n < 15001) {
			
			//Generate a test stack of size N.
			for(int i = 0; i < n; i++)
				stack.push((char)(65 + (i % 26)));
			
			/* MyStack Timing: push, pop, & peak */
			clock1 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				char x = '!';			// Note that instead of timing push, pop, and peak individually, I've chosen
				stack.push(x);			// to test how long it takes to call them in succession. As long as each
				stack.pop();			// method is O(C) then the whole will also be O(C). If any one method has
				stack.peek();			// a Big-O greater than O(C), it will bare out and more testing will follow.
			}
			clock2 = System.nanoTime();
			for(int iter = 0; iter < RUNS; iter++) {
				char x = '!';
			}
			clock3 = System.nanoTime();
			
			// Subtract the time necessary to loop and make another copy from the
			// time to call the addFirst() method. Average over the number of runs.
			long stackMeanTime = ((clock2 - clock1) - (clock3 - clock2))/RUNS;
			
			// Save the data.
			System.out.println(stackMeanTime + " ns to push, pop, & peak onto a stack of size N = " + n);
			try(FileWriter fout = new FileWriter(new File("time_MyStack.tsv"), true)) {
				fout.append(n + "\t" + stackMeanTime + "\n");
			}
			catch (IOException e) {
				System.out.println("ERROR: time_MyStack.tsv not found!");
				e.printStackTrace();
			}
			
			// Increment N.
			n += 400;
		}
	}
}
