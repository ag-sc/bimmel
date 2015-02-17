package sc.citec.uni.bielefeld.de.bimmel.learning;

import java.io.IOException;
import java.util.Set;

import core.Dataset;
import core.FeatureVector;
import core.Label;

public interface Classifier {
	
	public void train(Dataset dataset);

	public int predict(FeatureVector vector);
	
	public double predict(FeatureVector vector, int label);
	
	public void saveModel(String file) throws IOException;
	
	public void loadModel(String file) throws IOException;

}