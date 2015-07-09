package dnd.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;

import dnd.game.EquipmentSlot;
import dnd.game.InventoryInterface;
import dnd.game.entity.item.EquippableItem;
import dnd.game.entity.item.Item;
import dnd.game.entity.living.LivingEntity;

/**
 * The inventory UI panel
 *
 */
public class InventoryView extends JPanel {

	private static final long serialVersionUID = 3794680090269901823L;
	private InventoryInterface entity;
	private Map<EquipmentSlot, JLabel> equipButtons;
	private JList itemsList;
	private JPanel itemsPanel;
	private JPanel equipPanel;

	/**
	 * Build and show the player character's inventory
	 * 
	 * @param player
	 */
	public InventoryView(final InventoryInterface entity) {

		this.entity = entity;
		this.equipButtons = new HashMap<EquipmentSlot, JLabel>();

		// create items panel
		itemsList = new JList() {
			private static final long serialVersionUID = -4738193280985579835L;
		    public String getToolTipText(MouseEvent evt) {
		        int index = locationToIndex(evt.getPoint());
		        Item item = (Item)getModel().getElementAt(index);
		        return "<html><b>" + item.getName() + "</b><br>" + 
		        	   item.formatModifiers().replace("\n", "<br>") + "</html>";
		    }
		};
		itemsList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		itemsList.setBackground(SystemColor.control);
		JScrollPane itemsScrollPane = new JScrollPane(itemsList, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		itemsPanel = new JPanel();
		itemsPanel.setBorder(BorderFactory.createTitledBorder("Items"));
		itemsPanel.setPreferredSize(new Dimension(200, 200));
		itemsPanel.setLayout(new GridLayout(1, 1));
		itemsPanel.add(itemsScrollPane);
		
		// event handler for equipped items
		itemsList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					Item item = (Item)itemsList.getSelectedValue();
					InventoryView.this.firePropertyChange("use", null, item);
				}
			}
		});

		// create equipment panel
		equipPanel = new JPanel();
		equipPanel.setLayout(new BoxLayout(equipPanel, BoxLayout.Y_AXIS));
		equipPanel.setBorder(new CompoundBorder(
				BorderFactory.createTitledBorder("Equipment"), 
				BorderFactory.createEmptyBorder(0, 6, 0, 6)));

		EquipmentSlot[] values = EquipmentSlot.values();
		for (final EquipmentSlot slot : values) {
			JLabel label = new JLabel(slot.getName());
			label.setFont(Font.decode("sans-bold"));
			final JLabel equipButton = new JLabel("", JLabel.RIGHT);
			JPanel row = new JPanel();
			row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
			row.add(label);
			row.add(Box.createHorizontalGlue());
			row.add(equipButton);
			equipPanel.add(row);
			equipButtons.put(slot, equipButton);
			
			// handle events for items
			equipButton.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if (e.getClickCount() == 2) {
						InventoryView.this.firePropertyChange("unequip", null, slot);
					}
				}
			});
		}
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(equipPanel);
		add(itemsPanel);
		update();
	}
	
	public InventoryInterface getEntity() {
		return entity;
	}

	public void setEntity(InventoryInterface entity) {
		this.entity = entity;
	}
	
	/**
	 * A UI model for the item list
	 * 
	 * @author Team 6
	 *
	 */
	private class ItemListModel extends AbstractListModel {
		private static final long serialVersionUID = 3516938324344743490L;

		@Override
		public Object getElementAt(int index) {
			return getEntity().getInventory().getItemSlots().toArray()[index];
		}

		@Override
		public int getSize() {
			return getEntity().getInventory().getItemSlots().size();
		}
	}

	/**
	 * Update the inventory pane based on Player data.
	 */
	public void update() {
		if (entity == null) return;
		if (entity instanceof LivingEntity) {
			equipPanel.setVisible(true);
			for (Entry<EquipmentSlot, JLabel> item : equipButtons.entrySet()) {
				String text;
				EquippableItem eitem = entity.getInventory().equipmentInSlot(
						item.getKey());
				if (eitem == null)
					text = "<empty>";
				else
					text = eitem.getName();
				item.getValue().setText(text);
			}
		}
		else {
			equipPanel.setVisible(false);
		}
		
		itemsList.setModel(new ItemListModel());
		itemsPanel.setBorder(BorderFactory.createTitledBorder(
				"Items (Gold: " + entity.getInventory().getGold() + ")"));
	}
}
