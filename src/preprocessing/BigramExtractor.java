/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package preprocessing;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Aleksandar
 */
public class BigramExtractor {
    
    private HashMap<String, HashMap<String, Double>> counts;
    private HashMap<String, Double> unigramCounts;
    private final String START = "<s>";
            
    private Set<String> vocabulary;
    private double vocabSize;

       public static void extractBigrams() {
    }
    
    
}
