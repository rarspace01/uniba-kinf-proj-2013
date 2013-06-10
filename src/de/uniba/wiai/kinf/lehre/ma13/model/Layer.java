package de.uniba.wiai.kinf.lehre.ma13.model;

import java.util.LinkedList;
import java.util.List;

import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;

public class Layer extends OrderedObject implements ILayer
{
	List<IGeometry> allGeometries_;
	
	public Layer(Long objectId)
	{
		setObjectId(objectId);
		allGeometries_ = new LinkedList<IGeometry>();
	}
	
	@Override
	public List<IGeometry> getGeometries() {
		return allGeometries_;
	}

	@Override
	public List<IGeometry> getVisibleGeometries() {
		
		List<IGeometry> visibleGeometries = new LinkedList<IGeometry>();
		for(IGeometry geo: allGeometries_)
		{
			if(geo.isVisible())
				visibleGeometries.add(geo);
		}
		return visibleGeometries;
	}

	@Override
	public List<IGeometry> getGeometriesInBoundingBox(int x1, int x2, int y1, int y2) {
		
		List<IGeometry> boundingBoxGeometries = new LinkedList<IGeometry>();
		for(IGeometry geo: allGeometries_)
		{
			if(geo.isVisible() && geo.inBoundingBox(x1, x2, y1, y2))
				boundingBoxGeometries.add(geo);
		}
		return boundingBoxGeometries;
	}

}
