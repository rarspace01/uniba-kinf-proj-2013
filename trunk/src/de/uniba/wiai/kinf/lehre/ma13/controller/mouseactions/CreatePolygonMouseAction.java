package de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions;

import java.awt.Color;
import java.awt.Point;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.model.Polygon;
import de.uniba.wiai.kinf.lehre.ma13.view.drawtemplates.DrawPolygonUnfinished;

public class CreatePolygonMouseAction extends MouseAction {
	
	private Polygon polygon_;
	private boolean firstClick_ = false;
	
	public CreatePolygonMouseAction(IAppDelegate appDelegate) {
		super(appDelegate);
	}

	@Override
	public void onmouseMoved(boolean dragged, int x, int y) {
		if(firstClick_)
		{
			polygon_.getPoints().remove(polygon_.getPoints().size()-1);
			polygon_.getPoints().add(appDelegate_.getUtil().toWorldCoordinates(new Point(x, y)));
			
			appDelegate_.getWindow().getCanvas().addTempGeometry(new DrawPolygonUnfinished(appDelegate_), polygon_, false);
			appDelegate_.getWindow().getCanvas().repaint();
		}
	}

	@Override
	public void onmouseDown(int x, int y) {
		
		Point worldCoordinates = appDelegate_.getUtil().toWorldCoordinates(new Point(x, y));
		
		if(firstClick_ == false)
		{
			polygon_ = new Polygon(appDelegate_.getId());
			polygon_.setName("Polygon " + polygon_.getObjectId());
			polygon_.setVisibility(true);
			polygon_.setOpacity(0.8f);
			polygon_.setColor(new Color(0, 0, 100));
			firstClick_ = true;
			polygon_.getPoints().add(worldCoordinates);
		}
		else
		{
			if(polygon_.getPoints().size() > 1 && (Math.abs(polygon_.getPoints().get(0).getX() - worldCoordinates.x) + Math.abs(polygon_.getPoints().get(0).getY() - worldCoordinates.y)) < 10)
			{
				polygon_.getPoints().remove(polygon_.getPoints().size()-1);
				appDelegate_.getLayerStore().getVisibleLayers().get(0).getGeometries().add(polygon_);
				polygon_ = null;
				firstClick_ = false;
				appDelegate_.getWindow().getCanvas().clearTempGeometries();
				appDelegate_.getWindow().refresh();
				return;
			}
		}
		
		polygon_.getPoints().add(worldCoordinates);
	}

	@Override
	public void onmouseUp(int x, int y) { }

}
