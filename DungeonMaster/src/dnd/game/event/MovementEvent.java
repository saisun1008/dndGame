package dnd.game.event;

import dnd.game.Entity;
import dnd.game.Location;

/**
 * EquipEvent class defines a structure for passing data related to equipping
 * items between components of the game application.
 * 
 * @see GameEvent
 * 
 */
public class MovementEvent extends GameEvent {
	private Entity entity;
	private Location delta;
	
	/**
	 * Constructor - Initialize the event with the given parameters
	 * @param entity The entity involved with this event
	 * @param delta The desired offset in position
	 */
	public MovementEvent(Entity entity, Location delta) {
		this.entity = entity;
		this.delta = delta;
	}
	
	/**
	 * @return The entity involved with this event
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * @return The desired offset in position
	 */
	public Location getDelta() {
		return delta;
	}
	
	@Override
	public String toString() {
		return entity.getClass().getSimpleName() + " " + entity.getName() + 
				" moved to location " + entity.getLocation();
	}
}
