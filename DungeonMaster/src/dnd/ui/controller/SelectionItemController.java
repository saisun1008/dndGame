package dnd.ui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import dnd.game.World;
import dnd.ui.GameSelectionView;

/**
 * An abstract controller for the UI steps in the Wizard where the user is
 * prompted to either load or create a new character, level or game
 * 
 */
public abstract class SelectionItemController extends
		ViewController<GameSelectionView> {

	protected WizardController<World> wizard;

	/**
	 * @param wizard A reference to a {@link WizardController}
	 */
	public SelectionItemController(WizardController<World> wizard) {
		this.wizard = wizard;
		this.view = new GameSelectionView(titleLabel(), newLabel(), loadLabel());
		addEventListeners();
	}
	
	/**
	 * @return A reference to a {@link WizardController}
	 */
	protected WizardController<World> getWizard() {
		return wizard;
	}
	
	/**
	 * @return The label of the selection item
	 */
	protected abstract String titleLabel();
	
	/**
	 * @return The label of the "new" button
	 */
	protected abstract String newLabel();
	
	/**
	 * @return The label of the "load" button
	 */
	protected abstract String loadLabel();

	/**
	 * A callback for the click event on a button labeled "new"
	 * 
	 * @param event An ActionEvent
	 */
	protected abstract void newClicked(ActionEvent event);
	
	/**
	 * A callback for the click event on a button labeled "load"
	 * 
	 * @param event An ActionEvent
	 */
	protected abstract void loadClicked(ActionEvent event);

	/**
	 * Add the event listeners to the "new" and "load" buttons
	 */
	private void addEventListeners() {
		view.getNewButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				newClicked(evt);
			}
		});
		view.getLoadButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				loadClicked(evt);
			}
		});
	}
	
	/**
	 * A dialog for selecting a file (to load)
	 * 
	 * @param label A name for the filter
	 * @param extension The extension to filter
	 */
	protected void selectFile(String label, String extension) {

		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + "/data");
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(label, extension);
	    chooser.setFileFilter(filter);
	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	    	try {
	    		fileLoaded(chooser.getSelectedFile());
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, 
						"Could not find file " + chooser.getSelectedFile().getAbsolutePath());
			}
	    }
	}

	protected void fileLoaded(File file) throws FileNotFoundException { }
}
