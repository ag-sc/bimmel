package core;

import java.util.ArrayList;
import java.util.List;

public class Dataset {

	List<Instance> instances;
	public Dataset()
	{
		instances = new ArrayList<Instance>();
	}
	
	public void addInstance(Instance instance)
	{
		instances.add(instance);
	}

	public List<Instance> getInstances()
	{
		return instances;
	}
	
	
}
