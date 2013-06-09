package de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions;

import java.awt.geom.Point2D;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.model.Polygon;
import de.uniba.wiai.kinf.lehre.ma13.view.drawtemplates.DrawPolygonRaw;

public class InstantPrintMouseAction extends MouseAction {
	
	private Polygon _polygon;
	
	public InstantPrintMouseAction(IAppDelegate appDelegate) {
		super(appDelegate);
	}

	@Override
	public void onmouseMoved(boolean dragged, int x, int y) {
		if(dragged)
		{
			_polygon.getPoints().add(new Point2D.Double(x, y));
			_appDelegate.getWindow().getCanvas().getGraphics().drawLine(oldX, oldY, x, y);
		}
	}

	@Override
	public void onmouseDown(int x, int y) {
		_polygon = new Polygon(_appDelegate.getId());
		_polygon.setName("Polygon " + _polygon.getObjectId());
	}

	@Override
	public void onmouseUp(int x, int y) {
		if(mouseDragged)
		{
			_appDelegate.getLayerStore().getAllLayers().get(0).getGeometries().add(_polygon);
			_appDelegate.getWindow().refresh();
		}
	}

}
