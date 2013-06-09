package de.uniba.wiai.kinf.lehre.ma13.model;

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
}
