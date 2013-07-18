package de.uniba.wiai.kinf.lehre.ma13.view;

import javax.swing.ImageIcon;

import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IBackgroundImage;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IOrderedObject;

/**
 * 
 * @author lukas
 * 
 */
public class LayerViewListItem 
{
    // the object associated with this list-item
    private IOrderedObject object_ = null;
    // image icon (layer or polygon?)
    private ImageIcon icon_ = null;
    
 
    public LayerViewListItem(IOrderedObject object) 
    {
    	object_ = object;
    	
    	if(object_ instanceof ILayer)
    	{
    		icon_ = new ImageIcon(getClass().getResource("/res/layer.png"));
    	}
    	else if(object_ instanceof IBackgroundImage)
    	{
    		icon_ = new ImageIcon(getClass().getResource("/res/image.png"));
    	}
    	else
    	{
    		icon_ = new ImageIcon(getClass().getResource("/res/polygon.png"));
    	}
    }
 
    // return the icon for the jlist-icon
    public ImageIcon getIcon() 
    {
    return icon_;
    }
 
    // returns the text for the JList item
    public String getText() 
    {
        return object_.getName();
    }
    
    public IOrderedObject getObject()
    {
    	return object_;
    }
}
