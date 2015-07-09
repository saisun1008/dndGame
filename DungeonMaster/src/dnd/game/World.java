package dnd.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dnd.game.entity.item.EquippableItem;
import dnd.game.entity.item.Item;
import dnd.game.entity.living.LivingEntity;
import dnd.game.entity.living.NPC;
import dnd.game.entity.living.Player;
import dnd.game.event.DamageEvent;
import dnd.game.event.DiceRollEvent;
import dnd.game.event.DoorToggleEvent;
import dnd.game.event.EquipEvent;
import dnd.game.event.EventObserver;
import dnd.game.event.GameEvent;
import dnd.game.event.ItemUseEvent;
import dnd.game.event.KilledEntityEvent;
import dnd.game.event.MovementEvent;
import dnd.game.event.NextLevelEvent;
import dnd.game.event.OpenInventoryEvent;
import dnd.game.event.ResetLevelEvent;
import dnd.game.event.TakeInventoryItemEvent;

/**
 * The world class tracks everything related to the game world, from Monsters,
 * to tiles, to the player(s) and objects
 * 
 */
public class World {

	private transient Set<EventObserver> observers;
	private Level level;
	private int levelNumber;
	private List<Item> items;
	private List<Player> players;
	private List<NPC> monsters;
	private boolean started;
	private TurnManager turnManager;
	private AttackManager attackManager;
	private Dice dice;

	/**
	 * Constructor
	 */
	public World() {
		this.observers = new HashSet<EventObserver>();
		this.items = new ArrayList<Item>();
		this.players = new ArrayList<Player>();
		this.monsters = new ArrayList<NPC>();
		this.levelNumber = 1;
		this.turnManager = new TurnManager(this);
		this.attackManager = new AttackManager(this);
		this.dice = new Dice();
	}
	
	/**
	 * @return whether the game is started
	 */
	public boolean isStarted() {
		return started;
	}

	/**
	 * @return the main dice for the game
	 */
	public Dice getDice() {
		return dice;
	}
	
	/**
	 * Reset the dice object (useful when testing)
	 * @param dice new dice object to use
	 */
	public void setDice(Dice dice) {
		this.dice = dice;
	}

	/**
	 * @return The current Level
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * @return The current level number
	 */
	public int getLevelNumber() {
		return levelNumber;
	}
	
	/**
	 * Sets the level number
	 * @param levelNum the level number
	 */
	public void setLevelNumber(int levelNum) {
		levelNumber = levelNum;
	}

	/**
	 * @return the current turn number
	 */
	public int getTurnNumber() {
		return turnManager.getTurnNumber();
	}
	
	/**
	 * @return the turn manager responsible for moving players/entities
	 */
	public TurnManager getTurnManager() {
		return turnManager;
	}

	/**
	 * @param level
	 *            The level to set
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

	/**
	 * @return All items on the current level
	 */
	public List<Item> getItems() {
		return items;
	}

	/**
	 * @return All players on the current level
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * @return All NPCs on the current level
	 */
	public List<NPC> getMonsters() {
		return monsters;
	}
	
	/**
	 * @return all players and NPCs together
	 */
	public List<LivingEntity> getLivingEntities() {
		List<LivingEntity> ents = new ArrayList<LivingEntity>();
		ents.addAll(players);
		ents.addAll(monsters);
		return ents;
	}
	
	/**
	 * @param loc the location to look for entities at
	 * @return all entities (players, monsters, items) at a location
	 */
	public List<Entity> getEntitiesAtLocation(Location loc) {
		List<Entity> ents = new ArrayList<Entity>();
		List<Entity> finalEnts = new ArrayList<Entity>();
		ents.addAll(players);
		ents.addAll(monsters);
		ents.addAll(items);
		for (Entity e : ents) {
			if (e.getLocation().equals(loc)) finalEnts.add(e);
		}
		return finalEnts;
	}

	/**
	 * Starts the game and sets up the world. This is also called on each new
	 * level to initialize all entities to their spawn locations and
	 * {@link #spawn()} them.
	 */
	public void start() {
		if (started)
			return;
		if (level == null)
			return;

		new WorldBuilder(this).buildAll();
		started = true;
		turnManager.nextTurn();
	}
	
	/**
	 * Stops the game.
	 */
	public void stop() {
		started = false;
	}
	
	/**
	 * Restarts the game
	 */
	public void restart() {
		stop();
		start();
	}

	/**
	 * Proceed to the next dungeon level
	 * 
	 * @param level
	 *            the next level to set
	 */
	public void nextLevel(Level level) {
		this.level = level;
		this.levelNumber++;
		for (Player player : players) levelUp(player);
		sendEvent(new NextLevelEvent(this));
		restart();
	}
	
	/**
	 * Resets the current dungeon level
	 */
	public void resetLevel() {
		sendEvent(new ResetLevelEvent(this));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		restart();
	}
	
	/**
	 * Damages a target entity for a given amount, emitting a DamageEvent. To perform a full
	 * attack, use the attack method.
	 * @param source the source of the damage event
	 * @param target the target of the damage
	 * @param amount the amount to damage the target for
	 */
	public void damage(LivingEntity source, LivingEntity target, int amount) {
		if (!target.isAlive()) return;
		target.setHP(target.getHP() - amount);
		sendEvent(new DamageEvent(source, target, amount));
		if (!target.isAlive()) {
			sendEvent(new KilledEntityEvent(source, target));
			
			if (target instanceof Player) {
				for (Player player : players) {
					if (player.isAlive()) continue;
					resetLevel();
					break;
				}
			}
		}
	}
	
	/**
	 * Attacks a target
	 */
	public boolean attackEntity(LivingEntity source, LivingEntity target) {
		return attackManager.attackEntity(source, target);
	}

	/**
	 * Equips an item in a slot on an entity and emits an EquipEvent
	 */
	public boolean equipItem(LivingEntity entity, Item item, EquipmentSlot slot) {
		if (!(item instanceof EquippableItem))
			return false;
		if (entity.getInventory().equipSlot(slot, (EquippableItem) item)) {
			sendEvent(new EquipEvent(entity, (EquippableItem) item, slot, false));
			return true;
		}
		return false;
	}

	/**
	 * Equips an item in the first available slot
	 * 
	 * @param entity
	 *            the living entity to equip an item on
	 * @param item
	 *            the item to equip
	 * @return whether the item was equipped
	 */
	public boolean equipItem(LivingEntity entity, Item item) {
		if (!(item instanceof EquippableItem))
			return false;
		if (entity.getInventory().equipItem((EquippableItem) item)) {
			for (EquipmentSlot slot : EquipmentSlot.values()) {
				if (entity.getInventory().equipmentInSlot(slot) == item) {
					sendEvent(new EquipEvent(entity, (EquippableItem) item,
							slot, false));
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Unequip a given Slot on a given Entity
	 * 
	 * @param entity
	 *            The entity on which to unequip an item
	 * @param slot
	 *            the slot to unequip
	 * @return True if the item was unequipped from Entity
	 */
	public boolean unequipSlot(LivingEntity entity, EquipmentSlot slot) {
		EquippableItem item = entity.getInventory().equipmentInSlot(slot);
		if (entity.getInventory().unequipSlot(slot)) {
			sendEvent(new EquipEvent(entity, item, slot, true));
			return true;
		}
		return false;
	}
	/**
	 * use an item
	 * @param entity
	 * 				which will use the item
	 * @param item
	 * 				which is being used
	 * @return
	 */
	public boolean useItem(LivingEntity entity, Item item) {
		if (item.use(this, entity)) {
			sendEvent(new ItemUseEvent(entity, item));
			return true;
		}
		return false;
	}
	
	/**
	 * Levels the player to the next level and gives an HP bonus
	 * @param player the player to level up
	 */
	public void levelUp(Player player) {
		int roll = abilityRoll(1, 10, player, AbilityType.CONS);
		player.getBaseAbilityModifiers().add(AbilityType.HP, roll);
		player.setLevel(player.getLevel() + 1);
	}

	/**
	 * Moves an entity in the map
	 * 
	 * @param event
	 *            the movement event to process
	 * @return true if the move is valid and the model updated, false otherwise
	 */
	public boolean moveEntity(Entity entity, Location delta) {
		Location finalLocation = entity.getLocation().add(delta);
		
		// check if tile accepts entity
		if (!level.acceptsEntity(this, entity, finalLocation)) {
			return false;
		}
		
		// check if any entities are on the tile
		for (Entity e : getEntitiesAtLocation(finalLocation)) {
			if (e instanceof LivingEntity && ((LivingEntity)e).isAlive()) {
				return false;
			}
			else if (!(e instanceof LivingEntity)) return false;
		}

		// tile is clear of entities
		entity.setLocation(finalLocation);
		sendEvent(new MovementEvent(entity, finalLocation));
		level.getCell(finalLocation).touch(this, entity);
		return true;
	}

	/**
	 * Moves an entity to an absolute location if it can be moved, but does not
	 * fire an event
	 * 
	 * @param entity
	 *            the entity to move
	 * @param location
	 *            the final location to move the entity to
	 * @return whether the location accepted the entity's location
	 */
	public boolean teleportEntity(Entity entity, Location location) {
		if (level.acceptsEntity(this, entity, location)) {
			entity.setLocation(location);
			level.getCell(location).touch(this, entity);
			return true;
		}
		return false;

	}
	
	/**
	 * Rolls the main dice and generates a DiceRollEvent
	 * @param numDice the number of dice to roll (1d8 would be 1 dice)
	 * @param dieSize the size of each die (1d8 would be 8 die size)
	 * @param modifier the modifier value to add to the roll
	 * @param modName the name of the modifier used in the roll
	 * @param source the source entity triggering the roll, can be null
	 * @return the roll value
	 */
	public int roll(int numDice, int dieSize, Integer modifier, String modName, Entity source) {
		int value = dice.roll(numDice, dieSize, modifier);
		sendEvent(new DiceRollEvent(value, numDice, dieSize, modifier, modName, source));
		return value;
	}

	/**
	 * Rolls the main dice with no modifier and generates a DiceRollEvent
	 * @param numDice the number of dice to roll (1d8 would be 1 dice)
	 * @param dieSize the size of each die (1d8 would be 8 die size)
	 * @param source the source entity triggering the roll, can be null
	 * @return the roll value
	 */
	public int roll(int numDice, int dieSize, Entity source) {
		int value = dice.roll(numDice, dieSize);
		sendEvent(new DiceRollEvent(value, numDice, dieSize, null, null, source));
		return value;
	}

	/**
	 * Performs an initiative roll, to see which entity moves first
	 * @param source the source entity triggering the roll, can be null
	 * @return the roll value
	 */
	public int initiativeRoll(LivingEntity source) {
		return roll(1, 20, source.getAbilityModifier(AbilityType.DEX), "Initiative roll", source);
	}
	
	/**
	 * Convenience method of {@link #roll(int, int, int, String, Entity)} using abilityModifiers for
	 * a given AbilityType as the modifier value on the source entity. Requires a valid source entity.
	 * @param numDice the number of dice to roll (1d8 would be 1 dice)
	 * @param dieSize the size of each die (1d8 would be 8 die size)
	 * @param source the source entity triggering the roll, can be null
	 * @param type the ability type to add as a modifier
	 * @return the roll value
	 */
	public int abilityRoll(int numDice, int dieSize, LivingEntity source, AbilityType type) {
		return roll(numDice, dieSize, source.getAbilityModifier(type), type.toString(), source);
	}
	
	/**
	 * Convenience method of {@link #roll(int, int, int, String, Entity)} using damageModifiers for
	 * a given DamageType as the modifier value on the source entity. Requires a valid source entity.
	 * @param numDice the number of dice to roll (1d8 would be 1 dice)
	 * @param dieSize the size of each die (1d8 would be 8 die size)
	 * @param source the source entity triggering the roll, can be null
	 * @param type the damage type to add as a modifier
	 * @return the roll value
	 */
	public int damageRoll(int numDice, int dieSize, Entity source, DamageType type) {
		return roll(numDice, dieSize, source.damageModifiers().delta(type), type.toString(), source);
	}
	
	/**
	 * Opens and closes a door at a given location if it can be opened and if the
	 * source entity is close enough to open it. Emits a DoorToggleEvent if successful.
	 * 
	 * @param source the source entity opening the door
	 * @param doorLocation the location of the door
	 * @return whether the door was opened
	 */
	public boolean toggleDoor(LivingEntity source, Location doorLocation) {
		if (source.getLocation().distanceTo(doorLocation) > 1) return false;
		Tile cell = level.getCell(doorLocation), newCell;
		if (cell == Tile.DoorOpen) {
			newCell = Tile.DoorClosed;
		}
		else {
			newCell = Tile.DoorOpen;
		}
		level.setCell(doorLocation.getX(), doorLocation.getY(), newCell);
		sendEvent(new DoorToggleEvent(source, cell, newCell, doorLocation));
		return true;
	}
	
	/**
	 * Provide an entity access the inventory of another.
	 * 
	 * @param primary entity accessing the second's inventory
	 * @param secondary entity whose Inventory is being inspected
	 */
	public void openInventory(LivingEntity entity, Entity inventoryEntity) {
		if (!(inventoryEntity instanceof InventoryInterface)) return;
		//if (entity.getLocation().distanceTo(inventoryEntity.getLocation()) > 1) return;
		Inventory inventory = ((InventoryInterface)inventoryEntity).getInventory();
		
		sendEvent(new OpenInventoryEvent(entity, inventoryEntity, inventory));
	}

	/**
	 * Transfer the inventory from one entity to another.
	 * 
	 * @param target entity to which inventory is being transferred
	 * @param source entity whose Inventory is being depleted
	 * @param item being transferred
	 */
	public void takeInventoryItem(LivingEntity entity, Entity inventoryEntity, Item item) {
		if (!(inventoryEntity instanceof InventoryInterface)) return;
		//if (entity.getLocation().distanceTo(inventoryEntity.getLocation()) > 1) return;
		if (entity.getInventory().isFull()) return;
		
		Inventory source = ((InventoryInterface)inventoryEntity).getInventory();
		Inventory dest = entity.getInventory();
		if (source.getEquippedItems().contains(item)) {
			source.unequipItem((EquippableItem)item);
			source.dropItem(item);
			dest.addItem(item);
		}
		if (source.getItemSlots().contains(item)) {
			source.dropItem(item);
			dest.addItem(item);
		}
		
		sendEvent(new TakeInventoryItemEvent(entity, inventoryEntity, source, item));
	}

	/**
	 * Transfer gold from one entity to another.
	 * 
	 * @param target entity to which gold is being transferred
	 * @param source entity whose gold is being depleted
	 * @param amount of gold being transferred
	 */
	public void takeInventoryGold(LivingEntity entity, Entity inventoryEntity, int amount) {
		if (!(inventoryEntity instanceof InventoryInterface)) return;
		if (entity.getLocation().distanceTo(inventoryEntity.getLocation()) > 1) return;
		
		Inventory source = ((InventoryInterface)inventoryEntity).getInventory();
		Inventory dest = entity.getInventory();
		if (amount == 0) amount = source.getGold();
		dest.addGold(amount);
		source.addGold(-amount);
	}

	/**
	 * Add observers to model changes
	 * 
	 * @param observer
	 *            An EventObserver object
	 */
	public void addObserver(EventObserver observer) {
		if (observers == null)
			observers = new HashSet<EventObserver>();
		observers.add(observer);
	}
	
	/**
	 * Removes an observer from the observers list
	 * @param observer the observer to remove
	 */
	public void removeObserver(EventObserver observer) {
		observers.remove(observer);
	}

	/**
	 * Send an event to the observers
	 * 
	 * @param event
	 *            the event to send
	 */
	public void sendEvent(GameEvent event) {
		for (EventObserver e : observers)
			e.eventFired(this, event);
	}
}
