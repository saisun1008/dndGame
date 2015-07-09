package dnd.game.entity.item;

import dnd.game.Inventory;
import dnd.game.InventoryInterface;
import dnd.game.World;
import dnd.game.entity.living.LivingEntity;

/**
 * A chest. The chest contains treasure (other Items)
 * 
 * @see Item
 * 
 */
public class Chest extends Item implements InventoryInterface {
	private Inventory inventory;

	public Chest() {
		this.inventory = new Inventory();
	}
	
	/**
	 * @return the chest's inventory (containing items / gold)
	 */
	public Inventory getInventory() {
		if (inventory == null) {
			this.inventory = new Inventory();
		}
		return inventory;
	}
	
	@Override
	public boolean use(World world, LivingEntity entity) {
		world.openInventory(entity, this);
		return true;
	}
}
