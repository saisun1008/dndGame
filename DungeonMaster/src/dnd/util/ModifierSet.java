package dnd.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ModifierSet<T> is a List of Modifier<T> which is itself essentially a HashMap
 * of modifier types to modifier values.
 * 
 * Note that this classe's implementation assumes the Modifier<T> has only one
 * item in its HashMap, but this isn't always guaranteed
 * 
 * @param <T> A modifier type (currently one of AbilityType, DamageType)
 * @see AbilityType
 * @see DamageType
 * 
 */
public class ModifierSet<T> implements Iterable<Modifier<T>> {
	
	private List<Modifier<T>> modifiers = new ArrayList<Modifier<T>>();
	
	/**
	 * Convenience method to add a list of modifier values without creating an intermediate
	 * Modifier object.
	 * 
	 * @param mod the first modifier type
	 * @param value the first value
	 * @param others any other values (in the form TYPE, VALUE, ...)
	 */
	public void add(T type, int value, Object ... others) {
		add(new Modifier<T>(type, value, others));
	}
	
	/**
	 * Add a modifier to this modifier set
	 * 
	 * @param mod A modifier
	 */
	public void add(Modifier<T> mod) {
		if (mod == null) return;
		modifiers.add(mod);
	}
	
	/**
	 * Add a set of  modifiers to this modifier set
	 * 
	 * @param set A modifier set
	 */
	public void add(ModifierSet<T> set) {
		for (Modifier<T> mod : set) {
			modifiers.add(mod);
		}
	}
	
	/**
	 * Remove a modifier from this modifier set
	 * 
	 * @param mod
	 */
	public void remove(Modifier<T> mod) {
		modifiers.remove(mod);
	}
	
	/**
	 * Remove a set of modifiers from this modifier set
	 * 
	 * @param set The set of modifiers to remove
	 */
	public void remove(ModifierSet<T> set) {
		for (Modifier<T> mod : set) {
			modifiers.remove(mod);
		}
	}
	
	/**
	 * Clears all modifiers on set
	 */
	public void clear() {
		modifiers.clear();
	}
	
	/**
	 * Get the sum of a given type of modifier
	 * 
	 * @param type
	 *            A type of modifier, see {@link dnd.game.AbilityType} and
	 *            {@link dnd.game.DamageType}
	 * @return The sum of the modifiers
	 */
	public int delta(T type) {
		int total = 0;
		for (Modifier<T> mod : modifiers) {
			total += mod.delta(type);
		}
		return total;
	}
	
	/**
	 * Get the total number of modifiers in this set
	 * 
	 * @return The total number of modifiers in this set
	 */
	public int size() {
		return modifiers.size();
	}
	
	/**
	 * Convenience method to test if the number of modifiers in this set is 0.
	 * 
	 * @return whether the set is empty.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public Iterator<Modifier<T>> iterator() {
		return modifiers.iterator();
	}
	
	@Override
	public String toString() {
		String str = "";
		Iterator<Modifier<T>> it = this.iterator();
		int i = 0;
		while (it.hasNext()) {
	    	if (i++ > 0) {
	    		str += "\n";
	    	}
			Modifier<T> tmp = it.next();
			str += tmp.toString();
		}
		return str;
	}
}
