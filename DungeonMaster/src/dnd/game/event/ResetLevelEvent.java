package dnd.game.event;

import dnd.game.World;

/**
 * ResetLevelEvent class defines a structure for passing data related to
 * situations where the player changes dungeon levels
 * 
 * @see GameEvent
 * 
 */
public class ResetLevelEvent extends GameEvent {
	private World world;

	/**
	 * Constructor - Initialize the event with the given parameters
	 * @param world The World where the level resides
	 */
	public ResetLevelEvent(World world) {
		this.world = world;
	}

	/**
	 * 
	 * @return The World where the level resides
	 */
	public World getWorld() {
		return world;
	}
	
	@Override
	public String toString() {
		return "Restarting level.";
	}
}
