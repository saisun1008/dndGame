package dnd.game.event;

import dnd.game.entity.living.LivingEntity;

public class AttackCheckEvent extends GameEvent {
	private int bonus;
	private int roll;
	private int attackNumber;
	private LivingEntity target;
	private LivingEntity source;

	public AttackCheckEvent(LivingEntity source, LivingEntity target, int attackNum, int roll, int bonus) {
		this.source = source;
		this.target = target;
		this.attackNumber = attackNum;
		this.roll = roll;
		this.bonus = bonus;
	}

	public int getBonus() {
		return bonus;
	}

	public int getRoll() {
		return roll;
	}

	public int getAttackNumber() {
		return attackNumber;
	}

	public LivingEntity getTarget() {
		return target;
	}

	public LivingEntity getSource() {
		return source;
	}
	
	@Override
	public String toString() {
		String info = "Attack check #" + attackNumber + 
			" rolled " + roll + " + " + bonus + " (bonus) for total of " + (roll+bonus) + " and ";
		int ac = target.armorClass();
		if (roll + bonus >= ac) info += "succeeded";
		else info += "failed";
		info += " (AC: " + ac + ")";
		return info;
	}
}
