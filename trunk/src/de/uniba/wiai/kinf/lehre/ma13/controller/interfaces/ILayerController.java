package de.uniba.wiai.kinf.lehre.ma13.controller.interfaces;

/**
 * 
 * @author lukas
 * 
 */
public interface ILayerController
{
	/**
	 * delete the given object from store (either layer or geometry).
	 * the object is defined by its (internal) unique ID.
	 * 
	 * NOTE: if a layer is defined to be deleted, all containing objects/geometry
	 * will be deleted, too!
	 */
	public boolean deleteObject(Long objectId);
	
	/**
	 * moves the given geometry to a new position.
	 * Position is specified by newParentLayer, will be inserted after object
	 * specified by beforeId (null if object should be first)
	 */
	public boolean changeOrderGeometry(Long objectId, Long newParentLayer, Long beforeId);
	
	/**
	 * moves the given layer after layer specified by beforeId.
	 * (beforeId is null if layer should be first)
	 */
	public boolean changeOrderLayer(Long objectId, Long beforeId);
	
	/**
	 * sets the visibility of an object (can be a layer or a polygon!)
	 */
	public boolean setVisibility(Long objectId, boolean visibile);
	
	/**
	 * sets the opacity of a geometry (can NOT a layer!)
	 */
	public boolean setOpacity(Long objectId, boolean opacity);
}
