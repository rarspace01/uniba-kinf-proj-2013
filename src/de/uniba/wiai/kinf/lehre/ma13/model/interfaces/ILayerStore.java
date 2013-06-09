package de.uniba.wiai.kinf.lehre.ma13.model.interfaces;

import java.util.List;

public interface ILayerStore
{
	/**
	 * returns the complete list of all layers which contain
	 * all geometries.
	 */
	public List<ILayer> getAllLayers();
	
	/**
	 * get list of visible layers
	 */
	public List<ILayer> getVisibleLayers();
	
	/**
	 * will return all layers that have geometries that are (partially) within
	 * the given bounding box.
	 */
	public List<ILayer> getLayersInBoundingBox(float x1, float x2, float y1, float y2);
}
