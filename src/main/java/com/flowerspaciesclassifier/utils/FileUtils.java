/**
 * 
 */
package com.flowerspaciesclassifier.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * @author Arnaud
 *
 */

public class FileUtils {

	public byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ObjectOutputStream o = new ObjectOutputStream(b);
		o.writeObject(obj);
		return b.toByteArray();
	}
	
	
	public void writeToFile(byte[]binaryNetwork, String dumpLocation) throws IOException {
		FileOutputStream fostr = new FileOutputStream(dumpLocation + "trained_network.txt");
		fostr.write(binaryNetwork);
		fostr.close();
	}
	
	
	/*
	 Used to deserialize the file containing data
	 */
	public Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream b = new ByteArrayInputStream(bytes);
		ObjectInputStream o = new ObjectInputStream(b);
		return o.readObject();
	}
	
	
	public MultilayerPerceptron reaFromFile(String dumpLocation) {
		
		MultilayerPerceptron mlp = new MultilayerPerceptron();
		
		// Get the file where the binary network is saved
		File file = new File(dumpLocation + "trained_network.txt"); // Supposing the file name is: trained_network.txt
		
		FileInputStream fileInputStream = null;
		
		// Binary file variable where the binary content will be stored
		byte[] binaryFile = new byte[(int) file.length()];
		
		try {
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(binaryFile);
			fileInputStream.close();
			
		} catch(Exception ex) {
			System.out.println(ex);
		}
		
		try {
			mlp = (MultilayerPerceptron) deserialize(binaryFile);
			
		} catch(Exception ex) {
			System.out.println(ex);
		}
		
		return mlp;
	}
	
	/*
	 Used to retrieve training or test data
	 */
	public Instances retrieveData(String fileType, String file) {
		
		Instances data = null;
		
		try {
			// Read from weka format arff file
			if ("arff".equals(fileType)) {
				String historicalDataPath = System.getProperty("user.dir") + "\\" + file + ".arff";
				
				BufferedReader bf_reader = new BufferedReader(new FileReader(historicalDataPath));
				data = new Instances(bf_reader);
				bf_reader.close();
			}
			
			// Read from comma separated txt file, then transform it to weka file
			else if("txt".equals(fileType)) {
				String historicalDataPath = System.getProperty("user.dir") + "\\" + file + ".txt";
				
				BufferedReader bf_reader = new BufferedReader(new FileReader(historicalDataPath));
				String line = bf_reader.readLine();
				
				ArrayList<Attribute> attributes = new ArrayList<Attribute>();
				for (int i = 0; i < line.split(",").length; i++) {
					attributes.add(new Attribute(line.split(",")[i]));
				}
				
				data = new Instances("TrainingSet", attributes, 0);
				
				while (line != null) {
					line = bf_reader.readLine();
					if (line != null) {
						String[] items = line.split(",");
						Instance data_item = new DenseInstance(items.length);
						int j = -1;
						for(int i = 0; i < items.length; i++) {
							data_item.setValue(++j, Double.parseDouble(items[i]));
						}
						data.add(data_item);
					}
				}
				bf_reader.close();
			}
			
			// Read from format csv file
			else if("csv".equals(fileType)) {
				String historicalDataPath = System.getProperty("user.dir") + "\\" + file + ".csv";
			}
			
		}catch (Exception ex) {
			System.out.println(ex);
		}
		
		return data;
	}
}
