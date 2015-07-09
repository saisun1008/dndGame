package dnd.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Trace class performs a trace on the world between two locations,
 * detecting and reporting all entities and tiles intersected by the trace.
 */
public class Trace {
	private Location start;
	private Location end;
	private List<Entity> entities;
	private List<Tile> cells;
	private List<Location> locations;
	
	/**
	 * Performs a trace between two locations in the world
	 * @param world
	 * @param start
	 * @param end
	 */
	public Trace(World world, Location start, Location end) {
		this.start = start;
		this.end = end;
		this.entities = new ArrayList<Entity>();
		this.cells = new ArrayList<Tile>();
		this.locations = new ArrayList<Location>();
		performTrace(world);
	}
	
	/**
	 * Performs a trace between two locations without the world object.
	 * If the world object is not supplied, cell/entity data will not be populated
	 * and only location distance will be calculated.
	 * 
	 * @param start starting location
	 * @param end ending location
	 */
	public Trace(Location start, Location end) {
		this(null, start, end);
	}

	/**
	 * @return the end point in the trace
	 */
	public Location getEnd() {
		return end;
	}

	/**
	 * @return the start point in the trace
	 */
	public Location getStart() {
		return start;
	}
	
	/**
	 * @return all entities intersected by the trace
	 */
	public List<Entity> getEntities() {
		return entities;
	}
	
	/**
	 * @return all cells intersected by the trace
	 */
	public List<Tile> getCells() {
		return cells;
	}
	
	/**
	 * @return all location points traversed in the trace
	 */
	public List<Location> getLocations() {
		return locations;
	}
	
	/**
	 * @return the effective distance between the two locations
	 */
	public int distance() {
		return locations.size() - 1;
	}
	
	/**
	 * Performs the trace on a world object between the {@link #start} and {@link #end}
	 * locations. If world is null, no entities or cells are recorded.
	 * 
	 * @param world the world to trace, or null
	 */
	private void performTrace(World world) {
		List<Entity> ents = new ArrayList<Entity>();
		if (world != null) {
			ents.addAll(world.getMonsters());
			ents.addAll(world.getItems());
		}
		for (Location current : new TraceLineIterator(start, end)) {
			locations.add(current);
			
			if (world != null) {
				Tile tile = world.getLevel().getCell(current);
				if (tile != null) cells.add(tile);
				entities.addAll(world.getEntitiesAtLocation(current));
			}
		}
	}
	
	/**
	 * A convenience iterator class to perform a straight line trace between two locations
	 * using a grid system. The straight line prioritizes diagonal moves and then X over Y.
	 */
	private class TraceLineIterator implements Iterable<Location>, Iterator<Location> {
		private Location current;
		private Location start;
		private Location end;
		
		public TraceLineIterator(Location start, Location end) {
			this.start = start;
			this.end = end;
		}
		
		@Override
		public Iterator<Location> iterator() {
			return this;
		}

		@Override
		public boolean hasNext() {
			if (current != null && current.equals(end)) {
				return false;
			}
			return true;
		}

		@Override
		public Location next() {
			if (current == null) {
				current = start;
			}
			else {
				int x = current.getX();
				int y = current.getY();
				if (x < end.getX()) x++;
				if (x > end.getX()) x--;
				if (y < end.getY()) y++;
				if (y > end.getY()) y--;
				current = new Location(x, y);
			}
			return current;
		}

		@Override
		public void remove() {
		}
	}
}
