package de.uniba.wiai.kinf.lehre.ma13.view;

import javax.swing.ImageIcon;

public class LayerViewListItem 
{
    // Das anzuzeigende Icon
    private ImageIcon icon_ = null;
 
    // Der Text
    private String text_;
 
    public LayerViewListItem(String iconpath, String text) 
    {
        // Erzeugung des ImageIcons durch Angabe des Bild-Quellpfads
        icon_ = new ImageIcon(iconpath);
 
        // Zuweisung des Textes
        this.text_ = text;
    }       
 
    // Liefert das Icon
    public ImageIcon getIcon() 
    {
    return icon_;
    }
 
    // Liefert den Text
    public String getText() 
    {
        return text_;
    }
}
