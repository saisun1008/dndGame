package dnd.ui.canvas;

import java.awt.Font;
import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.StateBasedGame;

import dnd.game.Entity;
import dnd.game.Tile;
import dnd.game.World;
import dnd.game.entity.item.Chest;
import dnd.game.entity.living.Goblin;
import dnd.game.entity.living.Orc;
import dnd.game.entity.living.Player;
import dnd.game.entity.living.Troll;

/**
 * This class implements the Slick Game interface. It manages painting to the
 * canvas and UI events on it.
 * 
 */
public class GameCanvas extends StateBasedGame {
	public enum States {
		Main, Inventory, Editor
	}
	
	// Sprite data
	public static final String spriteFilename = "images/absurd32.png";
	public static final int spriteSize = 32;
	
	private World world;

	// tile and entity map
	public static HashMap<Tile, int[]> tileMap;
	public static HashMap<Class<? extends Entity>, int[]> entityMap;
	
	// font
	public static UnicodeFont font;
	
	static {
		initializeEntityMap();
		initializeTileMap();
	}

	/**
	 * Constructor
	 * @param world A reference to the {@link World}
	 */
	public GameCanvas(World world) {
		super(null);
		this.world = world;
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new MainState(world));
	}

	/**
	 * Creates the mapping from Tile objects to their mapping in the sprite sheet.
	 */
	static void initializeTileMap() {
		if (tileMap != null) return;
		tileMap = new HashMap<Tile, int[]>();
		tileMap.put(Tile.Rock, new int[]{ 23, 20 });
		tileMap.put(Tile.Floor, new int[] { 8, 21 });
		tileMap.put(Tile.StairsDown, new int[] { 12, 21 });
		tileMap.put(Tile.StairsUp, new int[] { 11, 21 });
		tileMap.put(Tile.WallV, new int[] { 30, 20 });
		tileMap.put(Tile.WallH, new int[] { 31, 20 });
		tileMap.put(Tile.WallUC, new int[] { 32, 20 });
		tileMap.put(Tile.WallBC, new int[] { 34, 20 });
		tileMap.put(Tile.DoorOpen, new int[] { 3, 21 });
		tileMap.put(Tile.DoorClosed, new int[] { 4, 21 });
	}
	
	/**
	 * Creates the mapping from the Player classes to their mapping in the sprite sheet
	 * TODO there should be a "getSpriteForEntity" method since players don't necessarily
	 *      choose a sprite based on the Java class alone.
	 */
	static void initializeEntityMap() {
		if (entityMap != null) return;
		entityMap = new HashMap<Class<? extends Entity>, int[]>();
		entityMap.put(Player.class, new int[] {16, 8});
		entityMap.put(Goblin.class, new int[] { 31, 1 });
		entityMap.put(Troll.class, new int[] { 21, 5 });
		entityMap.put(Orc.class, new int[] { 33, 1 });
		entityMap.put(Chest.class, new int[] { 26, 14 });
	}
	
	@SuppressWarnings("unchecked")
	static void initializeFonts() {
		if (font != null) return;
		font = new UnicodeFont(Font.decode("default-bold-14"));
		font.getEffects().add(new ColorEffect(java.awt.Color.white));
		font.addAsciiGlyphs();
		try {
			font.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	static void initialize() {
		initializeTileMap();
		initializeEntityMap();
		initializeFonts();
	}
}
