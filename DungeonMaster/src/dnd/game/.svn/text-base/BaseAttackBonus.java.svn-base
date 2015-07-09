package dnd.game;
/**
 * calculate base attack bonus and number of attacks can be performed in the current level
 */
public class BaseAttackBonus {
	private int minLevel = 1;
	private int maxLevel = 20;
	private int upAttacks = 5;
/**
 * get the number of attacks can be performed in the certain level
 * @param level current level
 * @return attack numbers 
 */
	public int getNumberOfAttacks(int level) {
		if (level < minLevel)
			return 0;
		if (level > maxLevel)
			level = maxLevel;
		return (level + 4) / upAttacks;
	}
/**
 * calculate the base attack bonus based on the current level and number of attack can be performed
 * @param level
 * @param attackNumber
 * @return attack bonus
 */
	public int getBaseAttackBonus(int level, int attackNumber) {
		if (level < minLevel)
			return 0;
		if (level > maxLevel)
			level = maxLevel;
		int attackBonus = level - upAttacks * (attackNumber - 1);
		if (attackBonus < 1)
			return 0;
		return attackBonus;
	}
}
