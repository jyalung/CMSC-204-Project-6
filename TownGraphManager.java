package project6;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class TownGraphManager implements TownGraphManagerInterface {
	private Graph network;

	public TownGraphManager() {
		network = new Graph();
	}

	@Override
	public boolean addRoad(String town1, String town2, int weight, String roadName) {

		if (network.addEdge(new Town(town1), new Town(town2), weight, roadName) == null)
			return false;

		return true;

	}

	@Override
	public String getRoad(String town1, String town2) {
		return network.getEdge(new Town(town1), new Town(town2)).getName();
	}

	@Override
	public boolean addTown(String v) {
		return network.addVertex(new Town(v));
	}

	@Override
	public Town getTown(String name) {
		return network.getVertex(new Town(name));
	}

	@Override
	public boolean containsTown(String v) {
		return network.containsVertex(new Town(v));
	}

	@Override
	public boolean containsRoadConnection(String town1, String town2) {
		return network.containsEdge(new Town(town1), new Town(town2));
	}

	@Override
	public ArrayList<String> allRoads() {
		ArrayList<String> roads = new ArrayList<String>();

		for (Road road : network.edgeSet()) {
			roads.add(road.getName());
		}
		if (roads.size() <= 0)
			return roads;

		Collections.sort(roads);

		return roads;
	}

	@Override
	public boolean deleteRoadConnection(String town1, String town2, String road) {
		if (network.removeEdge(new Town(town1), new Town(town2), 0, road) == null)
			return false;

		return true;
	}

	@Override
	public boolean deleteTown(String v) {
		return network.removeVertex(new Town(v));
	}

	@Override
	public ArrayList<String> allTowns() {
		ArrayList<String> towns = new ArrayList<String>();

		for (Town town : network.vertexSet()) {
			towns.add(town.getName());
		}
		if (towns.size() <= 0)
			return towns;

		Collections.sort(towns);

		return towns;
	}

	@Override
	public ArrayList<String> getPath(String town1, String town2) {
		return network.shortestPath(new Town(town1), new Town(town2));
	}

	public void populateTownGraph(File selectedFile) throws FileNotFoundException, IOException {
		String[] tokens;
		String currentLine;

		Scanner fileReader = new Scanner(selectedFile);

		while (fileReader.hasNextLine()) {
			currentLine = fileReader.nextLine();
			tokens = currentLine.split(";|,");
			network.addVertex(new Town(tokens[2]));
			network.addVertex(new Town(tokens[3]));
			network.addEdge(new Town(tokens[2]), new Town(tokens[3]), Integer.parseInt(tokens[1]), tokens[0]);
		} 
		fileReader.close();
	}

}