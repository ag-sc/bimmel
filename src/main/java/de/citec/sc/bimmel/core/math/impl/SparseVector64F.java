package de.citec.sc.bimmel.core.math.impl;

import java.util.Arrays;

import de.citec.sc.bimmel.core.math.DoubleIterator;
import de.citec.sc.bimmel.core.math.Vector;

/**
 * SparseVector is an array of doubles with a compression scheme for zero elements.Memory consumption is sizeof(double) + sizeof(int) 
 * for a non zero entry. So from a memory point of view the break even point for element compression is at > 1/3 zero elements. 
 * Iterator gives the best performance for a linear scan.
 * @author Maximilian Panzner
 */
public class SparseVector64F extends Vector {
	
	double[] data;
	int[] indices;  
	int length;
	
	
	public SparseVector64F(double[] array) {
		this.length = array.length;
		//count non zeros
		int nonZeroCount = 0;
		for (int i = 0; i < array.length; ++i) if (array[i] != 0) ++nonZeroCount;
		this.data = new double[nonZeroCount];
		this.indices = new int[nonZeroCount];
		
		//generate a sparse copy of data
		int idx = 0;
		for (int i = 0; i < array.length; ++i) {
			if (array[i] != 0) {
				this.data[idx] = array[i];	//copy non zero element
				this.indices[idx] = i;		//store index of element
				++idx;
			}
		}
	}
	
	/**
	 * finds the index into the data array corresponding to the given column index
	 * @param index column index
	 * @return corresponding index into the data array
	 */
	private int findDataIndex(int index) {
		for (int i = 0; i < indices.length; ++i) if (indices[i] == index) return i;
		throw new ArrayIndexOutOfBoundsException(index);
	}

	@Override
	/*
	 * does not check if the index is out of bounds 
	 */
	public double get(int index) {
		for (int i = 0; i < indices.length; ++i) if (indices[i] == index) return data[i];
		return 0;
	}

	@Override
	public int getLength() {
		return length;
	}
	
	@Override
	public DoubleIterator iterate() {
		return new Iterator();
	}
	
	
	class Iterator implements DoubleIterator {
		int colIdx = 0;  //current column index
		int dataPtr = 0; //current pointer into data array
		
		@Override
		public boolean hasNext() {
			return colIdx < length;
		}

		@Override
		public double next() {
			double result = 0;
			if (dataPtr < data.length && indices[dataPtr] == colIdx) {
				result = data[dataPtr]; ++ dataPtr; 
			}
			
			++colIdx;
			return result;
		}
		
	}
	

}
