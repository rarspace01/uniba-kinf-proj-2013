package de.uniba.wiai.kinf.lehre.ma13.controller;

import java.awt.Dimension;
import java.awt.Point;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;

/**
 * class for world ad screen transformations
 * 
 * @author denis
 * 
 */
public class Util {
	private IAppDelegate appDelegate_;

	/** last ID that was allocated */
	private Long lastId_;

	private float zoomFactor_;

	// private Point zoomOffset_; // would have been used for moving the zoomed
	// image

	private Util(IAppDelegate appDelegate) {
		lastId_ = 1337L;
		appDelegate_ = appDelegate;

		zoomFactor_ = 1.0f;
		// zoomOffset_ = new Point(0, 0); // would have been used for moving the
		// zoomed image
	}

	public static Util instance(IAppDelegate appDelegate) {
		return new Util(appDelegate);
	}

	public Long getId() {
		return lastId_++;
	}

	public void setId(Long lastId) {
		lastId_ = lastId;
	}

	/**
	 * converts a given point on the screen to a World Coordinate
	 * 
	 * @param pt
	 *            - {@link Point} on the Screen
	 * @return {@link Point} - in the world
	 */
	public Point toWorldCoordinates(Point pt) {

		// dimension of the background image
		Dimension originalDimension = appDelegate_.getLayerStore()
				.getBackgroundImage().getImageDimensions();
		// dimensions of the canvas
		Dimension canvasDimension = appDelegate_.getWindow().getCanvas()
				.getCanvasClippingBounds().getSize();

		// calc the ratio, we'll need to scale
		float ratio = Math
				.min(((float) canvasDimension.width / (float) originalDimension.width),
						((float) canvasDimension.height / (float) originalDimension.height));

		float pointX = 0.0f;
		float pointY = 0.0f;

		// calc the offset
		float offsetX = (((float) originalDimension.width * getZoom() * ratio) - (float) canvasDimension.width) / 2.0f;
		float offsetY = (((float) originalDimension.height * getZoom() * ratio) - (float) canvasDimension.height) / 2.0f;

		// calculate the new points based on the current zoomlevel & offset
		pointX = (pt.x + offsetX) / (ratio * getZoom());
		pointY = (pt.y + offsetY) / (ratio * getZoom());

		return new Point(Math.round(pointX), Math.round(pointY));

		// Dimension worldDimension = toWorldDimension(new Dimension(pt.x,
		// pt.y));
		// return new Point(worldDimension.width, worldDimension.height);
	}

	/**
	 * converts a given World point to a screen coordinate
	 * 
	 * @param pt
	 *            - Point in World
	 * @return {@link Point} on the screen
	 */
	public Point toScreenCoordinates(Point pt) {

		// dimension of the background image
		Dimension originalDimension = appDelegate_.getLayerStore()
				.getBackgroundImage().getImageDimensions();
		// dimensions of the canvas
		Dimension canvasDimension = appDelegate_.getWindow().getCanvas()
				.getCanvasClippingBounds().getSize();

		// calc the ratio for scaling
		float ratio = Math
				.min(((float) canvasDimension.width / (float) originalDimension.width),
						((float) canvasDimension.height / (float) originalDimension.height));

		float pointX = 0.0f;
		float pointY = 0.0f;

		// calc the offset
		float offsetX = (((float) originalDimension.width * getZoom() * ratio) - (float) canvasDimension.width) / 2.0f;
		float offsetY = (((float) originalDimension.height * getZoom() * ratio) - (float) canvasDimension.height) / 2.0f;

		pointX = pt.x * (ratio * getZoom()) - offsetX;
		pointY = pt.y * (ratio * getZoom()) - offsetY;

		return new Point(Math.round(pointX), Math.round(pointY));

		// Dimension screenDimension = toScreenDimension(new Dimension(pt.x,
		// pt.y));
		// return new Point(screenDimension.width, screenDimension.height);
	}

	/**
	 * convert a Dimension from screen to world
	 * 
	 * @param screen
	 *            - Dimension on the screen
	 * @return {@link Dimension} in the World
	 */
	public Dimension toWorldDimension(Dimension screen) {

		/*
		 * // TODO: zoom factor! Dimension originalDimension =
		 * appDelegate_.getLayerStore
		 * ().getBackgroundImage().getImageDimensions(); Dimension
		 * canvasDimension =
		 * appDelegate_.getWindow().getCanvas().getCanvasClippingBounds
		 * ().getSize();
		 * 
		 * // get aspect ratio of image float aspectRatio =
		 * (float)originalDimension.width / (float)originalDimension.height;
		 * 
		 * int worldWidth = Math.round((float)screen.width /
		 * ((float)canvasDimension.width / (float)originalDimension.width));
		 * //int worldHeight = Math.round((float)screen.height /
		 * ((float)canvasDimension.height / (float)originalDimension.height));
		 * int worldHeight = Math.round((float)worldWidth / aspectRatio);
		 * 
		 * return new Dimension(worldWidth, worldHeight);
		 */

		// dimension of the background image
		Dimension originalDimension = appDelegate_.getLayerStore()
				.getBackgroundImage().getImageDimensions();
		// dimensions of the canvas
		Dimension canvasDimension = appDelegate_.getWindow().getCanvas()
				.getCanvasClippingBounds().getSize();
		// get aspect ratio of image
		float aspectRatio = (float) originalDimension.width
				/ (float) originalDimension.height;

		float worldWidth = ((float) screen.width / ((float) canvasDimension.width / (float) originalDimension.width))
				* zoomFactor_;
		float worldHeight = worldWidth / aspectRatio;

		return new Dimension(Math.round(worldWidth), Math.round(worldHeight));
	}

	/**
	 * translates a dimension from the "real world" to the screen. The image has
	 * an original dimension which is translated to the screen here
	 * 
	 * @param world - {@link Dimension} of the world
	 * @return {@link Dimension} on the screen
	 */
	public Dimension toScreenDimension(Dimension world) {

		// dimension of the background image
		Dimension originalDimension = appDelegate_.getLayerStore()
				.getBackgroundImage().getImageDimensions();
		// dimensions of the canvas
		Dimension canvasDimension = appDelegate_.getWindow().getCanvas()
				.getCanvasClippingBounds().getSize();

		float ratio = Math
				.min(((float) canvasDimension.width / (float) originalDimension.width),
						((float) canvasDimension.height / (float) originalDimension.height));

		float screenWidth = ((float) world.width * ratio) * getZoom();
		float screenHeight = ((float) world.height * ratio) * getZoom();

		return new Dimension(Math.round(screenWidth), Math.round(screenHeight));
	}

	/**
	 * get the Zoom
	 * @return {@link float}
	 */
	public float getZoom() {
		return zoomFactor_;
	}

	/**
	 * sets the zoom
	 * @param zoomFactor - {@link float}
	 */
	public void setZoom(float zoomFactor) {
		zoomFactor_ = zoomFactor;
	}
}
