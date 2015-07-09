package dnd.ui.controller;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JOptionPane;

import dnd.game.Entity;
import dnd.game.Level;
import dnd.game.Location;
import dnd.game.Tile;
import dnd.game.World;
import dnd.game.entity.item.Item;
import dnd.game.entity.living.NPC;
import dnd.ui.LevelEditorView;
import dnd.ui.LevelSizeDialog;
import dnd.ui.TileResource;
import dnd.util.FileSaver;

/**
 * A view controller for the views related to the level/map editor. It implements UI functionality
 * related to the level editor
 * 
 * @see Wizard
 * 
 */
public class LevelEditorController extends ViewController<LevelEditorView> implements PropertyChangeListener {

	private Level level;
	private World world;
	private World tmpWorld;
	private TileResource selectedTile;
	private WizardController<?> wizard;
	private Location lastPaintedTile;
	
	/**
	 * Constructor
	 * 
	 * @param world A reference to a {@link World}
	 * @param wizard A reference to a {@link WizardController Wizard}
	 */
	public LevelEditorController(World world, WizardController<?> wizard) {
		this.world = world;
		this.wizard = wizard;
		this.level = selectMapDimensions();
		if (level != null) {
			tmpWorld = new World();
			tmpWorld.setLevel(level);
			this.view = new LevelEditorView(tmpWorld);
			this.view.addEventListener(this);
		}
	}
	
	/**
	 * Close the editor and proceed to the next step in the Wizard
	 */
	public void cancelEditor() {
		if (wizard != null) wizard.nextController();
	}
	
	/**
	 * Save the level to a persistent state and proceed to the next step in the Wizard
	 */
	public void saveLevel() {
		if (level.getSpawnLocation() == null) {
			JOptionPane.showMessageDialog(null, "The level needs a spawn point.");
			return;
		}
		if (FileSaver.saveLevel(level) != null) {
    		world.setLevel(level);
    		if (wizard != null) wizard.nextController();
		}
	}

	/**
	 * Paints the current brush to the map at the specified coordinates
	 * 
	 * @param location the coordinates of the map cell to set
	 */
	private void paintTile(Location location) {
		if (location.equals(lastPaintedTile)) return;
		Tile tile = selectedTile.getTile();
		Entity entity = selectedTile.getEntity();
		int x = location.getX();
		int y = location.getY();
		if (tile != null) level.setCell(x, y, tile);
		if (entity != null) {
			entity.setLocation(x, y);
			if (entity instanceof NPC) tmpWorld.getMonsters().add((NPC)entity);
			if (entity instanceof Item) tmpWorld.getItems().add((Item)entity);
			level.getInitialEntities().add(entity);
		}

		view.log("Painting " + selectedTile.getShortDescription() + " at (" + x + ", " + y + ")");
		
		// special handling for stairs up tile (sets spawn location)
		if (tile == Tile.StairsUp) {
			view.log("Setting spawn location at (" + x + ", " + y + ")");
			level.setSpawnLocation(new Location(x, y));
		}
		
		lastPaintedTile = location;
	}
	
	/**
	 * Set a Tile as the current paint brush
	 * 
	 * @param tileResource The tile to set as the current paint brush
	 */
	private void selectTile(TileResource tileResource) {
		lastPaintedTile = null;
		selectedTile = tileResource;
		view.log("Selected " + selectedTile.getShortDescription());
	}

	/**
	 * Create a new dialog which prompts the user for the number of rows and
	 * columns
	 * 
	 * @return a new level object with given dimensions
	 */
	private Level selectMapDimensions() {
		Dimension mapDims = new Dimension(0, 0);
		new LevelSizeDialog(mapDims);

		if (mapDims.width == 0 && mapDims.height == 0) {
			return null;
		}
		// If the dimensions entered are still out of bounds, throw an error
		else if (mapDims.height < LevelSizeDialog.MIN_MAP_ROWS
				|| mapDims.height > LevelSizeDialog.MAX_MAP_ROWS
				|| mapDims.width < LevelSizeDialog.MIN_MAP_COLUMNS
				|| mapDims.width > LevelSizeDialog.MAX_MAP_COLUMNS) {
			return null;
		}
		Level level = new Level(mapDims.width, mapDims.height);

		// fill level with floor tiles
		for (int x = 0; x < level.getWidth(); x++) {
			for (int y = 0; y < level.getHeight(); y++) {
				level.setCell(x, y, Tile.Floor);
			}
		}
		
		return level;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String name = evt.getPropertyName();
		if (name.equals("paint")) { // paint tile occurred
			paintTile((Location)evt.getNewValue());
		}
		else if (name.equals("state")) { // the state of the editor changed
			if (evt.getNewValue().equals("save")) { // save button clicked
				saveLevel();
			}
			else if (evt.getNewValue().equals("cancel")) { // cancel button clicked
				cancelEditor();
			}
		}
		else if (name.equals("selectTile")) { // tile selection occurred
			selectTile((TileResource)evt.getNewValue());
		}
	}
}
