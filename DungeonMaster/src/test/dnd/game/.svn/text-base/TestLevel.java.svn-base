package test.dnd.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dnd.game.Level;
import dnd.game.Location;
import dnd.game.Tile;
import dnd.game.World;
import dnd.game.entity.living.Player;

public class TestLevel {
	private Level level = new Level(2, 2);
	private Player player = new Player();
	private World world = new World();
	
	@Before public void start() {
		level.setSpawnLocation(new Location(0, 0));
		level.setCell(0, 0, Tile.Floor);
		level.setCell(0, 1, Tile.WallV);
		level.setCell(1, 0, Tile.DoorOpen);
		level.setCell(1, 1, Tile.StairsDown);
	}
	
	@Test public void doesNotAcceptEntityOutsideLevel() {
		assertFalse(level.acceptsEntity(world, player, new Location(-1, -1)));
		assertFalse(level.acceptsEntity(world, player, new Location(5, 5)));
		assertFalse(level.acceptsEntity(world, player, new Location(1, 3)));
		assertFalse(level.acceptsEntity(world, player, new Location(-3, 0)));
	}
	
	@Test public void acceptsEntityOnValidTile() {
		assertTrue(level.acceptsEntity(world, player, new Location(1, 1)));
		assertTrue(level.acceptsEntity(world, player, new Location(1, 0)));
		assertTrue(level.acceptsEntity(world, player, new Location(0, 0)));
	}
	
	@Test public void doesNotAcceptEntityOnWallTile() {
		assertFalse(level.acceptsEntity(world, player, new Location(0, 1)));
	}
	
	@Test public void locationInBoundsReturnsFalseWhenOOB() {
		assertFalse(level.locationInBounds(new Location(-1, -1)));
	}
	
	@Test public void locationInBoundsTrueWhenInBounds() {
		assertTrue(level.locationInBounds(new Location(1, 0)));
	}
}
