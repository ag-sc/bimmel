package de.citec.sc.bimmel.core.math.impl;

import de.citec.sc.bimmel.core.math.DoubleIterator;
import de.citec.sc.bimmel.core.math.Matrix;
import de.citec.sc.bimmel.core.math.Vector;

/**
 * Efficient implementation of a sparse matrix. Memory footprint is approximately sizeof(double) + sizeof(int) for each 
 * non zero element. This class is optimized for a row scan access scheme. Use the row iterator {@link rowIterator} for best performance
 * @author Maximilian Panzner
 *
 */
public class SparseMatrix extends Matrix {
	
	private int nonZeroElements = 0;
	
	private double[] data;	//array with non zero elements
	private int[] index; 	//column index
	private int[] rowPtr; 	//row pointers
	
	
	
	/**
	 * Constructs a new sparse Matrix from the given 2 dim array
	 * @param data 2dim array of matrix elements [row][col]
	 * @throws ArrayIndexOutOfBoundsException if the given Matrix is empty
	 */
	public SparseMatrix(double[][] data) {
		super(data.length, data[0].length);
		for (int row = 0; row < getRows(); ++row) {
			for (int col = 0; col < getCols(); ++col) {
				if (data[row].length != getCols()) throw new IllegalArgumentException("matrix has to be recangular");
				if (data[row][col] != 0) nonZeroElements++;
			}
		}
		
		//allocate storage
		this.data = new double[nonZeroElements];
		this.index = new int[nonZeroElements];
		this.rowPtr = new int[getRows()+1];
		this.rowPtr[0] = 0;	
		
		//copy non zero elements
		init(data);
	}
	
	//copy non zero elements
	private void init(double[][] values) {
		int dataPtr = 0;
		
		for (int row = 0; row < getRows(); ++row) {
			for (int col = 0; col < getCols(); ++col) {
				double val = values[row][col]; 
				if (val != 0) {
					data[dataPtr] = val;
					index[dataPtr] = col;
					++dataPtr;
				}
			}
			rowPtr[row+1] = dataPtr;	//insert row pointer
		}
	}
	
	@Override
	public double get(int row, int col) {
		if (row > getRows() - 1 || col > getCols() - 1) throw new IllegalArgumentException("row or column not found");
		
		//get current row ptr
		int startIndex = rowPtr[row];	//start at this index to look for the demanded column
		int endIndex = rowPtr[row+1];	//if we reach this index the demanded column could not be found
		
		//scan the row interval
		for (int i = startIndex; i < endIndex; ++ i) {
			if (index[i] == col) return data[i];  //the respective element is non zero
			else if (index[i] > col) return 0; 	  //no value recorded
		}
		
		//all elements after endIndex are zero
		return 0;
	}
	
	@Override
	public Vector getRow(int row) {
		return new RowWrapper(rowPtr[row], rowPtr[row+1]);
	}
	
	@Override
	public DoubleIterator rowIterator(int row) {
		return new RowIterator(rowPtr[row]);
	}
	
	class RowIterator implements DoubleIterator {
		int colIdx = 0;  //current column index
		int dataPtr = 0; //current pointer into data array

		public RowIterator(int rowOffset) {
			dataPtr = rowOffset;
		}
		
		@Override
		public boolean hasNext() {
			return colIdx < getCols();
		}

		@Override
		public double next() {
			double result = 0;
			if (dataPtr < data.length && index[dataPtr] == colIdx) {
				result = data[dataPtr]; ++ dataPtr; 
			}
			
			++colIdx;
			return result;
		}
	}
	
	private class RowWrapper extends Vector {
		int offset = 0;
		int endindex = 0;
		
		public RowWrapper(int offset, int endindex) {
			this.offset = offset;
			this.endindex = endindex;
		}

		@Override
		public int getLength() {return getCols();}

		@Override
		public double get(int col) {
			//scan the row interval
			for (int i = offset; i < endindex; ++ i) {
				if (index[i] == col) return data[i];  //the respective element is non zero
				else if (index[i] > col) return 0; 	  //no value recorded
			}
			//all elements after end index are zero			
			return 0;
		}
	}
	
}
