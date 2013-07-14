package de.uniba.wiai.kinf.lehre.ma13.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

class PreviewPane extends JPanel implements PropertyChangeListener {
	private JLabel label;
	private int maxImgWidth;
	private JLabel lblHorizontal;
	private JLabel lblVertical;
	public PreviewPane() {
		setLayout(new BorderLayout(5,5));
		setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		add(new JLabel("Preview:"), BorderLayout.NORTH);
		label = new JLabel();
		label.setBackground(Color.WHITE);
		label.setOpaque(true);
		label.setPreferredSize(new Dimension(200, 200));
		maxImgWidth = 195;
		label.setBorder(BorderFactory.createEtchedBorder());
		add(label, BorderLayout.CENTER);
		
		JLayeredPane bottomPane = new JLayeredPane();
		add(bottomPane, BorderLayout.SOUTH);
		bottomPane.setLayout(new BorderLayout(0, 0));
		
		JSlider sliderHorizontal = new JSlider();
		bottomPane.add(sliderHorizontal);
		
		lblHorizontal = new JLabel(" ");
		bottomPane.add(lblHorizontal, BorderLayout.NORTH);
		
		JLayeredPane leftPane = new JLayeredPane();
		add(leftPane, BorderLayout.WEST);
		leftPane.setLayout(new BorderLayout(0, 0));
		
		lblVertical = new JLabel("        ");
		leftPane.add(lblVertical, BorderLayout.EAST);
		
		drawRouler();
		
		JSlider sliderVertical = new JSlider();
		leftPane.add(sliderVertical);
		sliderVertical.setOrientation(SwingConstants.VERTICAL);
		
	}

	public void drawRouler(){
        Graphics2D g2d = (Graphics2D) lblHorizontal.getGraphics();
        //AffineTransform origTransform = g2d.getTransform(); //save original transform
        Insets insets = getInsets();
        int firstX = insets.left;
        int firstY = insets.top;
        int lastX = getWidth() - insets.right;
        int lastY = getHeight() - insets.bottom;
        int x = firstX;
//        if (!isHorisontal) {
//            g2d.rotate(Math.toRadians(90));
//            g2d.translate(0, -rulerHeight);
//            int tmp = lastX;
//            lastX = lastY;
//            lastY = tmp;
//        }
        int diff = 2;
        while (x < lastX) {
            if (x % 10 == 0) {
                g2d.drawLine(x, firstY, x, firstY + 10);
                g2d.drawLine(x, lastY, x, lastY - 10);
                boolean printCoord = false;
                if (x <= 100) {
                    printCoord = true;
                } else if ((x + 5) % 10 == 0) {
                    printCoord = true;
                    diff = 5;
                }
                if (printCoord) {
                    g2d.drawString("" + x, x - diff, firstY + 5);
                }
            } else {
                g2d.drawLine(x, firstY, x, firstY + 10);
                g2d.drawLine(x, lastY, x, lastY - 10);
            }
            x += 5;
        }
        //g2d.drawString("jslope.com", 41, 55);
        //g2d.setTransform(origTransform);
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		Icon icon = null;
		if(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(evt
				.getPropertyName())) {
			File newFile = (File) evt.getNewValue();
			if(newFile != null) {
				String path = newFile.getAbsolutePath();
				if(path.endsWith(".gif") || path.endsWith(".jpg") || path.endsWith(".png") || path.endsWith(".bmp")) {
					try {
						BufferedImage img = ImageIO.read(newFile);
						float width = img.getWidth();
						float height = img.getHeight();
						float scale = height / width;
						width = maxImgWidth;
						height = (width * scale); // height should be scaled from new width							
						icon = new ImageIcon(img.getScaledInstance(Math.max(1, (int)width), Math.max(1, (int)height), Image.SCALE_SMOOTH));
					}
					catch(IOException e) {
						// couldn't read image.
					}
				}
			}
				
			label.setIcon(icon);
			this.repaint();
					
		}
	}
	
}
