package dnd.ui.controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import dnd.game.World;
import dnd.game.mapper.WorldMapper;

/**
 * The controller for the game selection views, where the user is prompted to
 * either load a game from file, or start a new one
 * 
 */
public class GameSelectionController extends SelectionItemController {

	/**
	 * Constructor
	 * @param wizard A reference to a {@link WizardController Wizard}
	 */
	public GameSelectionController(WizardController<World> wizard) {
		super(wizard);
	}

	@Override
	protected String titleLabel() {
		return "Select a game";
	}

	@Override
	protected String newLabel() {
		return "New Game";
	}

	@Override
	protected String loadLabel() {
		return "Load Saved Game";
	}

	@Override
	protected void newClicked(ActionEvent event) {
		World world = new World();
		wizard.setState(world);
		wizard.nextController();
	}

	@Override
	protected void loadClicked(ActionEvent event) {
		selectFile("Saved Games", "world");
	}
	
	@Override
	protected void fileLoaded(File file) throws FileNotFoundException {
		wizard.setState(new WorldMapper().load(new FileInputStream(file)));
		wizard.nextController();
	}

}
