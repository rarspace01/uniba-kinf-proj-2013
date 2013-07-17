package de.uniba.wiai.kinf.lehre.ma13.data;

import java.sql.SQLException;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.data.interfaces.IDataManager;
import de.uniba.wiai.kinf.lehre.ma13.model.Layer;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IBackgroundImage;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayerStore;

/**
 * class for loading and saving the data
 * @author denis
 *
 */
public class DataManager implements IDataManager {

	private IAppDelegate appDelegate_;
	private String filename_;
	private DataManagerSQLiteSingleton currentDB_;
	
	public DataManager(IAppDelegate appDelegate) {
		appDelegate_ = appDelegate;
	}
	
	/**
	 * loads the initial Data for Startup
	 */
	public void loadInitialData()
	{
		//setup Layer 1, so user can directly draw on the image, after loading one
		Layer firstLayer = new Layer(appDelegate_.getId());
		firstLayer.setName("Layer 1");
		firstLayer.setVisibility(true);
		appDelegate_.getLayerStore().getAllLayers().add(firstLayer);
		
	}
	
	@Override
	public void save(ILayerStore layerStore, String filename) {

		filename_ = filename;
		
		//Step0 - cleanupDB
		openDb(filename_);
		cleanTables();
		
		// Step 1 - save the Background Image
		PBackgroundImage pbi=new PBackgroundImage();
		pbi.saveToDB(layerStore.getBackgroundImage());

		// Step 2 - save the Layers
		PLayers pl=new PLayers(appDelegate_);
		pl.saveToDB(layerStore.getAllLayers());
		
		//close the DB for removing the file handler on the file
		closeDb();
	}

	@Override
	public void load(ILayerStore layerStore, String filename) {
		
		filename_ = filename;
		
		// Step0 - open DB
		openDb(filename_);
		
		// Step 1 - Retrieve the Background Image
		PBackgroundImage pbi=new PBackgroundImage();
		IBackgroundImage loadedImage = pbi.loadFromDB();
		
		//set Image data from DB
		layerStore.getBackgroundImage().setData(loadedImage.getData());
		//set the image dimensions for canvas rendering
		layerStore.getBackgroundImage().setImageDimensions(loadedImage.getImageDimensions());
		
		// reset global object-id
		appDelegate_.getUtil().setId(0L);
		
		// Step 2 - Retrieve & save the Layers
		PLayers pl=new PLayers(appDelegate_);
		
		layerStore.getAllLayers().clear();
		layerStore.getAllLayers().addAll(pl.loadFromDB());
		
		appDelegate_.getUtil().setId(appDelegate_.getUtil().getId() + 1);
		
		closeDb();
	}
	
	/**
	 * opens a database, checks for missing tables, creates the tables
	 * @param filename
	 */
	private void openDb(String filename) {
		this.filename_=filename;
		//create db connection
		currentDB_=DataManagerSQLiteSingleton.getInstance(this.filename_);
		
		//creates nonexisting tables. if tables already exists, nothing happens
		createTables();
	}
	
	/**
	 * creates the initial tables
	 */
	private void createTables() {
		
		try {
			//point table
			currentDB_.execute("CREATE TABLE IF NOT EXISTS point (pointid INTEGER PRIMARY KEY, polygonid NUMERIC, x NUMERIC, y NUMERIC);");
			//poly table
			currentDB_.execute("CREATE TABLE IF NOT EXISTS polygon (polygonid INTEGER PRIMARY KEY, name TEXT, color NUMERIC, layerid NUMERIC, isvisible NUMERIC, opacity NUMERIC);");
			//layer table
			currentDB_.execute("CREATE TABLE IF NOT EXISTS layer (layerid INTEGER PRIMARY KEY, name TEXT, isvisible NUMERIC, color NUMERIC, opacity NUMERIC);");
			//background image
			currentDB_.execute("CREATE TABLE IF NOT EXISTS backgroundimage (imageid INTEGER PRIMARY KEY, scalex NUMERIC, scaley NUMERIC, x NUMERIC, y NUMERIC, imagepath TEXT, image BLOB, opacity NUMERIC);");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * drops all tables
	 */
	private void dropTables() {
		
		try {
			//point table
			currentDB_.execute("DROP TABLE IF EXISTS point;");
			//poly table
			currentDB_.execute("DROP TABLE IF EXISTS polygon;");
			//layer table
			currentDB_.execute("DROP TABLE IF EXISTS layer;");
			//background image
			currentDB_.execute("DROP TABLE IF EXISTS backgroundimage;");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * remove existing tables & recreate with the current structure
	 */
	private void cleanTables(){
		
		dropTables();
		createTables();
		
	}
	
	/**
	 * close the current DB
	 */
	private void closeDb() {
		currentDB_.dispose();
	}

}
