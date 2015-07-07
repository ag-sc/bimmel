package de.citec.sc.bimmel.core.math;

public abstract class Matrix extends MathBase {
	
	private int rows;
	private int cols;
	
	protected Matrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
	}
	
	/**
	 * Get a Vector view of the underlying matrix row
	 * @param row
	 * @return Vector view of the row
	 */
	public Vector getRow(int row) {throw new UnsupportedOperationException("not implemented");}
	
	/**
	 * Copies matrix elements from the given row to the destination buffer
	 * @param row row to copy
	 * @param data destination buffer
	 * @param offset first position in data to copy the rows elements to
	 */
	public void copyRowTo(int row, double[] data, int offset) {
		if (data.length -offset < getCols()) throw new IllegalArgumentException("data array is not large enough to store " + getCols() + " elements");
		
		//generic implementation using the iterator. Fast for sparse matrices, for dense matrices use arraycopy
		DoubleIterator it = rowIterator(row);
		int index = offset;
		while (it.hasNext()) {
			data[index] = it.next();
			++index;
		}
	}
	
	/**
	 * Copies matrix elements from the given row to the destination buffer
	 * @param row row to copy
	 * @param data destination buffer
	 */
	public void copyRowTo(int row, double[] data) {
		copyRowTo(row, data, 0);
	}
	
	
	/**
	 * Get an efficient look-forward iterator of the given matrix row
	 * @param row Matrix row to iterate over
	 * @return Iterator
	 */
	public DoubleIterator rowIterator(int row) {throw new UnsupportedOperationException("not supported by this matrix implementation");}
	
	/**
	 * Get an element from this matrix
	 * @param row 
	 * @param col
	 * @return element at [row][col]
	 */
	public abstract double get(int row, int col);
	
	
	/**
	 * Number of rows
	 * @return
	 */
	public int getRows() {
		return rows;
	}
	
	/**
	 * Number of columns
	 * @return
	 */
	public int getCols() {
		return cols;
	}

}
