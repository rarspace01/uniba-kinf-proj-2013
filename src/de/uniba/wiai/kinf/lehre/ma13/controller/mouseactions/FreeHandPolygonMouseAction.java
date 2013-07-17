package de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions;

import java.awt.Point;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.model.Polygon;
import de.uniba.wiai.kinf.lehre.ma13.view.drawtemplates.DrawPolygonUnfinished;

/**
 * 
 * @author lukas
 * 
 */
public class FreeHandPolygonMouseAction extends MouseAction {
	
	private Polygon polygon_;
	
	public FreeHandPolygonMouseAction(IAppDelegate appDelegate) {
		super(appDelegate);
	}

	@Override
	public void onmouseMoved(boolean dragged, int x, int y) {
		if(dragged)
		{
			polygon_.getPoints().add(new Point(x, y));
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
		polygon_.setColor(null);
	}

	@Override
	public void onmouseUp(int x, int y) {
		if(mouseDragged_)
		{
			appDelegate_.getWindow().getCanvas().clearTempGeometries();
			if(polygon_.getPoints().size() > 0)
			{
				polygon_.setParent(appDelegate_.getLayerStore().getActiveLayer());
				appDelegate_.getLayerStore().getActiveLayer().getGeometries().add(polygon_);
			}
			// refresh the whole window
			appDelegate_.getWindow().refresh();
		}
		polygon_ = null;
	}
	
	@Override
	public void onmouseDoubleClick(int x, int y) { }

}
