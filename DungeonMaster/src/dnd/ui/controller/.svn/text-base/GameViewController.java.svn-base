package dnd.ui.controller;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.lwjgl.opengl.Display;

import dnd.game.EquipmentSlot;
import dnd.game.RandomLevelGenerator;
import dnd.game.World;
import dnd.game.entity.item.Item;
import dnd.game.entity.living.LivingEntity;
import dnd.game.entity.living.Player;
import dnd.game.entity.living.PlayerFactory;
import dnd.game.event.EventObserver;
import dnd.game.event.GameEvent;
import dnd.game.event.KilledEntityEvent;
import dnd.game.event.MovementEvent;
import dnd.game.event.NextLevelEvent;
import dnd.game.event.OpenInventoryEvent;
import dnd.game.mapper.LevelMapper;
import dnd.game.mapper.PlayerMapper;
import dnd.game.mapper.WorldMapper;
import dnd.ui.GameView;
import dnd.util.FileSaver;

/**
 * A controller for the views related to the game. It implements UI functionality
 * related to the game
 * 
 */
public class GameViewController extends ViewController<GameView> implements EventObserver {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				World world = new World();
				Player player = new Player();
				player.setName("Mat");
				PlayerFactory.getPlayer("tank", 1, player, world.getDice());
				world.setLevel(new RandomLevelGenerator().randomLevel(20, 20));
				world.getPlayers().add(player);
				GameViewController ctl = new GameViewController(world);
				JFrame frame = new JFrame("Dungeon master");
				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						Display.destroy();
						System.exit(0);
					}
				});
				frame.getContentPane().add(ctl.view);
				frame.pack();
				frame.setVisible(true);

				// save all world elements to temporarily generate load data
				try {
					new WorldMapper().save(world, new FileOutputStream("world1.world"));
					new LevelMapper().save(world.getLevel(), new FileOutputStream("level1.lvl"));
					new PlayerMapper().save(world.getPlayers().get(0), new FileOutputStream("player1.char"));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private MenuBar menuBar;
	private World world;
	private String saveFilename;

	/**
	 * Constructor
	 * @param world A reference to a {@link World}
	 */
	public GameViewController(World world) {
		this.world = world;
		this.view = new GameView(world);
		createMenuBar();
		registerEventHandlers();
		world.addObserver(this);
		world.start();
	}
	
	/**
	 * Get the game's menu bar
	 * @return The main menu bar
	 */
	public MenuBar mainMenuBar() {
		return menuBar;
	}
	
	/**
	 * Save the game
	 */
	public void saveGame() {
		if (saveFilename == null) {
			saveFilename = FileSaver.saveWorld(world);
		}
		else {
			try {
				new WorldMapper().save(world, new FileOutputStream(saveFilename));
			} catch (FileNotFoundException e) {}
		}
		if (saveFilename != null) {
			log("Saved game");
		}
	}
	
	/**
	 * Log a message to the log view
	 * @param message The message to display
	 */
	private void log(String message) {
		if (message == null) return;
		view.getLogView().append(message);
	}

	/**
	 * Create a new menu bar
	 */
	private void createMenuBar() {
		menuBar = new MenuBar();
		Menu menu = new Menu("File");
		MenuItem mItem = new MenuItem("Save");
		mItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveGame();
			}
		});
		menuBar.add(menu);
		menu.add(mItem);
	}

	private void registerEventHandlers() {
		view.getEntityView().addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) { mainEntityViewChanged(evt); }
		});
		
		view.getAltEntityView().addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) { altEntityViewChanged(evt); }
		});
	}

	private void mainEntityViewChanged(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("use")) { // use item
			if (!(world.getTurnManager().activeEntity() instanceof Player)) return;
			world.useItem(world.getPlayers().get(0), (Item)evt.getNewValue());
		}
		else if (evt.getPropertyName().equals("unequip")) { // unequip event, slot in newValue
			if (!(world.getTurnManager().activeEntity() instanceof Player)) return;
			world.getTurnManager().unequipSlot((EquipmentSlot)evt.getNewValue());
		}
	}

	private void altEntityViewChanged(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		if (prop.equals("use")) { // take item
			if (!(world.getTurnManager().activeEntity() instanceof Player)) return;
			world.takeInventoryItem(world.getPlayers().get(0), 
					view.getAltEntityView().getEntity(), (Item)evt.getNewValue());
		}
		else if (prop.equals("unequip")) { // take item in equipped slot
			if (!(world.getTurnManager().activeEntity() instanceof Player)) return;
			LivingEntity entity = (LivingEntity)view.getAltEntityView().getEntity();
			Item item = entity.getInventory().equipmentInSlot((EquipmentSlot)evt.getNewValue());
			world.takeInventoryItem(world.getPlayers().get(0), entity, item);

		}
	}

	@Override
	public void eventFired(World world, GameEvent event) {
		if (event instanceof NextLevelEvent) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() { saveGame(); }
			});
		}
		if (event instanceof KilledEntityEvent) {
			if (((KilledEntityEvent)event).getTarget() instanceof Player) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() { 
						JOptionPane.showMessageDialog(null, "You died! Restarting level.");
					}
				});
				
			}
		}
		
		if (event instanceof OpenInventoryEvent) { // show inventory view for inventory entity
			view.getAltEntityView().setEntity(((OpenInventoryEvent) event).getInventoryEntity());
		}
		else if (event instanceof MovementEvent) {
			if (((MovementEvent) event).getEntity() == view.getEntityView().getEntity()) {
				view.getAltEntityView().setEntity(null); // close inv if movement occurs
			}
		}

		log(event.toString());
		
		// always update the entity view
		view.getEntityView().update();
		view.getAltEntityView().update();
	}
}
