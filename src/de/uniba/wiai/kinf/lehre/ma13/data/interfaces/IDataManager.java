package de.uniba.wiai.kinf.lehre.ma13.data.interfaces;

import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayerStore;

/**
 * itnerface for handling the databasefiles
 * 
 * @author denis
 * 
 */
public interface IDataManager {

	/**
	 * saves thhe givven layerstore into the given filename
	 * 
	 * @param layerStore
	 * @param filename
	 */
	public void save(ILayerStore layerStore, String filename);

	/**
	 * loads the given file into the given layerstore
	 * 
	 * @param layerStore
	 * @param filename
	 * 
	 */
	public void load(ILayerStore layerStore, String filename);

	/**
	 * open a SQLite DB
	 * 
	 * @param filename
	 */
	public void loadInitialData();

}
