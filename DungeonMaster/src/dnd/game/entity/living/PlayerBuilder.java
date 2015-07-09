package dnd.game.entity.living;

import java.util.Arrays;

import dnd.game.AbilityType;
import dnd.game.Dice;
import dnd.util.Modifier;

/**
 * The abstract character builder class implementation.
 * 
 */
public abstract class PlayerBuilder {
	private LivingEntity entity;
	private Dice dice = new Dice();
	private int level;

	/**
	 * Sets the dice for the builder object, used to copy the world's dice class
	 * 
	 * @param dice
	 *            the dice to use
	 */
	public void setDice(Dice dice) {
		this.dice = dice;
	}

	/**
	 * Sets the entity to perform build tasks on
	 * 
	 * @param entity
	 *            the entity to build
	 */
	public void setEntity(LivingEntity entity) {
		this.entity = entity;
	}

	/**
	 * Sets the desired level of the entity
	 * 
	 * @param level
	 *            the desired entity level
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the entity to use to build the player
	 */
	public LivingEntity getEntity() {
		return entity;
	}

	/**
	 * @return the dice to use for rolls
	 */
	protected Dice getDice() {
		return dice;
	}

	/**
	 * @return the level to build the player object at
	 */
	protected int getLevel() {
		return level == 0 ? 1 : level;
	}

	/**
	 * Convenience method to build all required attributes on an entity and
	 * return the entity object.
	 * 
	 * @return the built entity
	 */
	public LivingEntity buildAll() {
		buildClass();
		buildAbilities();
		buildLevel();
		buildInventory();
		return entity;
	}

	/**
	 * Builds the abilities on the entity object using the attribute priorities
	 * (see {@link #typePriorities()}) to determine the order that values are
	 * assigned to abilities
	 */
	public void buildAbilities() {
		// Use dice to determine ability scores, 6 times (4d6 sum top 3)
		int[] abilityValues = new int[6];
		for (int i = 0; i < 6; i++) {
			abilityValues[i] = dice.abilityRoll();
		}

		// sort outcomes
		Arrays.sort(abilityValues);

		// set values on attributes
		Modifier<AbilityType> baseStats = new Modifier<AbilityType>();
		int i = 5;
		for (AbilityType type : typePriorities()) {
			baseStats.setDelta(type, abilityValues[i--]);
		}

		// Assign ability scores to entity
		entity.getRawAbilityScores().clear();
		entity.getRawAbilityScores().add(baseStats);
	}

	/**
	 * Adds HP for a desired level value based on 1d10+cons.
	 * 
	 * To build HP for a target level, use {@link #buildLevel()} instead.
	 */
	public void buildHP() {
		int modifier = entity.abilityModifiers().delta(AbilityType.CONS);
		int dieRoll = dice.roll(1, 10);
		entity.getRawAbilityScores().add(AbilityType.HP, (dieRoll + modifier));
	}

	/**
	 * Builds the HP for a given level (by repeatedly performing
	 * {@link #buildHP()}) and setting the level. If this method is called,
	 * {@link #buildHP()} should not be called directly.
	 */
	public void buildLevel() {
		for (int i = 0; i < level; i++) {
			buildHP();
		}
		entity.setLevel(level);
	}

	/**
	 * Override this method to return the list of all 6 ability types in
	 * descending order of importance to the build type
	 * 
	 * @return the list of prioritized ability types
	 */
	public abstract AbilityType[] typePriorities();

	/**
	 * Builds the player's class (FIGHTER, etc.)
	 */
	public abstract void buildClass();

	/**
	 * Builds the player's inventory.
	 */
	public abstract void buildInventory();
}
