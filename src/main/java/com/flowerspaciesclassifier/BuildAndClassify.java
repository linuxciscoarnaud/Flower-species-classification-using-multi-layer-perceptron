/**
 * 
 */
package com.flowerspaciesclassifier;

import java.math.BigDecimal;
import java.util.Date;

import com.flowerspaciesclassifier.utils.FileUtils;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.Utils;

/**
 * @author Arnaud
 *
 */
public class BuildAndClassify {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// This will be used to access files
		FileUtils fileUtils = new FileUtils();
		
		// network variables
		String backPropOptions = "-L " + 0.1 +        // Learning rate
								 " -M " + 0 +         // Momentum
								 " -N " + 100000 +    // Epoch. The larger epoch, the best results
								 " -V " + 0 +         // Validation
								 " -S " + 0 +         // Seed
								 " -E " + 0 +         // Error
								 " -H " + "4,5,4,3";  // Neural network with 4 layers (4 neurons on first layer, 5 neurons on second layer, 4 neurons on the third layer and 3 neurons on the last layer)
		
		String fileType = "arff";
		String dumpLocation = "D:\\Development\\ML\\FlowerSpeciesClassifier\\";
		
		try {
			// Preparing the training data
			Instances train_data = fileUtils.retrieveData(fileType, "train_iris");
			
			// Set the train data set to the last attributes
			train_data.setClassIndex(train_data.numAttributes() - 1);
			
			// Create the classifier
			MultilayerPerceptron mlp = new MultilayerPerceptron();
			
			// Split up the String containing options to an array of Strings, one for each option.
			mlp.setOptions(Utils.splitOptions(backPropOptions));
			
			// Record the time that corresponds to the beginning of the training
			Date start = new Date();
			
			// Train the network (build the classifier)
			mlp.buildClassifier(train_data);
			
			// Record the time that corresponds to the end of the training
			Date end = new Date();
			
			// Store trained network
			byte[] binaryNetwork = fileUtils.serialize(mlp);
			fileUtils.writeToFile(binaryNetwork, dumpLocation);
			
			/*
			 Use this for an already trained network that has already been saved in a binary file
			 MultilayerPerceptron mlp = fileUtils.reaFromFile(dumpLocation);
			 */
			
			//Display actual and forecast values
			//for (int i = 0; i < train_data.numInstances(); i++) {
				//double actual = train_data.instance(i).classValue();
				//double prediction = mlp.distributionForInstance(train_data.instance(i))[0];
				//System.out.println(actual + "\t" + prediction);
			//}
			System.out.println();
			
			// Success Metrics
			Evaluation eval = new Evaluation(train_data);
			eval.evaluateModel(mlp, train_data);
			
			// Display metrics
			//System.out.println("Correlation: " + eval.correlationCoefficient());
			System.out.println("Mean Absolute Error: " + new BigDecimal(eval.meanAbsoluteError()));
			System.out.println("Root Mean Square Error: " + eval.rootMeanSquaredError());
			System.out.println("Root Absolute Error: " + eval.relativeAbsoluteError());
			System.out.println("Root Relative Square Error: " + eval.rootRelativeSquaredError());
			System.out.println("Instances: " + eval.numInstances());
			
			// Calculate training time
			System.out.println("\nTraining ends in "
						    + (double) (end.getTime() - start.getTime()) / 1000 + " seconds\n");
			System.out.println();
			
			// Preparing the test data
			Instances test_data = fileUtils.retrieveData(fileType, "test_iris");
			
			// Set the test data set to the last attributes
			test_data.setClassIndex(test_data.numAttributes() - 1);
			
			System.out.println("Actual and predicted classes on testing data:");
			for (int i = 0; i < test_data.numInstances(); i++) {
				double actual = test_data.instance(i).classValue();
				double prediction = mlp.classifyInstance(test_data.instance(i));
				System.out.println(actual + "\t" + prediction);
			}
			
		} catch(Exception ex) {
			System.out.println(ex);
		}
	}
}
