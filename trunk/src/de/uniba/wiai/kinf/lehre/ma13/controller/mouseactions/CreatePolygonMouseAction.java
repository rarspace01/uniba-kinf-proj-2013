package de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions;

import java.awt.geom.Point2D;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.model.Polygon;
import de.uniba.wiai.kinf.lehre.ma13.view.drawtemplates.DrawPolygonUnfinished;

public class CreatePolygonMouseAction extends MouseAction {
	
	private Polygon _polygon;
	private boolean firstClick = false;
	private int lastX, lastY;
	
	public CreatePolygonMouseAction(IAppDelegate appDelegate) {
		super(appDelegate);
	}

	@Override
	public void onmouseMoved(boolean dragged, int x, int y) {
		if(firstClick)
		{
	
			_polygon.getPoints().remove(_polygon.getPoints().size()-1);
			_polygon.getPoints().add(new Point2D.Double(x, y));
			
			_appDelegate.getWindow().getCanvas().addTempGeometry(new DrawPolygonUnfinished(), _polygon, false);
			_appDelegate.getWindow().getCanvas().repaint();
		}
	}

	@Override
	public void onmouseDown(int x, int y) {
		
		if(firstClick == false)
		{
			_polygon = new Polygon(_appDelegate.getId());
			_polygon.setName("Polygon " + _polygon.getObjectId());
			firstClick = true;
			_polygon.getPoints().add(new Point2D.Double(x, y));
		}
		else
		{
			if(_polygon.getPoints().size() > 1 && Math.abs(_polygon.getPoints().get(0).getX() - x) < 10 && Math.abs(_polygon.getPoints().get(0).getY() - y) < 10)
			{
				_polygon.getPoints().remove(_polygon.getPoints().size()-1);
				_appDelegate.getLayerStore().getAllLayers().get(0).getGeometries().add(_polygon);
				_polygon = null;
				firstClick = false;
				_appDelegate.getWindow().getCanvas().clearTempGeometries();
				_appDelegate.getWindow().refresh();
				return;
			}
		}
		
		_polygon.getPoints().add(new Point2D.Double(x, y));
		lastX = x;
		lastY = y;
	}

	@Override
	public void onmouseUp(int x, int y) { }

}
