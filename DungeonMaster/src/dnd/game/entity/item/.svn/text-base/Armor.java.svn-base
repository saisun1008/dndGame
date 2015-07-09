package dnd.game.entity.item;

import dnd.game.DamageType;
import dnd.game.EquipmentSlot;

/**
 * Basic class representing wearable armor
 */
public class Armor extends EquippableItem {
	/**
	 * Creates a new armor type with a name and modifier value for {@link DamageType#ARMOR}.
	 * @param armorModifierValue the modifier value for ARMOR
	 */
	public Armor(int armorModifierValue) {
		super(EquipmentSlot.ARMOR);
		getBaseDamageModifiers().add(DamageType.ARMOR, armorModifierValue);
		setName(getPrefix(armorModifierValue) + " Armor");
	}
	
	/**
	 * Copy constructor to create a new armor from existing one
	 * @param armor the armor object to copy
	 */
	public Armor(Armor armor) {
		this(armor.getBaseDamageModifiers().delta(DamageType.ARMOR));
		getBaseAbilityModifiers().add(armor.getBaseAbilityModifiers());
	}

	private String getPrefix(int armorModifierValue) {
		switch(armorModifierValue) {
			case 1: return "Padded";
			case 2: return "Leather";
			case 3: return "Studded Leather";
			case 4: return "Chain Shirt";
			case 5: return "Breast Plate";
			case 6: return "Branded Mail";
			case 7: return "Half Plate";
			case 8: return "Full Plate";
		}
		return "Unknown";
	}
}
