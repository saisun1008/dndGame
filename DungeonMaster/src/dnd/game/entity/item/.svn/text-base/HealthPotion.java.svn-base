package dnd.game.entity.item;

import dnd.game.World;
import dnd.game.entity.living.LivingEntity;

/**
 * basic health potion class
 * 
 */
public class HealthPotion extends Potion {
	private int amount;

	/**
	 * set te potion name based on the amount of HP it can restore
	 * @param amount
	 *            the potion can restore the amount of HP
	 */
	public HealthPotion(int amount) {
		super(null);
		this.amount = amount;
		String mod;
		if (amount >= 10)
			mod = "Major ";
		if (amount >= 5)
			mod = "";
		else
			mod = "Minor ";
		setName(mod + "Potion of Healing");
	}

	@Override
	public boolean use(World world, LivingEntity entity) {
		entity.setHP(entity.getHP() + amount);
		return super.use(world, entity);
	}
}
