package de.uniba.wiai.kinf.lehre.ma13.model;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;

import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IOrderedObject;

public abstract class OrderedObject implements IOrderedObject {

	private Long objectId_;
	
	private String name_;
	private Color color_;
	private boolean visible_;
	private float opacity_ = 1.0f;

	@Override
	public void setObjectId(Long objectId) {
		objectId_ = objectId;
	}

	@Override
	public Long getObjectId() {
		return objectId_;
	}
	
	@Override
	public void setName(String name) {
		name_ = name;
	}
	
	@Override
	public String getName() {
		return name_;
	}
	
	@Override
	public void setColor(Color color) {
		color_ = color;
	}
	
	@Override
	public Color getColor() {
		return color_;
	}
	
	@Override
	public void setVisibility(boolean visible) {
		visible_ = visible;
	}

	@Override
	public boolean isVisible() {
		return visible_;
	}

	@Override
	public void setOpacity(float opacity) {
		opacity_ = opacity;
	}

	@Override
	public float getOpacity() {
		return opacity_;
	}

	@Override
	public void writeObject(ObjectOutputStream out) throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
	}

	@Override
	public void readObjectNoData() throws ObjectStreamException {
		// TODO Auto-generated method stub
	}	
}
