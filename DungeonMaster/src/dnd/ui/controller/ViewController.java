package dnd.ui.controller;

import java.awt.Container;

/**
 * The ViewController is a parent class for the character, level and game editor
 * controllers
 * 
 * @param <T>  A UI component
 */
public abstract class ViewController<T extends Container> {
	protected T view;

	/**
	 * Get the current view to be displayed, from the Game, Character or map
	 * editor views
	 * 
	 * @return The currently set view
	 */
	public T getView() {
		return view;
	}

	/**
	 * Set the current view to be displayed from the Game, Character or map editor views
	 * 
	 * @param view The view to set
	 */
	protected void setView(T view) {
		this.view = view;
	}
}
