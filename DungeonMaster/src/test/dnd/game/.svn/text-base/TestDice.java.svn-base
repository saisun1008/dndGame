package test.dnd.game;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import dnd.game.Dice;

public class TestDice {
	private class MockDice extends Dice {
		@SuppressWarnings("serial")
		public MockDice() {
			this.generator = new Random() {
				public int nextInt(int max) {
					return max - 1;
				}
			};
		}
	}
	
	private Dice dice = new MockDice();
	
	@Test public void roll1DiceReturnsValue() {
		assertEquals(10, dice.roll(1, 10));
	}
	
	@Test public void roll2DiceReturnsSum() {
		assertEquals(6, dice.roll(2, 3));
	}
	
	@Test public void rollAddsModifier() {
		assertEquals(11, dice.roll(2, 3, 5));
	}
	
	@Test public void statRollRolls4Dice() {
		assertEquals(18, dice.abilityRoll());
	}
}
