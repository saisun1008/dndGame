package dnd.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import dnd.game.Level;
import dnd.game.World;
import dnd.game.entity.living.Player;

/**
 * A UI utility class which prompts the user for saving one of the three types
 * of persistent data - game, level and character
 * 
 * @see Player
 * @see Level
 * @see World
 * 
 */
public final class FileSaver {

	/**
	 * Private constructor - don't permit external instantiation
	 */
	private FileSaver() { }
	
	/**
	 * A generic method which creates a dialog asking the user to save the
	 * current character, level, or game
	 * 
	 * @param object The object to be made persistent
	 * @param description A description of the object
	 * @param extension the file extension related to this object
	 * @return True if the operation was successful, false otherwise
	 */
	public static <T> String saveFileDialog(T object, String description, String extension) {
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + "/data");
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(description, extension);
	    chooser.setFileFilter(filter);
	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	    	try {
	    		new XmlMapper<T>().save(object, new FileOutputStream(chooser.getSelectedFile()));
	    		return chooser.getSelectedFile().getAbsolutePath();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, 
						"Could not save file " + chooser.getSelectedFile().getAbsolutePath());
			}
	    }
	    return null;
	}
	
	/**
	 * The "Player" save to file dialog
	 * 
	 * @param player A reference to a Player object
	 * @return True if the operation was successful, false otherwise
	 */
	public static String savePlayer(Player player) {
		return saveFileDialog(player, "Character Files", "char");
	}
	
	/**
	 * The "World" save to file dialog
	 * 
	 * @param world A reference to the World object
	 * @return True if the operation was successful, false otherwise
	 */	
	public static String saveWorld(World world) {
		return saveFileDialog(world, "Saved Games", "world");
	}
	
	/**
	 * The "Level" save to file dialog
	 * 
	 * @param level A reference to a Level object
	 * @return True if the operation was successful, false otherwise
	 */	
	public static String saveLevel(Level level) {
		return saveFileDialog(level, "Level Files", "lvl");
	}
}
