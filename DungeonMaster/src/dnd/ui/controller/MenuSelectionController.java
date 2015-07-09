package dnd.ui.controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.lwjgl.opengl.Display;

import dnd.game.World;

/**
 * MenuSelectionController represents the first step in the Wizard, where the
 * user is prompted to establish a character, level and/or game. The root UI
 * frame is generated from this class.
 * 
 */
public class MenuSelectionController extends WizardController<World> {

	private JFrame window;

	/**
	 * Constructor
	 */
	public MenuSelectionController() {
		setState(new World());
		window = new JFrame("Dungeon Master");
		addController(new GameSelectionController(this));
		addController(new LevelSelectionController(this));
		addController(new CharacterSelectionController(this));
		initializeWindow();
	}
	
	/**
	 * Initialize the Initial UI frame
	 */
	private void initializeWindow() {
		
		// Add a special close event, required by lwjgl 
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Display.destroy();
				System.exit(0);
			}
		});
		showCurrentController();
	}

	@Override
	protected void showCurrentController() {
		if (currentController() instanceof LevelSelectionController && 
				getState().getLevel() != null) {
			nextController();
			return;
		}
		if (currentController() instanceof CharacterSelectionController && 
				getState().getPlayers().size() > 0) {
			nextController();
			return;
		}
		if (currentController().getView() == null) return;
		
		window.getContentPane().removeAll();
		window.getContentPane().add(currentController().getView());
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	@Override
	protected void finalControllerEnded() {
		GameViewController ctl = new GameViewController(getState());
		showModalController(ctl);
		window.setMenuBar(ctl.mainMenuBar());
	}
}
