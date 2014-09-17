/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weka;

import com.sun.corba.se.impl.orbutil.DenseIntMapImpl;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

/**
 *
 * @author Aleksandar
 */
public class WekaClassifier {


    String text;
    String sentiment;
    Instances testData;

    FilteredClassifier classifier;

    public void load(String testFilePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(testFilePath));
            ArffLoader.ArffReader arffReader = new ArffLoader.ArffReader(br);
            testData = arffReader.getData();
            testData.setClassIndex(0);
            System.out.println("***** Data set successfully loaded!! *****");
            br.close();
        } catch (IOException e) {
            throw new RuntimeException("File " + testFilePath + " not loaded! Please try again. " + e.getMessage());
        }
    }

    public void loadModel(String classifierPath) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(classifierPath));
            Object tmp = in.readObject();
            classifier = (FilteredClassifier) tmp;
            in.close();
            System.out.println("***** Classifier model -> " + classifierPath + " successfully loaded!! *****");
        } catch (Exception e) {
            throw new RuntimeException("Classifier - " + classifierPath + " not loaded! Please try again.");
        }
    }

    public void classify() {
        try {
            double acc = 0;
            for (int i = 0; i < testData.numInstances(); i++) {
                double pred = classifier.classifyInstance(testData.instance(i));
                System.out.println("***** Classification *****");
                String predicted = testData.classAttribute().value((int) pred);
                String actual = testData.instance(i).toString(0);
                System.out.println("    Predicted class: " + predicted);
                System.out.println("    Actual class: " + actual);
                if (actual.equals(predicted)) {
                    acc++;
                } else {
                    System.out.println("Missed Text ->  " + testData.instance(i).toString(1));
                }
                System.out.println("Acc: " + acc);
            }
            System.out.println("Overall accuracy: " + (double) acc / testData.numInstances());

        } catch (Exception ex) {
            throw new RuntimeException("Classification went wrong -- " + ex.getMessage());
        }
    }
}
