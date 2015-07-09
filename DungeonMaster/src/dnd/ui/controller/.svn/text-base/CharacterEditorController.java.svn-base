package dnd.ui.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import dnd.game.World;
import dnd.game.entity.living.Player;
import dnd.game.entity.living.PlayerFactory;
import dnd.ui.CharacterEditorView;
import dnd.util.FileSaver;

/**
 * The controller for the character editor views. It implements UI functionality
 * related to the character editor
 * 
 */
public class CharacterEditorController extends
		ViewController<CharacterEditorView> implements PropertyChangeListener {

	private Player player;
	private WizardController<World> wizard;

	/**
	 * Constructor
	 * 
	 * @param wizard
	 *            An instance of the Wizard controller
	 */
	public CharacterEditorController(WizardController<World> wizard) {
		this.wizard = wizard;
		this.player = new Player();
		this.view = new CharacterEditorView();
		view.addPropertyChangeListener(this);
		buildCharacter();
	}

	/**
	 * Process the saving of a character
	 */
	public void saveCharacter() {
		player.setName(view.getPlayerName());
		if (FileSaver.savePlayer(player) != null) {
			wizard.getState().getPlayers().add(player);
			wizard.nextController();
		}
	}

	/**
	 * Process the building of a character
	 */
	public void buildCharacter() {
		PlayerFactory.getPlayer(view.getPlayerType(), view.getPlayerLevel(), player);
		view.updatePlayerStats(player);
	}

	/**
	 * Go to the next step in the Wizard
	 */
	public void cancelEditor() {
		wizard.nextController();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String name = evt.getPropertyName();
		if (name.equals("state")) {
			if (evt.getNewValue().equals("save")) { // saving character
				saveCharacter();
			} else if (evt.getNewValue().equals("build")) { // saving character
				buildCharacter();
			} else { // cancel
				cancelEditor();
			}
		}
	}
}
