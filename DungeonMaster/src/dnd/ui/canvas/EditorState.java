package dnd.ui.canvas;

import org.newdawn.slick.Input;

import dnd.game.World;
import dnd.ui.LevelEditorView;

/**
 * This class implements the Slick Game interface. It manages painting to the
 * canvas and UI events on it.
 * 
 */
public class EditorState extends AbstractState {

	/**
	 * This is ugly coupling to the view. Ideally we would end
	 * a propertyChange event without knowing the endpoint, but
	 * unfortunately Java does not allow this (we would have to send
	 * a MouseEvent, but this makes handling much more complex)
	 */
	private LevelEditorView view;

	/**
	 * Constructor - Create a map with a specified number of columns and rows
	 * @param world A reference to the {@link World}
	 * @param view A reference to the root View
	 */
	public EditorState(World world, LevelEditorView view) {
		super(world);
		this.view = view;
	}
	
	@Override
	public int getID() {
		return GameCanvas.States.Editor.ordinal();
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		if (button != Input.MOUSE_LEFT_BUTTON) return;
		if (world.getLevel().getCell(mouseLocation) != null) {
			view.clicked(mouseLocation);
		}
	}
	
	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		super.mouseDragged(oldx, oldy, newx, newy);
		mousePressed(Input.MOUSE_LEFT_BUTTON, 0, 0);
	}
	
	@Override
	public void keyPressed(int key, char c) {
		switch(key) {
			case Input.KEY_LEFT:
				cameraX -= 10; break;
			case Input.KEY_RIGHT:
				cameraX += 10; break;
			case Input.KEY_UP:
				cameraY -= 10; break;
			case Input.KEY_DOWN:
				cameraY += 10; break;
		}
	}
}
