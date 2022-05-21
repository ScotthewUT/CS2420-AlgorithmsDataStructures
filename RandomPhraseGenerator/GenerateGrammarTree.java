package comprehensive;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Creates a test grammar with a tree-like structure with specified
 * height and branching factor.
 * 
 * @author Scott Crowley (u1178178) & Kressa Fox (u0620628)
 * @version 5 December 2018
 */
public class GenerateGrammarTree {
	
	private static int height;
	private static int branch;
	private static int size;
	private static String[] tree;

	public static void main(String[] args) throws IOException {
	
		String filename = args[0];
		
		height = Integer.parseInt(args[1]);
		if(0 > height || height > 20)
			throw new IllegalArgumentException("Valid range for height is 0-20.");
		
		branch = Integer.parseInt(args[2]);
		if(1 > branch || branch > 20)
			throw new IllegalArgumentException("Valid range for branching factor is 1-20.");
		
		size = findSize();
		
		tree = new String[size];
		
		tree[0] = "start";
		
		for(int idx = 1; idx < size; idx++)
			tree[idx] = getName(idx);
		
		FileWriter fout = new FileWriter(new File(filename), true);
		StringBuilder prodRule;
		
		for(int idx = 0; idx < size; idx++) {
			prodRule = new StringBuilder();
			if(isLeaf(idx))
				break;
			prodRule.append("{\n<" + tree[idx] + ">\n");
			for(int child = 1; child <= branch; child++) {
				if(isLeaf(idx * branch + child)) {
					prodRule.append(tree[idx * branch + child] + " ");
					continue;
				}
				prodRule.append("<" + tree[idx * branch + child] + "> ");
			}
			prodRule.append("\n}\n\n");
			fout.write(prodRule.toString());
		}
		fout.close();
	}
	
	
	/**
	 * Determines the necessary capacity of the tree by calculating the sum of
	 * the branching factor raised to the power of the depth of each level.
	 * For example a tree with height = 4 and branching factor = 3 gives:
	 * size = 3^0 + 3^1 + 3^2 + 3^3 + 3^4 = 121
	 * 
	 * @return size of the grammar tree
	 */
	private static int findSize() {
		int sum = 1;
		for(int depth = 1; depth <= height; depth++)
			sum += Math.pow(branch, depth);
		return sum;
	}
	
	
	/**
	 * Creates a name for the node based on its location in the tree.
	 * 
	 * @param index node's index in the backing array
	 * @return a String name
	 */
	private static String getName(int index) {
		String[] cap = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "J", "K",
									 "M", "N", "P", "R", "T", "U", "W", "X", "Y", "Z"};
		
		String[] num = new String[] {"01", "02", "03", "04", "05", "06", "07", "08",
									 "09", "10", "11", "12", "13", "14", "15", "16",
									 "17", "18", "19", "20"};
		
		String parent = tree[(index - 1) / branch];
		StringBuilder name = new StringBuilder();
		
		if(parent == "start")
			return cap[index - 1];
		
		name.append(parent);
		char last = name.charAt(name.length() - 1);
		
		if(last > 64) {
			name.append(num[(index - 1) % branch]);
			return name.toString();
		}
		name.append(cap[(index - 1) % branch]);
		return name.toString();
	}
	
	
	/**
	 * Checks if a node is a leaf.
	 * 
	 * @param index - the index of the node in the backing array
	 * @return true if the node is a leaf; false otherwise
	 */
	private static boolean isLeaf(int index) {
		return(index * branch + 1 >= tree.length);
	}
}
