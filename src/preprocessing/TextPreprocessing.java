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
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import sun.font.StrikeCache;

/**
 *
 * @author aleksandar
 */
public class TextPreprocessing {

    OpenNLPProcessing openNlp;
    private HashMap<String, HashMap<String, Double>> bigramCounts;
    private HashMap<String, Double> unigramCounts;
    private final String START = "<s>";

    private Set<String> vocabulary;
    private double vocabSize;
    private double totalWordCount; //total num of tokens, user to calculate probabilities for unigrams

    public TextPreprocessing() {
        vocabulary = new HashSet<>();
        unigramCounts = new HashMap<>();
        bigramCounts = new HashMap<>();
        totalWordCount = 0;
        vocabSize = 0;
        openNlp = new OpenNLPProcessing();
    }

    /**
     * Preprocess the text by removing punctuation, duplicate spaces and
     * lowercasing it, optionally it can POS tag text.
     *
     * @param text
     * @return
     */
    //      Dodaj posle ovoga i:
    //      2)Stemming i lemma 
    //      3)Negacija(n't do not didn't ....)-> NOT_(reč) tj. neku negaciju napravi(columbia rad)
    //      4)Emoticon -> their polarity(columbia)
    //      6)Reci sa vise od 3 vecana slova coooooool -> coool (columbia)
    //      8)Remove tweets with both positive :) AND negative :( emoticons
    //      9)Eliminisi sve reci krace od 2 char.
    public String preprocess(String text, boolean doPosTag) {
        //Zasto znak # ne izbaci??
        String patternUSER = "\"?@\\S+";
        String patternURL = "http(s?)://\\S+";

        //http://stackoverflow.com/questions/3807197/regex-for-matching-full-word-starting-with-javascript
        // ***** POCESIRAJ I SMAJLIJE pre ovog \p{Punct!}!!!
        text = text.toLowerCase();
        text = replaceSmileys(text);
        text = text.replaceAll(patternURL, "URL")
                .replaceAll("[\\p{Punct}&&[^@']]", " ").replaceAll("\\s+", " ").replaceAll("@\\s+", "@")
                .replaceAll(patternUSER, "USER");
        text = replaceNegations(text);
        text = removeRepeatedCharacters(text);
//                .replaceAll("not\\s", "not_");

        //Skloni ovo ***********************8
//        System.out.println("After regex: " + text);
        if (doPosTag) {
            return openNlp.POSTag(text);
        }
        return text;

    }

    public String replaceSmileys(String text) {
        String result = text;
        result = result.replaceAll(":(\\s*)[)]", "HAPPYSMILEY");
        result = result.replaceAll("[(]+(\\s*):", "HAPPYSMILEY");
        result = result.replaceAll("[(]+(\\s*);", "HAPPYSMILEY");
        result = result.replaceAll(";(\\s*)[)]", "HAPPYSMILEY");
        result = result.replaceAll(":-[)]+", "HAPPYSMILEY");
        result = result.replaceAll(";-[)]+", "HAPPYSMILEY");
        result = result.replaceAll(":(\\s*)d", "HAPPYSMILEY");
        result = result.replaceAll("\\s+xd\\b", " HAPPYSMILEY");
        result = result.replaceAll(":(\\s*)p", "HAPPYSMILEY");
        result = result.replaceAll(";(\\s*)d", "HAPPYSMILEY");
        result = result.replaceAll("=[)]", "HAPPYSMILEY");
        result = result.replaceAll("\\^_\\^", "HAPPYSMILEY");
        result = result.replaceAll(":(\\s*)[(]", "SADSMILEY");
        result = result.replaceAll(":-[(]", "SADSMILEY");
        result = result.replaceAll("[)]+(\\s*):", "SADSMILEY");
        result = result.replaceAll("[)]+(\\s*);", "SADSMILEY");
        result = result.replaceAll("[)]+-:", "SADSMILEY");
        return result;
    }

    public String removeRepeatedCharacters(String text) {
        return text.replaceAll("(.)\\1{2,100}", "$1$1");
    }
    
    public String replaceNegations(String text) {
        return text.replaceAll("\\w*n(o|'|t)t?\\b", "not");
    }

    /**
     * Splits the text into multiple chunks using 'space' as delimiter.
     *
     * @param text - text
     * @return
     */
    public String[] expandText(String text) {
        return text.split(" ");
    }

    /**
     * Counts the number of occurrences of all words inside the words array.
     *
     * @param words
     * @return words along with their count in words array
     */
    public Map<String, Double> createUnigramCount(String[] words) {
        for (int i = 0; i < words.length; ++i) {
            double counter = 0;
            if (unigramCounts.containsKey(words[i])) {
                counter = unigramCounts.get(words[i]);
            }
            unigramCounts.put(words[i], ++counter); //increase counter for this word

            vocabulary.add(words[i]);
            totalWordCount++;
        }

        vocabSize = vocabulary.size();
        return unigramCounts;
    }

    public HashMap<String, HashMap<String, Double>> createBigramCount(String[] words) {
        String prevWord = START;
        for (int i = 0; i < words.length; i++) {
            double unigramCounter = 0;
            if (unigramCounts.containsKey(prevWord)) {
                unigramCounter = unigramCounts.get(prevWord);
            }
            unigramCounts.put(prevWord, ++unigramCounter);

            vocabulary.add(words[i]);

            HashMap<String, Double> innerMap;
            if (bigramCounts.containsKey(prevWord)) {
                innerMap = bigramCounts.get(prevWord);
            } else {
                innerMap = new HashMap<>();
                bigramCounts.put(prevWord, innerMap);
            }

            double bigramCounter = 0.0;
            if (innerMap.containsKey(words[i])) {
                bigramCounter = innerMap.get(words[i]);
            }
            innerMap.put(words[i], ++bigramCounter);

            totalWordCount++;
            prevWord = words[i];
        }

        vocabSize = vocabulary.size();
        return bigramCounts;
    }

    /**
     * Tokenizes the document and returns a Document Object. In the end, we get
     * bag-of-words model for some text.
     *
     * @param text
     * @return
     */
    public Document tokenize(String text) {
        String preprocessedText = preprocess(text, false);
        System.out.println(preprocessedText);
        String[] keywordArray = expandText(preprocessedText);
        Document doc = new Document();
        doc.setBagBigram(createBigramCount(keywordArray));
        return doc;
    }

    public Set<String> getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(Set<String> vocabulary) {
        this.vocabulary = vocabulary;
    }

    public double getVocabSize() {
        return vocabSize;
    }

    public void setVocabSize(double vocabSize) {
        this.vocabSize = vocabSize;
    }

    public double getTotalWordCount() {
        return totalWordCount;
    }

    public void setTotalWordCount(double totalWordCount) {
        this.totalWordCount = totalWordCount;
    }
}
