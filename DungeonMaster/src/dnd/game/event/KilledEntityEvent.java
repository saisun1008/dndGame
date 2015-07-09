package dnd.game.event;

import dnd.game.Entity;
import dnd.game.entity.living.LivingEntity;

public class KilledEntityEvent extends GameEvent {
	private Entity source;
	private LivingEntity target;

	public KilledEntityEvent(Entity source, LivingEntity target) {
		this.source = source;
		this.target = target;
	}

	public Entity getSource() {
		return source;
	}

	public LivingEntity getTarget() {
		return target;
	}
	
	@Override
	public String toString() {
		return source + " killed " + target;
	}
}
