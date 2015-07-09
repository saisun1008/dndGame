package test.dnd.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dnd.game.EquipmentSlot;
import dnd.game.Inventory;
import dnd.game.World;
import dnd.game.entity.item.EquippableItem;
import dnd.game.entity.item.Item;
import dnd.game.entity.living.LivingEntity;

public class TestInventory {
	private class TestItem extends Item { 
		@Override
		public boolean use(World world, LivingEntity entity) {
			return false;
		}
	}
	private class TestArmorEquipment extends EquippableItem { 
		public TestArmorEquipment() {
			super(EquipmentSlot.ARMOR);
		}
	}
	private class TestWeaponEquipment extends EquippableItem { 
		public TestWeaponEquipment() {
			super(EquipmentSlot.WEAPON);
		}
	}
	
	private Inventory inventory;
	
	@Before public void setup() {
		inventory = new Inventory();
	}
	
	@Test public void canAddItems() {
		assertTrue(inventory.addItem(new TestItem()));
	}
	
	@Test public void cannotAddItemsWhenFull() {
		inventory.setMaxItemSlots(3);
		assertTrue(inventory.addItem(new TestItem()));
		assertTrue(inventory.addItem(new TestItem()));
		assertTrue(inventory.addItem(new TestItem()));
		assertFalse(inventory.addItem(new TestItem()));
		assertEquals(3, inventory.itemCount());
		assertTrue(inventory.isFull());
	}
	
	@Test public void canRemoveItems() {
		Item item1 = new TestItem();
		Item item2 = new TestItem();
		inventory.addItem(item1);
		inventory.addItem(item2);
		assertEquals(2, inventory.itemCount());
		assertEquals(item1, inventory.dropItem(item1));
		assertEquals(1, inventory.itemCount());
	}
	
	@Test public void canEquipEquippableItems() {
		EquippableItem e = new TestArmorEquipment();
		inventory.addItem(e);
		assertTrue(inventory.hasItem(e));
		assertTrue(inventory.equipSlot(EquipmentSlot.ARMOR, e));
		assertTrue(inventory.isEquipped(e));
		assertFalse(inventory.hasItem(e));
	}
	
	@Test public void canOnlyEquipItemsInInventory() {
		EquippableItem e = new TestArmorEquipment();
		assertFalse(inventory.equipSlot(EquipmentSlot.ARMOR, e));
	}
	
	@Test public void canOnlyEquipIfSlotIsEmpty() {
		EquippableItem e = new TestArmorEquipment();
		EquippableItem e2 = new TestArmorEquipment();
		inventory.addItem(e);
		inventory.addItem(e2);
		assertTrue(inventory.equipSlot(EquipmentSlot.ARMOR, e));
		assertFalse(inventory.equipSlot(EquipmentSlot.ARMOR, e2));
		assertTrue(inventory.isEquipped(e));
		assertTrue(inventory.hasItem(e2));
	}
	
	@Test public void equipShouldCheckIfItemIsEquippableInSlot() {
		EquippableItem e = new TestArmorEquipment();
		inventory.addItem(e);
		assertTrue(inventory.hasItem(e));
		assertTrue(inventory.equipSlot(EquipmentSlot.ARMOR, e));
		assertEquals(e, inventory.equipmentInSlot(EquipmentSlot.ARMOR));
		assertFalse(inventory.hasItem(e));
	}
	
	@Test public void canFastEquip() {
		EquippableItem e = new TestArmorEquipment();
		inventory.addItem(e);
		assertTrue(inventory.equipItem(e));
		assertEquals(e, inventory.equipmentInSlot(EquipmentSlot.ARMOR));
	}
	
	@Test public void fastEquipChecksInventory() {
		EquippableItem e = new TestArmorEquipment();
		assertFalse(inventory.equipItem(e));
	}
	
	@Test public void canUnequipItems() {
		EquippableItem e = new TestArmorEquipment();
		inventory.addItem(e);
		inventory.equipSlot(EquipmentSlot.ARMOR, e);
		assertTrue(inventory.unequipSlot(EquipmentSlot.ARMOR));
		assertFalse(inventory.isEquipped(e));
		assertFalse(inventory.isEquipped(EquipmentSlot.ARMOR));
		assertTrue(inventory.hasItem(e));
	}
	
	@Test public void unequipReturnsFalseIfItemNotEquipped() {
		assertFalse(inventory.unequipSlot(EquipmentSlot.ARMOR));
	}
	
	@Test public void canUnequipByItem() {
		EquippableItem e = new TestArmorEquipment();
		inventory.addItem(e);
		inventory.equipSlot(EquipmentSlot.ARMOR, e);
		assertTrue(inventory.unequipItem(e));
		assertFalse(inventory.isEquipped(e));
		assertFalse(inventory.isEquipped(EquipmentSlot.ARMOR));
		assertTrue(inventory.hasItem(e));		
	}
	
	@Test public void hasItemShouldNotCheckEquipmentByDefault() {
		EquippableItem e = new TestWeaponEquipment();
		inventory.addItem(e);
		inventory.equipSlot(EquipmentSlot.WEAPON, e);
		assertFalse(inventory.hasItem(e));
	}
	
	@Test public void hasItemShouldCheckEquipmentIfRequested() {
		EquippableItem e = new TestWeaponEquipment();
		inventory.addItem(e);
		inventory.equipSlot(EquipmentSlot.WEAPON, e);
		assertFalse(inventory.hasItem(e, false));
		assertTrue(inventory.hasItem(e, true));
	}
	
	@Test public void iteratorReturnsEquippedItems() {
		Item item = new TestItem();
		EquippableItem e = new TestWeaponEquipment();
		inventory.addItem(item);
		inventory.addItem(e);
		inventory.equipItem(e);
		int count = 0;
		for (Item i : inventory) {
			if (i == item || i == e) count++;
		}
		assertEquals(2, count);
	}
	
	@Test public void itemCountReturnsNonEquippedItems() {
		Item item = new TestItem();
		EquippableItem e = new TestWeaponEquipment();
		inventory.addItem(item);
		inventory.addItem(e);
		assertEquals(2, inventory.itemCount());
		inventory.equipItem(e);
		assertEquals(1, inventory.itemCount());
	}
	
	@Test public void modifyingItemSlotsDoesNotAddItems() {
		Item item = new TestItem();
		inventory.addItem(item);
		assertEquals(1, inventory.itemCount());
		inventory.getItemSlots().add(new TestItem());
		assertEquals(1, inventory.itemCount());
	}
	
	@Test public void canClearInventory() {
		Item item = new TestItem();
		EquippableItem e = new TestWeaponEquipment();
		inventory.addItem(item);
		inventory.addItem(e);
		inventory.equipItem(e);
		inventory.setGold(1000);
		inventory.setMaxItemSlots(1000);
		assertEquals(1, inventory.itemCount());
		assertEquals(1, inventory.getEquippedItems().size());
		assertEquals(1000, inventory.getGold());
		inventory.clear();
		assertEquals(0, inventory.itemCount());
		assertEquals(0, inventory.getEquippedItems().size());
		assertEquals(0, inventory.getGold());
		assertEquals(1000, inventory.getMaxItemSlots());
	}
}
