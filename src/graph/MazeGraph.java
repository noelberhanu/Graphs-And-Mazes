package graph;

import graph.WeightedGraph;
import maze.Juncture;
import maze.Maze;

/**
 * <P>
 * The MazeGraph is an extension of WeightedGraph. The constructor converts a
 * Maze into a graph.
 * </P>
 */
public class MazeGraph extends WeightedGraph<Juncture> {

	/*
	 * STUDENTS: SEE THE PROJECT DESCRIPTION FOR A MUCH MORE DETAILED EXPLANATION
	 * ABOUT HOW TO WRITE THIS CONSTRUCTOR
	 */

	/**
	 * <P>
	 * Construct the MazeGraph using the "maze" contained in the parameter to
	 * specify the vertices (Junctures) and weighted edges.
	 * </P>
	 * 
	 * <P>
	 * The Maze is a rectangular grid of "junctures", each defined by its X and Y
	 * coordinates, using the usual convention of (0, 0) being the upper left
	 * corner.
	 * </P>
	 * 
	 * <P>
	 * Each juncture in the maze should be added as a vertex to this graph.
	 * </P>
	 * 
	 * <P>
	 * For every pair of adjacent junctures (A and B) which are not blocked by a
	 * wall, two edges should be added: One from A to B, and another from B to A.
	 * The weight to be used for these edges is provided by the Maze. (The Maze
	 * methods getMazeWidth and getMazeHeight can be used to determine the number of
	 * Junctures in the maze. The Maze methods called "isWallAbove",
	 * "isWallToRight", etc. can be used to detect whether or not there is a wall
	 * between any two adjacent junctures. The Maze methods called "getWeightAbove",
	 * "getWeightToRight", etc. should be used to obtain the weights.)
	 * </P>
	 * 
	 * @param maze to be used as the source of information for adding vertices and
	 *             edges to this MazeGraph.
	 */
	public MazeGraph(Maze maze) {
		// nested for loop to add a vertex to every single juncture in our graph
		for (int xAxis = 0; xAxis < maze.getMazeWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < maze.getMazeHeight(); yAxis++) {
				this.addVertex(new Juncture(xAxis, yAxis));
			}
		}

		// nested for loop to add edges to our graph if there isn't a wall above, below
		// ,to the left or right of the
		// current vertex
		for (int xAxis = 0; xAxis < maze.getMazeWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < maze.getMazeHeight(); yAxis++) {

				Juncture right = new Juncture(xAxis + 1, yAxis); // the vertex right to our currVertex
				Juncture down = new Juncture(xAxis, yAxis + 1); // the vertex below our currVertex
				Juncture left = new Juncture(xAxis - 1, yAxis); // the vertex left to our currVertex
				Juncture up = new Juncture(xAxis, yAxis - 1); // the vertex above to our currVertex
				Juncture curr = new Juncture(xAxis, yAxis); // our currVertex

				// if there isn't a wall between our currVertex and the vertex we are comparing
				// it too, then we add the
				// edge
				if (maze.isWallToRight(curr) == false) {
					this.addEdge(curr, right, maze.getWeightToRight(curr));
				}

				if (maze.isWallAbove(curr) == false) {
					this.addEdge(curr, up, maze.getWeightAbove(curr));
				}

				if (maze.isWallToLeft(curr) == false) {
					this.addEdge(curr, left, maze.getWeightToLeft(curr));
				}

				if (maze.isWallBelow(curr) == false) {
					this.addEdge(curr, down, maze.getWeightBelow(curr));
				}
			}
		}
	}
}
