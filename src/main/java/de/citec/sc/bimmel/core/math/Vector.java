package de.citec.sc.bimmel.core.math;

/**
 * Base class for one dimensional array implementations
 * @author Maximilian Panzner
 */
public abstract class Vector extends MathBase {
	public abstract int getLength();
	public abstract double get(int index);
	public DoubleIterator iterate() {throw new UnsupportedOperationException("not supported by this vector implementation");}
}
