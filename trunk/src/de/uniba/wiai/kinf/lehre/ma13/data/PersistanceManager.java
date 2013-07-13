package de.uniba.wiai.kinf.lehre.ma13.data;

import de.uniba.wiai.kinf.lehre.ma13.data.interfaces.IPersistanceManager;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayerStore;

public class PersistanceManager implements IPersistanceManager {

	@Override
	public void save(ILayerStore layerStore, String filename) {

		// Step 1 - Retrieve & save the Background Image
		PBackgroundImage pbi=new PBackgroundImage();
		pbi.saveToDB(layerStore.getBackgroundImage());

		// Step 2 - Retrieve & save the Layers
		PLayers pl=new PLayers();
		pl.saveToDB(layerStore.getAllLayers());
	}

	@Override
	public ILayerStore load(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

}
