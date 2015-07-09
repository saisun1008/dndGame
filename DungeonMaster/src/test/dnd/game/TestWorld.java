package test.dnd.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import dnd.game.AbilityType;
import dnd.game.Dice;
import dnd.game.EquipmentSlot;
import dnd.game.Level;
import dnd.game.Location;
import dnd.game.PlayerClass;
import dnd.game.RandomLevelGenerator;
import dnd.game.Tile;
import dnd.game.World;
import dnd.game.entity.item.BasicItem;
import dnd.game.entity.item.EquippableItem;
import dnd.game.entity.item.EquippableItemFactory;
import dnd.game.entity.living.NPC;
import dnd.game.entity.living.Player;
import dnd.game.event.ActivateEntityEvent;
import dnd.game.event.EquipEvent;
import dnd.game.event.EventObserver;
import dnd.game.event.GameEvent;
import dnd.game.event.MovementEvent;
import dnd.game.event.NextTurnEvent;

public class TestWorld {
	private World world;
	private BasicItem basicItem1 = new BasicItem("Foo");
	private MockMonster mockMonster = new MockMonster();
	private MockPlayer player = new MockPlayer();
	private EventHandler gameHandler;
	
	private class EventHandler implements EventObserver {
		public GameEvent lastEvent;
		
		@Override
		public void eventFired(World world, GameEvent event) {
			lastEvent = event;
		}
	}
	
	private class MockMonster extends NPC {
		public boolean spawned = false;
		public void spawn() { super.spawn(); spawned = true; }
	}
	
	private class MockPlayer extends Player {
		public boolean spawned = false;
		public void spawn() { super.spawn(); spawned = true; }
	}
	
	@Before public void setup() {
		this.world = new World();
		this.player.getBaseAbilityModifiers().add(AbilityType.HP, 10);
		this.player.setPlayerClass(PlayerClass.FIGHTER);
		this.gameHandler = new EventHandler();
		world.setLevel(new RandomLevelGenerator().randomLevel(10, 10));
		world.addObserver(gameHandler);
		world.getPlayers().add(player);
		world.getLevel().getInitialEntities().add(basicItem1);
		world.getLevel().getInitialEntities().add(mockMonster);
		world.setDice(new Dice(222));
	}
	
	@Test public void canStartEmptyWorld() {
		new World().start();
	}
	
	@Test public void startCopiesInitialEntitiesFromLevel() {
		world.start();
		assertTrue(world.getItems().contains(basicItem1));
		assertTrue(world.getMonsters().contains(mockMonster));
	}
	
	@Test public void startSpawnsMonsters() {
		world.start();
		assertTrue(mockMonster.spawned);
	}
	
	@Test public void startSpawnsPlayers() {
		world.start();
		assertTrue(player.spawned);
	}
	
	@Test public void spawnEmitsActivateEvent() {
		world.start();
		assertTrue(gameHandler.lastEvent instanceof ActivateEntityEvent);
		ActivateEntityEvent e = (ActivateEntityEvent)gameHandler.lastEvent;
		assertNotNull(e.getActiveEntity());
	}
	
	@Test public void playerCanMoveOnValidSquare() {
		world.setLevel(new Level(2, 2));
		world.getLevel().setSpawnLocation(new Location(0, 0));
		world.getLevel().setCell(0, 0, Tile.Floor);
		world.getLevel().setCell(1, 1, Tile.Floor);
		world.start();
		assertTrue(world.moveEntity(world.getPlayers().get(0), new Location(1, 1)));
		assertTrue(gameHandler.lastEvent instanceof MovementEvent);
		MovementEvent e = (MovementEvent)gameHandler.lastEvent;
		assertEquals(e.getDelta(), new Location(1, 1));
	}
	
	@Test public void playerCannotMoveOnWallTile() {
		world.setLevel(new Level(2, 1));
		world.getLevel().setSpawnLocation(new Location(0, 0));
		world.getLevel().setCell(0, 0, Tile.Floor);
		world.getLevel().setCell(1, 0, Tile.WallV);
		world.start();
		gameHandler.lastEvent = null;
		assertFalse(world.moveEntity(world.getPlayers().get(0), new Location(1, 0)));
		assertNull(gameHandler.lastEvent);
	}
	
	@Test public void teleportMovesPlayerWithoutEvent() {
		world.setLevel(new Level(2, 2));
		world.getLevel().setSpawnLocation(new Location(0, 0));
		world.getLevel().setCell(0, 0, Tile.Floor);
		world.getLevel().setCell(1, 1, Tile.Floor);
		world.start();
		gameHandler.lastEvent = null;
		assertTrue(world.teleportEntity(world.getPlayers().get(0), new Location(1, 1)));
		assertNull(gameHandler.lastEvent);
	}
	
	@Test public void nextLevelSwitchesLevelsAndIncrementsLevelCount() {
		Level level = new Level(1, 1);
		level.setCell(0, 0, Tile.Floor);
		level.setSpawnLocation(new Location(0, 0));
		world.nextLevel(level);
		assertEquals(2, world.getLevelNumber());
	}
	
	@Test public void equipItemEmitsEquipEvent() {
		EquippableItem item = EquippableItemFactory.getItem(world.getDice(), 1);
		player.getInventory().addItem(item);
		assertTrue(world.equipItem(player, item));
		assertTrue(gameHandler.lastEvent instanceof EquipEvent);
		EquipEvent e = (EquipEvent)gameHandler.lastEvent;
		assertEquals(player, e.getEntity());
		assertEquals(item, e.getItem());
		assertEquals(EquipmentSlot.BOOTS, e.getSlot());
		assertFalse(e.isUnequipped());
	}
	
	@Test public void invalidEquipDoesNotEmitEvent() {
		EquippableItem item = EquippableItemFactory.getItem(world.getDice(), 1);
		player.getInventory().addItem(item);
		assertTrue(world.equipItem(player, item));
		gameHandler.lastEvent = null;
		assertFalse(world.equipItem(player, item));
		assertNull(gameHandler.lastEvent);
	}
	
	@Test public void unequipSlotEmitsEquipEvent() {
		EquippableItem item = EquippableItemFactory.getItem(world.getDice(), 1);
		player.getInventory().addItem(item);
		assertTrue(world.equipItem(player, item));
		assertTrue(world.unequipSlot(player, EquipmentSlot.BOOTS));
		assertTrue(gameHandler.lastEvent instanceof EquipEvent);
		EquipEvent e = (EquipEvent)gameHandler.lastEvent;
		assertEquals(player, e.getEntity());
		assertEquals(item, e.getItem());
		assertEquals(EquipmentSlot.BOOTS, e.getSlot());
		assertTrue(e.isUnequipped());
	}
	
	@Test public void canOnlyRegisterObserverOnce() {
		final int[] fired = new int[]{0};
		EventObserver mockObserver = new EventObserver() {
			@Override
			public void eventFired(World world, GameEvent event) {
				fired[0]++;
			}
		};
		
		fired[0] = 0;
		world.addObserver(mockObserver);
		world.addObserver(mockObserver);
		world.sendEvent(new NextTurnEvent(1));
		assertEquals(1, fired[0]);
	}
}
