package test.dnd.ui.controller;

import static org.junit.Assert.*;

import java.awt.Container;

import org.junit.Before;
import org.junit.Test;

import dnd.ui.controller.ViewController;
import dnd.ui.controller.WizardController;

public class TestWizardController {
	private MockWizardController wizard;
	private ViewController<Container> vc1 = new ViewController<Container>() { };
	private ViewController<Container> vc2 = new ViewController<Container>() { };
	private ViewController<Container> vc3 = new ViewController<Container>() { };
	
	private class MockWizardController extends WizardController<int[]> {
		public boolean showCurrentController;
		public boolean finalControllerEnded;

		@Override
		protected void showCurrentController() {
			showCurrentController = true;
		}
		
		protected void finalControllerEnded() { 
			finalControllerEnded = true;
		}
	}

	@Before public void setup() {
		this.wizard = new MockWizardController();
		wizard.setState(new int[] { 3, 5 });
		wizard.addController(vc1);
		wizard.addController(vc2);
		wizard.addController(vc3);
	}
	
	@Test public void wizardCanModifyStateObject() {
		wizard.getState()[0] = 1;
		assertEquals(1, wizard.getState()[0]);
	}
	
	@Test public void wizardStartsOnFirstController() {
		assertEquals(vc1, wizard.currentController());
	}
	
	@Test public void wizardWillCallShowController() {
		wizard.showCurrentController = false;
		wizard.nextController();
		assertEquals(vc2, wizard.currentController());
		assertTrue(wizard.showCurrentController);
	}
	
	@Test public void willCallFinalControllerEndedAfterLastController() {
		wizard.nextController();
		assertFalse(wizard.finalControllerEnded);
		wizard.nextController();
		assertFalse(wizard.finalControllerEnded);
		wizard.nextController();
		assertTrue(wizard.finalControllerEnded);
	}
	
	@Test public void allowsPrevControllerToMoveBack() {
		wizard.nextController();
		assertEquals(vc2, wizard.currentController());
		wizard.nextController();
		assertEquals(vc3, wizard.currentController());
		wizard.prevController();
		assertEquals(vc2, wizard.currentController());
		wizard.prevController();
		assertEquals(vc1, wizard.currentController());
	}
	
	@Test public void willNotMoveBeforeFirstController() {
		assertEquals(vc1, wizard.currentController());
		wizard.prevController();
		assertEquals(vc1, wizard.currentController());
	}
	
	@Test public void allowModalControllerToBeShown() {
		ViewController<Container> vc4 = new ViewController<Container>() { };
		assertEquals(vc1, wizard.currentController());
		wizard.showModalController(vc4);
		assertEquals(vc4, wizard.currentController());
		wizard.nextController();
		assertEquals(vc1, wizard.currentController());
	}
}
