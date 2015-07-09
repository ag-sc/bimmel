package de.citec.sc.bimmel.core.math.tests;

import java.util.Random;

import junit.framework.TestCase;

import org.junit.Test;

import de.citec.sc.bimmel.core.math.DoubleIterator;
import de.citec.sc.bimmel.core.math.Vector;
import de.citec.sc.bimmel.core.math.impl.SparseMatrix64F;
import de.citec.sc.bimmel.core.math.impl.SparseVector64F;

public class VectorTests extends TestCase {

	static Random rnd = new Random();
	
	

	
	

	
	/*
	 * TEST FRAGMENTS
	 */

	private void checkIterator(double[] data, DoubleIterator it) {
		for (int i = 0; i < data.length; ++i) {
			assertTrue(it.hasNext());
			assertEquals(data[i], it.next());
		}
	}
		
	
	
	/* 
	 * 	TESTS
	 */
	
	@Test
	public void testSparseMatrix() {
		final double nonZeroFrac = .5;
		final int iterations = 100;
		
		double[][] matrix = new double[200][100];
		
		for (int iteration = 0; iteration < iterations; ++iteration) {
			fillSparseMatrix(matrix, nonZeroFrac);
			SparseMatrix64F sm = new SparseMatrix64F(matrix);
			assertTrue(sm.getRows() == matrix.length);
			assertTrue(sm.getCols() == matrix[0].length);
			
			//check raw data
			for (int row = 0; row < sm.getRows(); ++row) {
				for (int col = 0; col < sm.getCols(); ++col) {
					assertEquals(matrix[row][col], sm.get(row, col));
				}
				
				//check iterator
				checkIterator(matrix[row], sm.rowIterator(row));
			}
			
		}

			
	}
	
	@Test
	public void testSparseVector() {
		final double nonZeroFrac = .5;
		final int iterations = 1000;
		
		double[] data = new double[1000];
		for (int iteration = 0; iteration < iterations; ++iteration) {
			fillSparseVector(data, nonZeroFrac);
			Vector sv = new SparseVector64F(data);
			assertEquals(data.length, sv.getLength());
			for (int i = 0; i < sv.getLength(); ++i) {
				assertEquals(data[i], sv.get(i));
			}
			
		} 
	}
	
	@Test
	public void testSparseVectorIterator() {
		final double nonZeroFrac = .5;
		final int iterations = 1000;
		
		double[] data = new double[1000];
		for (int iteration = 0; iteration < iterations; ++iteration) {
			fillSparseVector(data, nonZeroFrac);
			Vector sv = new SparseVector64F(data);
			DoubleIterator dit = sv.iterate();
			assertEquals(data.length, sv.getLength());
			checkIterator(data, dit);
		} 
	}	
	
	
	//SUPPORT METHODS
	private static void fillSparseVector(double[] data, double nonZeroFrac) {
		for (int i = 0; i < data.length; ++i) {
			if (rnd.nextDouble() < nonZeroFrac) data[i] = rnd.nextDouble(); else data[i] = 0; 
		}
	} 

	private static void fillSparseMatrix(double[][] matrix, double nonZeroFrac) {
		for (int row = 0; row < matrix.length; ++row) {
			fillSparseVector(matrix[row], nonZeroFrac);
		}
	}

	
	
}
