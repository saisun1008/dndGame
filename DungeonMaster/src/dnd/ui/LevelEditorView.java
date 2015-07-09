package dnd.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.newdawn.slick.GameCanvasContainer;
import org.newdawn.slick.SlickException;

import dnd.game.Location;
import dnd.game.World;
import dnd.ui.canvas.EditorState;
import dnd.ui.canvas.GameCanvas;

/**
 * LevelEditorView is the root view of the map editor UI
 *
 */
public class LevelEditorView extends JPanel {
	
	private static final long serialVersionUID = -309086605568838673L;
	private LogView logView;
	private GameCanvasContainer mapView;
	private LevelEditorToolsView toolsView;
	private JButton cancelButton;
	private JButton saveButton;

	private World world;

	/**
	 * Constructor - Build and show the UI
	 * @param world
	 */
	public LevelEditorView(World world) {
		this.world = world;
		initDisplay();
	}
	
	/**
	 * Add listener for special app events
	 * @param changeListener A PropertyChangeListener object
	 */
	public void addEventListener(PropertyChangeListener changeListener) {
		addPropertyChangeListener(changeListener);
		toolsView.addPropertyChangeListener(changeListener);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LevelEditorView.this.firePropertyChange("state", "editing", "cancel");
			}
		});
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LevelEditorView.this.firePropertyChange("state", "editing", "save");
			}
		});
	}

	/**
	 * Initialize and display all components/event handlers of the Map Editor UI
	 */
	private void initDisplay() {
		cancelButton = new JButton("Cancel");
		saveButton = new JButton("Save");
		
		JPanel buttonView = new JPanel();
		buttonView.setLayout(new FlowLayout(FlowLayout.RIGHT, 4, 4));
		buttonView.add(cancelButton);
		buttonView.add(saveButton);
		

		// Set up the log immediately so it can be accessed from other views
		logView = new LogView();
		logView.setPreferredSize(new Dimension(600, 160));

		setPreferredSize(new Dimension(1280, 800));

		// Set up the map and canvas
		ScrollPane mapScrollPane = new ScrollPane();
		try {
			GameCanvas canvas = new GameCanvas(world);
			canvas.addState(new EditorState(world, this));
			canvas.enterState(2);
			mapView = new GameCanvasContainer(canvas);
			int mapWidth = GameCanvas.spriteSize * world.getLevel().getWidth();
			int mapHeight = GameCanvas.spriteSize * world.getLevel().getHeight();
			mapView.setPreferredSize(new Dimension(mapWidth, mapHeight));
			mapView.start(); // Required by Slick
			mapView.requestFocusInWindow();
			
			// Place the map canvas in a scroll pane
			mapScrollPane.add(mapView);
			mapScrollPane.setWheelScrollingEnabled(true);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		// Set up the tools UI panel
		toolsView = new LevelEditorToolsView();
		toolsView.setPreferredSize(new Dimension(200, 200));
		
		// Layout code
		GroupLayout viewLayout = new GroupLayout(this);
		setLayout(viewLayout);

		viewLayout.setHorizontalGroup(viewLayout.createSequentialGroup()
			.addGap(3)
			.addGroup(viewLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(viewLayout.createSequentialGroup()
					.addComponent(mapScrollPane, 1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
					.addGap(5)
					.addComponent(toolsView, 200, 200, 200)
					.addGap(3)
				)
				.addComponent(logView, 50, 600, 2000)
				.addComponent(buttonView)
			)
		);

		viewLayout.setVerticalGroup(viewLayout.createSequentialGroup()
			.addGap(3)
			.addGroup(viewLayout.createParallelGroup()
				.addComponent(mapScrollPane, 1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
				.addComponent(toolsView, 0, GroupLayout.PREFERRED_SIZE, 800)
			)
			.addGap(3).addComponent(logView, 80, 150, 150)
			.addComponent(buttonView, 32, 32, 32)
		);
	}

	/**
	 * Display a message in the log view
	 * @param msg The message to display
	 */
	public void log(String msg) {
		logView.append(msg);
	}

	/**
	 * The mouse was clicked at a given cell in the canvas
	 * @param location the location of the clicked cell
	 */
	public void clicked(Location location) {
		firePropertyChange("paint", null, location);
	}
}