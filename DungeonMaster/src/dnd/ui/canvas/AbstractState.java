package dnd.ui.canvas;

import java.util.Collection;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import dnd.game.Entity;
import dnd.game.Location;
import dnd.game.Tile;
import dnd.game.World;
import dnd.game.entity.item.Item;
import dnd.game.entity.living.NPC;
import dnd.game.entity.living.Player;

public abstract class AbstractState extends BasicGameState {
	private StateBasedGame game;
	private boolean firstDraw = true;
	protected World world;
	protected Location mouseLocation = null;
	protected SpriteSheet sprites;
	protected int cameraX;
	protected int cameraY;

	public AbstractState(World world) {
		this.world = world;
	}

	@Override
	public void init(GameContainer g, StateBasedGame state) throws SlickException {
		this.game = state;
		g.setShowFPS(false);
		g.setAlwaysRender(true);
		g.setClearEachFrame(true);
		g.getInput().enableKeyRepeat();
		GameCanvas.initialize();
		sprites = new SpriteSheet(GameCanvas.spriteFilename, 
				GameCanvas.spriteSize, GameCanvas.spriteSize, 0);
	}

	@Override
	public void render(GameContainer game, StateBasedGame state, Graphics g) throws SlickException {
		if (firstDraw) { // HACK to fix Slick multiple canvas drawing issues 
			game.reinit();
			firstDraw = false;
		}
		
		drawCamera(game, g);
		drawWorld();
		drawMouseLocationInfo(game, g);
	}
	
	@Override
	public void update(GameContainer game, StateBasedGame state, int frame) throws SlickException {
	}
	
	protected StateBasedGame getGame() {
		return game;
	}

	protected void drawMouseLocationInfo(GameContainer game, Graphics g) {
		if (mouseLocation != null && world.getLevel().getCell(mouseLocation) != null) {
			// draw cell border around active cell (mouse cursor)
			g.setLineWidth(2);
			if (game.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				g.setColor(Color.red);
			}
			else {
				g.setColor(Color.blue);
			}
			g.drawRect(mouseLocation.getX() * GameCanvas.spriteSize, 
					   mouseLocation.getY() * GameCanvas.spriteSize, 
					   GameCanvas.spriteSize, GameCanvas.spriteSize);

			// draw debugging info
			g.resetTransform();
			g.setFont(GameCanvas.font);
			g.setColor(Color.white);
			g.drawString(cellInformation(), 10, 10);
		}
	}

	protected void drawWorld() {
		sprites.startUse();
		drawTiles();
		drawEntities(world.getItems());
		drawEntities(world.getPlayers());
		drawEntities(world.getMonsters());
		sprites.endUse();
	}

	protected void drawCamera(GameContainer game, Graphics g) {
		Entity entity = world.getPlayers().size() > 0 ? world.getPlayers().get(0) : null;
		if (entity instanceof Player) {
			cameraX = entity.getLocation().getX() * GameCanvas.spriteSize - game.getWidth() / 2;
			cameraY = entity.getLocation().getY() * GameCanvas.spriteSize - game.getHeight() / 2;
		}
		g.translate(-cameraX, -cameraY);
	}
	
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		mouseLocation = new Location((newx + cameraX) / GameCanvas.spriteSize, 
									 (newy + cameraY) / GameCanvas.spriteSize);
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		mouseMoved(oldx, oldy, newx, newy);
	}
	
	/**
	 * Draw the Tile map on the canvas
	 */
	protected void drawTiles() {
		for (int y = 0; y < world.getLevel().getHeight(); y++) {
			for (int x = 0; x < world.getLevel().getWidth(); x++) {
				Tile tile = world.getLevel().getCell(x, y);
				if (tile == null) continue;
				if (GameCanvas.tileMap.containsKey(tile)) {
					renderSprite(x, y, 0, 0, GameCanvas.tileMap.get(tile));
				}
			}
		}
	}
	
	/**
	 * Draw the level's entities on the canvas
	 * @param collection The collection of entities on the current level
	 */
	protected void drawEntities(Collection<? extends Entity> collection) {
		for (Entity e : collection) {
			if (GameCanvas.entityMap.containsKey(e.getClass())) {
				Location loc = e.getLocation();
				renderSprite(loc.getX(), loc.getY(), 0, 0, GameCanvas.entityMap.get(e.getClass()));
			}
		}
	}
	
	/**
	 * Render a sprite from the spritesheet at the given location
	 * @param x The x coordinate to start painting
	 * @param y The y coordinate to start painting
	 * @param offsetX The x-axis offset of the tile to print
	 * @param offsetY The y-axis offset of the tile to print
	 * @param index the "layer" to paint on
	 */
	private void renderSprite(int x, int y, int offsetX, int offsetY, int[] index) {
		sprites.renderInUse(x * GameCanvas.spriteSize + offsetX, y * GameCanvas.spriteSize + offsetY, index[0], index[1]);
	}

	/**
	 * Send a signal to move the player by given offset
	 * @param x The x-axis offset
	 * @param y The y-axis offset
	 */
	protected void movePlayer(int x, int y) {
		if (!world.isStarted()) return;
		if (world.getTurnManager().activeEntity() instanceof Player) {
			if (x == 0 && y == 0) world.getTurnManager().waitTurn();
			else world.getTurnManager().moveEntity(new Location(x, y));
		}
	}

	protected String cellInformation() {
		String info = "";
		Tile cell = world.getLevel().getCell(mouseLocation);
		info += "Tile: " + cell.getName() + "\n";
		List<Entity> entities = world.getEntitiesAtLocation(mouseLocation);
		for (Entity e : entities) {
			info += "Entity: " + e + "\n";
			info += e.formatModifiers();
			if (e instanceof NPC) {
				if (((NPC) e).isAlive()) {
					info += "Click Action: Attack\n";
				}
				else {
					info += "Click Action: Take Inventory\n";
				}
			}
			else if (e instanceof Item) {
				info += "Click Action: Use\n";
			}
		}
		if (cell == Tile.DoorClosed || cell == Tile.DoorOpen) {
			info += "Click Action: Toggle Open\n";
		}
		return info;
	}
}
