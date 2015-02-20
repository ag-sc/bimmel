package core;

public class Label {

	int Label;
	
	public Label(int label)
	{
		Label = label;
	}

	public int getLabel() {
		return Label;
	}

	public void setLabel(int label) {
		Label = label;
	}

	@Override
	public String toString() {
		return ""+Label;
	}
	
	
	
}
