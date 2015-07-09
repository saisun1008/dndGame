package dnd.game;

import dnd.game.entity.item.BasicItem;
import dnd.game.entity.item.Chest;
import dnd.game.entity.item.ChestBuilder;
import dnd.game.entity.item.EquippableItem;
import dnd.game.entity.item.EquippableItemFactory;
import dnd.game.entity.item.HealthPotion;
import dnd.game.entity.item.Item;
import dnd.game.entity.item.Potion;
import dnd.game.entity.item.Weapon;
import dnd.game.entity.living.Goblin;
import dnd.game.entity.living.NPC;
import dnd.game.entity.living.Orc;
import dnd.game.entity.living.Player;
import dnd.game.entity.living.PlayerFactory;
import dnd.game.entity.living.Troll;
import dnd.game.event.SpawnPlayerEvent;
import dnd.util.Modifier;

/**
 * Builder class for the world, populating the world with the monsters and
 * items from the Level object and filling monster objects with level based attributes /
 * equipment.
 */
public class WorldBuilder {
	/** The world object to build */
	private World world;

	/**
	 * Builds the given world. world.getLevel() should be set.
	 * @param world the world to build
	 */
	public WorldBuilder(World world) {
		this.world = world;
	}
	
	/**
	 * Convenience method to build level, monsters and player.
	 */
	public void buildAll() {
		buildLevel();
		buildMonsters();
		buildChests();
		buildPlayer();
	}
	
	/**
	 * Builds the level by populating the world with initial entities
	 * from the level object.
	 */
	public void buildLevel() {
		// clear entities
		world.getMonsters().clear();
		world.getItems().clear();

		// set dungeon level to match player level
		int min = Integer.MAX_VALUE;
		for (Player player : world.getPlayers()) {
			if (player.getLevel() < min) min = player.getLevel();
		}
		world.setLevelNumber(min);

		// load entities from level
		for (Entity entity : world.getLevel().getInitialEntities()) {
			if (entity instanceof Player)
				world.getPlayers().add((Player) entity);
			else if (entity instanceof NPC)
				world.getMonsters().add((NPC) entity);
			else if (entity instanceof Item)
				world.getItems().add((Item) entity);
			
			// copy the location object (HACK, ideally we want to clone() the entire entity)
			// so that it does not modify the location in initialEntities
			Location loc = entity.getLocation();
			entity.setLocation(new Location(loc.getX(), loc.getY()));
		}
	}
	
	/**
	 * Populates the monsters in the world with equipment and stats appropriate
	 * for the given dungeon level.
	 */
	public void buildMonsters() {
		if (world.getMonsters().size() == 0) {
			addMonsters();
		}
		for (NPC monster : world.getMonsters()) {
			// set random stats for level
			PlayerFactory.getPlayer("npc " + randomBuild(), world.getLevelNumber(), monster, world.getDice());
			
			// register monster as observer for attack events
			world.addObserver(monster);
			
			// spawn monster
			monster.spawn();
		}
	}
	
	/**
	 * Populates all chests with random items
	 */
	public void buildChests() {
		boolean hasChests = false;
		for (Item item : world.getItems()) {
			if (item instanceof Chest) {
				hasChests = true;
				break;
			}
		}
		if (!hasChests) addChests();
		
		for (Item item : world.getItems()) {
			if (item instanceof Chest) {
				Chest chest = (Chest)item;
				ChestBuilder builder = new ChestBuilder(chest, world.getLevelNumber(), world.getDice());
				builder.buildAll();
			}
		}
	}
	
	/**
	 * Populates player inventory and sets the player at the spawn location
	 */
	public void buildPlayer() {
		// spawn players
		for (Player player : world.getPlayers()) {
			world.teleportEntity(player, world.getLevel().getSpawnLocation());
			
			Inventory inv = player.getInventory();
			if (inv.getItemSlots().size() == 0 || player.getLevel() == 1) {
				inv.clear();
				inv.addGold(400);
				inv.addItem(new HealthPotion(5));
				inv.addItem(new HealthPotion(10));
				inv.addItem(new HealthPotion(3));
				inv.addItem(new Potion(new Modifier<AbilityType>(AbilityType.DEX, 5)));
				inv.addItem(new Potion(new Modifier<AbilityType>(AbilityType.STR, 5)));
				inv.addItem(EquippableItemFactory.getArmor(world.getDice(), player.getLevel()));
				inv.addItem(EquippableItemFactory.getBelt(world.getDice(), player.getLevel()));
				inv.addItem(EquippableItemFactory.getBoots(world.getDice(), player.getLevel()));
				inv.addItem(EquippableItemFactory.getBracers(world.getDice(), player.getLevel()));
				inv.addItem(EquippableItemFactory.getHelmet(world.getDice(), player.getLevel()));
				inv.addItem(EquippableItemFactory.getRing(world.getDice(), player.getLevel()));
				inv.addItem(EquippableItemFactory.getShield(world.getDice(), player.getLevel()));
				inv.addItem(new Weapon("Bow", 0, 8));
				inv.addItem(new Weapon("Sword", 8, 0));
				inv.addItem(new BasicItem("A Mysterious Note"));
				
				// equip all equippables
				for (Item item : inv.getItemSlots()) {
					if (item instanceof EquippableItem) {
						inv.equipItem((EquippableItem)item);
					}
				}
			}

			player.spawn();
			world.sendEvent(new SpawnPlayerEvent(player));
		}
	}
	
	/**
	 * Returns a random build string for the {@link PlayerFactory}.
	 * @return a random build string for the {@link PlayerFactory}.
	 */
	private String randomBuild() {
		switch (world.getDice().roll(1, 3)) {
			case 1: return "tank";
			case 2: return "nimble";
			case 3: return "bully";
		}
		return null;
	}
	
	/**
	 * Returns a random build monster (Non Player Character)
	 * @return a random build monster NPC object: Orc, Troll or Goblin.
	 */
	private NPC randomNPC() {
		switch (world.getDice().roll(1, 3)) {
			case 1: return new Orc();
			case 2: return new Troll();
			case 3: return new Goblin();
		}
		return null;
	}
	
	/**
	 * Adds monsters to the world if there are none
	 */
	private void addMonsters() {
		// add monsters proportional to dungeon size + level*0.2
		Level level = world.getLevel();
		int numMonsters = (level.getWidth() * level.getHeight()) / 50; 
		
		for (int i = 0; i < numMonsters; i++) {
			NPC npc = randomNPC();
			boolean npcAdded = false;
			for (int numAttempts = 0; !npcAdded && numAttempts < 3; numAttempts++) {
				Location random = randomLevelLocation();
				if (level.getCell(random) == Tile.Floor) {
					if (world.getEntitiesAtLocation(random).isEmpty()) {
						// empty cell, random chance.
						npc.setLocation(random);
						world.getMonsters().add(npc);
						npcAdded = true;
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Adds chests to the level. The number of chests added is based on the level's size.
	 * Chests are randomly located on the playing surface
	 */
	private void addChests() {
		// add chests proportional to dungeon size + level*0.2
		Level level = world.getLevel();
		int numChests = (level.getWidth() * level.getHeight()) / 50; 
		
		for (int i = 0; i < numChests; i++) {
			Chest chest = new Chest();
			boolean chestAdded = false;
			for (int numAttempts = 0; !chestAdded && numAttempts < 3; numAttempts++) {
				Location random = randomLevelLocation();
				if (level.getCell(random) == Tile.Floor) {
					if (world.getEntitiesAtLocation(random).isEmpty()) {
						// empty cell, random chance.
						chest.setLocation(random);
						world.getItems().add(chest);
						chestAdded = true;
						break;
					}
				}
			}
		}
	}
	
	/**
	 * @return a random location in the level
	 */
	private Location randomLevelLocation() {
		Level level = world.getLevel();
		Dice dice = world.getDice();
		int x = dice.roll(1, level.getWidth()) - 1;
		int y = dice.roll(1, level.getHeight()) - 1;
		return new Location(x, y);
	}
}
