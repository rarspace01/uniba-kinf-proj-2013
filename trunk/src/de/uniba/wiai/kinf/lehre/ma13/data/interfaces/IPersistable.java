package de.uniba.wiai.kinf.lehre.ma13.data.interfaces;

/**
 * interface for DB persistance management
 * @author denis
 *
 */
public interface IPersistable {

	/**
	 * method to Save a object to the DB
	 * @param toBeSaved
	 */
	public void saveToDB(Object toBeSaved);
	
	/**
	 * methodToLoad a object from the DB
	 * @return
	 */
	public Object loadFromDB();
	
}
