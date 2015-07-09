package dnd.game.event;

import dnd.game.EquipmentSlot;
import dnd.game.entity.item.EquippableItem;
import dnd.game.entity.living.LivingEntity;
import dnd.game.entity.living.Player;

/**
 * EquipEvent class defines a structure for passing data related to equipping
 * items between components of the application.
 * 
 * @see GameEvent
 * 
 */
public class EquipEvent extends GameEvent {

	private EquipmentSlot slot;
	private EquippableItem item;
	private LivingEntity entity;
	private boolean unequipped;

	/**
	 * Constructor - Initialize the event with the given parameters
	 * @param entity The entity involved with this event
	 * @param item The item involved with this event
	 * @param slot The item involved with this event
	 * @param unequipped True if the item involved should be unequipped from the entity
	 */
	public EquipEvent(LivingEntity entity, EquippableItem item, EquipmentSlot slot, boolean unequipped) {
		this.entity = entity;
		this.item = item;
		this.slot = slot;
		this.unequipped = unequipped;
	}

	/**
	 * Get the entity involved with this event
	 * @return The entity involved with this event
	 */
	public LivingEntity getEntity() {
		return entity;
	}

	/**
	 * Get the item involved with this event
	 * @return The item involved with this event
	 */
	public EquippableItem getItem() {
		return item;
	}

	/**
	 * Get the slot involved with this event
	 * @return The slot involved with this event
	 */
	public EquipmentSlot getSlot() {
		return slot;
	}

	/**
	 * @return True if the item involved was unequipped from the entity
	 */
	public boolean isUnequipped() {
		return unequipped;
	}
	
	@Override
	public String toString() {
		String type, connector;
		if (isUnequipped()) {
			type = "removed";
			connector = "from";
		}
		else {
			type = "added";
			connector = "to";
		}
		return "Player " + ((Player)getEntity()).getName() + " " + type + " item " + 
				getItem() + " " + connector + " " + getSlot().getName() + " slot";
	}
}
