package de.uniba.wiai.kinf.lehre.ma13.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Stack;

import javax.swing.JComponent;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions.MouseAction;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;
import de.uniba.wiai.kinf.lehre.ma13.view.drawtemplates.DrawGeometry;
import de.uniba.wiai.kinf.lehre.ma13.view.interfaces.ICanvas;

public class Canvas extends JComponent implements ICanvas  {

	private static final long serialVersionUID = 1L;
	private IAppDelegate appDelegate_;
	private DrawGeometry drawGeometry_;
	private DrawGeometry drawBackground_;
	
	/** temporary queue of geometries which should
	 * be drawn at the end of the paint-method
	 */
	private Stack<IGeometry>	tempDrawQueue_;
	private DrawGeometry	tempDrawGeometry_;
	
	public Canvas(IAppDelegate appDelegate)
	{
		appDelegate_ = appDelegate;
	}
	
	@Override
	public void paint(Graphics g) {
		
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		drawBackground_.draw(g, appDelegate_.getLayerStore().getBackgroundImage());

		for (ILayer layer: appDelegate_.getLayerStore().getVisibleLayers()) {
			
			for(IGeometry geometry: layer.getVisibleGeometries()) {
				drawGeometry_.draw(g, geometry);
			}
		}
		
		if(tempDrawQueue_ != null)
			for(IGeometry geometry: tempDrawQueue_) {
				tempDrawGeometry_.draw(g, geometry);
			}
	}

	@Override
	public void setGeometryDrawer(DrawGeometry drawGeometry) {
		drawGeometry_ = drawGeometry;
	}

	@Override
	public void setBackgroundDrawer(DrawGeometry drawBackground) {
		drawBackground_ = drawBackground;
	}

	/**
	 * adds mouse listener to the canvas
	 */
	public void setMouseAction(MouseAction mouseAction)
	{
		for(MouseListener ml: getMouseListeners())
		{
			removeMouseListener(ml);
		}
		for(MouseMotionListener mml: getMouseMotionListeners())
		{
			removeMouseMotionListener(mml);
		}
		
		addMouseListener(mouseAction);
		addMouseMotionListener(mouseAction);
	}
	
	public void addTempGeometry(DrawGeometry tempDrawGeometry, IGeometry tempGeometry, boolean stack)
	{
		if(tempDrawQueue_ == null)
		{
			tempDrawQueue_ = new Stack<IGeometry>();
		}
		if(!stack && !tempDrawQueue_.isEmpty())
		{
			tempDrawQueue_.pop();
		}
		tempDrawQueue_.push(tempGeometry);
		tempDrawGeometry_ = tempDrawGeometry;
	}

	public void clearTempGeometries()
	{
		if(tempDrawQueue_ != null)
			tempDrawQueue_.clear();
	}
	
	public Rectangle getCanvasClippingBounds()
	{
		// TODO: offset if it does not start at 0, 0?
		Rectangle clippingBounds = new Rectangle(0, 0, this.getWidth(), this.getHeight());
		return clippingBounds;
	}
}
