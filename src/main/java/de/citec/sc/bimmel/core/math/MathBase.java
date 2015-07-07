package de.citec.sc.bimmel.core.math;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class MathBase {
	
	/**
	 * Serialize this type to the given OutputStream
	 * @param out Stream to serialize to
	 * @throws IOException
	 */
	public void store(OutputStream out) throws IOException {throw new UnsupportedOperationException("not yet implemented");}
	
	/**
	 * Restore this types state from its serialized form generated by {@link store}
	 * @param in InputStream to restore this types state from
	 * @throws IOException
	 */
	public void load(InputStream in) throws IOException {throw new UnsupportedOperationException("not yet implemented");}
	
	protected void encodeLong(long l, byte[] buff, int offset) {throw new UnsupportedOperationException("not yet implemented");}
	protected void encodeDouble(double d, byte[] buff, int offset) {throw new UnsupportedOperationException("not yet implemented");}
}
