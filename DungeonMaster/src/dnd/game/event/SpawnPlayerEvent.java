package dnd.game.event;

import dnd.game.entity.living.Player;

/**
 * SpawnPlayerEvent class defines a structure for passing data related to the
 * spawning (instantiation) of a Player in the World
 * 
 * @see GameEvent
 * @see Player
 * @see World
 * 
 */
public class SpawnPlayerEvent extends GameEvent {
	private Player player;
	
	/**
	 * Constructor - Initialize the event with the given parameter
	 * @param player The Player involved with this event
	 */
	public SpawnPlayerEvent(Player player) {
		this.player = player;
	}

	/**
	 * @return The Player involved with this event
	 */
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public String toString() {
		return "Player " + player.getName() + " spawned.";
	}
}
