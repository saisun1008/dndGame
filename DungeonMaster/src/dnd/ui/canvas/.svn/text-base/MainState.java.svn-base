package dnd.ui.canvas;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import dnd.game.Entity;
import dnd.game.Tile;
import dnd.game.World;
import dnd.game.entity.item.Item;
import dnd.game.entity.living.NPC;

public class MainState extends AbstractState {

	public MainState(World world) {
		super(world);
	}
	
	@Override
	public int getID() {
		return GameCanvas.States.Main.ordinal();
	}
	
	@Override
	public void update(GameContainer game, StateBasedGame state, int frame) throws SlickException {
		// finalize turn
		if (world.isStarted()) {
			world.getTurnManager().runTurn();
		}
	}
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		if (button != Input.MOUSE_LEFT_BUTTON) return;
		for (Entity e : world.getEntitiesAtLocation(mouseLocation)) {
			if (e instanceof NPC) {
				if (((NPC) e).isAlive()) {
					world.getTurnManager().attackEntity((NPC)e);
				}
				else { // take items if dead
					world.openInventory(world.getTurnManager().activeEntity(), e);
				}
				break;
			}
			else if (e instanceof Item) {
				world.useItem(world.getTurnManager().activeEntity(), (Item)e);
			}
		}
		
		Tile cell = world.getLevel().getCell(mouseLocation);
		if (cell == Tile.DoorClosed || cell == Tile.DoorOpen) {
			world.getTurnManager().toggleDoor(mouseLocation);
		}
	}
	
	@Override
	public void keyPressed(int key, char c) {
		switch(key) {
			case Input.KEY_NUMPAD1:
				movePlayer(-1, 1); break;
			case Input.KEY_NUMPAD2:
				movePlayer(0, 1); break;
			case Input.KEY_NUMPAD3:
				movePlayer(1, 1); break;
			case Input.KEY_NUMPAD4:
				movePlayer(-1, 0); break;
			case Input.KEY_NUMPAD5:
				movePlayer(0, 0); break;
			case Input.KEY_NUMPAD6:
				movePlayer(1, 0); break;
			case Input.KEY_NUMPAD7:
				movePlayer(-1, -1); break;
			case Input.KEY_NUMPAD8:
				movePlayer(0, -1); break;
			case Input.KEY_NUMPAD9:
				movePlayer(1, -1); break;
			case Input.KEY_LEFT:
				cameraX--; break;
			case Input.KEY_RIGHT:
				cameraX++; break;
			case Input.KEY_UP:
				cameraY--; break;
			case Input.KEY_DOWN:
				cameraY++; break;
		}
	}
}