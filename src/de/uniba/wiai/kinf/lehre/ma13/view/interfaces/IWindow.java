package de.uniba.wiai.kinf.lehre.ma13.view.interfaces;

/**
 * the main window
 */
public interface IWindow
{
	/**
	 * refreshes the window, or rather the canvas / current clip
	 */
	public void refresh();
	
	/**
	 * returns the canvas
	 */
	public ICanvas getCanvas();
}
