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
        String test = "OVo je...test    string.!@";
        Document d = TextPreprocessing.tokenize(test);
        for (String string : d.getBag().keySet()) {
            System.out.println(string + " --- " + d.getBag().get(string));
        }

    }
}
