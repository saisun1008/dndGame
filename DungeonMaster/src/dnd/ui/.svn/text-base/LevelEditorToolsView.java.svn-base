package dnd.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import dnd.game.Tile;
import dnd.game.entity.item.Chest;
import dnd.game.entity.living.Goblin;
import dnd.game.entity.living.Orc;
import dnd.game.entity.living.Troll;
import dnd.ui.canvas.GameCanvas;

/**
 * The map editor tools view displays widgets for selecting which tile to draw
 * on the canvas
 *
 */
public class LevelEditorToolsView extends JPanel {

	private static final long serialVersionUID = 14637346246235367L;
	public static final int height = 44;
	public static final int width = 180;
	
	private List<TileButton> buttons;

	/**
	 * Constructor - Build and show this panel
	 */
	public LevelEditorToolsView() {
		this.buttons = new ArrayList<TileButton>();
		JPanel viewPortPanel = new JPanel();
		Map<String, List<TileResource>> resourceMap = loadImageResources();
		int i = 0;
		for (Entry<String, List<TileResource>> map : resourceMap.entrySet()) {
			JLabel label = new JLabel(map.getKey());
			label.setFont(Font.decode("default-bold-16"));
			viewPortPanel.add(label);
			label.setBounds(5, i++ * height, width, height);
			for (TileResource res : map.getValue()) {
				TileButton button = new TileButton(res);
				viewPortPanel.add(button);
				buttons.add(button);
				button.setBounds(5, i++ * height, width, height);
			}
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				buttons.get(0).select();
			}
		});
		viewPortPanel.setLayout(null);
		viewPortPanel.setPreferredSize(new Dimension(width, viewPortPanel.getComponentCount() * height));
		setLayout(new GridLayout(1, 1));
		add(new JScrollPane(viewPortPanel));
	}
	
	/**
	 * Initialize the image resources used in the <em>Swing</em> UI
	 */
	private Map<String, List<TileResource>> loadImageResources() {
		List<TileResource> tiles = new ArrayList<TileResource>();
		List<TileResource> entities = new ArrayList<TileResource>();
		Map<String, List<TileResource>> resourceMap = new HashMap<String, List<TileResource>>();
		resourceMap.put("Terrain Tiles", tiles);
		resourceMap.put("Entities", entities);

		// Initialize tile data
		BufferedImage spriteImg = null;
		try {
			spriteImg = ImageIO.read(new File(GameCanvas.spriteFilename));
		} catch (IOException ex) {
			System.err.println(ex);
			return resourceMap;
		}

		// Tile maps
		tiles.add(new TileResource(spriteImg, Tile.Floor, "Floor"));
		tiles.add(new TileResource(spriteImg, Tile.Rock, "Boulder"));
		tiles.add(new TileResource(spriteImg, Tile.StairsUp, "Stairs (Entrance)"));
		tiles.add(new TileResource(spriteImg, Tile.StairsDown, "Stairs (Exit)"));
		tiles.add(new TileResource(spriteImg, Tile.WallV, "Wall (vertical)"));
		tiles.add(new TileResource(spriteImg, Tile.WallH, "Wall (horizontal)"));
		tiles.add(new TileResource(spriteImg, Tile.WallUC, "Wall (upper corner)"));
		tiles.add(new TileResource(spriteImg, Tile.WallBC, "Wall (bottom corner)"));
		tiles.add(new TileResource(spriteImg, Tile.DoorClosed, "Closed Door"));
		tiles.add(new TileResource(spriteImg, Tile.DoorOpen, "Open Door"));

		// Produce monster/entity tile resources
		entities.add(new TileResource(spriteImg, Goblin.class, "Goblin"));
		entities.add(new TileResource(spriteImg, Troll.class, "Troll"));
		entities.add(new TileResource(spriteImg, Orc.class, "Orc"));
		entities.add(new TileResource(spriteImg, Chest.class, "Chest"));
		
		return resourceMap;
	}
	
	/**
	 * MouseLisetener implementation for brush selection buttons
	 */
	private class TileButton extends JLabel implements MouseListener {
		private static final long serialVersionUID = 803318193737620067L;
		private TileResource resource;
		
		/**
		 * Constructor
		 * @param resource A Tile resource
		 */
		public TileButton(TileResource resource) {
			super(resource.getShortDescription(), resource.getIcon(), JLabel.LEFT);
			this.resource = resource;
			addMouseListener(this);
		}
		
		/**
		 * Select handler
		 */
		public void select() {
			Border inner = BorderFactory.createEmptyBorder(3, 3, 3, 3);
			for (TileButton button : buttons) {
				button.setBorder(new CompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), inner));
			}
			setBorder(new CompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), 
					new CompoundBorder(BorderFactory.createLineBorder(Color.blue, 2), inner)));
			LevelEditorToolsView.this.firePropertyChange("selectTile", null, resource);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			select();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}
}
