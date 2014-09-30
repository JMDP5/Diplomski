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
        String[] trainingFiles = new String[8];

        for (int i = 1; i < trainingFiles.length; i++) {
            trainingFiles[i] = "data/escaped/C" + i + ".arff";
            System.out.println(trainingFiles[i]);
        }

        String treningFile = "data/escaped/";
        String testFile = "data/escaped/test_preproceirani.arff";
        String classifier = "data/NBMultinomial.dat";
        for (int i = 3; i < trainingFiles.length; i++) {

            boolean dotest = false;
            boolean doPOS = false;
            boolean doStop = false;
            boolean createFile = false;

            if (dotest) {
                if (createFile) {
                    CSVParser parser = new CSVParser("data/test_data_no_neutral.csv", testFile);
                    parser.processCSV(doPOS);
                }

                WekaClassifier nbClassifier = new WekaClassifier();
                nbClassifier.load(testFile);
                System.out.println("Testing with file: " + testFile);
                nbClassifier.loadModel(classifier);
                nbClassifier.classify();

            } else {
                if (createFile) {
                    CSVParser parser = new CSVParser("data/tweets-without-comma-equal.csv", treningFile);
                    parser.processCSV(doPOS);
                    return;
                }

                WekaLearner learner = new WekaLearner();
                learner.loadTrainingData(trainingFiles[i]);
                System.out.println("Training with file: " + trainingFiles[i]);
                learner.evaluate();
                learner.train();
                learner.saveClassifier(classifier);
            }
        }
////        String t = "llcoolj These people CANNOT know who Mick Jagger is...you ain't old  old skool maybe but c'mon! Mick Jagger has a lot more years than you!";
//        String test = "@Kenichan :)) vise : ) doont cxd :-) don't xd :p :d no not cannot don't didn't I dived many times for the ball. Managed to save 50%  The rest go out of bounds https://www.google.com";
////        String test = "@Kenichan i found tickets right now...and yeah, booked hotel for next week using  https://www.booking.com, it is great!";
////        String test1 = "i love@kirsten / leah / kate @escapades and mission impossible tom as well...http://shopping.pchome.com.tw/hpnb/detail.php?pid=ALG00289 #itm  ";
////        String test2 = "@markhardy1974 Me too  #itm"
//        String test3 = "I don't like apples #feelingsick";
//        String smileys = "1 :-))) 2 : ) cxd 3 ( : 4 xd 5 (; 6 )))-: 7 ) : 8 ) ;";
//        String negations = "1 dont 2 don't 3 cannot 4 wont 5 nooooooo 6 notaaaani";
////        String test4 = "This is simple string. It is simple, is it?";
////        String test5 = "Awesome diner here @ Purdue...";
//
//        TextPreprocessing tp = new TextPreprocessing();
////        System.out.println("");
//        System.out.println("Original message: " + test);
//        System.out.println("Preprocessed :    " + tp.preprocess(test, false));
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
