package dnd.game.event;

import dnd.game.World;

/**
 * An interface for defining Event observers. The event observers reacts to
 * various action performed in the game.
 * 
 * @see GameEvent
 * 
 */
public interface EventObserver {
	/**
	 * Signal that an event fired (so observers can trigger callbacks)
	 * @param world the world the event fired from
	 * @param event A generic GameEvent
	 */
	public void eventFired(World world, GameEvent event);
}
