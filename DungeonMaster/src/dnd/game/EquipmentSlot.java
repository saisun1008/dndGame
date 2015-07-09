package dnd.game;

import static dnd.game.AbilityType.CHAR;
import static dnd.game.AbilityType.CONS;
import static dnd.game.AbilityType.DEX;
import static dnd.game.AbilityType.INT;
import static dnd.game.AbilityType.STR;
import static dnd.game.AbilityType.WIS;

/**
 * An enumeration of the possible equipment slots for a player/monster/NPC
 * 
 */
public enum EquipmentSlot {

	// The equipment slots and their potential ability modifier types
	HELMET("Helmet", new Object[] { INT, WIS, DamageType.ARMOR }), ARMOR(
			"Armor", new Object[] { DamageType.ARMOR }), BRACERS("Bracers",
			new Object[] { STR, DamageType.ARMOR }), RING("Ring", new Object[] {
			STR, CONS, WIS, CHAR, DamageType.ARMOR }), BELT("Belt",
			new Object[] { CONS, STR, DamageType.ARMOR }), BOOTS("Boots",
			new Object[] { DEX, DamageType.ARMOR }), WEAPON("Weapon",
			new Object[] { INT, WIS, DamageType.ARMOR }), SHIELD("Shield",
			new Object[] { INT, WIS, DamageType.ARMOR });

	private String name;
	private final Object[] abilityTypes;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            The human readable name of the equipment slot
	 */
	private EquipmentSlot(String name) {
		this.name = name;
		this.abilityTypes = new AbilityType[0];
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            The human readable name of the equipment slot
	 * @param abilityTypes
	 *            the possible ability (& damage) modifiers that items in this
	 *            slot can modify
	 */
	private EquipmentSlot(String name, Object[] abilityTypes) {
		this.name = name;
		this.abilityTypes = abilityTypes;
	}

	/**
	 * Get the human readable name of the equipment slot
	 * 
	 * @return name The human readable name of the equipment slot
	 */
	public String getName() {
		return name;
	}

	/**
	 * get the ability types associated with the equipment slot
	 * @return an array of ability types 
	 */
	public Object[] getAbilityTypes() {
		return abilityTypes;
	}
}
