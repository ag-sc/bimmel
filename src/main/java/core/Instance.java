package core;

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
	
	
	
}
