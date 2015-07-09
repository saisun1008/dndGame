package dnd.game;

import java.util.List;

import dnd.game.trait.CollidableTrait;
import dnd.game.trait.NextLevelTrait;
import dnd.game.trait.TileTrait;

/**
 * An abstract class which defines common Tile properties and behaviors for all game tiles
 *
 */
public enum Tile {
	Rock(new CollidableTrait()), 
	Floor, 
	StairsDown("Stairs Down (Exit)", new NextLevelTrait()), 
	StairsUp("Stairs Up (Entrance)"),
	WallV("Wall (Vertical)", new CollidableTrait()), 
	WallH("Wall (Horizontal)", new CollidableTrait()), 
	WallUC("Wall (Upper Corner)", new CollidableTrait()), 
	WallBC("Wall (Bottom Corner)", new CollidableTrait()), 
	DoorClosed("Door (Closed)", new CollidableTrait()), 
	DoorOpen("Door (Open)");
	
	private TileTrait[] traits;
	private String name;
	
	Tile(String name, TileTrait ... traits) {
		this.name = name;
		this.traits = traits;
	}
	
	Tile(TileTrait ... traits) {
		this.traits = traits;
	}
	
	/**
	 * Gets the display name for the tile
	 * @return the tile's display name
	 */
	public String getName() {
		return name != null ? name : name();
	}

	/**
	 * This is an alias for {@link #isCollidable()}
	 * @param world the world to test this in
	 * @param entity And entity to test against the Tile
	 * @return True if the entity can move onto (or have its location set) to this Tile
	 */
	public boolean acceptsEntity(World world, Entity entity) {
		for (TileTrait trait : traits) {
			if (!trait.acceptsEntity(world, entity)) return false;
		}
		return true;
	}
	
	/**
	 * A callback for when a LivingEntity moves onto the Tile
	 * @param world A reference to a {@link World} object
	 * @param entity A reference to an {@link Entity} object
	 */
	public void touch(World world, Entity entity) {
		for (TileTrait trait : traits) {
			trait.touch(world, entity);
		}
	}
	
	/**
	 * Tests a set of tiles that they all accept an entity
	 * @param tiles the tiles to check
	 * @param world the world object
	 * @param entity the entity to test
	 * @return true if all tiles accept the entity, false if not.
	 */
	public static boolean tilesAcceptEntity(List<Tile> tiles, World world, Entity entity) {
		for (Tile tile : tiles) {
			if (!tile.acceptsEntity(world, entity)) return false;
		}
		return true;
	}
}
