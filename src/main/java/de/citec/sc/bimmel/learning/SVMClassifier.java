package de.citec.sc.bimmel.learning;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;
import de.citec.sc.bimmel.core.Alphabet;
import de.citec.sc.bimmel.core.Dataset;
import de.citec.sc.bimmel.core.FeatureVector;
import de.citec.sc.bimmel.core.Instance;

public class SVMClassifier implements Classifier {

	String Path;
	Alphabet Alphabet;
	svm_model Model;
	
	public SVMClassifier()
	{
		Alphabet = new Alphabet();
	}
	
	@Override
	public void train(Dataset dataset) {
		
		Alphabet = new Alphabet();
		
		System.out.print("Training SVM\n");
				
		svm_parameter param=new svm_parameter();
        param.svm_type=svm_parameter.C_SVC;
        param.kernel_type=svm_parameter.LINEAR;
        param.gamma=0.5;
        param.nu=0.5;
        param.cache_size=20000;
        param.C=20;
        param.eps=0.001;
        param.p=0.1;
	param.probability = 1;
        
        svm_problem prob=new svm_problem();
        int numTrainingInstances= dataset.size();
        prob.l=numTrainingInstances;
        prob.y=new double[prob.l];
        prob.x=new svm_node[prob.l][];

        int i = 0;
        
        for (Instance instance: dataset.getInstances()){
        	
        	System.out.print(instance+"\n");
        	
            HashMap<String,Double> tmp= instance.getVector().getFeatureMap();
            prob.x[i]=new svm_node[tmp.keySet().size()];
            int indx=0;
            for(String feature: tmp.keySet()){
                svm_node node=new svm_node();
                node.index= Alphabet.getOrCreateFeature(feature);
                node.value=tmp.get(feature);
                prob.x[i][indx]=node;
                indx++;
            }

            prob.y[i]= instance.getLabel().getLabel();
            
            i++;
        }
        
        Model = svm.svm_train(prob,param);


	     System.out.print("Finished training\n");
		
		
	}

	@Override
	public int predict(FeatureVector vector) {
		
		Set<String> features = vector.getFeatures();
		
		List<Integer> intFeatures = new ArrayList<Integer>();
	
		for (String feature : features) {
        	    Integer i = Alphabet.getIndex(feature);
        	    if (i != null) {
        	        intFeatures.add(i);
	            }
        	}
		
		Collections.sort(intFeatures);	
		
		
		svm_node[] nodes = new svm_node[intFeatures.size()];
		
		int ni=0;
		
		for (Integer index: intFeatures)
		{
				nodes[ni] = new svm_node();
				nodes[ni].value = vector.getValueOfFeature(Alphabet.getFeature(index));
				nodes[ni].index = index.intValue();
				ni++;
		}
		
		double result = svm.svm_predict(Model, nodes);
		
		return (int) result;
	}

	@Override
	public double predict(FeatureVector vector, int label) {
		Set<String> features = vector.getFeatures();

		List<Integer> intFeatures = new ArrayList<Integer>();

		for (String feature : features) {
		    Integer i = Alphabet.getIndex(feature);
		    if (i != null) {
		        intFeatures.add(i);
		    }
		}

		Collections.sort(intFeatures);

		svm_node[] nodes = new svm_node[intFeatures.size()];

		int ni = 0;

		for (Integer index : intFeatures) {
		    nodes[ni] = new svm_node();
		    nodes[ni].value = vector.getValueOfFeature(Alphabet.getFeature(index));
		    nodes[ni].index = index.intValue();
		    ni++;
		}

		double[] prob_estimates = new double[Model.nr_class];

		svm.svm_predict_probability(Model, nodes, prob_estimates);
		if (label > Model.nr_class) {
		    return 0;
		}
		return prob_estimates[label - 1];
	}

	@Override
	public void saveModel(String file) throws IOException {
		
        svm.svm_save_model(file, Model); 
        Alphabet.saveModel(file+".alphabet");
		
	}

	@Override
	public void loadModel(String file) throws IOException {
		Model = svm.svm_load_model(file);
		
		Alphabet = new Alphabet();
		Alphabet.loadModel(file+".alphabet");
		
		
	}
	
	public void setSVMPath(String path)
	{
		Path = path;
	}

}
