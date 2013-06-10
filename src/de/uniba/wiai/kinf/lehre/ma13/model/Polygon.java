package de.uniba.wiai.kinf.lehre.ma13.model;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;

public class Polygon extends OrderedObject implements IGeometry {

	private float opacity_;
	private ILayer parent_;
	
	/** list of points, position in "units" in respect to the background image */
	private List<Point> points_;
	
	public Polygon(Long objectId)
	{
		setObjectId(objectId);
		points_ = new LinkedList<Point>();
	}
	
	@Override
	public void setOpacity(float opacity) {
		opacity_ = opacity;
	}

	@Override
	public float getOpacity() {
		return opacity_;
	}

	@Override
	public ILayer getParent() {
		return parent_;
	}

	@Override
	public void setParent(ILayer parentLayer) {
		parent_ = parentLayer;
	}
	
	public List<Point> getPoints()
	{
		return points_;
	}

	@Override
	public boolean inBoundingBox(int x1, int x2, int y1, int y2) {
		// create bounding box
		Rectangle boundingBox = new Rectangle(x1, y1, Math.abs(x1-x2), Math.abs(y1-y2));
		
		// create polygon
		java.awt.Polygon polygon = new java.awt.Polygon();
		for(Point point : points_)
		{
			polygon.addPoint(point.x, point.y);
		}
		
		// check if bounding box of polygon intersects with given box
		return polygon.getBounds().intersects(boundingBox);
	}
}