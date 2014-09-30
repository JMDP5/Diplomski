/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import weka.WekaLearner;

/**
 *
 * @author aleksandar
 */
public class AttributeSelectionTest {

    public static void main(String[] args) {
        //Ovde bi trebalo neku for petlju i da ide po 50 featura vise i da na osnovu nje 
        // nacrtam za bigram i unigram dva grafika.
        
        String treningFile = "data/escaped/C4.arff";
        WekaLearner learner = new WekaLearner();
        learner.loadTrainingData(treningFile);
        System.out.println("Training with file: " +  treningFile);
        learner.evaluate();
    }

}
