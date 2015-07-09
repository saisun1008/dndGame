package dnd.ui.controller;

import javax.swing.UIManager;

/**
 * Main application driver
 * 
 */
public class AppLauncher {

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				}catch (Exception e) {
				}
				new MenuSelectionController();
			}
		});
	}
}
