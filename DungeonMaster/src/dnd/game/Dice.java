package dnd.game;

import java.util.Random;

/**
 * A Random number generator class, customized for dice rolling in the Dungeons
 * and Dragons environment.
 * 
 */
public class Dice {
	protected Random generator;

	public Dice(int seed) {
		// Set the seed in the constructor - that is, only once per instantiation.
		this.generator = new Random(seed);
	}

	public Dice() {
		this.generator = new Random(System.currentTimeMillis());
	}

	/**
	 * Method for simulating dice rolls
	 * 
	 * @param numDice The number of times to roll the die
	 * @param dieSize The size of the die (4 sided, 6 sided, 20 sided, etc)
	 * @param modifier A value to add to the roll
	 * @return The result of the roll
	 */
	public int roll(int numDice, int dieSize, int modifier) {
		int roll = 0;
		for (int i = 0; i < numDice; i++) {
			roll += generator.nextInt(dieSize) + 1;
		}
		return roll + modifier;
	}

	/**
	 * Method for simulating dice rolls, but with no modifier
	 * 
	 * @param numDice The number of times to roll the die
	 * @param dieSize The size of the die (4 sided, 6 sided, 20 sided, etc)
	 * @return The result of the roll
	 */
	public int roll(int numDice, int dieSize) {
		return roll(numDice, dieSize, 0);
	}

	/**
	 * This method rolls dice for the sake of establishing a player ability like
	 * strength, intelligence. It uses the common 3.0-3.5 DnD rules of rolling
	 * four 6-sided dice and keeping the sum of the three highest rolls.
	 * 
	 * @return The value of the roll
	 */
	public int abilityRoll() {
		int sum = 0;
		int lowest = Integer.MAX_VALUE;

		// Add all rolls to sum but store the lowest
		for (int i = 0; i < 4; i++) {
			int roll = roll(1, 6);
			if (roll < lowest) {
				lowest = roll;
			}
			sum += roll;
		}
		return sum - lowest;
	}
}

