package dnd.game;

import java.util.ArrayList;
import java.util.List;

/**
 * The level class implements the map for the map editor and game
 * 
 */
public class Level {

	private int width;
	private int height;
	private Location spawnLocation;
	private Tile[][] tiles;
	private List<Entity> initialEntities;

	/**
	 * Constructor
	 * @param width Number of column in the map
	 * @param height Number of rows in the map
	 */
	public Level(int width, int height) {
		this.initialEntities = new ArrayList<Entity>();
		this.width = width;
		this.height = height;
		this.tiles = new Tile[width][height];
	}

	/**
	 * @return The number of column in the map
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return The number of rows in the map
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Get a specific map cell Tile
	 * @param location The location of the Tile to retrieve
	 * @return The Tile object from the specified location
	 */
	public Tile getCell(Location location) {
		try {
			return tiles[location.getX()][location.getY()];
		}
		catch (NullPointerException e) {
			return null;
		}
		catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	/**
	 * Get a specific map cell Tile
	 * @param x The x coordinate of the map cell to get the Tile from
	 * @param y The y coordinate of the map cell to get the Tile from
	 * @return The Tile object from the specified location
	 */
	public Tile getCell(int x, int y) {
		return tiles[x][y];
	}

	/**
	 * Bind a specific Tile object to a given location in the map
	 * @param x The x coordinate of the map cell to set the Tile at
	 * @param y The y coordinate of the map cell to set the Tile at
	 * @param tile The Tile to set
	 */
	public void setCell(int x, int y, Tile tile) {
		tiles[x][y] = tile;
	}

	/**
	 * Test a map cell to see if it prevents or allows a specific Entity to set it's current location to it.
	 * @param world the world to test in
	 * @param entity The entity to test
	 * @param location The location the map to test
	 * @return True if the Entity can be set the given location
	 */
	public boolean acceptsEntity(World world, Entity entity, Location location) {
		if (!locationInBounds(location))
			return false;
		
		if (!getCell(location.getX(), location.getY()).acceptsEntity(world, entity))
			return false;
		
		return true;
	}

	/**
	 * Test if the given location is within the boundaries of the map
	 * @param location An arbitrary location
	 * @return True if the location is within the boundaries
	 */
	public boolean locationInBounds(Location location) {
		return location.getX() >= 0 && location.getX() < getWidth() &&
			location.getY() >= 0 && location.getY() < getHeight();
	}

	/**
	 * Get the current spawn location
	 * @return The current spawn location
	 */
	public Location getSpawnLocation() {
		return spawnLocation;
	}

	/**
	 * Set the current spawn location
	 * @param spawnLocation The current spawn location
	 */
	public void setSpawnLocation(Location spawnLocation) {
		this.spawnLocation = spawnLocation;
	}

	/**
	 * Get a list of initial entities (When the map loads or is generated)
	 * @return A list of entities
	 * @see Entity
	 */
	public List<Entity> getInitialEntities() {
		return initialEntities;
	}
}
