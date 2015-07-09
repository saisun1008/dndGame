package test.dnd.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dnd.game.AbilityType;
import dnd.util.Modifier;
import dnd.util.ModifierSet;
import static dnd.game.AbilityType.*;

public class TestModifierSet {
	private Modifier<AbilityType> mod1 = new Modifier<AbilityType>();
	private Modifier<AbilityType> mod2 = new Modifier<AbilityType>();
	private Modifier<AbilityType> mod3 = new Modifier<AbilityType>();
	
	@Before public void setup() {
		mod1.setDelta(WIS, 3);
		mod1.setDelta(STR, 8);
		mod2.setDelta(WIS, -5);
		mod3.setDelta(WIS, 1);
	}
	
	@Test public void calculatesTotalDelta() {
		ModifierSet<AbilityType> set = new ModifierSet<AbilityType>();
		set.add(mod1);
		set.add(mod2);
		set.add(mod3);
		assertEquals(-1, set.delta(WIS));
		assertEquals(8, set.delta(STR));
		assertEquals(0, set.delta(CONS));
	}
	
	@Test public void canAddSets() {
		ModifierSet<AbilityType> set1 = new ModifierSet<AbilityType>();
		ModifierSet<AbilityType> set2 = new ModifierSet<AbilityType>();
		set2.add(mod1);
		set2.add(mod2);
		set1.add(mod3);
		assertEquals(1, set1.delta(WIS));
		set1.add(set2);
		assertEquals(-1, set1.delta(WIS));
	}
	
	@Test public void canAddWithConvenienceMethod() {
		ModifierSet<AbilityType> set1 = new ModifierSet<AbilityType>();
		set1.add(HP, 1, STR, 2);
		assertEquals(1, set1.delta(HP));
		assertEquals(2, set1.delta(STR));
	}
	
	@Test public void disallowAddingNullModifiers() {
		ModifierSet<AbilityType> set1 = new ModifierSet<AbilityType>();
		set1.add((Modifier<AbilityType>)null);
		assertEquals(0, set1.size());
	}
}
