package de.uniba.wiai.kinf.lehre.ma13.model;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;

public class Polygon extends OrderedObject implements IGeometry {

	private float _opacity;
	private ILayer _parent;
	
	/** list of points, position in "units" in respect to the background image */
	private List<Point2D> _points;
	
	public Polygon(Long objectId)
	{
		setObjectId(objectId);
		_points = new LinkedList<Point2D>();
	}
	
	@Override
	public void setOpacity(float opacity) {
		_opacity = opacity;
	}

	@Override
	public float getOpacity() {
		return _opacity;
	}

	@Override
	public ILayer getParent() {
		return _parent;
	}

	@Override
	public void setParent(ILayer parentLayer) {
		_parent = parentLayer;
	}
	
	public List<Point2D> getPoints()
	{
		return _points;
	}

	@Override
	public boolean inBoundingBox(float x1, float x2, float y1, float y2) {
		// create bounding box
		Rectangle boundingBox = new Rectangle(Math.round(x1), Math.round(y1), Math.round(Math.abs(x1-x2)), Math.round(Math.abs(y1-y2)));
		
		// create polygon
		java.awt.Polygon polygon = new java.awt.Polygon();
		for(Point2D point : _points)
		{
			polygon.addPoint((int)point.getX(), (int)point.getY());
		}
		
		// check if bounding box of polygon intersects with given box
		return polygon.getBounds().intersects(boundingBox);
	}
}
