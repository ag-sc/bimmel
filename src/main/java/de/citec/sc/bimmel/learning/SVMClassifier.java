package learning;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import core.Alphabet;
import core.Dataset;
import core.FeatureVector;
import core.Instance;
import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

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
		
		List<Instance> instances = dataset.getInstances();
		
		String file = "training";
		
		String model = "model";
				
		System.out.print("Training SVM\n");
				
		svm_parameter param=new svm_parameter();
        param.svm_type=svm_parameter.C_SVC;
        param.kernel_type=svm_parameter.RBF;
        param.gamma=0.5;
        param.nu=0.5;
        param.cache_size=20000;
        param.C=1;
        param.eps=0.001;
        param.p=0.1;
        
        svm_problem prob=new svm_problem();
        int numTrainingInstances= dataset.size();
        prob.l=numTrainingInstances;
        prob.y=new double[prob.l];
        prob.x=new svm_node[prob.l][];

        // serialize model: svm.svm_save_model(String model_file_name, svm_model model) 
        // load model: svm_model model = svm.svm_load_model("filename");
        
        int i = 0;
        
        for (Instance instance: dataset.getInstances()){
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
	
		for (String feature: features)
		{
			intFeatures.add(Alphabet.getIndex(feature));
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void saveModel(String file) throws IOException {
		
        svm.svm_save_model(file, Model); 
		
	}

	@Override
	public void loadModel(String file) throws IOException {
		Model = svm.svm_load_model(file);
		
	}
	
	public void setSVMPath(String path)
	{
		Path = path;
	}

}
