/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import preprocessing.OpenNLPProcessing;
import preprocessing.TextPreprocessing;

/**
 *
 * @author aleksandar
 */
public class SimpleTest {

    public static void main(String[] args) {
        String text = "I think that these tweets messages are being crazy... right? @alex";
        String notTest = "Wish Maddie luck next saturday  she has a solo audition for Britain's Got Talent! :) ~ Ellie xxxxx";
        String notTest1 = "knocked @Kenichan :)) cantwait : p : paul cant/wont  CANT : ) doont cxd :-) don't xd :p :d no not cannot don't didn't I dived many times for the ball. Managed to save 50%  The rest go out of bounds https://www.google.com";
        OpenNLPProcessing openNlp = new OpenNLPProcessing();
        System.out.println(openNlp.POSTag(text));
        TextPreprocessing tp = new TextPreprocessing();
//        System.out.println("");
        System.out.println("Original message: " + notTest);
        System.out.println("Preprocessed :    " + tp.preprocess(notTest, false));
        System.out.println("*****************************************");
        System.out.println("Original message: " + notTest1);
        System.out.println("Preprocessed : " + tp.preprocess(notTest1, false));
//        String[] tokenized = openNlp.tokenize(text);
//        for (String s : tokenized) {
//            System.out.println(s);
//        }
    }

}
