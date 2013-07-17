package de.uniba.wiai.kinf.lehre.ma13.controller.interfaces;

import de.uniba.wiai.kinf.lehre.ma13.controller.Util;
import de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions.MouseAction;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayerStore;
import de.uniba.wiai.kinf.lehre.ma13.view.interfaces.IWindow;

/**
 * 
 * @author lukas
 * 
 */
public interface IAppDelegate {
	/**
	 * initialise the main window, load all needed instances for functioning
	 * 
	 * Entry point of the application.
	 */
	public void init();

	/**
	 * initialise the main window and load up the given project.
	 */

	public void init(String fileName);

	public IWindow getWindow();

	public void setWindow(IWindow window);

	public ILayerStore getLayerStore();

	/**
	 * Strategy-Pattern: set the mouse-action which handles mouse events on the
	 * canvas
	 */
	public void setMouseAction(MouseAction ma);

	/**
	 * Strategy-Pattern: get the mouse-action object which handles mouse events
	 * on the canvas
	 */
	public MouseAction getMouseAction();

	public Util getUtil();

	/**
	 * short call at app delegate, will delegate call to util-class
	 */
	public Long getId();
}
