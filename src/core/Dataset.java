package core;

import java.util.HashMap;

public class Dataset {

	HashMap<FeatureVector, Integer> instances;
	
	public Dataset()
	{
		instances = new HashMap<FeatureVector,Integer>();
	}
	
	public void addInstance(FeatureVector vector, int label)
	{
		instances.put(vector, new Integer(label));
	}
	
}
