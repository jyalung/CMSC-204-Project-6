package project6;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Road_STUDENT_Test {
	private Road road;
	
	@Before
	public void setUp() throws Exception {
		road = new Road(new Town("Town 0"), new Town("Town 4"), 6, "Road 1");
	}

	@After
	public void tearDown() throws Exception {
		road = null;
	}
	
	@Test
	public void testCompareTo() {
		assertEquals(0, road.compareTo(new Road(new Town("Town 0"), new Town("Town4"), 6, "Road 1")));
		assertEquals(0, road.compareTo(road));
		assertEquals(0, road.compareTo(new Road(new Town("Town 4"), new Town("Town0"), 6, "Road 1")));
	}
	
	@Test
	public void testEquals() {
		assertEquals(true, road.equals(new Road(new Town("Town 0"), new Town("Town 4"), 6, "Road 1")));
		assertEquals(true, road.equals(road));
	}
	
	@Test
	public void testContains() {
		assertEquals(true, road.contains(new Town("Town 0")));
		assertEquals(true, road.contains(new Town("Town 4")));
		assertEquals(false, road.contains(new Town("Town 1")));
	}
}
