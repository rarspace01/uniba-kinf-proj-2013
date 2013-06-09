package de.uniba.wiai.kinf.lehre.ma13;

import java.awt.Point;
import java.awt.geom.Point2D;

import de.uniba.wiai.kinf.lehre.ma13.geometry.IGeometry;
import de.uniba.wiai.kinf.lehre.ma13.geometry.IGeometryStore;

public interface ICanvas {
	void setGeometryStore(IGeometryStore geomStore);
	IGeometryStore getGeometryStore();
	
	Point2D toWorldCoordinates(Point pt);
	Point toScreenCoordinates(Point2D pt);
}
