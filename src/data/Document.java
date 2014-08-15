/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aleksandar
 */
public class Document {

    private Map<String, Double> bag;
    private Map<String, HashMap<String, Double>> bagBigram;

    public Map<String, HashMap<String, Double>> getBagBigram() {
        return bagBigram;
    }

    public void setBagBigram(Map<String, HashMap<String, Double>> bagBigram) {
        this.bagBigram = bagBigram;
    }
    private String category;

    public Document() {
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Map<String, Double> getBag() {
        return bag;
    }

    public void setBag(Map<String, Double> bag) {
        this.bag = bag;
    }



}
