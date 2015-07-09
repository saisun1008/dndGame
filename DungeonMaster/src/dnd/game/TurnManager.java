package dnd.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import dnd.game.entity.item.Item;
import dnd.game.entity.living.LivingEntity;
import dnd.game.entity.living.NPC;
import dnd.game.event.ActivateEntityEvent;
import dnd.game.event.NextTurnEvent;

/**
 * Manages turn states for all players / entities. Entities should move through this
 * class when a move counts against their turns. The main {@link #runTurn()} method
 * should be called to continually run any remaining AI moves in the turn and
 * advance all turns until the nextTurn is called.
 * 
 * The turn manager manages a number of moves for a list of entities. After every
 * entity makes all moves (each entity has 6 moves where attacks/equips cost all 6 moves),
 * the turn is advanced to the next via {@link #nextTurn()}.
 * 
 */
public class TurnManager {
	private World world;
	private Stack<TurnState> entities;
	private int turnNumber;

	/**
	 * Creates a new turn manager for the world class
	 * @param world the world to manage turns for
	 */
	public TurnManager(World world) {
		this.world = world;
		this.turnNumber = 0;
	}
	
	/**
	 * @return the current turn number
	 */
	public int getTurnNumber() {
		return turnNumber;
	}
	
	/**
	 * @return the number of moves left in the turn
	 */
	public int getNumMoves() {
		return current().numMoves;
	}
	
	/**
	 * Increases the turn count and begins the next turn.
	 * 
	 * This method performs a 1d20 + dexterity modifier dice roll for all
	 * movable entities to see which one should move first (an initiative roll
	 * in D&D terms). The highest value goes first.
	 * 
	 * TODO Currently this method does not handle multiple entities having the
	 * same roll value. Ordering is undefined when the values are equal.
	 */
	public void nextTurn() {
		turnNumber++;
		world.sendEvent(new NextTurnEvent(turnNumber));

		entities = new Stack<TurnState>();
		ArrayList<DicePair> rolls = new ArrayList<DicePair>();
		for (LivingEntity e : world.getLivingEntities()) {
			if (!e.isAlive()) continue; // don't roll for dead entities
			int roll = world.initiativeRoll(e);
			rolls.add(new DicePair(e, roll));
		}
		
		Collections.sort(rolls);
		for (DicePair pair : rolls) {
			entities.push(new TurnState(pair.entity));
		}
		
		activateEntity();
	}
	
	/**
	 * Runs the current turn by executing any AI decisions and
	 * checking if all turns are finished.
	 */
	public void runTurn() {
		if (current().numMoves <= 0) {
			nextEntity();
			return;
		}
		
		// handle NPC movement
		if (activeEntity() instanceof NPC) {
			int num = current().numMoves;
			((NPC)activeEntity()).think(world);
			if (current().numMoves == num) {
				// AI didn't make any decision, automatically
				// cancel this entity's turn
				waitTurn();
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @return the currently active entity making turn moves
	 */
	public LivingEntity activeEntity() {
		return current().entity;
	}
	
	/**
	 * Moves the entity by a given delta, costs 1 move.
	 * @param delta the amount to move the entity
	 */
	public void moveEntity(Location delta) {
		if (world.moveEntity(activeEntity(), delta)) {
			current().numMoves--;
		}
	}
	
	/**
	 * Attacks a given entity, costs all moves if allowed
	 * @param target the target to attack
	 */
	public void attackEntity(LivingEntity target) {
		if (world.attackEntity(activeEntity(), target)) {
			waitTurn();
		}
	}

	/**
	 * Equips a given item, costs all moves if successful
	 * @param item the item to equip.
	 */
	public void equipItem(Item item) {
		if (world.equipItem(activeEntity(), item)) {
			waitTurn();
		}
	}

	/**
	 * Unequips an item, costs all moves if successful
	 * @param slot the slot to unequip
	 */
	public void unequipSlot(EquipmentSlot slot) {
		if (world.unequipSlot(activeEntity(), slot)) {
			waitTurn();
		}
	}
	
	/**
	 * Toggles a door at a location, costs 1 move if successful
	 * @param doorLocation the location of the door
	 */
	public void toggleDoor(Location doorLocation) {
		if (world.toggleDoor(activeEntity(), doorLocation)) {
			current().numMoves--;
		}
	}
	
	/**
	 * Cedes the turn to the next entity (costs all moves)
	 */
	public void waitTurn() {
		current().numMoves = 0;
	}
	
	/**
	 * @return the current turn state object holding the active
	 * 		entity and the number of moves left.
	 */
	private TurnState current() {
		return entities.peek();
	}
	
	/**
	 * Advances to the next entity in the list for the turn. If
	 * no entities are left, {@link #nextTurn()} is called.
	 */
	private void nextEntity() {
		entities.pop();
		if (entities.size() == 0) {
			nextTurn();
		}
		else {
			activateEntity();
		}
	}
	
	/**
	 * Emits an activate entity event
	 */
	private void activateEntity() {
		world.sendEvent(new ActivateEntityEvent(activeEntity()));
	}
	
	/**
	 * Small state class holding the entity and their number of moves
	 */
	private class TurnState {
		public int numMoves;
		public LivingEntity entity;
		
		public TurnState(LivingEntity entity) {
			this.numMoves = 6;
			this.entity = entity;
		}
	}
	
	/**
	 * Small comparable state class to sort entities by their associated
	 * dice roll.
	 */
	private class DicePair implements Comparable<DicePair> {
		public final LivingEntity entity;
		public final Integer diceRoll;

		public DicePair(LivingEntity entity, Integer diceRoll) {
			this.entity = entity;
			this.diceRoll = diceRoll;
		}

		/**
		 * Sorts dice pairs by the roll value
		 */
		@Override
		public int compareTo(DicePair other) {
			return diceRoll.compareTo(other.diceRoll);
		}
	}
}
