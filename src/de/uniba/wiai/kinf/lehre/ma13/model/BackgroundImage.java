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

/**
 * class which implements {@link IBackgroundImage}. Used to hold the Background
 * Image.
 * 
 * @author denis
 * 
 */
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

		// reads the dimensions of the image at the path present in imagePath
		ImageInputStream in = null;
		try {
			in = ImageIO.createImageInputStream(new File(imagePath));
			final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
			// iterate through all Imagedecoders, one of them retrieves the
			// heigh and width of the curren timage
			if (readers.hasNext()) {
				ImageReader reader = (ImageReader) readers.next();
				try {
					reader.setInput(in);
					// set the dimension
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

		// after getting the dimensions we want to store the complete image into
		// the model. this enables us to save the complete picture with the
		// objects into one portable file

		// create a file object
		File inputFile = new File(imagePath);

		FileInputStream fileInputStream;
		try {

			// create an input stream of the file
			fileInputStream = new FileInputStream(inputFile);
			// create a byte array of the size of the input stream
			byte[] data = new byte[(int) inputFile.length()];
			// write the data from the input stream into the byte array
			fileInputStream.read(data);
			// close
			fileInputStream.close();

			// set the data
			data_ = data;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ste the path
		imagePath_ = imagePath;

		refreshBufferedImage();
		
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
		return imageBuffered_;
	}

	@Override
	public byte[] getData() {
		return data_;
	}

	@Override
	public void setData(byte[] imageData) {
		data_ = imageData;
		refreshBufferedImage();

	}

	/**
	 * refreshs the imageBuffered content. only to be called when the image data
	 * has change or a new image was loaded
	 */
	private void refreshBufferedImage() {
		ImageInputStream in = null;

		// make sure there is a picture present
		if (data_ != null) {

			try {

				// create a Input Stream based on our data of the image
				ByteArrayInputStream baris = new ByteArrayInputStream(data_);
				// use theinput stream as a source for the IMageIO read method
				imageBuffered_ = ImageIO.read(baris);

				// ensure the dimension gets set if the image data was retrieved
				// from the database not a file
				in = ImageIO.createImageInputStream(baris);
				final Iterator<ImageReader> readers = ImageIO
						.getImageReaders(in);
				// iterate through all Imagedecoders, one of them retrieves the
				// heigh and width of the current image
				if (readers.hasNext()) {
					ImageReader reader = (ImageReader) readers.next();
					try {
						reader.setInput(in);
						// set the dimension
						scaledDimension_ = new Dimension(reader.getWidth(0),
								reader.getHeight(0));
					} finally {
						reader.dispose();
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
