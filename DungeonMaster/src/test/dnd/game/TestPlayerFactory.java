package test.dnd.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dnd.game.Dice;
import dnd.game.entity.living.Goblin;
import dnd.game.entity.living.Orc;
import dnd.game.entity.living.PlayerFactory;
import dnd.game.entity.living.Troll;

public class TestPlayerFactory {
	@Test public void factoryCreatesNPCs() {
		assertTrue(PlayerFactory.getPlayer("npc tank", 1, null, new Dice(1)) instanceof Troll);
		assertTrue(PlayerFactory.getPlayer("npc tank", 1, null, new Dice(2)) instanceof Orc);
		assertTrue(PlayerFactory.getPlayer("npc tank", 1, null, new Dice(3)) instanceof Goblin);
	}
	
	@Test public void factoryShouldSetProperLevel() {
		assertEquals(5, PlayerFactory.getPlayer("tank", 5, null).getLevel());
	}
}
