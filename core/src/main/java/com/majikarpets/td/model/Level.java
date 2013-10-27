package com.majikarpets.td.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.majikarpets.td.StreamSerializable;


/**
 * Contains all the information about a given level
 * @author Aaron Cake
 */
public class Level implements StreamSerializable {
	
	/** The width of the level */
	private int width;
	
	/** The height of the level */
	private int height;

	@Override
	public void serializeBinary(DataOutputStream out) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deserializeBinary(DataInputStream in) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * How do we specify the areas in a level (buildable, walkable) efficiently?
	 */
	
	// need spawn areas
	

}
