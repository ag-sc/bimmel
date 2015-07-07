package de.citec.sc.bimmel.core.math;

import java.util.NoSuchElementException;

public interface DoubleIterator {
	/**
	 * Returns {@code true} if the iteration has more elements
	 * @return {@code true} if more elements are available
	 */
	boolean hasNext();
	
	/**
	 * Return the next element in this iteration
	 * @return next element
	 * @throws NoSuchElementException if no next element exists
	 */
	double next();
}
