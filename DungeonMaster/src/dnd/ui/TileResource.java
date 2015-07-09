package dnd.ui;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import dnd.game.Entity;
import dnd.game.Tile;
import dnd.ui.canvas.GameCanvas;

/**
 * An immutable class for defining tile resources used throughout the UI
 * 
 */
public final class TileResource {
	private ImageIcon imageIcon;
	private String shortDescription;
	private Class<? extends Entity> entityClass;
	private Tile tile;

	/**
	 * Constructor
	 * @param image A reference to a BufferedImage for this tile
	 * @param tile A reference to the {@link dnd.game.Tile} related to this resource
	 * @param entityClass The entity class of this tile resource
	 * @param sDesc A short description of this resource
	 */
	public TileResource(BufferedImage image, Tile tile, Class<? extends Entity> entityClass, String sDesc) {
		this.shortDescription = sDesc;
		this.tile = tile;
		this.entityClass = entityClass;
		
		int[] tileInfo;
		if (tile != null) {
			tileInfo = GameCanvas.tileMap.get(tile);
		}
		else if (entityClass != null) {
			tileInfo = GameCanvas.entityMap.get(entityClass);
		}
		else tileInfo = new int[] {0,0};
		if (image != null && tileInfo != null) {
			int size = GameCanvas.spriteSize;
			int x = tileInfo[0] * size;
			int y = tileInfo[1] * size;
			this.imageIcon = new ImageIcon(image.getSubimage(x, y, size, size));
		}
	}
	
	/**
	 * Constructor
	 * @param image A reference to a BufferedImage for this tile
	 * @param tile A reference to the {@link Tile} related to this resource
	 * @param sDesc A short description of this resource
	 */
	public TileResource(BufferedImage image, Tile tile, String sDesc) {
		this(image, tile, null, sDesc);
	}
	
	/**
	 * Constructor
	 * @param image A reference to a BufferedImage for this tile
	 * @param entityClass The entity class of this tile resource
	 * @param sDesc A short description of this resource
	 */
	public TileResource(BufferedImage image, Class<? extends Entity> entityClass, String sDesc) {
		this(image, null, entityClass, sDesc);
	}
	
	/**
	 * @return The tile resource as an ImageIcon
	 */
	public ImageIcon getIcon() {
		return imageIcon;
	}
	
	/**
	 * @return The {@link Tile} related to these resource
	 */
	public Tile getTile() {
		return tile;
	}

	/**
	 * @return The entity class of this tile resource
	 */
	public Entity getEntity() {
		if (entityClass == null) return null;
		try {
			return (Entity)entityClass.getConstructors()[0].newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @return A short description of this resource
	 */
	public String getShortDescription() {
		return shortDescription;
	}
}
