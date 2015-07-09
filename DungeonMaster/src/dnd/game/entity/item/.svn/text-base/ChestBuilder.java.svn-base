package dnd.game.entity.item;

import dnd.game.AbilityType;
import dnd.game.Dice;
import dnd.util.Modifier;

/**
 * builder class for the chest involving the builder pattern
 * 
 */
public class ChestBuilder {
	private Chest chest;
	private Dice dice;
	private int level;

	public ChestBuilder(Chest chest, int level, Dice dice) {
		this.setChest(chest);
		this.setLevel(level);
		this.setDice(dice);
	}

	public ChestBuilder() {
		this(new Chest(), 1, new Dice());
	}

	public Chest getChest() {
		return chest;
	}

	public void setChest(Chest chest) {
		this.chest = chest;
	}

	public Dice getDice() {
		return dice;
	}

	public void setDice(Dice dice) {
		this.dice = dice;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void buildAll() {
		buildWeapons();
		buildArmor();
		buildPotions();
	}

	/**
	 * generate a bunch of weapons. the probability for generating a weapon goes
	 * down each time a weapon is generated. 2 weapons is 50% less likely, 3 is
	 * 66% less likely, etc. this is adjusted based on the level (at lvl 2, 2
	 * weapons is 75% less likely)
	 */
	public void buildWeapons() {

		for (int chance = 0; chance < 5; chance++) {
			int roll = dice.roll(1, chance + level);
			if (roll != 1)
				break; // no more weapons
			Item item = EquippableItemFactory.getWeapon(dice, level);
			chest.getInventory().addItem(item);
		}
	}

	/**
	 * generate a bunch of armor. the probability for generating armor goes down
	 * each time armor is generated
	 */
	public void buildArmor() {

		for (int chance = 0; chance < 5; chance++) {
			int roll = dice.roll(1, (chance + level) / 2 + 1);
			if (roll != 1)
				break; // no more armor
			Item item = EquippableItemFactory.getItem(dice, level);
			if (item instanceof Weapon || item instanceof Shield) {
				chance--;
				continue; // retry if this is a weapon/shield
			}
			chest.getInventory().addItem(item);
		}
	}

	/**
	 * random ability type for potion
	 */
	public void buildPotions() {

		AbilityType type = AbilityType.values()[dice.roll(1,
				AbilityType.values().length) - 1];

		// generate potions, low probability
		for (int chance = 0; chance < 5; chance++) {
			int roll = dice.roll(1, chance + level * 2);
			if (roll != 1)
				break; // no more armor
			Item item;
			int amount = dice.roll(1, level);
			if (type == AbilityType.HP)
				item = new HealthPotion(amount * 2);
			else
				item = new Potion(new Modifier<AbilityType>(type,
						amount / 6 + 1));
			chest.getInventory().addItem(item);
		}
	}
}
