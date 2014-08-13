/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocessing;

import data.Document;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author aleksandar
 */
public class TextPreprocessing {

    /**
     * Preprocess the text by removing punctuation, duplicate spaces and
     * lowercasing it.
     *
     * @param text
     * @return
     */
    public static String preprocess(String text) {
        return text.replaceAll("\\p{P}", " ").replaceAll("\\s+", " ").toLowerCase(Locale.getDefault());
        //Dodaj posle ovoga i 1)URL -> |U|
        //      1)URL -> |U|
        //      2)Username(Tag) -> |T|
        //      3)Negations -> NOT (columbia rad)
        //      4)Emoticon -> their polarity(columbia)
        //      5)Reci sa vise od 3 vecana slova coooooool -> coool (columbia)
        //      6)Remove RT
        //      7)Remove tweets with both positive :) AND negative :( emoticons
    }

    /**
     * Ngram extraction from the text. For now it supports unigram and bigram
     * extraction.
     *
     * @param text - text
     * @param ngramSize - size of ngrams that will be extracted.
     * @return
     */
    public static String[] extractNgram(String text, int ngramSize) {
        if (ngramSize == 1) {
            return text.split(" ");
            
        } else if (ngramSize == 2) {
            throw new UnsupportedOperationException("Not supported yet.");
        } else {
            throw new RuntimeException("Only unigram and bigram models are "
                    + "suppored!");
        }
    }

    /**
     * Counts the number of occurrences of all words inside the words array.
     *
     * @param words
     * @return words along with their count in words array
     */
    public static Map<String, Integer> getUnigramCount(String[] words) {
        Map<String, Integer> counts = new HashMap<>();
        Integer counter;
        for (int i = 0; i < words.length; ++i) {
            counter = counts.get(words[i]);
            if (counter == null) {
                counter = 0;
            }
            counts.put(words[i], ++counter); //increase counter for the keyword
        }
        
        return counts;
    }

    /**
     * Tokenizes the document and returns a Document Object. Na kraju dobijam
     * Bag-of-word model za neki tekst
     *
     * @param text
     * @return
     */
    public static Document tokenize(String text) {
        String preprocessedText = preprocess(text);
        System.out.println(preprocessedText);
        String[] keywordArray = extractNgram(preprocessedText, 1);
        Document doc = new Document();
        doc.setBag(getUnigramCount(keywordArray));
        return doc;
    }
    
    
    public ArrayList<String> unigramTokenizer(String input) {
        String[] tokens = input.trim().split("\\s+");
        ArrayList<String> result = new ArrayList<String>(Arrays.asList(tokens));
        return result;
    }
    /*
     * BIGRAM TOKENIZER
     * Will create a BIGRAM+ ArrayList from the input string.
     * (prev->current) tuples added
     */
    
    public ArrayList<String> bigramTokenizer(String input) {
        String[] tokens = input.trim().split("\\s+");
        ArrayList<String> result = unigramTokenizer(input);
        String prev = "";
        String current;
        for (String s : tokens) {
            current = s;
            if (prev != "") {
                result.add(prev + " " + current);
            }
            prev = s;
        }
        return result;
    }
}
