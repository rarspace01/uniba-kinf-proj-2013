package de.uniba.wiai.kinf.lehre.ma13.view.interfaces;

import java.awt.Graphics;
import java.awt.Rectangle;

import de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions.MouseAction;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;
import de.uniba.wiai.kinf.lehre.ma13.view.drawtemplates.DrawGeometry;

public interface ICanvas
{
	/**
	 * (re)paint the canvas with the current state.
	 * Will invoke the controller.
	 */
	public void paint(Graphics g);
	
	/**
	 * trigger a repaint of the canvas
	 */
	public void repaint();
	
	/**
	 * set a template for drawing different geometries
	 */
	public void setGeometryDrawer(DrawGeometry drawGeometry);
	
	/**
	 * sets a template for drawing the background
	 */
	public void setBackgroundDrawer(DrawGeometry drawBackground);
	
	/**
	 * adds a specific mouse listener to the canvas
	 */
	public void setMouseAction(MouseAction mouseAction);
	
	/**
	 * returns the graphics component
	 */
	public Graphics getGraphics();
	
	/**
	 * as paint() is called asynchronously, use this method to add temporary
	 * geometries which should be drawn after clearing/repainting the canvas.
	 * Use the DrawGeometry algorithm and add the geometry to the list.
	 *
	 * NOTE: If the last element in the list should be replaced, set "stack" to false!
	 */
	public void addTempGeometry(DrawGeometry tempDrawGeometry, IGeometry tempGeometry, boolean stack);
	
	/**
	 * clears all temporary geometries
	 */
	public void clearTempGeometries();
	
	/**
	 * returns the bounds of the current clipping at the canvas
	 * if the zoom is 100%, it would return (0, 0, imagewidth, imageheight)
	 */
	public Rectangle getCanvasClippingBounds();
}
