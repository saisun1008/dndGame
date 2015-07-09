package dnd.game.entity.living;

import dnd.game.AbilityType;
import dnd.game.DamageType;
import dnd.game.Entity;
import dnd.game.Inventory;
import dnd.game.InventoryInterface;
import dnd.game.entity.item.EquippableItem;
import dnd.util.Modifier;
import dnd.util.ModifierSet;

/**
 * LivingEntity is an extension of the basic Entity class and is used for
 * defining the player, monsters and NPCs (or any other living creature in the
 * game world)
 * 
 * @see Entity
 * 
 */
public abstract class LivingEntity extends Entity implements InventoryInterface {
	private Inventory inventory = new Inventory();
	private ModifierSet<AbilityType> rawAbilityScores = new ModifierSet<AbilityType>();
	private int actualHP;
	private int level;

	public LivingEntity() {
		setName(getClass().getSimpleName());
	}

	/**
	 * @return The inventory of this LivingEntity
	 */
	public Inventory getInventory() {
		return inventory;
	}
	
	/**
	 * Get the ability roll modifier for a given ability type
	 * @param abilityType The ability type of the modifier to get
	 * @return the modifier value for the given ability type
	 */
	public int getAbilityModifier(AbilityType abilityType) {
		ModifierSet<AbilityType> tmp = abilityModifiers();
		int baseValue = rawAbilityScores.delta(abilityType);
		int modifier = tmp.delta(abilityType);
		// The modifier value is: the floor of half the ability value minus 5.
		// This value cannot be less than -5.
		return Math.max(-5, ((baseValue + modifier) / 2) - 5);
	}

	/**
	 * Bind an inventory to this LivingEntity
	 * @param inventory The inventory to bind
	 */
	protected void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	/**
	 * @return whether the entity is alive
	 */
	public boolean isAlive() {
		return actualHP > 0;
	}
	
	/**
	 * @return The current hit point value for this LivingEntity
	 */
	public int getHP() {
		return actualHP;
	}
	
	/**
	 * @param value The current hit point value for this LivingEntity
	 */
	public void setHP(int value) {
		actualHP = value > getTotalHP() ? getTotalHP() : value;
	}
	
	/**
	 * @return The max number of hit points this LivingEntity can have
	 */
	public int getTotalHP() {
		return abilityModifiers().delta(AbilityType.HP);
	}
	
	/**
	 * Set the current hit points to the maximum number of hit points this
	 * LivingEntity can have
	 */
	public void resetHP() {
		actualHP = getTotalHP();
	}
	
	/**
	 * Sets the player's level value
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level - 1;
		resetHP();
	}

	/**
	 * Get the level value for the player
	 * @return the player's level
	 */
	public int getLevel() {
		return level + 1;
	}
	
	/**
	 * @return the raw ability modifier scores (used during char generation)
	 */
	public ModifierSet<AbilityType> getRawAbilityScores() {
		if (rawAbilityScores == null) {
			rawAbilityScores = new ModifierSet<AbilityType>();
		}
		return rawAbilityScores;
	}

	/**
	 * Alias for {@link #resetHP()}
	 */
	public void spawn() {
		resetHP();
	}

	/**
	 * @return the armor class given the modifiers and equipped items
	 */
	public int armorClass() {
		return 10 + abilityModifiers().delta(AbilityType.DEX) + 
				damageModifiers().delta(DamageType.ARMOR) +
				damageModifiers().delta(DamageType.SHIELD);
	}
	
	@Override
	public ModifierSet<AbilityType> abilityModifiers() {
		ModifierSet<AbilityType> set = new ModifierSet<AbilityType>();
		set.add(super.abilityModifiers());

		// sum up total abilities in equipped items
		for (EquippableItem item : inventory.getEquippedItems()) {
			set.add(item.abilityModifiers());
		}
		
		set.add(adjustedAbilityScores()); // add raw ability scores
		return set;		
	}
	
	@Override
	public ModifierSet<DamageType> damageModifiers() {
		ModifierSet<DamageType> set = new ModifierSet<DamageType>();
		set.add(super.damageModifiers());

		// sum up total abilities in equipped items
		for (EquippableItem item : inventory.getEquippedItems()) {
			set.add(item.damageModifiers());
		}
		return set;		
	}
	
	@Override
	public String formatAbilityModifiers() {
		String hp = "HP: " + getHP() + "/" + getTotalHP();
		if (getHP() <= 0) hp += " (Dead)";

		ModifierSet<AbilityType> modifiers = abilityModifiers();
		
		StringBuilder builder = new StringBuilder();
		for (AbilityType t : AbilityType.values()) {
			if (t == AbilityType.HP) continue;
			int value = rawAbilityScores.delta(t) + modifiers.delta(t);
			int modifier = getAbilityModifier(t);
			builder.append(t + ": " + value + " (" + (modifier >= 0 ? "+" : "") + modifier + ")\n");
		}
		return builder.toString() + hp + "\n";
	}
	
	@Override
	public String formatDamageModifiers() {
		String extra = "AC: " + armorClass() + "\n";
		extra += "Attack modifiers: ";
		int levelPlusOne = level + 1;
		for (int i = levelPlusOne; i > 0; i -= 5) {
			if (i != levelPlusOne) {
				extra += "/";
			}
			extra += "+" + i;
		}
		extra += "\n";
		return super.formatDamageModifiers() + extra;
	}
	
	/**
	 * Calculate the adjustment bonus/penalties for ability scores (10-11 baseline +0)
	 * TODO: Remove this
	 * @return the modifier that is added to {@link #abilityModifiers()}.
	 */
	private Modifier<AbilityType> adjustedAbilityScores() {
		Modifier<AbilityType> mod = new Modifier<AbilityType>();
		if (rawAbilityScores.isEmpty()) return mod; // don't do adjustment on empty set
		for (AbilityType type : AbilityType.values()) {
			int adjust;
			if (type == AbilityType.HP) adjust = rawAbilityScores.delta(type); // no adjust for HP
			//else adjust = (rawAbilityScores.delta(type) / 2) - 5;
			else adjust = 0;
			mod.setDelta(type, adjust);
		}
		return mod;
	}
}
