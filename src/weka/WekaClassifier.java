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
import weka.attributeSelection.ChiSquaredAttributeEval;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.tokenizers.NGramTokenizer;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.StringToWordVector;

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
            
            
            AttributeSelection attributeSelection = new AttributeSelection();
            InfoGainAttributeEval infoGain = new InfoGainAttributeEval();
            ChiSquaredAttributeEval chiSquare = new ChiSquaredAttributeEval();
            Ranker search = new Ranker();
            search.setNumToSelect(1350);
            attributeSelection.setEvaluator(infoGain);
            attributeSelection.setSearch(search);
            
            
            NGramTokenizer tokenizer = new NGramTokenizer();
            tokenizer.setNGramMinSize(1);
            tokenizer.setNGramMaxSize(2);
            tokenizer.setDelimiters("\\W");

            StringToWordVector filter = new StringToWordVector(100000);
            filter.setTokenizer(tokenizer);
            filter.setInputFormat(testData);
            filter.setAttributeIndices("last");

            Instances filtered = Filter.useFilter(testData, filter);
//            attributeSelection.setInputFormat(filtered);
//            Instances newData = Filter.useFilter(filtered, attributeSelection);
            
            this.testData = filtered;

            
            System.out.println("***** Data set successfully loaded!! *****");
            br.close();
        } catch (IOException e) {
            throw new RuntimeException("File " + testFilePath + " not loaded! Please try again. " + e.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException("File " + testFilePath + " not loaded! Please try again. " + ex.getMessage());
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
            double hits = 0;
            for (int i = 0; i < testData.numInstances(); i++) {
                double pred = classifier.classifyInstance(testData.instance(i));
                System.out.println("***** Classification *****");
                String predicted = testData.classAttribute().value((int) pred);
                String actual = testData.instance(i).toString(0);
                System.out.println("    Predicted class: " + predicted);
                System.out.println("    Actual class: " + actual);
                if (actual.equals(predicted)) {
                    hits++;
                } else {
                    System.out.println("Missed Text ->  " + testData.instance(i).toString(1));
                }
                System.out.println("Current hit count: " + hits);
            }
            System.out.println("Overall accuracy: " + (double) hits / testData.numInstances());

        } catch (Exception ex) {
            throw new RuntimeException("Classification went wrong -- " + ex.getMessage());
        }
    }
}
