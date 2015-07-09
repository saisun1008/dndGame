package dnd.game.event;

import dnd.game.Entity;
import dnd.game.Inventory;
import dnd.game.entity.living.LivingEntity;

public class OpenInventoryEvent extends GameEvent {

	private LivingEntity entity;
	private Entity inventoryEntity;
	private Inventory inventory;

	public OpenInventoryEvent(LivingEntity entity, Entity inventoryEntity, Inventory inventory) {
		this.entity = entity;
		this.inventoryEntity = inventoryEntity;
		this.inventory = inventory;
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

	@Override
	public String toString() {
		return entity + " opened inventory of " + inventoryEntity;
	}
}
