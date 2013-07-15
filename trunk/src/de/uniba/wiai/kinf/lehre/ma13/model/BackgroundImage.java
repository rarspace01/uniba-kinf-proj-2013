package de.uniba.wiai.kinf.lehre.ma13.model;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IBackgroundImage;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;

public class BackgroundImage extends OrderedObject implements IBackgroundImage {

	private byte[] data_;
	private BufferedImage imageBuffered_;
	private String imagePath_;
	private Dimension scaledDimension_;

	public BackgroundImage() {
		scaledDimension_ = new Dimension(1024, 768);
		setVisibility(true);
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
					scaledDimension_ = new Dimension(reader.getWidth(0),
							reader.getHeight(0));
				} finally {
					reader.dispose();
				}
			}
		} catch (IOException e) {
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
				}
		}
		
		File inputFile = new File(imagePath);

		FileInputStream fileInputStream;
		try {
			
			fileInputStream = new FileInputStream(inputFile);
			byte[] data = new byte[(int) inputFile.length()];
			fileInputStream.read(data);
			fileInputStream.close();
			
			data_ = data;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		imagePath_ = imagePath;

	}

	@Override
	public Dimension getImageDimensions() {
		return scaledDimension_;
	}

	@Override
	public void setImageDimensions(Dimension dimension) {
		scaledDimension_ = dimension;
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
		// the image is the biggest instance, it is always in every (legal)
		// bounding box
		return true;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public Image getImage() {

		ImageInputStream in = null;
		
		if (data_ != null) {

			try {
				
				
				
				 ByteArrayInputStream baris= new ByteArrayInputStream(data_);
				imageBuffered_ = ImageIO.read(baris);
				
				in = ImageIO.createImageInputStream(baris);
				final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
				if (readers.hasNext()) {
					ImageReader reader = (ImageReader) readers.next();
					try {
						reader.setInput(in);
						scaledDimension_ = new Dimension(reader.getWidth(0),
								reader.getHeight(0));
					} finally {
						reader.dispose();
					}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return imageBuffered_;
	}

	public byte[] getData() {
		return data_;
	}

	@Override
	public void setData(byte[] imageData) {
		data_ = imageData;
		
	}

	
	
}
