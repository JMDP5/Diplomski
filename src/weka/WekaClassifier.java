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

/**
 *
 * @author Aleksandar
 */
public class WekaClassifier {

    private static String testFilePath = "data/weka_simple_test.txt";
    private static String classifierPath = "data/NaiveBayesWeka.dat";

    String text;

    Instances instances;

    FilteredClassifier classifier;

    public static void main(String[] args) {
        WekaClassifier nbClassifier = new WekaClassifier();
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
            System.out.println("***** Classifier model -> " + classifierPath + " successfully loaded!! *****");
        } catch (Exception e) {
            throw new RuntimeException("Classifier - " + testFilePath + " not loaded! Please try again.");
        }
    }

    private void makeInstance() {
        FastVector classValues = new FastVector(2);
        classValues.addElement("negative");
        classValues.addElement("positive");
        Attribute attribute1 = new Attribute("Class", classValues);
        Attribute attribute2 = new Attribute("Text", (FastVector) null);
        FastVector attrDescription = new FastVector();
        attrDescription.addElement(attribute1);
        attrDescription.addElement(attribute2);
        instances = new Instances("Tweets", attrDescription, 1);
        instances.setClassIndex(0);
        Instance i = new Instance(2);
        i.setValue(attribute2, text);
        instances.add(i);

    }

    private void classify() {
        try {
            double pred = classifier.classifyInstance(instances.instance(0));
            System.out.println("***** Classification *****");
            System.out.println("    Predicted class: " + instances.classAttribute().value((int) pred));
        } catch (Exception ex) {
            throw new RuntimeException("Classification went wrong -- " + ex.getMessage());
        }
    }
}
