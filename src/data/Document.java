/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.Map;

/**
 *
 * @author aleksandar
 */
public class Document {

    private Map<String, Integer> bag;
    private String category;

    public Document() {
    }

    public Map<String, Integer> getBag() {
        return bag;
    }

    public void setBag(Map<String, Integer> bag) {
        this.bag = bag;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



}
