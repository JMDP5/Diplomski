/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package preprocessing;

import java.util.Map;

/**
 *
 * @author aleksandar
 */
public abstract class FeatureExtractor {
    public abstract Map<String, Double> extractFeatures();
}
