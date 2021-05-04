package project6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Graph implements GraphInterface<Town, Road> {

	private HashMap<String, Town> towns;
	private HashSet<Road> roads;
	private HashMap<Town, ArrayList<String>> listOfPaths;
	private HashMap<Town, Integer> dist;
	private final int INFINITY = -1;

	public Graph() {
		towns = new HashMap<String, Town>();
		roads = new HashSet<Road>();
	}

	@Override
	public Road getEdge(Town sourceVertex, Town destinationVertex) {
		if (sourceVertex == null || destinationVertex == null)
			return null;

		if (!containsVertex(sourceVertex) || !containsVertex(destinationVertex))
			return null;
		for (Road r : edgesOf(sourceVertex)) {
			if (r.getDestination().equals(destinationVertex))
				return r;
		} 

		return null;
	}
	
	public Town getVertex(Town vertex) {
		return towns.get(vertex.getName());
	}

	@Override
	public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
		Road newRoad;

		if (sourceVertex == null || destinationVertex == null)
			throw new NullPointerException();

		if (!containsVertex(sourceVertex) || !containsVertex(destinationVertex))
			throw new IllegalArgumentException();

		newRoad = new Road(sourceVertex, destinationVertex, weight, description);

		towns.get(sourceVertex.getName()).addAdjacentTown(destinationVertex);
		towns.get(destinationVertex.getName()).addAdjacentTown(sourceVertex);

		roads.add(newRoad);

		return newRoad;
	}

	@Override
	public boolean addVertex(Town v) {
		if (v == null)
			throw new NullPointerException();

		if (containsVertex(v))
			return false;

		towns.put(v.getName(), new Town(v));

		return true;
	}

	@Override
	public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
		if (!containsVertex(sourceVertex) || !containsVertex(destinationVertex))
			return false;

		return towns.get(sourceVertex.getName()).isAdjacentTo(destinationVertex);
	}

	@Override
	public boolean containsVertex(Town v) {
		if(v == null)
			return false;
		
		return towns.containsKey(v.getName());
	}

	@Override
	public Set<Road> edgeSet() {
		return roads;
	}

	@Override
	public Set<Road> edgesOf(Town vertex) {
		Set<Road> adjacencies;
		if (vertex == null)
			throw new NullPointerException();

		if (!containsVertex(vertex))
			throw new IllegalArgumentException();

		adjacencies = new HashSet<Road>();

		for (Road road : roads) {
			if (road.getSource().equals(vertex))
				adjacencies.add(road);
			else if (road.getDestination().equals(vertex))
				adjacencies.add(new Road(vertex, road.getSource(), road.getWeight(), road.getName()));
		}

		return adjacencies;
	}

	@Override
	public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
		boolean noRoadsBetween = true;
		
		if(sourceVertex == null || destinationVertex == null)
			return null;
		
		Road removedRoad = new Road(sourceVertex, destinationVertex, weight, description);

		if (roads.remove(removedRoad)) {

			for (Road road : roads) {
				if (road.contains(sourceVertex) && road.contains(destinationVertex))
					noRoadsBetween = false;
			}

			if (noRoadsBetween) {
				towns.get(sourceVertex.getName()).removeAdjacentTown(destinationVertex);
				towns.get(destinationVertex.getName()).removeAdjacentTown(sourceVertex);
			}
		}

		return null;
	}

	@Override
	public boolean removeVertex(Town v) {
		Road currentRoad;
		if (!containsVertex(v))
			return false;

		Iterator<Road> iter = roads.iterator();
		while (iter.hasNext()) {
			currentRoad = iter.next();

			if (currentRoad.contains(v))
				iter.remove();
		}

		towns.remove(v.getName());

		return true;
	}

	@Override
	public Set<Town> vertexSet() {
		Set<Town> vertexSet = new HashSet<Town>();

		towns.forEach((k, v) -> vertexSet.add(v));

		return vertexSet;
	}

	@Override
	public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
		
		dijkstraShortestPath(sourceVertex);

		if (dist.get(destinationVertex) == INFINITY) {
			return new ArrayList<String>();
		}

		return listOfPaths.get(destinationVertex);
	}

	@Override
	public void dijkstraShortestPath(Town sourceVertex) {
		HashMap<Town, Boolean> visitedTowns = new HashMap<Town, Boolean>();// store whether this town has already been
																			// visited
		dist = new HashMap<Town, Integer>();// store the minimum found distance to each town from the source
		listOfPaths = new HashMap<Town, ArrayList<String>>();// store the list Towns in each shortestPath
		PriorityQueue<Vector2D> queue = new PriorityQueue<Vector2D>();// used to determine which node to visit next
		Vector2D currentTown;
		int newDist;
		
		for (Map.Entry<String, Town> entry : towns.entrySet()) {
			visitedTowns.put(entry.getValue(), false);
			dist.put(entry.getValue(), INFINITY);
		}

		dist.put(sourceVertex, 0);
		queue.add(new Vector2D(sourceVertex, 0));

		while (!queue.isEmpty()) {
			currentTown = queue.poll();
			visitedTowns.replace(currentTown.town, true);// show that we have visited this town

			if (dist.get(currentTown.town) != INFINITY && dist.get(currentTown.town) < currentTown.distance)
				continue;

			for (Road road : edgesOf(currentTown.town)) {

				if (visitedTowns.get(road.getDestination()) == true)
					continue;

				newDist = dist.get(currentTown.town) + road.getWeight();

				if (newDist < dist.get(road.getDestination()) || dist.get(road.getDestination()) == INFINITY) {
					dist.replace(road.getDestination(), newDist);
					queue.add(new Vector2D(road.getDestination(), newDist));
					if (listOfPaths.get(currentTown.town) == null) {
						listOfPaths.put(road.getDestination(), new ArrayList<String>());
					} else {
						listOfPaths.put(road.getDestination(),
								new ArrayList<String>(listOfPaths.get(currentTown.town)));
					}
					listOfPaths.get(road.getDestination()).add(road.toString());
				}

			}
		}
	}

	private class Vector2D implements Comparable<Vector2D> {
		Town town;
		int distance;

		public Vector2D(Town town, int distance) {
			this.town = town;
			this.distance = distance;
		}
		
		@Override
		public int compareTo(Vector2D otherVector) {
			return this.distance - otherVector.distance;
		}

	}
}