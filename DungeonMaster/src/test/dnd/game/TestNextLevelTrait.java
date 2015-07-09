package test.dnd.game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dnd.game.AbilityType;
import dnd.game.Level;
import dnd.game.World;
import dnd.game.entity.living.Player;
import dnd.game.entity.living.Troll;
import dnd.game.trait.NextLevelTrait;

public class TestNextLevelTrait {
	private class MockWorld extends World {
		public boolean nextLevel = false;
		
		@Override public void nextLevel(Level level) {
			nextLevel = true;
		}
	}
	
	@Test public void touchShouldSendNextLevelOnPlayerTouch() {
		MockWorld world = new MockWorld();
		world.setLevel(new Level(5, 5));
		NextLevelTrait trait = new NextLevelTrait();
		trait.touch(world, new Player());
		assertTrue(world.nextLevel);
	}
	
	@Test public void onlyAllowsNextLevelIfMonstersAreDead() {
		MockWorld world = new MockWorld();
		Troll troll = new Troll();
		troll.getBaseAbilityModifiers().add(AbilityType.HP, 10);
		troll.resetHP();
		world.getMonsters().add(troll);
		world.setLevel(new Level(5, 5));
		NextLevelTrait trait = new NextLevelTrait();
		assertFalse(trait.acceptsEntity(world, new Player()));
	}
	
	@Test public void onlyPlayersCanUseTrait() {
		Troll troll = new Troll();
		MockWorld world = new MockWorld();
		NextLevelTrait trait = new NextLevelTrait();
		assertFalse(trait.acceptsEntity(world, troll));
	}
}
