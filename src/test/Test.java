/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import csv.CSVParser;
import data.Document;
import preprocessing.TextPreprocessing;

/**
 *
 * @author aleksandar
 */
public class Test {

    public static void main(String[] args) {
//        CSVParser parser = new CSVParser("testdata.csv");
//        parser.printCsv();
        String test = "@Kenichan I dived many times for the ball. Managed to save 50%  The rest go out of bounds https://www.google.com";
        String test1 = "i love@kirsten / leah / kate @escapades and mission impossible tom as well...http://shopping.pchome.com.tw/hpnb/detail.php?pid=ALG00289 ";
        String test2 = "@markhardy1974 Me too  #itm";
        String test3 = "I like the the apple the apple appke";
        String test4 = "This is simple string. It is simple, is it?";
        String test5 = "Awesome diner here @ Purdue...";
        
        TextPreprocessing tp = new TextPreprocessing();
        Document d = tp.tokenize(test);
//        for (String string : d.getBag().keySet()) {
//            System.out.println(string + " --- " + d.getBag().get(string));
//        }
        System.out.println(d.getBagBigram());
        
        System.out.println("Total count: " + tp.getTotalWordCount());
        System.out.println("Vocab size: " + tp.getVocabSize());
        System.out.println("Vocabulary");
        for (String string : tp.getVocabulary()) {
            System.out.println(string);
        }
    }
}
