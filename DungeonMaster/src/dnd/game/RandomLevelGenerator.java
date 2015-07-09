package dnd.game;


/**
 * A class used to generate random dungeon levels
 * 
 * @see Level
 *
 */
public class RandomLevelGenerator {

	private Dice dice;
	
	/**
	 * Constructor - Instantiate with a new seed
	 * 
	 * @param seed The seed for the random generator
	 */
	public RandomLevelGenerator(int seed) {
		this.dice = new Dice(seed);
	}
	
	/**
	 * Constructor - Instantiate without a specified feed
	 */
	public RandomLevelGenerator() {
		this.dice = new Dice();
	}
	
	/**
	 * Generate a new random level
	 * 
	 * @param width The number of columns in the new level
	 * @param height The number of rows in the new level
	 * @return A new Level object
	 */
	public Level randomLevel(int width, int height) {
		boolean createdSpawn = false;
		boolean createdStairs = false;
		Level level = new Level(width, height);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int roll = dice.roll(1, 5);
				if (roll == 5) {
					level.setCell(x, y, Tile.Rock);
				} else {
					if (!createdSpawn && (dice.roll(1, 20) == 1 || (x == width - 1 && y == height - 1))) {
						level.setCell(x, y, Tile.StairsUp);
						level.setSpawnLocation(new Location(x, y));
						createdSpawn = true;
					}
					else if (!createdStairs && (dice.roll(1, 20) == 1 || (x == width - 1 && y == height - 1))) {
						level.setCell(x, y, Tile.StairsDown);
						createdStairs = true;
					}
					else {
						level.setCell(x, y, Tile.Floor);
					}
				}
			}
		}
		return level;
	}
}
