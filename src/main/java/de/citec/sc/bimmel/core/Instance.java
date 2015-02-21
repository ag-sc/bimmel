package de.citec.sc.bimmel.core;

public class Instance {


	FeatureVector vector;
	Label label;
	
	public Instance(FeatureVector vector, Label label) {
		super();
		this.vector = vector;
		this.label = label;
	}

	public FeatureVector getVector() {
		return vector;
	}

	public void setVector(FeatureVector vector) {
		this.vector = vector;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}
	
	@Override
	public String toString() {
		String string = ""+label.getLabel();
		
		for (String feature: vector.getFeatureMap().keySet())
		{
			string += feature + " => " + vector.getFeatureMap().get(feature);
		}
		
		
		return string;
		
	}

	
}
