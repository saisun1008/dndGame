package test.dnd;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.dnd.game.TestBaseAttackBonus;
import test.dnd.game.TestDice;
import test.dnd.game.TestInventory;
import test.dnd.game.TestLevel;
import test.dnd.game.TestLivingEntity;
import test.dnd.game.TestLocation;
import test.dnd.game.TestNextLevelTrait;
import test.dnd.game.TestPlayerBuilder;
import test.dnd.game.TestPlayerFactory;
import test.dnd.game.TestTile;
import test.dnd.game.TestTrace;
import test.dnd.game.TestWorld;
import test.dnd.ui.controller.TestWizardController;
import test.dnd.util.TestModifierSet;
import test.dnd.util.TestXmlMapper;


@RunWith(Suite.class)
@Suite.SuiteClasses({TestInventory.class, TestLevel.class, TestTile.class,
	TestNextLevelTrait.class, TestWorld.class, TestLivingEntity.class, TestPlayerBuilder.class,
	TestTrace.class, TestLocation.class, TestPlayerFactory.class, TestBaseAttackBonus.class,
	TestModifierSet.class, TestXmlMapper.class, TestWizardController.class, TestDice.class})

public class AllTests {
}