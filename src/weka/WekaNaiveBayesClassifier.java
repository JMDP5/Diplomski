/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weka;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;

/**
 *
 * @author Aleksandar
 */
public class WekaNaiveBayesClassifier {

    private static String testFilePath = "data/smstest.txt";
    private static String classifierPath = "NaiveBayesWeka";

    String text;

    Instances instances;

    FilteredClassifier classifier;

    public static void main(String[] args) {
        WekaNaiveBayesClassifier nbClassifier = new WekaNaiveBayesClassifier();
        nbClassifier.load();
        nbClassifier.loadModel();
        nbClassifier.makeInstance();
        nbClassifier.classify();
    }

    private void load() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(testFilePath));
            String line;
            text = "";
            while ((line = reader.readLine()) != null) {
                text = text + " " + line;
            }
            System.out.println("***** Data set successfully loaded!! *****");
            reader.close();
            System.out.println(text);
        } catch (IOException e) {
            throw new RuntimeException("File " + testFilePath + " not loaded! Please try again.");
        }
    }

    private void loadModel() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(classifierPath));
            Object tmp = in.readObject();
            classifier = (FilteredClassifier) tmp;
            in.close();
             System.out.println("***** Classifier model" + classifierPath + " successfully loaded!! *****");
        } catch (Exception e) {
             throw new RuntimeException("Classifier - " + testFilePath + " not loaded! Please try again.");
        }
    }

    private void makeInstance() {
        
    }

    private void classify() {
        try {
            double pred = classifier.classifyInstance(instances.instance(0));
            System.out.println("Predicted class: " + instances.classAttribute().value((int) pred));
        } catch (Exception ex) {
            throw new RuntimeException("Classification went wrong -- " + ex.getMessage());
        }
    }
}
