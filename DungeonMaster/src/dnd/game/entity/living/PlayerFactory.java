package dnd.game.entity.living;

import dnd.game.Dice;

/**
 * PlayerFactory generates the player character and NPC entities
 */
public class PlayerFactory {
	
	/**
	 * Factory static method for making players/NPCs (uses local instance of Dice)
	 * @param buildInfo Details about the character to create
	 * @param level The level of character to generate
	 * @param entity The entity, in the event it already exists
	 * @return The entity that is created
	 */
	public static LivingEntity getPlayer(String buildInfo, int level, LivingEntity entity) {
		return getPlayer(buildInfo, level, entity, new Dice());
	}
	
	/**
	 * Factory static method for making players/NPCs
	 * @param buildInfo Details about the character to create
	 * @param level The level of character to generate
	 * @param entity The entity, in the event it already exists
	 * @param dice A reference to the Dice object
	 * @return The entity that is created
	 */
	public static LivingEntity getPlayer(String buildInfo, int level, LivingEntity entity, Dice dice) {
		buildInfo = buildInfo.toLowerCase();
		PlayerBuilder builder = null;
		
		// parse out whether the player is npc or not
		if (entity == null) {
			if (buildInfo.contains("npc")) {
				switch (dice.roll(1, 3)) {
					case 1: entity = new Troll(); break;
					case 2: entity = new Orc(); break;
					case 3: entity = new Goblin(); break;
				}
			}
			else {
				entity = new Player();
			}
		}
		
		// parse build type from buildInfo
		if (buildInfo.contains("bully")) {
			builder = new BullyBuilder();
		}
		else if (buildInfo.contains("nimble")) {
			builder = new NimbleBuilder();
		}
		else if (buildInfo.contains("tank")) {
			builder = new TankBuilder();
		}
		
		if (builder == null) return null;
		builder.setDice(dice);
		builder.setEntity(entity);
		builder.setLevel(level);
		if (buildInfo.contains("npc")) { // decorate builder with NPCBuilder
			builder = new NPCBuilder(builder);
		}
		return builder.buildAll();
	}
}
