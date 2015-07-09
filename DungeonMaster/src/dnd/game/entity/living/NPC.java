package dnd.game.entity.living;

import dnd.game.Dice;
import dnd.game.Entity;
import dnd.game.Location;
import dnd.game.Trace;
import dnd.game.World;
import dnd.game.event.AttackCheckEvent;
import dnd.game.event.EventObserver;
import dnd.game.event.GameEvent;

/**
 * A container class for non player characters (i.e. Monsters, shopkeepers)
 * 
 * @see LivingEntity
 * @see Entity
 * 
 */
public class NPC extends LivingEntity implements EventObserver {
	private LivingEntity attacker;
	
	/**
	 * Perform one move decision
	 * @param world the world to think about
	 */
	public void think(World world) {
		if (!isAlive()) return; // no moving when dead
		if (world.getTurnManager().activeEntity() != this) return; // not our turn
		
		Dice dice = world.getDice();
		
		if (attacker != null) { // someone was attacking us, attack back
			Trace trace = new Trace(getLocation(), attacker.getLocation());
			if (trace.distance() > 1) { // far away, move towards them
				Location diff = trace.getLocations().get(1).subtract(getLocation());
				world.getTurnManager().moveEntity(diff);
			}
		}

		//check if there is any player around the monster
		for (Player player : world.getPlayers()) {
			if (getLocation().distanceTo(player.getLocation()) <= 1) {
				if (player.isAlive()) {
					attacker = player;
					world.getTurnManager().attackEntity(player);
					return;
				}
			}
		}
		
		// move around, if we already started moving, keep moving.
		if (world.getTurnManager().getNumMoves() < 6 || dice.roll(1, 2) == 1) {
			if (dice.roll(1, 12) == 1) { // 1 in 12 chance to stop moving early
				world.getTurnManager().waitTurn();
			}
			else {
				int x = dice.roll(1, 3) - 2;
				int y = dice.roll(1, 3) - 2;
				world.getTurnManager().moveEntity(new Location(x, y));
			}
		}
		else {
			world.getTurnManager().waitTurn();
		}
	}
	
	@Override
	public void spawn() {
		super.spawn();
		attacker = null; // forget attacker
	}

	@Override
	public void eventFired(World world, GameEvent event) {
		if (event instanceof AttackCheckEvent) {
			AttackCheckEvent aEvent = (AttackCheckEvent)event;
			if (aEvent.getTarget() == this) { // attacking me, attack back!
				attacker = aEvent.getSource();
			}
		}
	}
}
