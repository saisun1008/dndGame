package dnd.ui.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * WizardController is a base class for UI controllers used throughout the
 * Wizard. Each step in the Wizard has a specific controller and should inherit
 * from this class
 * 
 * The wizard also manages a {@link #getState()} object that can be accessed and modified by sub controllers.
 * 
 */
public abstract class WizardController<T> {

	private T state;
	private List<ViewController<?>> controllers = new ArrayList<ViewController<?>>();
	private ViewController<?> currentController;
	private ViewController<?> lastController;
	private int controllerIndex;

	/**
	 * Add one of the controllers to the list of controllers to run
	 * 
	 * @param controller
	 */
	public void addController(ViewController<?> controller) {
		if (currentController == null) currentController = controller;
		controllers.add(controller);
	}
	
	/**
	 * Proceed to the next step (controller) in the Wizard
	 */
	public void nextController() {
		lastController = currentController();
		if (controllers.indexOf(currentController) >= 0) {
			controllerIndex++;
		}
		if (controllerIndex >= controllers.size()) {
			finalControllerEnded();
			return;
		}
		currentController = controllers.get(controllerIndex);
		showCurrentController();
	}
	
	/**
	 * Go to the previous step in the Wizard
	 */
	public void prevController() {
		lastController = currentController();
		if (controllers.indexOf(currentController) >= 0) {
			controllerIndex--;
		}
		if (controllerIndex < 0) {
			controllerIndex = 0;
			return;
		}
		currentController = controllers.get(controllerIndex);
		showCurrentController();
	}

	/**
	 * Show a given controller's UI as a modal dialog
	 * 
	 * @param controller the controller of the view which needs to be displayed a modal
	 */
	public void showModalController(ViewController<?> controller) {
		currentController = controller;
		showCurrentController();
	}

	/**
	 * @return The controller of the current view in the Wizard 
	 */
	public ViewController<?> currentController() {
		return currentController;
	}

	/**
	 * @return The last controller of the UI from the previous step in the Wizard
	 */
	public ViewController<?> lastController() {
		return lastController;
	}
	
	/**
	 * Get the current state object managed by this wizard
	 * 
	 * @return the current state object managed by this wizard
	 */
	public T getState() {
		return state;
	}

	/**
	 * Set the state object to be managed by the wizard
	 * 
	 * @param state the state object to be managed by the wizard
	 */
	public void setState(T state) {
		this.state = state;
	}

	/**
	 * Display the View corresponding to the current step in the Wizard
	 */
	protected abstract void showCurrentController();
	
	/**
	 * Callback for actions to be performed when the Wizard ends
	 */
	protected void finalControllerEnded() { }
}
