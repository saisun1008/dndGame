package test.dnd.game;

import static org.junit.Assert.*;
import dnd.game.BaseAttackBonus;

import org.junit.Test;

public class TestBaseAttackBonus {

	@Test
	public void testNumberOfAttacksLevel1to5() {
		BaseAttackBonus baseAttackBonus = new BaseAttackBonus();
		for (int i = 1; i < 6; i++)
			assertEquals(baseAttackBonus.getNumberOfAttacks(i), 1);
	}

	@Test
	public void testNumberOfAttacksLevel6to10() {
		BaseAttackBonus baseAttackBonus = new BaseAttackBonus();
		for (int i = 6; i < 11; i++)
			assertEquals(baseAttackBonus.getNumberOfAttacks(i), 2);
	}

	@Test
	public void testNumberOfAttacksLevel11to15() {
		BaseAttackBonus baseAttackBonus = new BaseAttackBonus();
		for (int i = 11; i < 16; i++)
			assertEquals(baseAttackBonus.getNumberOfAttacks(i), 3);
	}

	@Test
	public void testNumberOfAttacksLevel16to20() {
		BaseAttackBonus baseAttackBonus = new BaseAttackBonus();
		for (int i = 16; i < 21; i++)
			assertEquals(baseAttackBonus.getNumberOfAttacks(i), 4);
	}

	@Test
	public void testBonusForAttack1() {
		BaseAttackBonus baseAttackBonus = new BaseAttackBonus();
		for (int i = 1; i < 21; i++)
			assertEquals(baseAttackBonus.getBaseAttackBonus(i, 1), i);
	}

	@Test
	public void testBonusForAttack2() {
		BaseAttackBonus baseAttackBonus = new BaseAttackBonus();
		for (int i = 6; i < 21; i++)
			assertEquals(baseAttackBonus.getBaseAttackBonus(i, 2), i - 5);
	}

	@Test
	public void testBonusForAttack3() {
		BaseAttackBonus baseAttackBonus = new BaseAttackBonus();
		for (int i = 11; i < 21; i++)
			assertEquals(baseAttackBonus.getBaseAttackBonus(i, 3), i - 10);
	}

	@Test
	public void testBonusForAttack4() {
		BaseAttackBonus baseAttackBonus = new BaseAttackBonus();
		for (int i = 16; i < 21; i++)
			assertEquals(baseAttackBonus.getBaseAttackBonus(i, 4), i - 15);
	}

}
