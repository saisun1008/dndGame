package dnd.ui.controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import dnd.game.World;
import dnd.game.mapper.PlayerMapper;

/**
 * The controller for the character selection views, where the user is prompted
 * to either load a character from file, or create a new one by proceeding to
 * the character editor
 * 
 */
public class CharacterSelectionController extends SelectionItemController {

	/**
	 * Constructor
	 * @param wizard A reference to a {@link WizardController Wizard}
	 */
	public CharacterSelectionController(WizardController<World> wizard) {
		super(wizard);
	}

	@Override
	protected String titleLabel() {
		return "Select a Character";
	}

	@Override
	protected String newLabel() {
		return "New Character";
	}

	@Override
	protected String loadLabel() {
		return "Load Character";
	}

	@Override
	protected void newClicked(ActionEvent event) {
		wizard.showModalController(new CharacterEditorController(wizard));
	}

	@Override
	protected void loadClicked(ActionEvent event) {
		selectFile("Saved Characters", "char");
	}
	
	@Override
	protected void fileLoaded(File file) throws FileNotFoundException {
		wizard.getState().getPlayers().add(new PlayerMapper().load(new FileInputStream(file)));
		wizard.nextController();
	}
}
