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

import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IBackgroundImage;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;

public class LayerViewCellRenderer extends JPanel implements ListCellRenderer<LayerViewListItem>
{
	private static final long serialVersionUID = 1L;
	private JLabel label_ = null;
	private JButton visibilityButton_ = null;
 
    public LayerViewCellRenderer()
    {
        // Konstruktor des JPanels mit FlowLayout aufrufen
        super(new FlowLayout(FlowLayout.LEFT));
 
        // JPanel undurchsichtig machen
        setOpaque(true);

        visibilityButton_ = new JButton();
        add(visibilityButton_);
           
        // JLabel instanzieren, durchsichtig machen und hinzufügen    
        label_ = new JLabel();
        label_.setOpaque(false);
        add(label_);
    }
 
    public Component getListCellRendererComponent(JList<? extends LayerViewListItem> list, // JList Objekt
    												final LayerViewListItem value, // anzuzeigende Komponente
                                                  int index,    // Zellenindex
                                                  boolean iss,  // Ist selektiert?
                                                  boolean chf)  // Hat den Fokus?
    {
        // JLabel das Icon aus unserem MyListItem zuweisen
        label_.setIcon(value.getIcon());
 
        // JLabel den Text aus unserem MyListItem zuweisen
        label_.setText(value.getText());
 
		visibilityButton_.setIcon(new ImageIcon(
				(value.getObject().isVisible() ? "res/layer_visible.png" : "res/layer_invisible.png")
				));
		
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

        this.setName(value.getObject().getName());
		
       // Hintergrundfarbe des JPanels bei Fokuswechseln definieren
        if(iss) setBackground(Color.lightGray); // Hat den Fokus
        else setBackground(list.getBackground()); // Hat den Fokus nicht
        
        return this;
    }
}
