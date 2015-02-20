package de.citec.sc.bimmel.learning;

import java.io.IOException;
import java.util.Set;

import de.citec.sc.bimmel.core.Dataset;
import de.citec.sc.bimmel.core.FeatureVector;
import de.citec.sc.bimmel.core.Label;

public interface Classifier {
	
	public void train(Dataset dataset);

	public int predict(FeatureVector vector);
	
	public double predict(FeatureVector vector, int label);
	
	public void saveModel(String file) throws IOException;
	
	public void loadModel(String file) throws IOException;

}