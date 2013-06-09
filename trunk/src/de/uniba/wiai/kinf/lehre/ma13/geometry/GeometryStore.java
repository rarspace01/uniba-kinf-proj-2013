package de.uniba.wiai.kinf.lehre.ma13.geometry;

import java.util.ArrayList;
import java.util.List;

public class GeometryStore 
	implements IGeometryStore
{
	private final List<IGeometry> _geometries = new ArrayList<>();

	@Override
	public boolean add(IGeometry geom) {
		return _geometries.add(geom);
	}

	@Override
	public boolean remove(IGeometry geom) {
		return _geometries.remove(geom);
	}

	@Override
	public List<IGeometry> getGeometries() {
		return _geometries;				
	}

}
