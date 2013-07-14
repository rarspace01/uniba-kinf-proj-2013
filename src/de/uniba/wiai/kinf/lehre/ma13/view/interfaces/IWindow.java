package de.uniba.wiai.kinf.lehre.ma13.view.interfaces;

import javax.swing.JSlider;
import javax.swing.JToolBar;

import de.uniba.wiai.kinf.lehre.ma13.view.LayerView;

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
	
	/**
	 * gets the toolbar
	 */
	public JToolBar getToolBar();
	
	/**
	 * returns the layer-view (JList)
	 */
	public LayerView getLayerView();
	
	/**
	 * returns the slider for opacity
	 */
	public JSlider getOpacitySlider();
}
