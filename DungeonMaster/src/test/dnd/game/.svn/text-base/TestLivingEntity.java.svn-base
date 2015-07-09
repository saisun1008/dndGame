package test.dnd.game;

import static dnd.game.AbilityType.CHAR;
import static dnd.game.AbilityType.CONS;
import static dnd.game.AbilityType.DEX;
import static dnd.game.AbilityType.HP;
import static dnd.game.AbilityType.INT;
import static dnd.game.AbilityType.STR;
import static dnd.game.AbilityType.WIS;
import static dnd.game.DamageType.ARMOR;
import static dnd.game.DamageType.SHIELD;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import dnd.game.entity.item.Armor;
import dnd.game.entity.item.EquippableItem;
import dnd.game.entity.item.Shield;
import dnd.game.entity.living.Player;

public class TestLivingEntity {
	@Test public void abilityModifierAdjustmentsOnBaseline() {
		Player player = new Player();
		player.getRawAbilityScores().add(DEX, 10, STR, 11, CONS, 12, WIS, 0, CHAR, 2, INT, 100);
		assertEquals(0, player.abilityModifiers().delta(DEX));
		assertEquals(0, player.abilityModifiers().delta(STR));
		assertEquals(1, player.abilityModifiers().delta(CONS));
		assertEquals(-5, player.abilityModifiers().delta(WIS));
		assertEquals(-4, player.abilityModifiers().delta(CHAR));
		assertEquals(45, player.abilityModifiers().delta(INT));
	}
	
	@Test public void armorClassBasedOnEquippedItems() {
		EquippableItem item = new Armor(4);
		EquippableItem item2 = new Shield(1);
		Player player = new Player();
		player.getBaseAbilityModifiers().add(DEX, -4);
		assertEquals(0, player.damageModifiers().delta(ARMOR));
		player.getInventory().addItem(item);
		player.getInventory().addItem(item2);
		player.getInventory().equipItem(item);
		player.getInventory().equipItem(item2);
		assertEquals(4, player.damageModifiers().delta(ARMOR));
		assertEquals(1, player.damageModifiers().delta(SHIELD));
		assertEquals(11, player.armorClass()); // 10 + 4armor + 1shield - 4dex
	}
	
	@Test public void totalHPBasedOnStats() {
		Player player = new Player();
		player.getBaseAbilityModifiers().add(HP, 10);
		assertEquals(10, player.getTotalHP());
	}
	
	@Test public void resetHPResetsHP() {
		Player player = new Player();
		player.getBaseAbilityModifiers().add(HP, 10);
		player.setHP(3);
		player.resetHP();
		assertEquals(10, player.getHP());
	}
	
	@Test public void spawnResetsHP() {
		Player player = new Player();
		player.getBaseAbilityModifiers().add(HP, 10);
		player.setHP(3);
		player.spawn();
		assertEquals(10, player.getHP());
	}
}
