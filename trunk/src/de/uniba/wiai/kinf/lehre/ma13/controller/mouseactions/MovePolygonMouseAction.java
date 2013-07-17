package de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions;

import java.awt.Point;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.model.Polygon;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IOrderedObject;

/**
 * 
 * @author lukas
 * 
 */
public class MovePolygonMouseAction extends MouseAction {

	private IGeometry selectedGeometry_;
	private Point startingPoint_;
	
	public MovePolygonMouseAction(IAppDelegate appDelegate) {
		super(appDelegate);
		
		IOrderedObject selectedObject = appDelegate_.getWindow().getLayerView().getModel().getElementAt(
					appDelegate_.getWindow().getLayerView().getMaxSelectionIndex()
				).getObject();
		if(selectedObject instanceof IGeometry)
		{
			selectedGeometry_ = (IGeometry) selectedObject;
		}
	}

	@Override
	public void onmouseMoved(boolean dragged, int x, int y) {
		if(dragged)
		{
			if(selectedGeometry_ instanceof Polygon)
			{
				Polygon movingPolygon = (Polygon)selectedGeometry_;
				for(Point current : movingPolygon.getPoints())
				{
					current.x += (x - startingPoint_.x);
					current.y += (y - startingPoint_.y);
				}
				appDelegate_.getWindow().getCanvas().repaint();
				startingPoint_ = new Point(x, y);
			}
		}
	}

	@Override
	public void onmouseDown(int x, int y) {
		startingPoint_ = new Point(x, y);
	}

	@Override
	public void onmouseUp(int x, int y) {
		
	}

	@Override
	public void onmouseDoubleClick(int x, int y) {
		
	}

}
