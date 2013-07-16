package de.uniba.wiai.kinf.lehre.ma13.data;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.data.interfaces.IPersistanceManager;
import de.uniba.wiai.kinf.lehre.ma13.model.LayerStore;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IBackgroundImage;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayerStore;

public class PersistanceManager implements IPersistanceManager {

	private IAppDelegate appDelegate_;
	private String filename_;
	
	public PersistanceManager(IAppDelegate appDelegate) {
		appDelegate_ = appDelegate;
	}
	
	@Override
	public void save(ILayerStore layerStore, String filename) {

		filename_ = filename;
		
		//Step0 - cleanupDB
		DataBaseManager dbm = new DataBaseManager();
		
		dbm.openDb(filename_);
		dbm.cleanTables();
		
		// Step 1 - Retrieve & save the Background Image
		PBackgroundImage pbi=new PBackgroundImage();
		pbi.saveToDB(layerStore.getBackgroundImage());

		// Step 2 - Retrieve & save the Layers
		PLayers pl=new PLayers(appDelegate_);
		pl.saveToDB(layerStore.getAllLayers());
		
		dbm.closeDb();
	}

	@Override
	public void load(ILayerStore layerStore, String filename) {
		
		filename_ = filename;
		
		//Step0 - open DB
		DataBaseManager dbm = new DataBaseManager();
		dbm.openDb(filename_);
		
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
		
		dbm.closeDb();
	}

}
