package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * <P>
 * This class represents a general "directed graph", which could be used for any
 * purpose. The graph is viewed as a collection of vertices, which are sometimes
 * connected by weighted, directed edges.
 * </P>
 * 
 * <P>
 * This graph will never store duplicate vertices.
 * 
 * The weights will always be non-negative integers.
 * </P>
 * 
 * <P>
 * The WeightedGraph will be capable of performing three algorithms:
 * Depth-First-Search, Breadth-First-Search, and Djikatra's.
 * </P>
 * 
 * <P>
 * The Weighted Graph will maintain a collection of "GraphAlgorithmObservers",
 * which will be notified during the performance of the graph algorithms to
 * update the observers on how the algorithms are progressing.
 * </P>
 */
public class WeightedGraph<V> {

	/*
	 * We are implementing the graph as a Nested HashMap
	 */

	/*
	 * Collection of observers. Be sure to initialize this list in the constructor.
	 * The method "addObserver" will be called to populate this collection. Your
	 * graph algorithms (DFS, BFS, and Dijkstra) will notify these observers to let
	 * them know how the algorithms are progressing.
	 */
	private Collection<GraphAlgorithmObserver<V>> observerList;
	private HashMap<V, HashMap<V, Integer>> map; // This will be our graph

	/**
	 * Initialize the data structures to "empty", including the collection of
	 * GraphAlgorithmObservers (observerList).
	 */

	public WeightedGraph() {
		this.observerList = new ArrayList<>();
		this.map = new HashMap<>();
	}

	/**
	 * Add a GraphAlgorithmObserver to the collection maintained by this graph
	 * (observerList).
	 * 
	 * @param observer
	 */
	public void addObserver(GraphAlgorithmObserver<V> observer) {
		observerList.add(observer);
	}

	/**
	 * Add a vertex to the graph. If the vertex is already in the graph, throw an
	 * IllegalArgumentException.
	 * 
	 * @param vertex vertex to be added to the graph
	 * @throws IllegalArgumentException if the vertex is already in the graph
	 */
	public void addVertex(V vertex) {
		if (map.containsKey(vertex)) {
			throw new IllegalArgumentException();
		} else {
			map.put(vertex, new HashMap<V, Integer>()); // adds vertex
		}
	}

	/**
	 * Searches for a given vertex.
	 * 
	 * @param vertex the vertex we are looking for
	 * @return true if the vertex is in the graph, false otherwise.
	 */
	public boolean containsVertex(V vertex) {
		return map.containsKey(vertex);
	}

	/**
	 * <P>
	 * Add an edge from one vertex of the graph to another, with the weight
	 * specified.
	 * </P>
	 * 
	 * <P>
	 * The two vertices must already be present in the graph.
	 * </P>
	 * 
	 * <P>
	 * This method throws an IllegalArgumentExeption in three cases:
	 * </P>
	 * <P>
	 * 1. The "from" vertex is not already in the graph.
	 * </P>
	 * <P>
	 * 2. The "to" vertex is not already in the graph.
	 * </P>
	 * <P>
	 * 3. The weight is less than 0.
	 * </P>
	 * 
	 * @param from   the vertex the edge leads from
	 * @param to     the vertex the edge leads to
	 * @param weight the (non-negative) weight of this edge
	 * @throws IllegalArgumentException when either vertex is not in the graph, or
	 *                                  the weight is negative.
	 */
	public void addEdge(V from, V to, Integer weight) {
		if (weight < 0 || !this.containsVertex(from) || !this.containsVertex(to)) {
			throw new IllegalArgumentException();
		}
		// this adds the edge
		map.get(from).put(to, weight);

	}

	/**
	 * <P>
	 * Returns weight of the edge connecting one vertex to another. Returns null if
	 * the edge does not exist.
	 * </P>
	 * 
	 * <P>
	 * Throws an IllegalArgumentException if either of the vertices specified are
	 * not in the graph.
	 * </P>
	 * 
	 * @param from vertex where edge begins
	 * @param to   vertex where edge terminates
	 * @return weight of the edge, or null if there is no edge connecting these
	 *         vertices
	 * @throws IllegalArgumentException if either of the vertices specified are not
	 *                                  in the graph.
	 */
	public Integer getWeight(V from, V to) {
		if (!this.containsVertex(from) || !this.containsVertex(to)) {
			throw new IllegalArgumentException();
		}

		if (map.get(from).containsKey(to)) {
			return map.get(from).get(to); // returns the weight from retrieving from the nested HashMap
		} else {
			return null;
		}
	}

	/**
	 * <P>
	 * This method will perform a Breadth-First-Search on the graph. The search will
	 * begin at the "start" vertex and conclude once the "end" vertex has been
	 * reached.
	 * </P>
	 * 
	 * <P>
	 * Before the search begins, this method will go through the collection of
	 * Observers, calling notifyBFSHasBegun on each one.
	 * </P>
	 * 
	 * <P>
	 * Just after a particular vertex is visited, this method will go through the
	 * collection of observers calling notifyVisit on each one (passing in the
	 * vertex being visited as the argument.)
	 * </P>
	 * 
	 * <P>
	 * After the "end" vertex has been visited, this method will go through the
	 * collection of observers calling notifySearchIsOver on each one, after which
	 * the method should terminate immediately, without processing further vertices.
	 * </P>
	 * 
	 * @param start vertex where search begins
	 * @param end   the algorithm terminates just after this vertex is visited
	 */

	public void DoBFS(V start, V end) {
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyBFSHasBegun(); // let observer know BFS has begun
		}
		HashSet<V> visitedSet = new HashSet<>(); // set that represents every vertex visited
		Queue<V> queue = new LinkedList<V>(); // our queue
		queue.add(start); // add start to queue

		while (!queue.isEmpty()) {
			V vertex = queue.poll(); // removes vertex from the queue
			if (!visitedSet.contains(vertex)) {
				visitedSet.add(vertex); // we have know visited this vertex

				for (GraphAlgorithmObserver<V> observer : observerList) {
					observer.notifyVisit(vertex);
				}

				if (vertex.equals(end)) { // we have completed our search
					for (GraphAlgorithmObserver<V> observer : observerList) {
						observer.notifySearchIsOver();
					}
					return;
				}

				// visits all the adjacent keys and adds them to the queue
				for (V curr : map.get(vertex).keySet()) {
					if (!visitedSet.contains(curr)) {
						queue.add(curr);
					}
				}
			}
		}
	}

	/**
	 * <P>
	 * This method will perform a Depth-First-Search on the graph. The search will
	 * begin at the "start" vertex and conclude once the "end" vertex has been
	 * reached.
	 * </P>
	 * 
	 * <P>
	 * Before the search begins, this method will go through the collection of
	 * Observers, calling notifyDFSHasBegun on each one.
	 * </P>
	 * 
	 * <P>
	 * Just after a particular vertex is visited, this method will go through the
	 * collection of observers calling notifyVisit on each one (passing in the
	 * vertex being visited as the argument.)
	 * </P>
	 * 
	 * <P>
	 * After the "end" vertex has been visited, this method will go through the
	 * collection of observers calling notifySearchIsOver on each one, after which
	 * the method should terminate immediately, without visiting further vertices.
	 * </P>
	 * 
	 * @param start vertex where search begins
	 * @param end   the algorithm terminates just after this vertex is visited
	 */
	public void DoDFS(V start, V end) {
		HashSet<V> visitedSet = new HashSet<>(); // set that represents every vertex visited

		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDFSHasBegun();
		}

		Stack<V> stack = new Stack<V>(); // our stack
		stack.push(start); // push start into stack

		while (!stack.empty()) {
			V vertex = stack.pop(); // remove a vertex from the stack and save it
			if (!visitedSet.contains(vertex)) {
				visitedSet.add(vertex); // we have visited the vertex

				for (GraphAlgorithmObserver<V> observer : observerList) {
					observer.notifyVisit(vertex);
				}

				if (vertex.equals(end)) { // if the vertex equals end, we have completed the dfs
					for (GraphAlgorithmObserver<V> observer : observerList) {
						observer.notifySearchIsOver();
					}
					return;
				}
				// visits the adjacent keys of the vertex and pushes them onto the stack
				for (V curr : map.get(vertex).keySet()) {
					if (!visitedSet.contains(curr)) {
						stack.push(curr);
					}

				}
			}
		}

	}

	/**
	 * <P>
	 * Perform Dijkstra's algorithm, beginning at the "start" vertex.
	 * </P>
	 * 
	 * <P>
	 * The algorithm DOES NOT terminate when the "end" vertex is reached. It will
	 * continue until EVERY vertex in the graph has been added to the finished set.
	 * </P>
	 * 
	 * <P>
	 * Before the algorithm begins, this method goes through the collection of
	 * Observers, calling notifyDijkstraHasBegun on each Observer.
	 * </P>
	 * 
	 * <P>
	 * Each time a vertex is added to the "finished set", this method goes through
	 * the collection of Observers, calling notifyDijkstraVertexFinished on each one
	 * (passing the vertex that was just added to the finished set as the first
	 * argument, and the optimal "cost" of the path leading to that vertex as the
	 * second argument.)
	 * </P>
	 * 
	 * <P>
	 * After all of the vertices have been added to the finished set, the algorithm
	 * will calculate the "least cost" path of vertices leading from the starting
	 * vertex to the ending vertex. Next, it will go through the collection of
	 * observers, calling notifyDijkstraIsOver on each one, passing in as the
	 * argument the "lowest cost" sequence of vertices that leads from start to end
	 * (I.e. the first vertex in the list will be the "start" vertex, and the last
	 * vertex in the list will be the "end" vertex.)
	 * </P>
	 * 
	 * @param start vertex where algorithm will start
	 * @param end   special vertex used as the end of the path reported to observers
	 *              via the notifyDijkstraIsOver method.
	 */

	public void DoDijsktra(V start, V end) {

		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDijkstraHasBegun();
		}

		HashSet<V> finishedSet = new HashSet<>(); // vertex's that have been visited will be added here
		HashMap<V, Integer> cost = new HashMap<>(); // our cost map
		HashMap<V, V> pred = new HashMap<>(); // our predecessor map

		// makes all the values in our cost map the max integer value, representing
		// infinity
		for (V curr : map.keySet()) {
			if (!curr.equals(start)) {
				cost.put(curr, Integer.MAX_VALUE);
			}
		}
		// our start will be set to zero
		cost.put(start, 0);

		// makes all the values in our predecessor map equal to null
		for (V curr : map.keySet()) {
			if (!curr.equals(start)) {
				pred.put(curr, null);
			}
		}

		// set our start to start
		pred.put(start, start);

		// we are adding every vertex in the map, so we iterate through the whole map
		while (finishedSet.size() != map.keySet().size()) {

			Integer smallestCost = Integer.MAX_VALUE; // this will become our smallest cost
			V smallestVertex = null; // this will become our smallest vertex with the smallest cost

			// this will find out smallestVertex and smallestCost by iterating through the
			// keys of our cost map and
			// continuously reassign the values of our variables until we find the
			// smallestVertex and smallestCost
			for (V curr : cost.keySet()) {
				if (cost.get(curr) < smallestCost && !finishedSet.contains(curr)) {
					smallestVertex = curr;
					smallestCost = cost.get(curr);
				}
			}

			finishedSet.add(smallestVertex); // we have found our smallest vertex and we will add it to finished set

			for (GraphAlgorithmObserver<V> observer : observerList) {
				observer.notifyDijkstraVertexFinished(smallestVertex, smallestCost);
			}

			// we go through the adjacent keys of our smallestVertex
			for (V curr : map.get(smallestVertex).keySet()) {
				if (!finishedSet.contains(curr)) {
					// if the cost of our smallestVertex plus the weight between smallestVertex and
					// curr is less than
					// the cost of curr, we set the cost of curr to be the cost of our
					// smallestVertex plus the weight between smallestVertex and curr
					if (cost.get(smallestVertex) + this.getWeight(smallestVertex, curr) < cost.get(curr)) {
						cost.put(curr, smallestCost + this.getWeight(smallestVertex, curr));
						pred.put(curr, smallestVertex);
					}
				}
			}

		}
		// create a list that will let the observerList know that our Dijkstra method is
		// over
		List<V> list = new ArrayList<>();
		list.add(end); // end will be our first element
		V currVertex = end;

		// the loop will end once start has been placed into the list
		while (!list.contains(start)) {
			// we add the predecessor to the list
			list.add(pred.get(currVertex));
			// reassign currVertex as the predecessor to our currVertex
			currVertex = pred.get(currVertex);
		}

		// we reverse the list to get it in it's correct order
		Collections.reverse(list);

		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDijkstraIsOver(list);
		}
	}

}
