package dnd.game.event;

import dnd.game.Location;
import dnd.game.Tile;
import dnd.game.entity.living.LivingEntity;

public class DoorToggleEvent extends GameEvent {
	private LivingEntity source;
	private Tile oldCell;
	private Tile newCell;
	private Location location;

	public DoorToggleEvent(LivingEntity source, Tile oldCell, Tile newCell, Location location) {
		this.source = source;
		this.oldCell = oldCell;
		this.newCell = newCell;
		this.location = location;
	}

	public LivingEntity getSource() {
		return source;
	}

	public Tile getOldCell() {
		return oldCell;
	}

	public Tile getNewCell() {
		return newCell;
	}
	
	public Location getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return source + (newCell == Tile.DoorOpen ? " opened " : " closed ") + "door at " + location;
	}
}
