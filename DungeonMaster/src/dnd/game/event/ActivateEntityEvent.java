package dnd.game.event;

import dnd.game.Entity;

public class ActivateEntityEvent extends GameEvent {
	private Entity activeEntity;
	
	public ActivateEntityEvent(Entity activeEntity) {
		this.activeEntity = activeEntity;
	}

	public Entity getActiveEntity() {
		return activeEntity;
	}
	
	@Override
	public String toString() {
		return activeEntity + " will now move.";
	}
}
