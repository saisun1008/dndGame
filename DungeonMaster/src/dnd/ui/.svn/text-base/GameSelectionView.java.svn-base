package dnd.ui;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

/**
 * An abstraction to the "new" and "load" buttons found throughout the Wizard.
 * This class is used to generate the buttons which are visually similar buttons
 * yet functionally different, hence the lack of event handlers 
 * 
 */
public class GameSelectionView extends JPanel {

	private static final long serialVersionUID = 3448806684363457859L;
	private JButton newButton;
	private JButton loadButton;

	/**
	 * Constructor - Build and show the buttons
	 * @param borderTitle The title of the panel
	 * @param newLabel The label for the "new" button
	 * @param loadLabel The label for the "load" button
	 */
	public GameSelectionView(String borderTitle, String newLabel, String loadLabel) {
		newButton = new JButton(newLabel);
		loadButton = new JButton(loadLabel);

		Border padding = BorderFactory.createEmptyBorder(7, 7, 7, 7);
		Border outer = new CompoundBorder(padding, BorderFactory.createTitledBorder(borderTitle));
		Border inner = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		setBorder(new CompoundBorder(outer, inner));
		setLayout(new GridLayout(4, 1, 0, 10));
		add(newButton);
		add(loadButton);
	}

	/**
	 * @return An instance of the "new" button
	 */
	public JButton getNewButton() {
		return newButton;
	}

	/**
	 * @return An instance of the "new" button
	 */
	public JButton getLoadButton() {
		return loadButton;
	}
}
