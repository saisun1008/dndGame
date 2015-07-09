package dnd.game;


/**
 * An enumeration of damage types
 *
 */
public enum DamageType {
	/** Melee damage type */
	MELEE("Melee"),
	/** Ranged damage type */
	RANGED("Ranged"),
	/** Armor value */
	ARMOR("Armor"),
	/** Shield armor value */
	SHIELD("Shield");
	
	private String name;

	/**
	 * Constructor
	 * @param name The name of this damage type
	 */
	DamageType(String name) {
		this.name = name;
	}

	/**
	 * Get the human readable name of this damage type
	 * @return The human readable name of this damage type
	 */
	public String getName() {
		return name;
	}
}
