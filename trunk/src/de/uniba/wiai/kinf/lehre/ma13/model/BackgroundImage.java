package de.uniba.wiai.kinf.lehre.ma13.model;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IBackgroundImage;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;

public class BackgroundImage extends OrderedObject implements IBackgroundImage {

	private String imagePath_;
	private Dimension scaledDimension_;
	
	public BackgroundImage() {
		scaledDimension_ = new Dimension(1024, 768);
	}
	
	@Override
	public String getImagePath() {
		return imagePath_;
	}

	@Override
	public void setImagePath(String imagePath) {
		
		ImageInputStream in = null;
		try {
			in = ImageIO.createImageInputStream(new File(imagePath));
			final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
			if (readers.hasNext()) {
				ImageReader reader = (ImageReader) readers.next();
				try {
					reader.setInput(in);
					scaledDimension_ = new Dimension(reader.getWidth(0), reader.getHeight(0));
				} finally {
					reader.dispose();
				}
			}
		} catch (IOException e) {}
		finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) { }
		}
		imagePath_ = imagePath;
	}

	@Override
	public Dimension getImageDimensions() {
		return scaledDimension_;
	}

	@Override
	public ILayer getParent() {
		return null;
	}

	@Override
	public void setParent(ILayer parentLayer) {
		return;
	}

	@Override
	public boolean inBoundingBox(int x1, int x2, int y1, int y2) {
		// the image is the biggest instance, it is always in every (legal) bounding box
		return true;
	}
}
