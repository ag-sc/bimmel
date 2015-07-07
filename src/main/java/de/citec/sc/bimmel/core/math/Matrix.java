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
