package dnd.game.trait;

import dnd.game.Entity;
import dnd.game.World;

/**
 * Represents a tile's behaviour. A tile might have many different behaviours, each influencing
 * how the tile interacts with entities on it (via {@link #touch(World, Entity)}) or accepting
 * of entities (via {@link #acceptsEntity(World, Entity)}).
 */
public abstract class TileTrait {
	/**
	 * Subclasses should perform any trait specific behaviours when an entity touches
	 * this tile in the given world.
	 * 
	 * @param world the world the event occurred in
	 * @param entity the entity that touched the tile
	 */
	public abstract void touch(World world, Entity entity);
	
	/**
	 * Override this method if the tile can conditionally not accept an
	 * entity. This default implementation always returns true.
	 * 
	 * @param world the world object the event occurred on
	 * @param entity the entity to accept 
	 * @return true or false, depending on whether the tile should accept
	 *   the entity.
	 */
	public boolean acceptsEntity(World world, Entity entity) {
		return true;
	}
}
