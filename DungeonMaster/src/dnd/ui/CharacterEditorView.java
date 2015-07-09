package dnd.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dnd.game.AbilityType;
import dnd.game.entity.living.Player;

/**
 * CharacterEditorView is the UI container for all character editor UI components.
 * 
 */
public class CharacterEditorView extends JPanel {

	private static final long serialVersionUID = -1812884184434498356L;
	private JTextField nameField;
	private JComboBox jCBPlayerTypeSelect;
	private JSpinner levelSpinner;
	private Map<String, JLabel> mapAttributes = new HashMap<String, JLabel>();

	/**
	 * Constructor - Define UI elements and instantiate them. In other words,
	 * build and show the UI
	 */
	public CharacterEditorView() {
		JLabel nameLabel = new JLabel("Name");
		nameField = new JTextField(10);
		JLabel levelLabel = new JLabel("Level");
		levelSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1)); 
		JLabel classField = new JLabel("Fighter");
		JLabel raceField = new JLabel("Human");
		JLabel classLabel = new JLabel("Class");
		JLabel raceLabel = new JLabel("Race");
		JLabel typeLabel = new JLabel("Type");
		JPanel buttons = new JPanel();
		
		JButton cancelButton = new JButton("Cancel");
		JButton saveButton = new JButton("Save");
	    jCBPlayerTypeSelect = new JComboBox();

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CharacterEditorView.this.firePropertyChange("state", null, "cancel");
			}
		});
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CharacterEditorView.this.firePropertyChange("state", null, "save");
			}
		});

		jCBPlayerTypeSelect.setModel(new DefaultComboBoxModel(new String[] { "Bully", "Nimble", "Tank" }));
		jCBPlayerTypeSelect.setName("PlayerTypeSelect"); // NOI18N
		jCBPlayerTypeSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
            	CharacterEditorView.this.firePropertyChange("state", null, "build");
            }
        });
		
		levelSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				CharacterEditorView.this.firePropertyChange("state", null, "build");
			}
		});

		buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttons.add(cancelButton);
		buttons.add(Box.createHorizontalStrut(5));
		buttons.add(saveButton);

		// Layout code
		GroupLayout statsPanelLayout = new GroupLayout(this);
		setLayout(statsPanelLayout);

		ParallelGroup horiz = statsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
		SequentialGroup vert = statsPanelLayout.createSequentialGroup();
		
		horiz.addGroup(statsPanelLayout.createSequentialGroup()
				.addComponent(nameLabel, 80, 80, 80)
				.addComponent(nameField)
			)
			.addGroup(statsPanelLayout.createSequentialGroup()
				.addComponent(raceLabel, 80, 80, 80)
				.addComponent(raceField)
			)
			.addGroup(statsPanelLayout.createSequentialGroup()
				.addComponent(classLabel, 80, 80, 80)
				.addComponent(classField)
			)
			.addGroup(statsPanelLayout.createSequentialGroup()
				.addComponent(typeLabel, 80, 80, 80)
				.addComponent(jCBPlayerTypeSelect)
			)
			.addGroup(statsPanelLayout.createSequentialGroup()
				.addComponent(levelLabel, 80, 80, 80)
				.addComponent(levelSpinner)
			);
		vert.addGroup(statsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(nameLabel)
				.addComponent(nameField)
			)
			.addGroup(statsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(raceLabel)
				.addComponent(raceField)
			)
			.addGroup(statsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(classLabel)
				.addComponent(classField)
			)
			.addGroup(statsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(typeLabel)
				.addComponent(jCBPlayerTypeSelect)
			)
			.addGroup(statsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(levelLabel)
				.addComponent(levelSpinner)
			);
		
		AbilityType[] values = AbilityType.values();
		for (final AbilityType t : values) {
			JLabel label = new JLabel(t.getName());
			JLabel attrLabel = new JLabel("", JLabel.RIGHT);
			mapAttributes.put(t.getName(), attrLabel);
			horiz.addGroup(statsPanelLayout.createSequentialGroup()
					.addComponent(label, 80, 80, 80)
					.addComponent(attrLabel, 80, 80, 80));
			vert.addGroup(statsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(label)
				.addComponent(attrLabel));
		}
		
		horiz.addComponent(buttons);
		vert.addComponent(buttons);
		
		statsPanelLayout.setHorizontalGroup(statsPanelLayout.createSequentialGroup().addGroup(horiz));
		statsPanelLayout.setVerticalGroup(statsPanelLayout.createSequentialGroup().addGroup(vert));
		statsPanelLayout.setAutoCreateContainerGaps(true);
		statsPanelLayout.setAutoCreateGaps(true);
	}
	
	/**
	 * Get the name of the player (from the field)
	 * @return The player character's name
	 */
	public String getPlayerName() {
		return nameField.getText();
	}
	
	/**
	 * Get the selected player type (from the combo box)
	 * @return The player type
	 */
	public String getPlayerType() {
		return jCBPlayerTypeSelect.getSelectedItem().toString();
	}

	/**
	 * @return the desired player level
	 */
	public int getPlayerLevel() {
		return (Integer)levelSpinner.getValue();
	}
	
	/**
	 * Update player Statistics
	 * @return void
	 */
	public void updatePlayerStats(Player player) {
		AbilityType[] values = AbilityType.values();
		for (final AbilityType t : values) {
			//Update the spinners
			JLabel label = mapAttributes.get(t.getName());
			if (label != null) {
				int value = player.getRawAbilityScores().delta(t);
				label.setText("" + value);
			}
		}
		return;
	}
}
