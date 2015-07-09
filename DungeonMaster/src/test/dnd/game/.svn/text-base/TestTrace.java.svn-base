package test.dnd.game;

import static org.junit.Assert.*;

import org.junit.Test;

import dnd.game.Level;
import dnd.game.Location;
import dnd.game.Tile;
import dnd.game.Trace;
import dnd.game.World;
import dnd.game.entity.living.NPC;
import dnd.game.entity.living.Orc;
import dnd.game.entity.living.Troll;

public class TestTrace {
	
	private static World world = new World();
	private static NPC npc1 = new Orc();
	private static NPC npc2 = new Troll();
	static {
		world.setLevel(new Level(3, 3));
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				world.getLevel().setCell(x, y, x == 1 && y == 1 ? Tile.Rock : Tile.Floor);
			}
		}
		npc1.setLocation(0, 2);
		npc2.setLocation(2, 2);
		world.getMonsters().add(npc1);
		world.getMonsters().add(npc2);
	}

	@Test public void traceLocationForDistance() {
		assertEquals(0, new Trace(new Location(2, 2), new Location(2, 2)).distance());
		assertEquals(1, new Trace(new Location(1, 1), new Location(2, 2)).distance());
		assertEquals(1, new Trace(new Location(2, 2), new Location(1, 1)).distance());
		assertEquals(2, new Trace(new Location(1, 1), new Location(2, 3)).distance());
	}

	@Test public void traceForCells() {
		Trace trace = new Trace(world, new Location(0, 0), new Location(2, 2));
		assertTrue(trace.getCells().contains(Tile.Rock));
		assertEquals(3, trace.getCells().size());
	}
	
	@Test public void traceForEntities() {
		Trace trace = new Trace(world, new Location(0, 2), new Location(2, 2));
		assertEquals(npc1, trace.getEntities().get(0));
		assertEquals(npc2, trace.getEntities().get(1));
		assertEquals(2, trace.getEntities().size());
	}

	@Test public void traceForLocations() {
		Trace trace = new Trace(world, new Location(0, 1), new Location(2, 2));
		assertEquals(new Location(0, 1), trace.getLocations().get(0));
		assertEquals(new Location(1, 2), trace.getLocations().get(1));
		assertEquals(new Location(2, 2), trace.getLocations().get(2));
		assertEquals(3, trace.getLocations().size());
	}
}
