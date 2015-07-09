package dnd.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Modifier<T> is a HashMap of modifier types to modifier values, with specific
 * additional functionality

 * @param <T> A modifier type (currently one of AbilityType, DamageType)
 * @see AbilityType
 * @see DamageType
 * 
 */
public class Modifier<T> {

	private Map<T, Integer> modifications = new HashMap<T, Integer>();
	
	public Modifier() {
	}
	
	/**
	 * Convenience constructor to create a modifier with a given set of modifier types and values
	 * @param type the first modifier type
	 * @param value the first value
	 * @param others other modifiers and types (in the form TYPE, VALUE, ...)
	 */
	public Modifier(T type, Integer value, Object ... others) {
		setDelta(type, value);
		for (int i = 0; i < others.length; i += 2) {
			@SuppressWarnings("unchecked")
			T t = (T)others[i];
			int v = (Integer)others[i+1];
			setDelta(t, v);
		}
	}
	

	/**
	 * Get the value of a specific modifier in the HashMap
	 * 
	 * @param e The key (modifier type) of the modifier to retrieve
	 * @return The modifier which corresponds to the key provided
	 */
	public int delta(T e) {
		if (!modifications.containsKey(e))
			return 0;
		return modifications.get(e);
	}

	/**
	 * Set a specific modifier
	 * 
	 * @param e The key of the modifier which will be set
	 * @param value The value of the modifier to be set
	 */
	public void setDelta(T e, int value) {
		modifications.put(e, value);
	}

	@Override
	public String toString() {
		String str = "";
	    Iterator<Entry<T, Integer>> it = modifications.entrySet().iterator();
	    int i = 0;
	    while (it.hasNext()) {
	    	if (i++ > 0) {
	    		str += ", ";
	    	}
	        @SuppressWarnings("rawtypes")
			Map.Entry pairs = (Map.Entry)it.next();
	        str += pairs.getKey() + ":" + pairs.getValue();
	    }
		return  str;
	}
}
