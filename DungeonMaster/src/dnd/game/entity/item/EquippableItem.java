package dnd.game.entity.item;

import dnd.game.DamageType;
import dnd.game.EquipmentSlot;
import dnd.game.World;
import dnd.game.entity.living.LivingEntity;

/**
 * An abstract class used for defining <em>equippable</em> items
 * 
 * @see Item
 * 
 */
public abstract class EquippableItem extends Item {
	private EquipmentSlot slot;

	/**
	 * Constructor
	 * @param name The name of the item
	 */
	public EquippableItem(String name, EquipmentSlot slot) {
		super(name);
		this.slot = slot;
	}

	/**
	 * Constructor
	 */
	public EquippableItem(EquipmentSlot slot) {
		this.slot = slot;
	}

	/**
	 * Test whether this item fits in the given equipment slot
	 * 
	 * @param slot The slot that is being tested for fitness
	 * @return True if this item fits in the given slot, false otherwise.
	 */
	public boolean fitsInSlot(EquipmentSlot slot) {
		return this.slot == slot;
	}
	
	/**
	 * The equipment slot this item fits into
	 * @return the slot this item fits into
	 */
	public EquipmentSlot getSlot() {
		return slot;
	}

	/**
	 * Equips this item on the entity
	 */
	@Override
	public boolean use(World world, LivingEntity entity) {
		if (world.getTurnManager().activeEntity() != entity) return false;
		world.getTurnManager().equipItem(this);
		return true;
	}
	
	@Override
	public String formatDamageModifiers() {
		String extra = "ARMOR: " + damageModifiers().delta(DamageType.ARMOR) + "\n";
		return super.formatDamageModifiers() + extra;
	}
}
