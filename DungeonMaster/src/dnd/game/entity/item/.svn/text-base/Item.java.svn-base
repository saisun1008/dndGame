package dnd.game.entity.item;

import dnd.game.Entity;
import dnd.game.World;
import dnd.game.entity.living.LivingEntity;

/**
 * This class extends the entity class to define a skeleton for all game items
 * 
 * @see Entity
 * 
 */
public abstract class Item extends Entity {
	/**
	 * Constructor
	 */
	public Item() {
	}

	/**
	 * Constructor
	 * @param name The name of the item
	 */
	public Item(String name) {
		setName(name);
	}
	
	/**
	 * Triggered when an entity attempts to use an item
	 * @param world the world to use the item in
	 * @param entity the entity that used this item
	 */
	public abstract boolean use(World world, LivingEntity entity);
}
