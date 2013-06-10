package de.uniba.wiai.kinf.lehre.ma13.view;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;

public class LayerView extends JList<LayerViewListItem> {
	
	private static final long serialVersionUID = 1L;
	private IAppDelegate appDelegate_;
	private boolean lockrepaint_ = false;
	

	public LayerView(IAppDelegate appDelegate)
	{
		appDelegate_ = appDelegate;
	}
	
	public void repaint()
	{
		if(lockrepaint_)
			return;
		
		lockrepaint_ = true;
		if(appDelegate_ == null)
			return;
		
		DefaultListModel<LayerViewListItem> listModel = new DefaultListModel<LayerViewListItem>();	
		for (ILayer layer: appDelegate_.getLayerStore().getAllLayers()) {
			
			
			listModel.addElement(new LayerViewListItem("res/layer.png", layer.getName()));
			
			
			for(IGeometry geometry: layer.getGeometries()) {
				listModel.addElement(new LayerViewListItem("res/polygon.png", geometry.getName()));
			}
		}
		
		listModel.addElement(new LayerViewListItem("res/image.png", "Structure"));
		
		setModel(listModel);
		setCellRenderer(new LayerViewCellRenderer());
		
		this.setFixedCellWidth(230);
		
		lockrepaint_ = false;
		super.repaint();
	}
}
