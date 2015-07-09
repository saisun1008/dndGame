package dnd.game.entity.item;

import dnd.game.AbilityType;
import dnd.game.World;
import dnd.game.entity.living.LivingEntity;
import dnd.game.event.EventObserver;
import dnd.game.event.GameEvent;
import dnd.game.event.NextTurnEvent;
import dnd.util.Modifier;

/**
 * basic usable potion class
 */
public class Potion extends ConsumableItem implements EventObserver {
	private int numTurns;
	private LivingEntity usedEntity;
	private Modifier<AbilityType> modifier;

	/*
	 * default constructor for Potion if no number of turns are specified
	 */
	public Potion(Modifier<AbilityType> modifier) {
		this(modifier, 3);
	}

	/**
	 * constructor for potion if the number of turns are specified
	 * 
	 * @param modifier
	 *            for potion
	 * @param numTurns
	 */
	public Potion(Modifier<AbilityType> modifier, int numTurns) {
		this.modifier = modifier;
		this.numTurns = numTurns;
		getBaseAbilityModifiers().add(modifier); // for ability formatting
		setName();
	}

	/**
	 * set the name for the potion based on the potion type
	 */
	private void setName() {
		if (modifier == null)
			return;
		AbilityType type = AbilityType.HP;
		int value = 0;
		for (AbilityType t : AbilityType.values()) {
			value = modifier.delta(t);
			if (value != 0) {
				type = t;
				break;
			}
		}
		String mod;
		if (value >= 10)
			mod = "Major ";
		if (value >= 5)
			mod = "";
		else
			mod = "Minor ";
		setName(mod + "Potion of " + type.getName());
	}

	@Override
	public boolean use(World world, LivingEntity entity) {
		if (modifier != null) {
			entity.getBaseAbilityModifiers().add(modifier);
			world.addObserver(this); // track the next turn events for 3 turns
		}
		return super.use(world, entity); // consume this entity
	}

	@Override
	public void eventFired(World world, GameEvent event) {
		if (event instanceof NextTurnEvent) { // track num turns to deactivate
												// effects
			numTurns--;
			if (numTurns == 0) { // deactivate effects
				usedEntity.getBaseAbilityModifiers().remove(modifier);
				world.removeObserver(this);
			}
		}
	}
}
