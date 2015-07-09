package test.dnd.game;

import static org.junit.Assert.*;

import org.junit.Test;

import dnd.game.Location;

public class TestLocation {

	@Test public void distanceToOtherLocationIsValid() {
		Location loc1 = new Location(1, 1);
		Location loc2 = new Location(2, 2);
		assertEquals(1, loc1.distanceTo(loc2));
	}

	@Test public void distanceToOtherLocationIsValid2() {
		Location loc1 = new Location(1, 1);
		Location loc2 = new Location(6, 6);
		Location loc3 = new Location(1, 6);
		assertEquals(loc1.distanceTo(loc3), loc1.distanceTo(loc2));
	}
	
	@Test public void distanceToSelfIsZero() {
		Location loc1 = new Location(1, 1);
		Location loc2 = new Location(1, 1);
		assertEquals(0, loc1.distanceTo(loc2));
	}
	
	@Test public void addLocations() {
		Location loc1 = new Location(1, 4);
		Location loc2 = new Location(2, 3);
		assertEquals(new Location(3, 7), loc1.add(loc2));
	}
}
