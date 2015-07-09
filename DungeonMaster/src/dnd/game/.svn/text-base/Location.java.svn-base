package dnd.game;

/**
 * Location defines an x, y pair which represent a location on the map grid.
 * 
 */
public class Location implements Comparable<Location> {

	private int x;
	private int y;
	
	/**
	 * Constructor
	 * @param x The x coordinate of the location
	 * @param y The y coordinate of the location
	 */
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return The x coordinate of this location
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return The y coordinate of this location
	 */
	public int getY() {
		return y;
	}

	/**
	 * Add two locations objects together
	 * @param o The location to add to the location property of this object
	 * @return The sum of the x and y coordinates, as a new location object
	 */
	public Location add(Location o) {
		return new Location(x + o.x, y + o.y);
	}
	
	/**
	 * Subtract two locations
	 * @param o the location to substract
	 * @return a new Location object holding the difference of the two locations
	 */
	public Location subtract(Location o) {
		return new Location(x - o.x, y - o.y);
	}
	
	/**
	 * Returns the distance between this location and another
	 * @param o the other location
	 * @return distance between locations
	 */
	public int distanceTo(Location o) {
		return new Trace(this, o).distance();
	}

	// Object overrides
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	@Override
	public int compareTo(Location o) {
		if (getY() == o.getY() && getX() == o.getX()) return 0;
		else if (getY() > o.getY() || (getY() == o.getY() && getX() > o.getX())) {
			return -1;
		}
		else {
			return 1;
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Location) {
			return compareTo((Location)other) == 0;
		}
		return false;
	}
}
