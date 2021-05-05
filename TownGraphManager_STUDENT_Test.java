package project6;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TownGraphManager_STUDENT_Test {
	private TownGraphManagerInterface graph;
	private String[] town;
	
	@Before
	public void setUp() throws Exception {
		graph = new TownGraphManager();
		town = new String[12];

		for (int i = 0; i < 5; i++) {
			town[i] = "Town_" + i;
			graph.addTown(town[i]);
		}

		graph.addRoad(town[0], town[1], 6, "Road_1");
		graph.addRoad(town[0], town[2], 1, "Road_2");
		graph.addRoad(town[1], town[2], 3, "Road_3");
		graph.addRoad(town[1], town[3], 2, "Road_4");
		graph.addRoad(town[2], town[3], 5, "Road_5");
		graph.addRoad(town[3], town[4], 7, "Road_6");
	}
	
	@After
	public void tearDown() throws Exception {
		graph = null;
	}
	
	@Test
	public void testAddRoad() {
		ArrayList<String> roads = graph.allRoads();
		assertEquals("Road_1", roads.get(0));
		assertEquals("Road_2", roads.get(1));
		assertEquals("Road_3", roads.get(2));
		assertEquals("Road_4", roads.get(3));
		graph.addRoad(town[0], town[4], 2,"Road_7");
		roads = graph.allRoads();
		assertEquals("Road_1", roads.get(0));
		assertEquals("Road_2", roads.get(1));
		assertEquals("Road_3", roads.get(2));
		assertEquals("Road_4", roads.get(3));
		assertEquals("Road_5", roads.get(4));
	}
	
	@Test
	public void testGetRoad() {
		assertEquals("Road_4", graph.getRoad(town[1], town[3]));
	}
	
	@Test
	public void testAddTown() {
		assertEquals(false, graph.containsTown("Town_60"));
		graph.addTown("Town_60");
		assertEquals(true, graph.containsTown("Town_60"));
	}
	
	@Test
	public void testDisjointGraph() {
		assertEquals(false, graph.containsTown("Town_60"));
		graph.addTown("Town_60");
		ArrayList<String> path = graph.getPath(town[0],"Town_60");
		assertFalse(path.size() > 0);
	}
	
	@Test
	public void testContainsTown() {
		assertEquals(true, graph.containsTown("Town_4"));
		assertEquals(false, graph.containsTown("Town_15"));
	}
	
	@Test
	public void testContainsRoadConnection() {
		assertEquals(true, graph.containsRoadConnection(town[3], town[1]));
		assertEquals(false, graph.containsRoadConnection(town[0], town[4]));
	}
	
	@Test
	public void testAllRoads() {
		ArrayList<String> roads = graph.allRoads();
		assertEquals("Road_1", roads.get(0));
		assertEquals("Road_2", roads.get(1));
		assertEquals("Road_3", roads.get(2));
		assertEquals("Road_4", roads.get(3));
		assertEquals("Road_5", roads.get(4));
	}
	
	@Test
	public void testDeleteRoadConnection() {
		assertEquals(true, graph.containsRoadConnection(town[3], town[1]));
		graph.deleteRoadConnection(town[3], town[1], "Road_4");
		assertEquals(false, graph.containsRoadConnection(town[3], town[1]));
	}
	
	@Test
	public void testDeleteTown() {
		assertEquals(true, graph.containsTown("Town_3"));
		graph.deleteTown(town[3]);
		assertEquals(false, graph.containsTown("Town_3"));
	}
	
	@Test
	public void testAllTowns() {
		ArrayList<String> roads = graph.allTowns();
		assertEquals("Town_0", roads.get(0));
		assertEquals("Town_1", roads.get(1));
		assertEquals("Town_2", roads.get(2));
		assertEquals("Town_3", roads.get(3));
		assertEquals("Town_4", roads.get(4));
	}
	
	@Test
	public void testGetPathA() {
		ArrayList<String> path = graph.getPath(town[1],town[4]);
		  assertNotNull(path);
		  assertTrue(path.size() > 0);
		  assertEquals("Town_1 via Road_4 to Town_3 2 mi",path.get(0).trim());
		  assertEquals("Town_3 via Road_6 to Town_4 7 mi",path.get(1).trim());
	}
}
