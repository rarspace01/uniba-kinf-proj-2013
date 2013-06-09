package de.uniba.wiai.kinf.lehre.ma13.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;

import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IOrderedObject;

public abstract class OrderedObject implements IOrderedObject {

	private boolean _visible;
	private Long _objectId;
	
	private String _name;
	
	@Override
	public void setVisibility(boolean visible) {
		_visible = visible;
	}

	@Override
	public boolean isVisible() {
		return _visible;
	}

	@Override
	public void setObjectId(Long objectId) {
		_objectId = objectId;
	}

	@Override
	public Long getObjectId() {
		return _objectId;
	}
	
	@Override
	public void setName(String name) {
		_name = name;
	}
	
	@Override
	public String getName() {
		return _name;
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
