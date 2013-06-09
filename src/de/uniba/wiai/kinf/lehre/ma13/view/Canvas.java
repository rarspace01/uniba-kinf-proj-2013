package de.uniba.wiai.kinf.lehre.ma13.view;

import java.awt.Color;
import java.awt.Graphics;
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
		

		for (ILayer layer: _appDelegate.getLayerStore().getAllLayers()) {
			
			for(IGeometry geometry: layer.getGeometries()) {
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
	
	@Override
	public void repaint()
	{		
		repaint(0);
	}

	/**
	 * adds mouse listener to the canvas
	 */
	public void setMouseAction(MouseAction mouseAction)
	{
		
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
		_tempDrawQueue.clear();
	}
}
