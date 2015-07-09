package dnd.game.entity.living;

import dnd.game.AbilityType;
import static dnd.game.AbilityType.*;

/**
 * Concrete builder for a Tank build of a Fighter class
 */
public class TankBuilder extends FighterBuilder {

	@Override
	public AbilityType[] typePriorities() {
		return new AbilityType[] {CONS, DEX, STR, INT, CHAR, WIS};
	}
}
