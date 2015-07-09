package dnd.game.entity.living;

import dnd.game.Inventory;
import dnd.game.PlayerClass;
import dnd.game.entity.item.Armor;
import dnd.game.entity.item.EquippableItem;
import dnd.game.entity.item.EquippableItemFactory;
import dnd.game.entity.item.Shield;
import dnd.game.entity.item.Weapon;

/**
 * Abstract builder for a Fighter player class
 */
public abstract class FighterBuilder extends PlayerBuilder {
	@Override
	public void buildClass() {
		if (getEntity() instanceof Player) {
			((Player)getEntity()).setPlayerClass(PlayerClass.FIGHTER);
		}
	}
	
	@Override
	public void buildInventory() {

		Inventory inv = getEntity().getInventory();
		inv.clear(); // clear inv before generating

		// give 1 armor, 1 weapon, 1 shield, and equip them.
		Armor armor = EquippableItemFactory.getArmor(getDice(), getLevel());
		Weapon weapon = new Weapon("Sword", 8, 0);
		Shield shield = getDice().roll(1, 2) == 1 ? EquippableItemFactory
				.getShield(getDice(), getLevel()) : null;
		EquippableItem[] items = new EquippableItem[] { armor, weapon, shield };
		for (EquippableItem item : items) {
			if (item == null)
				continue;
			inv.addItem(item);
			inv.equipItem(item);
		}

		// give some amount of gold
		inv.setGold((int) (5 * getLevel() * 0.3 + getDice().roll(4, 6)));
	}
}
