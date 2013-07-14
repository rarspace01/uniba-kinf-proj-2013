package de.uniba.wiai.kinf.lehre.ma13.data;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.data.interfaces.IPersistanceManager;
import de.uniba.wiai.kinf.lehre.ma13.model.LayerStore;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayerStore;

public class PersistanceManager implements IPersistanceManager {

	private IAppDelegate appDelegate_;
	
	public PersistanceManager(IAppDelegate appDelegate) {
		appDelegate_ = appDelegate;
	}
	
	@Override
	public void save(ILayerStore layerStore, String filename) {

		//Step0 - cleanupDB
		DataBaseManager dbm = new DataBaseManager();
		
		dbm.openDb(filename);
		dbm.cleanTables();
		
		// Step 1 - Retrieve & save the Background Image
		PBackgroundImage pbi=new PBackgroundImage();
		pbi.saveToDB(layerStore.getBackgroundImage());

		// Step 2 - Retrieve & save the Layers
		PLayers pl=new PLayers();
		pl.saveToDB(layerStore.getAllLayers());
	}

	@Override
	public ILayerStore load(String filename) {
		
		//Step0 - open DB
		DataBaseManager dbm = new DataBaseManager();
		dbm.openDb(filename);
		
		ILayerStore layerStore = new LayerStore(appDelegate_);
		
		// Step 1 - Retrieve the Background Image
		PBackgroundImage pbi=new PBackgroundImage();
		
		layerStore.setBackgroundImage(pbi.loadFromDB());

		// Step 2 - Retrieve & save the Layers
		PLayers pl=new PLayers();
		
		
		
		layerStore.getAllLayers().clear();
		layerStore.getAllLayers().addAll(pl.loadFromDB());
		
		return layerStore;
	}

}
