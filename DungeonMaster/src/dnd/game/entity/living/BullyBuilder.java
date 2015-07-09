package dnd.game.entity.living;

import dnd.game.AbilityType;
import static dnd.game.AbilityType.*;

/**
 * Concrete builder for a Bully build of a Fighter class
 */
public class BullyBuilder extends FighterBuilder {

	@Override
	public AbilityType[] typePriorities() {
		return new AbilityType[] {STR, CONS, DEX, INT, CHAR, WIS};
	}
}
