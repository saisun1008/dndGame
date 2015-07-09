package test.dnd.game;

import static dnd.game.AbilityType.CHAR;
import static dnd.game.AbilityType.CONS;
import static dnd.game.AbilityType.DEX;
import static dnd.game.AbilityType.HP;
import static dnd.game.AbilityType.INT;
import static dnd.game.AbilityType.STR;
import static dnd.game.AbilityType.WIS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dnd.game.AbilityType;
import dnd.game.Dice;
import dnd.game.entity.living.BullyBuilder;
import dnd.game.entity.living.LivingEntity;
import dnd.game.entity.living.NimbleBuilder;
import dnd.game.entity.living.Player;
import dnd.game.entity.living.PlayerBuilder;
import dnd.game.entity.living.TankBuilder;
import dnd.util.ModifierSet;

public class TestPlayerBuilder {
	@Test public void canCreateBully() {
		PlayerBuilder builder = new BullyBuilder();
		LivingEntity player = new Player();
		builder.setEntity(player);
		builder.buildAbilities();
		ModifierSet<AbilityType> playerBaseStats = player.abilityModifiers();

		//	Bully: 	STR, CONS, DEX, INT, CHAR, WIS
		assertTrue(playerBaseStats.delta(STR) >= playerBaseStats.delta(CONS));
		assertTrue(playerBaseStats.delta(CONS) >= playerBaseStats.delta(DEX));
		assertTrue(playerBaseStats.delta(DEX) >= playerBaseStats.delta(INT));
		assertTrue(playerBaseStats.delta(INT) >= playerBaseStats.delta(CHAR));
		assertTrue(playerBaseStats.delta(CHAR) >= playerBaseStats.delta(WIS));
	}
	
	@Test public void canCreateNimble() {
		PlayerBuilder builder = new NimbleBuilder();
		LivingEntity player = new Player();
		builder.setEntity(player);
		builder.buildAbilities();
		ModifierSet<AbilityType> playerBaseStats = player.abilityModifiers();

		//	Nimble: DEX, CONS, STR, INT, CHAR, WIS
		assertTrue(playerBaseStats.delta(DEX) >= playerBaseStats.delta(CONS));
		assertTrue(playerBaseStats.delta(CONS) >= playerBaseStats.delta(STR));
		assertTrue(playerBaseStats.delta(STR) >= playerBaseStats.delta(INT));
		assertTrue(playerBaseStats.delta(INT) >= playerBaseStats.delta(CHAR));
		assertTrue(playerBaseStats.delta(CHAR) >= playerBaseStats.delta(WIS));
	}
	
	@Test public void canCreateTank() {
		PlayerBuilder builder = new TankBuilder();
		LivingEntity player = new Player();
		builder.setEntity(player);
		builder.buildAbilities();
		ModifierSet<AbilityType> playerBaseStats = player.abilityModifiers();

		//	Tank: 	CONS, DEX, STR, INT, CHAR, WIS
		assertTrue(playerBaseStats.delta(CONS) >= playerBaseStats.delta(DEX));
		assertTrue(playerBaseStats.delta(DEX) >= playerBaseStats.delta(STR));
		assertTrue(playerBaseStats.delta(STR) >= playerBaseStats.delta(INT));
		assertTrue(playerBaseStats.delta(INT) >= playerBaseStats.delta(CHAR));
		assertTrue(playerBaseStats.delta(CHAR) >= playerBaseStats.delta(WIS));
	}
	
	@Test public void willSetProperHP() {
		PlayerBuilder builder = new TankBuilder();
		LivingEntity player = new Player();
		builder.setDice(new Dice(1));
		builder.setEntity(player);
		builder.buildAbilities();
		builder.buildHP();
		assertEquals(7, player.getRawAbilityScores().delta(HP));
		assertEquals(14, player.getRawAbilityScores().delta(CONS));
	}

	@Test public void willSetProperHPForLevel() {
		PlayerBuilder builder = new TankBuilder();
		LivingEntity player = new Player();
		builder.setDice(new Dice(1));
		builder.setEntity(player);
		builder.setLevel(3);
		builder.buildAbilities();
		builder.buildLevel();
		assertEquals(30, player.getRawAbilityScores().delta(HP));
		assertEquals(14, player.getRawAbilityScores().delta(CONS));
		assertEquals(13, player.getRawAbilityScores().delta(DEX));
		assertEquals(30, player.getHP());
	}
}
