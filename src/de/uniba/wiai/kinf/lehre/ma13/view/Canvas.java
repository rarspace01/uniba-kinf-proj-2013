package de.uniba.wiai.kinf.lehre.ma13.view;

import java.awt.Color;
import java.awt.Graphics;
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
	private IAppDelegate _appDelegate;
	private DrawGeometry _drawGeometry;
	
	/** temporary queue of geometries which should
	 * be drawn at the end of the paint-method
	 */
	private Stack<IGeometry>	_tempDrawQueue;
	private DrawGeometry	_tempDrawGeometry;
	
	public Canvas(IAppDelegate appDelegate)
	{
		_appDelegate = appDelegate;
	}
	
	@Override
	public void paint(Graphics g) {
		
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		

		for (ILayer layer: _appDelegate.getLayerStore().getVisibleLayers()) {
			
			for(IGeometry geometry: layer.getVisibleGeometries()) {
				_drawGeometry.draw(g, geometry);
			}
		}
		
		if(_tempDrawQueue != null)
			for(IGeometry geometry: _tempDrawQueue) {
				_tempDrawGeometry.draw(g, geometry);
			}
	}

	@Override
	public void setGeometryDrawer(DrawGeometry drawGeometry) {
		_drawGeometry = drawGeometry;
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
		if(_tempDrawQueue == null)
		{
			_tempDrawQueue = new Stack<IGeometry>();
		}
		if(!stack && !_tempDrawQueue.isEmpty())
		{
			_tempDrawQueue.pop();
		}
		_tempDrawQueue.push(tempGeometry);
		_tempDrawGeometry = tempDrawGeometry;
	}

	public void clearTempGeometries()
	{
		if(_tempDrawQueue != null)
			_tempDrawQueue.clear();
	}
}
