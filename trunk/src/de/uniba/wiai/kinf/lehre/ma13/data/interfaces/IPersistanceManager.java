package de.uniba.wiai.kinf.lehre.ma13.data.interfaces;

import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayerStore;

/**
 * interface for persisting and loading the layerstore
 * @author denis
 *
 */
public interface IPersistanceManager {

	/**
	 * saves thhe givven layerstore into the given filename
	 * @param layerstore
	 * @param filename
	 */
	public void save(ILayerStore layerStore, String filename);
	
	/**
	 * loads the given file and returns a Layerstore	
	 * @param filename
	 * @return
	 */
	public ILayerStore load(String filename); 
	
}
