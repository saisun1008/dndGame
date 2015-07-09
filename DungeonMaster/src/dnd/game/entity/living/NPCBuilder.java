package dnd.game.entity.living;

import dnd.game.AbilityType;
import dnd.game.Dice;
import dnd.game.Inventory;
import dnd.game.entity.item.Armor;
import dnd.game.entity.item.EquippableItem;
import dnd.game.entity.item.EquippableItemFactory;
import dnd.game.entity.item.Shield;
import dnd.game.entity.item.Weapon;

/**
 * concrete builder class of the playerBuilder class for NPC 
 * 
 */
public class NPCBuilder extends PlayerBuilder {
	private PlayerBuilder builder;

	public NPCBuilder(PlayerBuilder builder) {
		this.builder = builder;
	}

	@Override
	public void setDice(Dice dice) {
		builder.setDice(dice);
	}

	@Override
	public void setEntity(LivingEntity entity) {
		builder.setEntity(entity);
	}

	@Override
	public void setLevel(int level) {
		builder.setLevel(level);
	}

	@Override
	public LivingEntity getEntity() {
		return builder.getEntity();
	}

	@Override
	public void buildAbilities() {
		builder.buildAbilities();
	}

	@Override
	public void buildHP() {
		builder.buildHP();
	}

	@Override
	public LivingEntity buildAll() {
		return builder.buildAll();
	}

	/**
	 * NPC players should have 30% less HP than players
	 */
	@Override
	public void buildLevel() {
		builder.buildLevel();
		int adjust = -(int) (getEntity().getTotalHP() * 0.5);
		getEntity().getBaseAbilityModifiers().add(AbilityType.HP, adjust);
		getEntity().resetHP();
	}

	@Override
	public AbilityType[] typePriorities() {
		return builder.typePriorities();
	}

	@Override
	public void buildClass() {
		builder.buildClass();
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
