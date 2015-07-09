package dnd.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import dnd.game.entity.item.EquippableItem;
import dnd.game.entity.item.Item;

/**
 * Inventory is a data structure which represents the inventory
 * (typically of) a LivingEntity. It also defines rules for accessing and
 * manipulating the inventory and validating inventory related requests
 * 
 * @see EquippableItem
 * @see EquipmentSlot
 * 
 */
public class Inventory implements Iterable<Item> {

	public static final int DEFAULT_MAX_ITEMS = 100;
	private Set<Item> itemSlots;
	private int maxItemSlots;
	private Map<EquipmentSlot, EquippableItem> equipmentSlots;
	private int gold;
	
	/**
	 * Constructor
	 */
	public Inventory() {
		itemSlots = new HashSet<Item>();
		gold = 0;
		maxItemSlots = DEFAULT_MAX_ITEMS;
		equipmentSlots = new HashMap<EquipmentSlot, EquippableItem>();
	}

	/**
	 * @return The amount of gold in this inventory
	 */
	public int getGold() {
		return gold;
	}

	/**
	 * @return The slots in this inventory
	 */
	public Set<Item> getItemSlots() {
		return new HashSet<Item>(itemSlots);
	}
	
	/**
	 * Get all equipped items
	 * @return All equipped items from this inventory
	 */
	public List<EquippableItem> getEquippedItems() {
		return new ArrayList<EquippableItem>(equipmentSlots.values());
	}

	/**
	 * @return The maximum number of slots for items in this inventory
	 */
	public int getMaxItemSlots() {
		return maxItemSlots;
	}

	/**
	 * @param value The amount of gold to set
	 */
	public void setGold(int value) {
		gold = value;
	}

	/**
	 * @param value The amount of gold to add to the current amount
	 */
	public void addGold(int value) {
		gold += value;
	}
	
	/**
	 * @param value The amount of gold to subtract from the current amount
	 */
	public void subtractGold(int value) {
		gold -= value;
	}
	
	/**
	 * Add a generic item to the inventory
	 * @param item the item to add
	 * @return True if the item could be added, false otherwise
	 */
	public boolean addItem(Item item) {
		if (itemSlots.size() < maxItemSlots) {
			itemSlots.add(item);
			return true;
		}
		return false;
	}
	
	/**
	 * Remove an item from the inventory and return it
	 * @param item An {@link Item}
	 * @return the same Item that was passed to the method
	 */
	public Item dropItem(Item item) {
		itemSlots.remove(item);
		return item;
	}

	/**
	 * @param maxItemSlots
	 *            The maximum number of slots for items in this inventory
	 */
	public void setMaxItemSlots(int maxItemSlots) {
		this.maxItemSlots = maxItemSlots;
	}

	/**
	 * Check if an item is equipped in a given equipment slot
	 * @param slot The equipment slot to check
	 * @return True if the equipment slot has an item, false otherwise
	 */
	public boolean isEquipped(EquipmentSlot slot) {
		return equipmentSlots.containsKey(slot);
	}
	
	/**
	 * Check if a given item is equipped
	 * @param equipment The {@link EquippableItem} to check
	 * @return True if the item is equipped, false otherwise
	 */
	public boolean isEquipped(EquippableItem equipment) {
		return equipmentSlots.containsValue(equipment);
	}

	/**
	 * Check if an inventory equipment slot contains an item or not
	 * @param slot The slot to check
	 * @return True if there is an item in the slot, false otherwise
	 */
	public EquippableItem equipmentInSlot(EquipmentSlot slot) {
		return equipmentSlots.get(slot);
	}
	
	/**
	 * Test if an equippable item can fit into an inventory slot. If the item
	 * can be equipped, equip it and unequip any item that was already equipped
	 * in that slot.
	 * 
	 * @param slot The slot to test against the item
	 * @param equipment The item to test against the slot
	 * @return True if the item was successfully equipped
	 */
	public boolean equipSlot(EquipmentSlot slot, EquippableItem equipment) {

		if (equipment.fitsInSlot(slot) && hasItem(equipment, true) && !isEquipped(slot)) {
			dropItem(equipment);
			EquipmentSlot equippedSlot = null;
			for (Entry<EquipmentSlot, EquippableItem> set : equipmentSlots.entrySet()) {
				if (set.getValue().equals(equipment)) {
					equippedSlot = set.getKey();
					break;
				}
			}
			equipmentSlots.remove(equippedSlot);
			equipmentSlots.put(slot, equipment);
			return true;
		}
		return false;
	}
	
	/**
	 * Equip an item
	 * @param equipment the item to equip
	 * @return True if the item has been equipped, false otherwise
	 */
	public boolean equipItem(EquippableItem equipment) {
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			if (equipSlot(slot, equipment)) return true;
		}
		return false;
	}
	
	/**
	 * Unequip a given slot
	 * @param slot The slot to unequip
	 * @return True if the slot was unequipped, false otherwise
	 */
	public boolean unequipSlot(EquipmentSlot slot) {
		EquippableItem e = equipmentSlots.remove(slot);
		if (e != null) {
			addItem(e);
			return true;
		}
		return false;
	}
	
	/**
	 * Unequip a given item
	 * @param equipment And equippable item
	 * @return True if the item was unequipped, false otherwise
	 */
	public boolean unequipItem(EquippableItem equipment) {
		for (EquipmentSlot slot : equipmentSlots.keySet()) {
			if (equipmentInSlot(slot).equals(equipment)) {
				unequipSlot(slot);
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if this inventory can hold any more items
	 * @return True if this inventory is full, false otherwise
	 */
	public boolean isFull() {
		return itemCount() == getMaxItemSlots();
	}

	/**
	 * Get the total number of slots in this inventory
	 * @return The total number of slots in this inventory
	 */
	public int itemCount() {
		return getItemSlots().size();
	}
	
	/**
	 * Check if this inventory contains the given item
	 * @param item The item to compare with the items in inventory
	 * @return True if the item is in the inventory, false otherwise.
	 */
	public boolean hasItem(Item item) {
		return getItemSlots().contains(item);
	}
	
	/**
	 * Check if a given item is found in this inventory or is equipped
	 * @param item The item to check from the inventory
	 * @param checkEquipment Whether or not the equipment slots should be check in addition
	 * @return true if the item ins in the inventory or equipped
	 */
	public boolean hasItem(Item item, boolean checkEquipment) {
		return hasItem(item) || (checkEquipment &&
				(item instanceof EquippableItem && isEquipped((EquippableItem)item)));
	}
	
	/**
	 * Clears all inventory items, equipped items and gold
	 */
	public void clear() {
		itemSlots.clear();
		equipmentSlots.clear();
		gold = 0;
	}
	
	@Override
	public Iterator<Item> iterator() {
		Set<Item> list = getItemSlots();
		list.addAll(equipmentSlots.values());
		return list.iterator();
	}
}
