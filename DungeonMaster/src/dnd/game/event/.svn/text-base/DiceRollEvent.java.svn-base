package dnd.game.event;

import dnd.game.Entity;

public class DiceRollEvent extends GameEvent {
	private int value;
	private int numDice;
	private int dieSize;
	private Integer modifier;
	private Entity source;
	private String modName;

	public DiceRollEvent(int value, int numDice, int dieSize, Integer modifier, String modName, Entity source) {
		this.value = value;
		this.numDice = numDice;
		this.dieSize = dieSize;
		this.source = source;
		this.modName = modName;
		this.modifier = modifier;
	}

	public int getValue() {
		return value;
	}

	public int getNumDice() {
		return numDice;
	}

	public int getModifier() {
		return modifier;
	}

	public int getDieSize() {
		return dieSize;
	}

	public Entity getSource() {
		return source;
	}

	public String getModName() {
		return modName;
	}
	
	@Override
	public String toString() {
		String line = "";
		if (source != null) line += source + " rolled";
		else line += "Rolled";
		line += " " + numDice + "d" + dieSize;
		if (modifier != null) {
			if (modifier >= 0) line += "+";
			line += modifier + " (" + modName + ")";
		}
		line += " with value " + value;
		return line;
	}
}
