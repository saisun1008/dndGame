package dnd.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 * This dialog should be called before creating a new map. It prompts the user
 * for the number of columns and rows
 *
 */
public class LevelSizeDialog extends JDialog {

	public static final int DEFAULT_MAP_COLUMNS = 10;
	public static final int DEFAULT_MAP_ROWS = 10;
	public static final int MAX_MAP_COLUMNS = 100;
	public static final int MAX_MAP_ROWS = 100;
	public static final int MIN_MAP_COLUMNS = 10;
	public static final int MIN_MAP_ROWS = 10;

	private JSpinner rowsSpinner;
	private JSpinner columnsSpinner;
	private JPanel dialogPanel = null;
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor - Build and show the dialog
	 * @param mapSize Placeholder for user specified dimensions
	 */
	public LevelSizeDialog(final Dimension mapSize) {
		super((JFrame)null, true);

		/**
		 * Default to 0 rows and columns, in case the user closes the dialog
		 * without making a proper selection
		 */
		mapSize.setSize(0, 0);

		/** The custom dialog panel */
		dialogPanel = new JPanel();
		getContentPane().add(dialogPanel);

		/** The labels */
		JLabel fieldsetLegend = new JLabel("Choose map dimensions:");
		JLabel rowsLabel = new JLabel("Number of rows (" + MIN_MAP_ROWS + "-"
				+ MAX_MAP_ROWS + ")");
		JLabel columnsLabel = new JLabel("Number of columns ("
				+ MIN_MAP_COLUMNS + "-" + MAX_MAP_COLUMNS + ")");

		/** The spinners */
		SpinnerModel rowsSpinnerModel = new SpinnerNumberModel(
				DEFAULT_MAP_ROWS, MIN_MAP_ROWS, MAX_MAP_ROWS, 1);
		SpinnerModel columnsSpinnerModel = new SpinnerNumberModel(
				DEFAULT_MAP_COLUMNS, MIN_MAP_COLUMNS, MAX_MAP_COLUMNS, 1);
		rowsSpinner = new JSpinner(rowsSpinnerModel);
		columnsSpinner = new JSpinner(columnsSpinnerModel);

		/** The buttons */
		JButton btnOK = new JButton("Ok");
		JButton btnCancel = new JButton("Cancel");

		/** Button action listeners */
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mapSize.setSize((Integer) columnsSpinner.getValue(),
						(Integer) rowsSpinner.getValue());
				dispose();
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dispose();
			}
		});

		/** Layout code */
		GroupLayout dialogLayout = new GroupLayout(dialogPanel);
		dialogPanel.setLayout(dialogLayout);

		dialogLayout
				.setHorizontalGroup(dialogLayout
						.createSequentialGroup()

						.addGap(20)
						.addGroup(
								dialogLayout
										.createParallelGroup(
												GroupLayout.Alignment.TRAILING)
										.addComponent(fieldsetLegend)
										.addComponent(rowsLabel)
										.addComponent(columnsLabel)
										.addComponent(btnOK))
						.addGap(8)
						.addGroup(
								dialogLayout
										.createParallelGroup(
												GroupLayout.Alignment.TRAILING)
										.addComponent(columnsSpinner)
										.addComponent(rowsSpinner)
										.addComponent(btnCancel)).addGap(20));

		dialogLayout.setVerticalGroup(dialogLayout
				.createSequentialGroup()
				.addGap(20)
				.addGroup(
						dialogLayout.createParallelGroup(
								GroupLayout.Alignment.BASELINE).addComponent(
								fieldsetLegend))
				.addGap(10)
				.addGroup(
						dialogLayout
								.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
								.addComponent(rowsLabel)
								.addComponent(columnsSpinner))
				.addGap(5)
				.addGroup(
						dialogLayout
								.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
								.addComponent(columnsLabel)
								.addComponent(rowsSpinner))
				.addGap(15)
				.addGroup(
						dialogLayout
								.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
								.addComponent(btnOK).addComponent(btnCancel))
				.addGap(20));

		/** Handle window closing (X) */
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				dispose();
			}
		});

		/** Display the dialog */
		setTitle("Dungeon Master - Choose Map Dimensions");
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
