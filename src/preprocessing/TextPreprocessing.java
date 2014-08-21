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
    }

    /**
     * Preprocess the text by removing punctuation, duplicate spaces and
     * lowercasing it.
     *
     * @param text
     * @return
     */
    public String preprocess(String text) {
        //Zasto znak # ne izbaci??
        String patternUSER = "\"?@\\S+";
        String patternURL = "http(s?)://\\S+";
        //Pogledaj ovo!
        //http://stackoverflow.com/questions/3807197/regex-for-matching-full-word-starting-with-javascript
        // ***** POCESIRAJ I SMAJLIJE pre ovog \p{Punct!}!!!
        return text.toLowerCase().replaceAll(patternURL, "URL")
                .replaceAll("[\\p{Punct}&&[^@%]]", " ").replaceAll("\\s+", " ").replaceAll("@\\s+", "@")
                .replaceAll(patternUSER, "USER");

        //Dodaj posle ovoga i 1)URL -> |U|
        //      1)URL -> |U|
        //      2)Username(Tag) -> |T|
        //      3)Negations -> NOT tj neku negaciju napravi(columbia rad)
        //      4)Emoticon -> their polarity(columbia)
        //      5)Reci sa vise od 3 vecana slova coooooool -> coool (columbia)
        //      6)Stemming (Ne znam da li se isplati ovo..)
        //      7)Remove tweets with both positive :) AND negative :( emoticons
        //      8)Collapsing QUERY_TERM
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
     * Tokenizes the document and returns a Document Object. 
     * In the end, we get bag-of-words model for some text.
     *
     * @param text
     * @return
     */
    public Document tokenize(String text) {
        String preprocessedText = preprocess(text);
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
