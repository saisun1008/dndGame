package dnd.game.event;

import dnd.game.Entity;
import dnd.game.Inventory;
import dnd.game.entity.item.Item;
import dnd.game.entity.living.LivingEntity;

public class TakeInventoryItemEvent extends GameEvent {

	private LivingEntity entity;
	private Entity inventoryEntity;
	private Inventory inventory;
	private Item item;

	public TakeInventoryItemEvent(LivingEntity entity, Entity inventoryEntity, Inventory inventory, Item item) {
		this.entity = entity;
		this.inventoryEntity = inventoryEntity;
		this.inventory = inventory;
		this.item = item;
	}

	public LivingEntity getEntity() {
		return entity;
	}

	public Entity getInventoryEntity() {
		return inventoryEntity;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public Item getItem() {
		return item;
	}

	@Override
	public String toString() {
		return entity + " took " + item + " from inventory of " + inventoryEntity;
	}
}
