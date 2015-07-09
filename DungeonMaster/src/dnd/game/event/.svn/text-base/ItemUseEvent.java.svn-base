package dnd.game.event;

import dnd.game.entity.item.EquippableItem;
import dnd.game.entity.item.Item;
import dnd.game.entity.living.LivingEntity;

public class ItemUseEvent extends GameEvent {
	private Item item;
	private LivingEntity entity;

	public ItemUseEvent(LivingEntity entity, Item item) {
		this.entity = entity;
		this.item = item;
	}
	
	@Override
	public String toString() {
		if (item instanceof EquippableItem) return null; // no message for equipment
		return entity + " used item " + item;
	}
}
