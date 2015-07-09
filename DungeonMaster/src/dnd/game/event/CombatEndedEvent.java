package dnd.game.event;

import dnd.game.entity.living.LivingEntity;

public class CombatEndedEvent extends GameEvent {
	private LivingEntity winner;
	private LivingEntity loser;

	public CombatEndedEvent(LivingEntity winner, LivingEntity loser) {
		this.winner = winner;
		this.loser = loser;
	}

	public LivingEntity getLoser() {
		return loser;
	}

	public LivingEntity getWinner() {
		return winner;
	}

	@Override
	public String toString() {
		return winner + " defeated " + loser + " in combat.";
	}
}
