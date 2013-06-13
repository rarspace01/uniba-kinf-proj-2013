package de.uniba.wiai.kinf.lehre.ma13.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IBackgroundImage;
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
					appDelegate_.getWindow().refresh();
				}
			}
		};
		this.addMouseListener(mouseListener_);
		
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.addListSelectionListener(new ListSelectionListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				if(!e.getValueIsAdjusting())
				{
					int newIndex = ((JList<LayerViewListItem>)e.getSource()).getMaxSelectionIndex();
					if(newIndex < 0)
					{
						// moving polygon is disabled
						appDelegate_.getWindow().getToolBar().getComponent(2).setEnabled(false);
						// deleting object is disabled
						appDelegate_.getWindow().getToolBar().getComponent(3).setEnabled(false);
						return;
					}
					
					LayerViewListItem item = getModel().getElementAt(newIndex);
					
					if(item.getObject() instanceof IBackgroundImage)
					{
						// moving polygon is disabled
						appDelegate_.getWindow().getToolBar().getComponent(2).setEnabled(false);
						// deleting object is disabled
						appDelegate_.getWindow().getToolBar().getComponent(3).setEnabled(false);
					}
					else if(item.getObject() instanceof ILayer)
					{
						// moving polygon is disabled
						appDelegate_.getWindow().getToolBar().getComponent(2).setEnabled(false);
						// deleting object is enabled
						appDelegate_.getWindow().getToolBar().getComponent(3).setEnabled(true);
					}
					else if(item.getObject() instanceof IGeometry)
					{
						// moving polygon is enabled
						appDelegate_.getWindow().getToolBar().getComponent(2).setEnabled(true);
						// deleting object is enabled
						appDelegate_.getWindow().getToolBar().getComponent(3).setEnabled(true);
						
						// polygon selected, refresh canvas
						appDelegate_.getWindow().getCanvas().repaint();
					}
				}
			}
		});
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
