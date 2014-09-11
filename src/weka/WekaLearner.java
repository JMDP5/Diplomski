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
import java.util.Arrays;
import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.Logistic;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;
import weka.core.tokenizers.NGramTokenizer;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 *
 * @author Aleksandar
 */
public class WekaLearner {

    //Da li ce ucitati ok ako je tamo String bez navodnika??
//    private static String trainingFilePath = "data/weka_simple_training.arff";
    private static String trainingFilePath = "data/escaped/java-processed-tweets-all3.arff";
    private static String classifierPath = "data/NaiveBayesWeka.dat";

    // Training data will reside in this object.
    Instances trainingData;

    StringToWordVector filter;

    FilteredClassifier classifier;

    public static void main(String[] args) {
        WekaLearner learner = new WekaLearner();
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

            NGramTokenizer tokenizer = new NGramTokenizer();
            tokenizer.setNGramMinSize(1);
            tokenizer.setNGramMaxSize(3);
            tokenizer.setDelimiters("\\W");

//            String[] options = new String[2];
//            options[0] = "-R <1,2,3>";
//            options[1] = "-C";

            filter = new StringToWordVector();
//            filter.setOptions(weka.core.Utils.splitOptions("-T"));
//            filter.setOptions(weka.core.Utils.splitOptions("-I -T"));
//            filter.setOptions(options); 
            filter.setTokenizer(tokenizer);
            filter.setAttributeIndices("last");

            classifier = new FilteredClassifier();
            classifier.setFilter(filter);

//            Classifier clas = new LibSVM();
//            String[] options = weka.core.Utils.splitOptions("-K 0 -W 1");
////            String[] options = weka.core.Utils.splitOptions("-K 1 -D 2 -W 1");
//            clas.setOptions(options);


            Classifier clas = new NaiveBayesMultinomial();
            
//            Classifier clas = new Logistic();
            
            classifier.setClassifier(clas);
            Evaluation eval = new Evaluation(trainingData);
            System.out.println("evaluating.....");
            eval.crossValidateModel(classifier, trainingData, 5, new Random(1));
            System.out.println("Evaluation finished!");
            System.out.println(eval.toSummaryString());
            System.out.println(eval.toClassDetailsString());
            System.out.println("Confusion matrix: ");
            boolean newrow = false;
            for (double[] d : eval.confusionMatrix()) {
                for (int i = 0; i < d.length; i++) {
                    System.out.print((int) d[i] + "  ");
                }
                System.out.println("");
            }
            System.out.println("***** Evaluation on filtered (training) dataset done! *****");
        } catch (Exception ex) {
            throw new RuntimeException("Evaluation didn't finish! Please try again!" + ex.getMessage());
        }

    }

    private void train() {
        try {
            trainingData.setClassIndex(0);

            NGramTokenizer tokenizer = new NGramTokenizer();
            tokenizer.setNGramMinSize(1);
            tokenizer.setNGramMaxSize(2);
            tokenizer.setDelimiters("\\W");

            filter = new StringToWordVector();
            //  filter.setTokenizer(tokenizer);
            filter.setAttributeIndices("last");

            classifier = new FilteredClassifier();
            classifier.setFilter(filter);

//            Classifier clas = new LibSVM();
//            //String[] options = weka.core.Utils.splitOptions("-K 0");
//            String[] options = weka.core.Utils.splitOptions("-K 1 -D 2");
//            clas.setOptions(options);
            Classifier clas = new NaiveBayesMultinomial();

            classifier.setClassifier(clas);
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
