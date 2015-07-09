package dnd.game.entity.item;

import dnd.game.AbilityType;
import dnd.game.DamageType;
import dnd.game.Dice;

/**
 * A factory class or generating Equippable Items
 * 
 * @see EquippableItem
 * @see Item
 * 
 */
public class EquippableItemFactory {

	/**
	 * The static factory method to return a random equippable item
	 * 
	 * @param dice
	 *            the dice to use to return an item
	 * @param level
	 *            the level of the entity to scale the item for
	 * @return An equippable item
	 */
	public static EquippableItem getItem(Dice dice, int level) {
		switch (dice.roll(1, 8)) {
		case 1:
			return getArmor(dice, level);
		case 2:
			return getWeapon(dice, level);
		case 3:
			return getShield(dice, level);
		case 4:
			return getBelt(dice, level);
		case 5:
			return getBracers(dice, level);
		case 6:
			return getBoots(dice, level);
		case 7:
			return getHelmet(dice, level);
		case 8:
			return getRing(dice, level);
		}
		return null;
	}

	/**
	 * Return a random armor item
	 * 
	 * @param dice
	 *            the dice to use to return an item
	 * @param level
	 *            the level of the entity to scale the item for
	 * @return a random armor
	 */
	public static Armor getArmor(Dice dice, int level) {
		return modified(new Armor(maxRoll(dice, level, 8)), dice, level);
	}

	/**
	 * Return a random weapon item
	 * 
	 * @param dice
	 *            the dice to use to return an item
	 * @param level
	 *            the level of the entity to scale the item for
	 * @return a random weapon
	 */
	public static Weapon getWeapon(Dice dice, int level) {
		if (dice.roll(1, 2) == 1) {
			return modified(new Weapon("Sword", 8, 0), dice, level);
		} else {
			return modified(new Weapon("Bow", 0, 8), dice, level);
		}
	}

	/**
	 * Return a random shield item
	 * 
	 * @param dice
	 *            the dice to use to return an item
	 * @param level
	 *            the level of the entity to scale the shield for
	 * @return a random shield
	 */
	public static Shield getShield(Dice dice, int level) {
		return modified(new Shield(maxRoll(dice, level, 3)), dice, level);
	}

	/**
	 * return a random belt item
	 * 
	 * @param dice
	 *            the dice to use to return an item
	 * @param level
	 *            the level of the entity to scale the belt for
	 * @return a random shield
	 */
	public static Belt getBelt(Dice dice, int level) {
		return modified(new Belt(), dice, level);
	}

	/**
	 * return a random boot item
	 * 
	 * @param dice
	 *            the dice to use to return an item
	 * @param level
	 *            the level of the entity to scale the boot for
	 * @return a random boots
	 */
	public static Boots getBoots(Dice dice, int level) {
		return modified(new Boots(), dice, level);
	}

	/**
	 * return a random bracers item
	 * 
	 * @param dice
	 *            the dice to use to return an item
	 * @param level
	 *            the level of the entity to scale the bracers for
	 * @return a random bracers
	 */
	public static Bracers getBracers(Dice dice, int level) {
		return modified(new Bracers(), dice, level);

	}

	/**
	 * return a random helmet item
	 * 
	 * @param dice
	 *            the dice to use to return an item
	 * @param level
	 *            the level of the entity to scale the helmet
	 * @return a random helmet
	 */
	public static Helmet getHelmet(Dice dice, int level) {
		return modified(new Helmet(), dice, level);
	}

	/**
	 * return a random ring item
	 * 
	 * @param dice
	 *            the dice to use to return an item
	 * @param level
	 *            the level of the entity to scale the ring
	 * @return a random ring
	 */
	public static Ring getRing(Dice dice, int level) {
		return modified(new Ring(), dice, level);
	}

	/**
	 * return the maximum roll based on the level
	 * 
	 * @param dice
	 * @param level
	 * @param max
	 * @return the max roll can be used
	 */
	private static int maxRoll(Dice dice, int level, int max) {
		int num = level * 3;
		if (num > max)
			num = max;
		return num;
	}

	/**
	 * return the modified item
	 * 
	 * @param item
	 *            item which is to be modified
	 * @param dice
	 *            used for the modify
	 * @param level
	 *            used for calculation of the modify item
	 * @return the modified item
	 */
	private static <T extends EquippableItem> T modified(T item, Dice dice,
			int level) {
		Object[] types = item.getSlot().getAbilityTypes();
		int scaledLevel = level / 6 + 1;
		Object type = types[dice.roll(1, types.length) - 1];
		if (type instanceof AbilityType) {
			AbilityType at = (AbilityType) type;
			item.getBaseAbilityModifiers().add(at, scaledLevel);
			item.setName(item.getName() + " of " + at.getName());
		} else if (type instanceof DamageType) {
			DamageType dt = (DamageType) type;
			item.getBaseDamageModifiers().add(dt, scaledLevel);
			item.setName("Reinforced " + item.getName());
		}
		return item;
	}
}
