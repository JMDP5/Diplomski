/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import csv.CSVParser;
import data.Document;
import preprocessing.TextPreprocessing;
import weka.WekaClassifier;
import weka.WekaLearner;

/**
 *
 * @author aleksandar
 */
public class Test {

    public static void main(String[] args) {

        String treningFile = "data/escaped/kaggle_bezpos.arff";
        String testFile = "data/escaped/test.arff";
        boolean dotest = true;

        if (dotest) {
            CSVParser parser = new CSVParser("data/test_data_no_neutral.csv", testFile);
            parser.processCSV();
            WekaClassifier nbClassifier = new WekaClassifier();
            nbClassifier.load();
            nbClassifier.loadModel("data/NaiveBayesWeka.dat");
            nbClassifier.classify();

        } else {
            CSVParser parser = new CSVParser("data/kaggle_tweets-without-comma.csv", treningFile);
            parser.processCSV();

            WekaLearner learner = new WekaLearner();
            learner.loadTrainingData(treningFile);
            learner.evaluate();
            learner.train();
            learner.saveClassifier("data/NaiveBayesWeka.dat");
        }
//        String t = "llcoolj These people CANNOT know who Mick Jagger is...you ain't old  old skool maybe but c'mon! Mick Jagger has a lot more years than you!";
        String test = "@Kenichan dont don't no not cannot don't didn't I dived many times for the ball. Managed to save 50%  The rest go out of bounds https://www.google.com";
//        String test1 = "i love@kirsten / leah / kate @escapades and mission impossible tom as well...http://shopping.pchome.com.tw/hpnb/detail.php?pid=ALG00289 #itm  ";
//        String test2 = "@markhardy1974 Me too  #itm";
        String test3 = "I dont like the the apple the apple appke";
//        String test4 = "This is simple string. It is simple, is it?";
//        String test5 = "Awesome diner here @ Purdue...";
//        
//        TextPreprocessing tp = new TextPreprocessing();
//        System.out.println(tp.preprocess(test3));
//        Document d = tp.tokenize(test);
////        for (String string : d.getBag().keySet()) {
////            System.out.println(string + " --- " + d.getBag().get(string));
////        }
//        System.out.println(d.getBagBigram());
//        
//        System.out.println("Total count: " + tp.getTotalWordCount());
//        System.out.println("Vocab size: " + tp.getVocabSize());
//        System.out.println("Vocabulary");
//        for (String string : tp.getVocabulary()) {
//            System.out.println(string);
//        }
    }
}
