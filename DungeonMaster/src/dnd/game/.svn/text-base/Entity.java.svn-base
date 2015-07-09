package dnd.game;

import dnd.util.ModifierSet;

/**
 * The entity class is the base class for all dynamic entities in the game.
 * Specifically, objects and monsters (not tiles). For example, an attribute of
 * all game entities is location. In this respect, it represents something
 * physical (in the game world)
 */
public abstract class Entity {

	private Location location = new Location(0, 0);
	private ModifierSet<AbilityType> baseAbilityModifiers = new ModifierSet<AbilityType>();
	private ModifierSet<DamageType> baseDamageModifiers = new ModifierSet<DamageType>();
	private String name;

	/**
	 * Alias for {@link #getBaseAbilityModifiers() getBaseAbilityModifiers()}
	 * 
	 * @return the set of base ability modifiers
	 */
	public ModifierSet<AbilityType> abilityModifiers() {
		return getBaseAbilityModifiers();
	}

	/**
	 * Alias for {@link #getBaseDamageModifiers() getBaseDamageModifiers()}
	 * 
	 * @return the set of damage modifiers
	 */
	public ModifierSet<DamageType> damageModifiers() {
		return getBaseDamageModifiers();
	}

	/**
	 * Get the base ability modifiers
	 * 
	 * @return the set of base ability modifiers
	 */
	public ModifierSet<AbilityType> getBaseAbilityModifiers() {
		return baseAbilityModifiers;
	}

	/**
	 * Get the damage modifiers
	 * 
	 * @return the set of damage modifiers
	 */
	public ModifierSet<DamageType> getBaseDamageModifiers() {
		return baseDamageModifiers;
	}

	/**
	 * Get the name of this entity
	 * 
	 * @return the class name of this entity if it exists, or the defined name
	 *         otherwise
	 */
	public String getName() {
		return name == null ? getClass().getSimpleName() : name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the location of this entity
	 * 
	 * @return The location of this entity
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Set the location of this entity
	 * 
	 * @param location
	 *            The location of this entity
	 */
	public void setLocation(Location location) {
		setLocation(location.getX(), location.getY());
	}

	/**
	 * Set the location of this entity
	 * 
	 * @param x
	 *            The x coordinate of the location to set
	 * @param y
	 *            The y coordinate of the location to set
	 */
	public void setLocation(int x, int y) {
		location = new Location(x, y);
	}

	@Override
	public String toString() {
		return getName();
	}

	/**
	 * All ability modifiers formatted as a multi-line string
	 * 
	 * @return all ability modifiers formatted as a multi-line string
	 */
	public String formatAbilityModifiers() {
		StringBuilder builder = new StringBuilder();
		for (AbilityType t : AbilityType.values()) {
			if (t == AbilityType.HP)
				continue;
			int value = abilityModifiers().delta(t);
			builder.append(t + ": " + (value >= 0 ? "+" : "") + value + "\n");
		}
		return builder.toString();
	}

	/**
	 * All damage modifiers formatted as a multi-line string
	 * 
	 * @return all damage modifiers formatted as a multi-line string
	 */
	public String formatDamageModifiers() {
		StringBuilder builder = new StringBuilder();
		for (DamageType t : DamageType.values()) {
			if (t == DamageType.SHIELD || t == DamageType.ARMOR)
				continue;
			int value = damageModifiers().delta(t);
			builder.append(t + ": " + (value >= 0 ? "+" : "") + value + "\n");
		}
		return builder.toString();
	}

	/**
	 * All modifiers formatted as a multi-line string
	 * 
	 * @return all modifiers formatted as a multi-line string
	 */
	public String formatModifiers() {
		return formatAbilityModifiers() + formatDamageModifiers();
	}
}
