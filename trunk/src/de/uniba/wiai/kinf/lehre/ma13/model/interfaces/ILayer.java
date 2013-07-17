package de.uniba.wiai.kinf.lehre.ma13.model.interfaces;

import java.util.List;

/**
 * interface for the layer. Helps to retrieve the contained geometries and visible objects 
 * @author denis
 *
 */
public interface ILayer extends IOrderedObject
{
	/**
	 * get complete list of geometries in their respective order
	 */
	public List<IGeometry> getGeometries();
	
	/**
	 * get ordered list of all visible geometries
	 * NOTE: they may be visible, but have opactity zero!
	 */
	public List<IGeometry> getVisibleGeometries();

	/**
	 * returns the list of polygons that are (partially) within the bounding box
	 */
	public List<IGeometry> getGeometriesInBoundingBox(int x1, int x2, int y1, int y2);
}
