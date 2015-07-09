package dnd.game.entity.item;

import dnd.game.EquipmentSlot;
/**
 * Basic class representing wearable belt
 */
public class Belt extends EquippableItem {
	public Belt() {
		super("Belt", EquipmentSlot.BELT);
	}
}
