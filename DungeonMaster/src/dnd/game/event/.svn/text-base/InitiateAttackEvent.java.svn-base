package dnd.game.event;

import dnd.game.entity.living.LivingEntity;

public class InitiateAttackEvent extends GameEvent {
	private LivingEntity source;
	private LivingEntity target;
	private int attackNumber;

	public InitiateAttackEvent(LivingEntity source, LivingEntity target, int attackNumber) {
		this.source = source;
		this.target = target;
		this.attackNumber = attackNumber;
	}

	public LivingEntity getSource() {
		return source;
	}

	public LivingEntity getTarget() {
		return target;
	}

	public int getAttackNumber() {
		return attackNumber;
	}

	@Override
	public String toString() {
		return source + " initiating attack #" + attackNumber + " on " + 
				target + " (AC: " + target.armorClass() + ")";
	}
}
