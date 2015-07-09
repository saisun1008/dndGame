package dnd.game;

import dnd.game.entity.living.LivingEntity;
import dnd.game.event.AttackCheckEvent;
import dnd.game.event.InitiateAttackEvent;
import dnd.game.event.SpeakEvent;
import static dnd.game.DamageType.*;
import static dnd.game.AbilityType.*;

/**
 * Manages attack calculations on behalf of the world (base attack bonus, ranged/melee damage
 * calculations, dice rolls).
 */
public class AttackManager {
	private World world;
	private BaseAttackBonus baseAttack;

	/**
	 * Manage attacks on a given world
	 * @param world the world to manage attacks for
	 */
	public AttackManager(World world) {
		this.world = world;
		this.baseAttack = new BaseAttackBonus();
	}
	
	/**
	 * Main attack method to inflict damage on a target from a source given the source's
	 * currently equipped weapon. This method will run the attack a number of times proportional
	 * to the player's level.
	 * 
	 * @param source the initiating entity
	 * @param target the target entity of the attack
	 * @return whether the attack can take place (not counting initiating roll)
	 */
	public boolean attackEntity(LivingEntity source, LivingEntity target) {
		for (int i = 1; i <= baseAttack.getNumberOfAttacks(source.getLevel()); i++) {
			if (source.damageModifiers().delta(MELEE) > 0) {
				if (!meleeAttack(source, target, i)) return false;
			}
			else if (source.damageModifiers().delta(RANGED) > 0) {
				if (!rangedAttack(source, target, i)) return false;
			}
			else {
				return false;
			}
		}
		return true;
	}

	/**
	 * Performs a melee attack on a target with the attack number
	 * 
	 * @param source the source of the attack
	 * @param target the target of the attack
	 * @param attackNum the attack number (for AC check)
	 * @return whether a melee attack took place (failing AC check counts as attack)
	 */
	private boolean meleeAttack(LivingEntity source, LivingEntity target, int attackNum) {
		if (source.getLocation().distanceTo(target.getLocation()) > 1) {
			tooFar(source);
			return false; 
		}
		
		// AC check counts as attack
		if (!canAttack(source, target, attackNum)) return true; 
		
		// we can attack
		damage(source, target);
		return true;
	}

	/**
	 * Performs a ranged attack on a target with the attack number
	 * 
	 * @param source the source of the attack
	 * @param target the target of the attack
	 * @param attackNum the attack number (for AC check)
	 * @return whether a melee attack took place (failing AC check counts as attack)
	 */
	private boolean rangedAttack(LivingEntity source, LivingEntity target, int attackNum) {
		// perform trace to see if any entities/objects are in the way
		Trace trace = new Trace(world, source.getLocation(), target.getLocation());
		if (trace.distance() > 3) {
			tooFar(source);
			return false; // max ranged distance is 3
		}
		if (trace.getEntities().size() != 2) {
			blocked(source);
			return false;
		}
		if (trace.getEntities().get(0) != source) return false; 
		if (trace.getEntities().get(1) != target) return false;
		if (!Tile.tilesAcceptEntity(trace.getCells(), world, source)) {
			blocked(source);
			return false;
		}
		
		// AC check counts as attack
		if (!canAttack(source, target, attackNum)) return true; 
		
		// we can attack
		damage(source, target);
		return true;
	}
	
	/**
	 * Performs actual damage roll and does damage on target (AC check was valid)
	 * @param source the source performing damage
	 * @param target the target of the attack
	 */
	private void damage(LivingEntity source, LivingEntity target) {
		int amount = 0;
		if (source.damageModifiers().delta(MELEE) > 0) {
			amount = world.abilityRoll(1, source.damageModifiers().delta(MELEE), source, STR);
		}
		else if (source.damageModifiers().delta(RANGED) > 0) {
			amount = world.roll(1, source.damageModifiers().delta(RANGED), source);
		}
		world.damage(source, target, amount);
	}
	
	/**
	 * Performs the AC check; whether the source is allowed to attack the target and perform
	 * the damage roll.
	 * 
	 * @param source the source performing the attack
	 * @param target the target of the attack
	 * @param attackNum the attack number
	 * @return whether the AC check succeeds
	 */
	private boolean canAttack(LivingEntity source, LivingEntity target, int attackNum) {
		world.sendEvent(new InitiateAttackEvent(source, target, attackNum));
		int roll = world.roll(1, 20, source);
		int bonus = attackBonus(source, attackNum);
		world.sendEvent(new AttackCheckEvent(source, target, attackNum, roll, bonus));
		return roll + bonus >= target.armorClass();
	}

	/**
	 * The attack bonus for a given source and attack number
	 * @param source the source of the attack
	 * @param attackNum the attack number
	 * @return the total attack bonus (base + str/dex)
	 */
	private int attackBonus(LivingEntity source, int attackNum) {
		int bonus = baseAttack.getBaseAttackBonus(source.getLevel(), attackNum);

		if (source.damageModifiers().delta(MELEE) > 0) {
			bonus += source.getAbilityModifier(STR);
		}
		else if (source.damageModifiers().delta(RANGED) > 0) {
			bonus += source.getAbilityModifier(DEX);
		}
		return bonus;
	}
	
	/**
	 * Notify the source that they are too far to attack
	 * @param source the attacker
	 */
	private void tooFar(LivingEntity source) {
		world.sendEvent(new SpeakEvent(source + " is too far to attack"));
	}
	
	/**
	 * Notify the source that the attack is blocked by a cell/entity
	 * @param source the attacker
	 */
	private void blocked(LivingEntity source) {
		world.sendEvent(new SpeakEvent("Something is blocking the path of the attack"));
	}
}
