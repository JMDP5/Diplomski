/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import preprocessing.OpenNLPProcessing;

/**
 *
 * @author aleksandar
 */
public class SimpleTest {

    public static void main(String[] args) {
        String text = "I think that these tweets messages are being crazy... right? @alex";
        OpenNLPProcessing openNlp = new OpenNLPProcessing();
        System.out.println(openNlp.POSTag(text));
//        String[] tokenized = openNlp.tokenize(text);
//        for (String s : tokenized) {
//            System.out.println(s);
//        }
    }

}
