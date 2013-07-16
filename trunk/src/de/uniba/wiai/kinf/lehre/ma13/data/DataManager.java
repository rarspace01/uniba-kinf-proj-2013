package de.uniba.wiai.kinf.lehre.ma13.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.data.interfaces.IDataManager;
import de.uniba.wiai.kinf.lehre.ma13.model.Layer;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IBackgroundImage;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayerStore;

public class DataManager implements IDataManager {

	private IAppDelegate appDelegate_;
	private String filename_;
	private DataManagerSQLiteSingleton currentDB_;
	
	public DataManager(IAppDelegate appDelegate) {
		appDelegate_ = appDelegate;
	}
	
	public void loadTestData()
	{
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
		
		// Step 1 - Retrieve & save the Background Image
		PBackgroundImage pbi=new PBackgroundImage();
		pbi.saveToDB(layerStore.getBackgroundImage());

		// Step 2 - Retrieve & save the Layers
		PLayers pl=new PLayers(appDelegate_);
		pl.saveToDB(layerStore.getAllLayers());
		
		closeDb();
	}

	@Override
	public void load(ILayerStore layerStore, String filename) {
		
		filename_ = filename;
		
		//Step0 - open DB
		openDb(filename_);
		
		// Step 1 - Retrieve the Background Image
		PBackgroundImage pbi=new PBackgroundImage();
		IBackgroundImage loadedImage = pbi.loadFromDB();
		
		layerStore.getBackgroundImage().setData(loadedImage.getData());
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
	
	@Override
	public void openDb(String filename) {
		this.filename_=filename;
		currentDB_=DataManagerSQLiteSingleton.getInstance(this.filename_);
		
		if(databaseIsEmpty()){
			createTables();
		}
	}
	
	/**
	 * checks if the sqlite db is empty
	 * @return boolean
	 */
	private boolean databaseIsEmpty(){
		ResultSet rs;
		int count=0;
		boolean isEmpty = true;
		
		try {
			rs = currentDB_.select("SELECT count(*) FROM sqlite_master WHERE type = 'table' AND name != 'android_metadata' AND name != 'sqlite_sequence';");
			
			while(rs.next()){
				count=rs.getInt(1);
				System.out.println("table count: "+count);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(count>0){
			isEmpty=false;
		}
		
		return isEmpty;
	}
	
	/**
	 * creates the initial tables
	 */
	private void createTables() {
		
		try {
			//point table
			currentDB_.execute("CREATE TABLE point (pointid INTEGER PRIMARY KEY, polygonid NUMERIC, x NUMERIC, y NUMERIC);");
			//poly table
			currentDB_.execute("CREATE TABLE polygon (polygonid INTEGER PRIMARY KEY, name TEXT, color NUMERIC, layerid NUMERIC, isvisible NUMERIC, opacity NUMERIC);");
			//layer table
			currentDB_.execute("CREATE TABLE layer (layerid INTEGER PRIMARY KEY, name TEXT, isvisible NUMERIC, color NUMERIC, opacity NUMERIC);");
			//background image
			currentDB_.execute("CREATE TABLE backgroundimage (imageid INTEGER PRIMARY KEY, scalex NUMERIC, scaley NUMERIC, x NUMERIC, y NUMERIC, imagepath TEXT, image BLOB, opacity NUMERIC);");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void cleanTables(){
		
		try {
			currentDB_.execute("DELETE FROM point");
			currentDB_.execute("DELETE FROM polygon");
			currentDB_.execute("DELETE FROM layer");
			currentDB_.execute("DELETE FROM backgroundimage");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void closeDb() {
		currentDB_.dispose();
	}

}
