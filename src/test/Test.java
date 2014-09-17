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

        String treningFile = "data/escaped/equal_lemma.arff";
        String testFile = "data/escaped/test_lemmastop.arff";
        String classifier = "data/NBMultinomial.dat";
        
        boolean dotest = false;
        boolean doPOS = true;
        boolean doLemma = false;
        boolean createFile = true;

        if (dotest) {
            if (createFile) {
                CSVParser parser = new CSVParser("data/test_data_no_neutral.csv", testFile);
                parser.processCSV(doPOS);
            }

            WekaClassifier nbClassifier = new WekaClassifier();
            nbClassifier.load(testFile);
            System.out.println("Testing with file: "+ testFile);
            nbClassifier.loadModel(classifier);
            nbClassifier.classify();

        } else {
            if (createFile) {
                CSVParser parser = new CSVParser("data/tweets-without-comma-equal.csv", treningFile);
                parser.processCSV(doPOS);

            }
            WekaLearner learner = new WekaLearner();
            learner.loadTrainingData(treningFile);
            System.out.println("Training with file: "+ treningFile);
            learner.evaluate();
            learner.train();
            learner.saveClassifier(classifier);
        }
//        String t = "llcoolj These people CANNOT know who Mick Jagger is...you ain't old  old skool maybe but c'mon! Mick Jagger has a lot more years than you!";
        String test = "@Kenichan :)) vise : ) dont cxd :-) don't xd :p :d no not cannot don't didn't I dived many times for the ball. Managed to save 50%  The rest go out of bounds https://www.google.com";
//        String test1 = "i love@kirsten / leah / kate @escapades and mission impossible tom as well...http://shopping.pchome.com.tw/hpnb/detail.php?pid=ALG00289 #itm  ";
//        String test2 = "@markhardy1974 Me too  #itm";
        String test3 = "I dont like the the apple the apple appke";
//        String test4 = "This is simple string. It is simple, is it?";
//        String test5 = "Awesome diner here @ Purdue...";
//        
//        TextPreprocessing tp = new TextPreprocessing();
//        System.out.println(tp.preprocess(test,false));
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
