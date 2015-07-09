package dnd.game.entity.item;

import static dnd.game.DamageType.*;
import dnd.game.EquipmentSlot;

/**
 * Basic equippable weapon class with a melee and ranged damage value
 */
public class Weapon extends EquippableItem {
	/**
	 * Creates a new weapon with a name and given melee / ranged attack values. These
	 * values will be used for the dice roll (1dN where N = melee or ranged)
	 * @param name the name of the weapon
	 * @param melee the melee damage modifier
	 * @param ranged the ranged damage modifier
	 */
	public Weapon(String name, int melee, int ranged) {
		super(name, EquipmentSlot.WEAPON);
		getBaseDamageModifiers().add(MELEE, melee, RANGED, ranged);
	}
}
