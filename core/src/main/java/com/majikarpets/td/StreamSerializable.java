package com.majikarpets.td;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Any entity that can be serialized for the purpose of transmitting over a network.
 * @author Aaron Cake
 */
public interface StreamSerializable {

	/**
	 * Serialize the object in an efficient, binary format
	 * @param out the output stream to write to
	 */
	public void serializeBinary(DataOutputStream out);
	
	/**
	 * Deserialize the binary serializtion of the object by applying all the serialized states.
	 * @param in the input stream to read from
	 */
	public void deserializeBinary(DataInputStream in);
	
}
