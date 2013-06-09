package de.uniba.wiai.kinf.lehre.ma13.model;

import java.util.LinkedList;
import java.util.List;

import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;

public class Layer extends OrderedObject implements ILayer
{
	List<IGeometry> _allGeometries;
	
	public Layer(Long objectId)
	{
		setObjectId(objectId);
		_allGeometries = new LinkedList<IGeometry>();
	}
	
	@Override
	public List<IGeometry> getGeometries() {
		return _allGeometries;
	}

	@Override
	public List<IGeometry> getVisibleGeometries() {
		return _allGeometries;
	}

	@Override
	public List<IGeometry> getGeometriesInBoundingBox(float x1, float x2,
			float y1, float y2) {
		return _allGeometries;
	}

}
