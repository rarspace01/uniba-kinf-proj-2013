package de.uniba.wiai.kinf.lehre.ma13.view;

import javax.swing.ImageIcon;

public class LayerViewListItem 
{
    // Das anzuzeigende Icon
    private ImageIcon icon = null;
 
    // Der Text
    private String text;
 
    public LayerViewListItem(String iconpath, String text) 
    {
        // Erzeugung des ImageIcons durch Angabe des Bild-Quellpfads
        icon = new ImageIcon(iconpath);
 
        // Zuweisung des Textes
        this.text = text;
    }       
 
    // Liefert das Icon
    public ImageIcon getIcon() 
    {
    return icon;
    }
 
    // Liefert den Text
    public String getText() 
    {
        return text;
    }
}
