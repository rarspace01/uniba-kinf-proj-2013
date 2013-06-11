package de.uniba.wiai.kinf.lehre.ma13.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;

public class LayerView extends JList<LayerViewListItem> {
	
	private static final long serialVersionUID = 1L;
	private IAppDelegate appDelegate_;
	private boolean lockrepaint_ = false;
	private MouseListener mouseListener_ = null;
	

	public LayerView(IAppDelegate appDelegate)
	{
		appDelegate_ = appDelegate;
		
		mouseListener_ = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) { }
			@Override
			public void mousePressed(MouseEvent arg0) { }
			@Override
			public void mouseExited(MouseEvent arg0) { }
			@Override
			public void mouseEntered(MouseEvent arg0) { }

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getX() >= 5 && arg0.getX() <= 55)
				{
					LayerViewListItem item = getModel().getElementAt(locationToIndex(arg0.getPoint()));
					item.getObject().setVisibility(!item.getObject().isVisible());
					System.out.println("Button clicked: " + getModel().getElementAt(locationToIndex(arg0.getPoint())).getObject().getName());
					appDelegate_.getWindow().refresh();
				}
			}
		};
		this.addMouseListener(mouseListener_);
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
			
			
			listModel.addElement(new LayerViewListItem(layer));
			
			
			for(IGeometry geometry: layer.getGeometries()) {
				listModel.addElement(new LayerViewListItem(geometry));
			}
		}
		
		listModel.addElement(new LayerViewListItem(appDelegate_.getLayerStore().getBackgroundImage()));
		
		setModel(listModel);
		setCellRenderer(new LayerViewCellRenderer());
		
		this.setFixedCellWidth(230);
		
		lockrepaint_ = false;
		super.repaint();
	}
}
