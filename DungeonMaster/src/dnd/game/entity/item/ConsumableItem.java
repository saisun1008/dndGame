package dnd.game.entity.item;

import dnd.game.World;
import dnd.game.entity.living.LivingEntity;

public class ConsumableItem extends Item {

	@Override
	public boolean use(World world, LivingEntity entity) {
		entity.getInventory().dropItem(this);
		return true;
	}

}
