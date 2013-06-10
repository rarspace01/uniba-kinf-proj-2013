package de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions;

import java.awt.Color;
import java.awt.Point;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.model.Polygon;
import de.uniba.wiai.kinf.lehre.ma13.view.drawtemplates.DrawPolygonUnfinished;

public class FreeHandPolygonMouseAction extends MouseAction {
	
	private Polygon polygon_;
	
	public FreeHandPolygonMouseAction(IAppDelegate appDelegate) {
		super(appDelegate);
	}

	@Override
	public void onmouseMoved(boolean dragged, int x, int y) {
		if(dragged)
		{
			polygon_.getPoints().add(appDelegate_.getUtil().toWorldCoordinates(new Point(x, y)));
			appDelegate_.getWindow().getCanvas().addTempGeometry(new DrawPolygonUnfinished(appDelegate_), polygon_, false);
			appDelegate_.getWindow().getCanvas().repaint();
		}
	}

	@Override
	public void onmouseDown(int x, int y) {
		polygon_ = new Polygon(appDelegate_.getId());
		polygon_.setName("Polygon " + polygon_.getObjectId());
		polygon_.setVisibility(true);
		polygon_.setOpacity(1.0f);
		polygon_.setColor(new Color(0, 255, 0));
	}

	@Override
	public void onmouseUp(int x, int y) {
		if(mouseDragged_)
		{
			appDelegate_.getWindow().getCanvas().clearTempGeometries();
			if(polygon_.getPoints().size() > 0)
				appDelegate_.getLayerStore().getVisibleLayers().get(0).getGeometries().add(polygon_);
			appDelegate_.getWindow().refresh();
		}
		polygon_ = null;
	}
	
	@Override
	public void onmouseDoubleClick(int x, int y) { }

}
