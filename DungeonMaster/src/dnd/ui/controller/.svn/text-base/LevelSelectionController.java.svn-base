package dnd.ui.controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import dnd.game.World;
import dnd.game.mapper.LevelMapper;

/**
 * The controller for the level selection views, where the user is prompted
 * to either load a level from file, or create a new one by proceeding to
 * the level editor
 * 
 */
public class LevelSelectionController extends SelectionItemController {
	
	/**
	 * Constructor
	 * 
	 * @param wizard A reference to a {@link WizardController Wizard}
	 */
	public LevelSelectionController(WizardController<World> wizard) {
		super(wizard);
	}

	@Override
	protected String titleLabel() {
		return "Select a level";
	}

	@Override
	protected String newLabel() {
		return "New Level";
	}

	@Override
	protected String loadLabel() {
		return "Load Level";
	}
	
	@Override
	protected void newClicked(ActionEvent event) {
		wizard.showModalController(new LevelEditorController(wizard.getState(), wizard));
	}

	@Override
	protected void loadClicked(ActionEvent event) {
		selectFile("Dungeon Levels", "lvl");
	}
	
	@Override
	protected void fileLoaded(File file) throws FileNotFoundException {
		wizard.getState().setLevel(new LevelMapper().load(new FileInputStream(file)));
		wizard.nextController();
	}
}
