package dnd.game;

import dnd.game.entity.living.LivingEntity;

/**
 * An enumeration of LivingEntity abilities
 * 
 * @see LivingEntity
 * 
 */
public enum AbilityType {

	// The abilities
	STR("Strength"),
	DEX("Dexterity"),
	CONS("Constitution"),
	INT("Intelligence"),
	WIS("Wisdom"), 
	CHAR("Charisma"),
	HP("Hit Points");

	private String name;

	/**
	 * Constructor
	 * @param name The name of this ability
	 */
	AbilityType(String name) {
		this.name = name;
	}

	/**
	 * Get the human readable name of this ability
	 * @return the human readable name of this ability
	 */
	public String getName() {
		return name;
	}
}
