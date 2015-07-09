package dnd.game.trait;

import dnd.game.Entity;
import dnd.game.Level;
import dnd.game.RandomLevelGenerator;
import dnd.game.World;
import dnd.game.entity.living.NPC;
import dnd.game.entity.living.Player;
import dnd.game.event.SpeakEvent;

public class NextLevelTrait extends TileTrait {
	@Override
	public boolean acceptsEntity(World world, Entity entity) {
		if (!(entity instanceof Player)) return false;
		
		// all monsters must be dead to be allowed to next level
		for (NPC monster : world.getMonsters()) {
			if (monster.isAlive()) {
				world.sendEvent(new SpeakEvent("Cannot move to next level while monsters are alive."));
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void touch(World world, Entity entity) {
		// If the player goes down the stairs, generate a new level
		if (entity instanceof Player) {
			Level level = world.getLevel();
			Level randomLevel = new RandomLevelGenerator().randomLevel(level.getWidth(), level.getHeight());
			world.nextLevel(randomLevel);
		}
	}

}
