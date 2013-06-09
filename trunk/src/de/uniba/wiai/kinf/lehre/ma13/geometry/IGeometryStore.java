package de.uniba.wiai.kinf.lehre.ma13.geometry;

import java.util.List;

public interface IGeometryStore {
	
	boolean add(IGeometry geom);
	boolean remove(IGeometry geom);
	List<IGeometry> getGeometries();
}
