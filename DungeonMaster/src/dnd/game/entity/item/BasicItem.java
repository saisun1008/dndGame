package dnd.game.entity.item;

import dnd.game.World;
import dnd.game.entity.living.LivingEntity;

/**
 * The BasicItem class enhances the Item class by allowing dynamic features on
 * otherwise static game items
 * 
 * @see Item
 * 
 */
public class BasicItem extends Item {

	/**
	 * Constructor
	 * @param name the name of the item
	 */
	public BasicItem(String name) {
		super(name);
	}
	
	@Override
	public boolean use(World world, LivingEntity entity) {
		return false;
	}
}
