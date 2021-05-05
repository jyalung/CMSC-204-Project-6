package project6;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Town_STUDENT_Test {
	private Town town;
	@Before
	public void setUp() throws Exception {
		town = new Town("Town 2");
		 
	}

	@After
	public void tearDown() throws Exception {
		town = null;
	}
	
	@Test
	public void testCompareTo() {
		assertEquals(0, town.compareTo(new Town("Town 2")));
		assertEquals(0, town.compareTo(town));
		assertEquals(-1, town.compareTo(new Town("Town 3")));
	}
	
	@Test
	public void testEquals() {
		assertEquals(true, town.equals(new Town("Town 2")));
		assertEquals(true, town.equals(town));
	}
}
