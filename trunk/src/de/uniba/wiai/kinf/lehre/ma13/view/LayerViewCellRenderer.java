package de.uniba.wiai.kinf.lehre.ma13.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;

/**
 * 
 * @author lukas
 * 
 */
public class LayerViewCellRenderer extends JPanel implements ListCellRenderer<LayerViewListItem>
{
	private static final long serialVersionUID = 1L;
	private JLabel label_ = null;
	private JButton visibilityButton_ = null;
	private IAppDelegate appDelegate_;
 
    public LayerViewCellRenderer(IAppDelegate appDelegate)
    {
        super(new FlowLayout(FlowLayout.LEFT));
 
        // set JPanel opaque
        setOpaque(true);
        
        appDelegate_ = appDelegate;

        visibilityButton_ = new JButton();
        add(visibilityButton_);
           
        // create new JLabel and set it opaque
        label_ = new JLabel();
        label_.setOpaque(false);
        add(label_);
    }
 
    public Component getListCellRendererComponent(JList<? extends LayerViewListItem> list, // JList instance (LayerView)
    												final LayerViewListItem value, // item of object to display
                                                  int index,    // index of this cell
                                                  boolean iss,  // selected?
                                                  boolean chf)  // focused?
    {
        // set icon for JLabel
        label_.setIcon(value.getIcon());
 
        // set text for JLabel
        label_.setText(value.getText());
 
        // set icon for visibility button (visible or invisible?)
		visibilityButton_.setIcon(new ImageIcon(
				(value.getObject().isVisible() ? getClass().getResource("/res/layer_visible.png") : getClass().getResource("/res/layer_invisible.png"))
				));
		// if parent is invisible, disable visibility-button
		if(value.getObject() instanceof IGeometry)
		{
			if( ((IGeometry)value.getObject()).getParent() != null && ((IGeometry)value.getObject()).getParent().isVisible() )
			{
				visibilityButton_.setEnabled(true);
			}
			else
			{
				visibilityButton_.setEnabled(false);
			}
		}
		else
		{
			visibilityButton_.setEnabled(true);
		}

		// set name of this button
        this.setName(value.getObject().getName());
		
        boolean defaultBackground = true;
        // set background-color, according to current selection
        if(iss)
        {
        	// if item is selected, set grey
        	setBackground(Color.gray);
        	defaultBackground = false;
        }
        else
        {
        	if(value.getObject() instanceof IGeometry)
        	{
        		if(appDelegate_.getLayerStore().getActiveLayer().equals(((IGeometry)value.getObject()).getParent()))
        		{
        			// if current geometry is part of an active layer
        			// set background color light grey
        			setBackground(Color.lightGray);
                	defaultBackground = false;
        		}
        	}
        	else if(value.getObject() instanceof ILayer)
        	{
        		if(appDelegate_.getLayerStore().getActiveLayer().equals(value.getObject()))
        		{
                	// if layer is active, set grey
                	setBackground(Color.gray);
                	defaultBackground = false;        			
        		}
        	}
        }
        if(defaultBackground)
        	setBackground(list.getBackground());
        
        return this;
    }
}
