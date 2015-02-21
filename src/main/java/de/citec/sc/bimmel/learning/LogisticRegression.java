package de.citec.sc.bimmel.learning;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import de.citec.sc.bimmel.core.Dataset;
import de.citec.sc.bimmel.core.FeatureVector;
import de.citec.sc.bimmel.core.Instance;

public class LogisticRegression implements Classifier {

	HashMap<String,Double> theta;
	
	double alpha = 0.1;
	
	
	public LogisticRegression()
	{
		theta = new HashMap<String,Double>();
	}
	
	@Override
	public void train(Dataset dataset) {
		
		// initialize features
		
		FeatureVector vector;
		
		for (Instance instance: dataset.getInstances())
		{
			vector = instance.getVector();
			
			for (String feature: vector.getFeatures())
			{
				theta.put(feature, new Double(0.0));
			}

		}
		
		theta.put("neutral", new Double(0.0));
		
		double accuracy = 0;
		
		double new_accuracy = 0;
		
		for (Instance instance: dataset.getInstances())
		{
			if (predict(instance.getVector()) == instance.getLabel().getLabel()) new_accuracy +=1;
		}
		
		new_accuracy = new_accuracy / (double) dataset.size();
		

		System.out.print("Initial accuracy: "+new_accuracy+"\n");
		
		double derivative;
		
		int i = 0;
		
		while (i <= 10)
		{
			accuracy = new_accuracy;
			
			i++; 
			System.out.print("Iteration: "+i+"\n");
			
			derivative = 0.0;
			
			for (String feature: theta.keySet())
			{
				
				for (Instance instance: dataset.getInstances())
				{
					vector = instance.getVector();
					derivative += (h(vector) - (double) instance.getLabel().getLabel()) * vector.getValueOfFeature(feature);
				}
				
				System.out.print("Updating theta of "+feature+" from "+theta.get(feature)+" ");
				
				theta.put(feature, new Double(theta.get(feature).doubleValue() - alpha * derivative));
				
				System.out.print("to"+theta.get(feature)+"\n");
			}
			
			
			
			new_accuracy = 0;
			
			for (Instance instance: dataset.getInstances())
			{
				if (predict(instance.getVector()) == instance.getLabel().getLabel()) new_accuracy +=1;
			}
			
			new_accuracy = new_accuracy / (double) dataset.size();
			

			System.out.print("New accuracy: "+new_accuracy+"\n");
			
		}
		
		

	}

	private double h(FeatureVector vector) {
		
		double h = 0.0;
		
		HashMap<String,Double> map = vector.getFeatureMap();
		
		for (String feature: map.keySet())
		{
			h = h + theta.get(feature).doubleValue();
		}
		
		h = h + theta.get("neutral").doubleValue();
		
		return  1.0 / (1.0 + Math.exp(-h));
		
	}

	@Override
	public int predict(FeatureVector vector) {
		
		if (h(vector) >= 0.5) return 1;
		else return 0;
		
	}

	@Override
	public double predict(FeatureVector vector, int label) {
		
		if (label == 1)
		{
			return predict(vector);
		}
		else
		{
			return 1 - predict(vector);
		}
		
	}

	@Override
	public void saveModel(String file) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadModel(String file) throws IOException {
		// TODO Auto-generated method stub

	}

}
