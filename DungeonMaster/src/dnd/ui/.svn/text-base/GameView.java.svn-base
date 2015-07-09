package dnd.ui;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.newdawn.slick.GameCanvasContainer;
import org.newdawn.slick.SlickException;

import dnd.game.World;
import dnd.ui.canvas.GameCanvas;

/**
 * GameView is the UI container for all game UI components.
 * 
 * @see World
 * 
 */
public class GameView extends JPanel {

	private static final long serialVersionUID = -9039957379179307117L;
	private EntityView entityView;
	private EntityView altEntityView;
	private LogView logView;
	private GameCanvasContainer mapView;

	/**
	 * Constructor - Build and show the game UI
	 * @param world A reference to a World object
	 */
	public GameView(World world) {
		setPreferredSize(new Dimension(1280, 800));

		try {
			mapView = new GameCanvasContainer(new GameCanvas(world));
			mapView.start();
			mapView.setPreferredSize(new Dimension(600, 300));
			mapView.setMinimumSize(new Dimension(640, 480));
			
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					mapView.requestFocusInWindow();
				}
			});
		} catch (SlickException e) {
			e.printStackTrace();
		}

		// Set up the player info  HUD
		entityView = new EntityView(world.getPlayers().get(0));
		entityView.setMaximumSize(new Dimension(200, 900));

		// Set up the inventory HUD
		altEntityView = new EntityView(null);
		altEntityView.setMaximumSize(new Dimension(200, 900));

		// Setup the log HUD
		logView = new LogView();

		// Layout code
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel topRow = new JPanel();
		topRow.setLayout(new BoxLayout(topRow, BoxLayout.X_AXIS));
		topRow.add(mapView);
		topRow.add(Box.createHorizontalStrut(5));
		topRow.add(entityView);
		topRow.add(Box.createHorizontalStrut(5));
		topRow.add(altEntityView);
		add(topRow);
		add(logView);
	}

	/**
	 * @return an instance of the logView UI object
	 */
	public LogView getLogView() {
		return logView;
	}

	/**
	 * @return an instance of the EntityView UI object
	 */
	public EntityView getEntityView() {
		return entityView;
	}

	/**
	 * @return an instance of the EntityView UI object
	 */
	public EntityView getAltEntityView() {
		return altEntityView;
	}
}