package dnd.game.entity.item;

import dnd.game.DamageType;
import dnd.game.EquipmentSlot;

/**
 * Basic wearable shield class
 */
public class Shield extends EquippableItem {
	/**
	 * Creates a new shield with a name and a {@link DamageType#SHIELD} modifier value
	 * @param modValue the modifier value for the SHIELD attribute
	 */
	public Shield(int modValue) {
		super(EquipmentSlot.SHIELD);
		getBaseDamageModifiers().add(DamageType.SHIELD, modValue);
		setName(getName(modValue));
	}
	
	private String getName(int modifierValue) {
		switch(modifierValue) {
			case 1: return "Buckler";
			case 2: return "Heavy Shield";
			case 3: return "Tower Shield";
		}
		return "Unknown Shield";
	}
	
	@Override
	public String formatDamageModifiers() {
		return super.formatDamageModifiers() + 
				"SHIELD: " + getBaseDamageModifiers().delta(DamageType.SHIELD) + "\n";
	}
}
