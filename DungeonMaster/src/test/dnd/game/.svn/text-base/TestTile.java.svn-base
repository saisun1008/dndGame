package test.dnd.game;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import dnd.game.Tile;

public class TestTile {

	@Test
	public void testTilesCanAcceptEntity() {
		assertTrue(Tile.tilesAcceptEntity(
				Arrays.asList(Tile.Floor, Tile.Floor, Tile.DoorOpen), null, null));
	}

	@Test
	public void testCollidableTilesWontAcceptEntity() {
		assertFalse(Tile.tilesAcceptEntity(
				Arrays.asList(Tile.Floor, Tile.Rock, Tile.DoorOpen), null, null));
	}

}
