package de.uniba.wiai.kinf.lehre.ma13.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class LayerViewCellRenderer extends JPanel implements ListCellRenderer<LayerViewListItem>
{
	private static final long serialVersionUID = 1L;
	private JLabel label = null;
 
    public LayerViewCellRenderer()
    {
        // Konstruktor des JPanels mit FlowLayout aufrufen
        super(new FlowLayout(FlowLayout.LEFT));
 
        // JPanel undurchsichtig machen
           setOpaque(true);
 
        // JLabel instanzieren, durchsichtig machen und hinzufügen    
        label = new JLabel();
        label.setOpaque(false);
        add(label);                
    }
 
    public Component getListCellRendererComponent(JList<? extends LayerViewListItem> list, // JList Objekt
    												LayerViewListItem value, // anzuzeigende Komponente
                                                  int index,    // Zellenindex
                                                  boolean iss,  // Ist selektiert?
                                                  boolean chf)  // Hat den Fokus?
    {
        // JLabel das Icon aus unserem MyListItem zuweisen
        label.setIcon(value.getIcon());
 
        // JLabel den Text aus unserem MyListItem zuweisen
        label.setText(value.getText());
 
       // Hintergrundfarbe des JPanels bei Fokuswechseln definieren
        if(iss) setBackground(Color.lightGray); // Hat den Fokus
        else setBackground(list.getBackground()); // Hat den Fokus nicht
 
        return this;
    }
}
