package dnd.game.event;

import dnd.game.entity.living.LivingEntity;

public class DamageEvent extends GameEvent {
	private int amount;
	private LivingEntity target;
	private LivingEntity source;

	public DamageEvent(LivingEntity source, LivingEntity target, int amount) {
		this.source = source;
		this.target = target;
		this.amount = amount;
	}

	public LivingEntity getSource() {
		return source;
	}

	public LivingEntity getTarget() {
		return target;
	}

	public int getAmount() {
		return amount;
	}
	
	@Override
	public String toString() {
		return source + " damaged " + target + " for " + amount + 
				" HP (now " + target.getHP() + "/" + target.getTotalHP() + ")";
	}
}
