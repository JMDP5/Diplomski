/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weka;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 *
 * @author Aleksandar
 */
public class WekaNaiveBayesLearner {

    //Da li ce ucitati ok ako je tamo String bez navodnika??
//    private static String trainingFilePath = "data/weka_simple_training.arff";
    private static String trainingFilePath = "data/java-processed-tweets.arff";
    private static String classifierPath = "data/NaiveBayesWeka.dat";

    // Training data will reside in this object.
    Instances trainingData;

    StringToWordVector filter;

    FilteredClassifier classifier;

    public static void main(String[] args) {
        WekaNaiveBayesLearner learner = new WekaNaiveBayesLearner();
        learner.loadTrainingData(trainingFilePath);
        learner.evaluate();
        learner.train();
        learner.saveClassifier(classifierPath);
    }

    private void loadTrainingData(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            ArffReader arffReader = new ArffReader(br);
            trainingData = arffReader.getData();
            System.out.println("***** Data set successfully loaded!! *****");
            br.close();
        } catch (IOException e) {
            throw new RuntimeException("File " + fileName + " not loaded! Please try again. " + e.getMessage());
        }
    }

    private void evaluate() {
        try {
            trainingData.setClassIndex(0);
            filter = new StringToWordVector();
            filter.setAttributeIndices("last");
            classifier = new FilteredClassifier();
            classifier.setFilter(filter);
            classifier.setClassifier(new NaiveBayes());
            Evaluation eval = new Evaluation(trainingData);
            eval.crossValidateModel(classifier, trainingData, 10, new Random(1));
            System.out.println(eval.toSummaryString());
            System.out.println(eval.toClassDetailsString());
            System.out.println("***** Evaluation on filtered (training) dataset done! *****");
        } catch (Exception ex) {
            throw new RuntimeException("Evaluation didn't finish! Please try again!" + ex.getMessage());
        }

    }

    private void train() {
        try {
            trainingData.setClassIndex(0);
            filter = new StringToWordVector();
            filter.setAttributeIndices("last");
            classifier = new FilteredClassifier();
            classifier.setFilter(filter);
            classifier.setClassifier(new NaiveBayesMultinomial());
            classifier.buildClassifier(trainingData);
            System.out.println("***** Classifier successfuly trained! *****");
        } catch (Exception ex) {
            throw new RuntimeException("Classifier training didn't finish! Please try again! " + ex.getMessage());
        }
    }

    private void saveClassifier(String classifierPath) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(classifierPath));
            out.writeObject(classifier);
            out.close();
            System.out.println("***** Classifier successfuly saved! *****");
        } catch (IOException ex) {
            throw new RuntimeException("Classifier not saved!! Please try again!");
        }

    }

}
