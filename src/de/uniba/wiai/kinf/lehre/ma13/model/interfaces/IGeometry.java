package de.uniba.wiai.kinf.lehre.ma13.model.interfaces;

public interface IGeometry extends IOrderedObject
{
	public void setOpacity(float opacity);
	public float getOpacity();
	
	/**
	 * every geometry has a parent which is a layer
	 */
	public ILayer getParent();
	/**
	 * set the parent of the geometry. Every parent is a layer
	 */
	public void setParent(ILayer parentLayer);
}
