package dnd.game.entity.living;

import dnd.game.PlayerClass;

/**
 * The player character class implementation.
 *
 */
public class Player extends LivingEntity {
	private PlayerClass playerClass;
	
	/**
	 * Get the player character's class
	 * @return the player's class as a PlayerClass object
	 */
	public PlayerClass getPlayerClass() {
		return playerClass;
	}

	/**
	 * Set the player character's class
	 * @param playerClass An instance PlayerClass
	 */
	public void setPlayerClass(PlayerClass playerClass) {
		this.playerClass = playerClass;
	}
	
	@Override
	public String toString() {
		return getName() + " the " + getPlayerClass().getName() + " (lvl " + getLevel() + ")";
	}
}
