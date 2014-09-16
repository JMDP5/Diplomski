/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.dictionary.Dictionary;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import static weka.core.Utils.arrayToString;

/**
 *
 * @author aleksandar
 */
public class OpenNLPProcessing {

    Tokenizer tokenizer;
    POSTagger tagger;
    List<String> stopwords;

    public OpenNLPProcessing() {
        InputStream is = null;
        try {
            is = new FileInputStream("data/en-token.bin");
            TokenizerModel model = new TokenizerModel(is);
            this.tokenizer = new TokenizerME(model);

            POSModel posModel = new POSModelLoader().load(new File("data/en-pos-maxent.bin"));
            this.tagger = new POSTaggerME(posModel);
            JWNL.initialize(new FileInputStream("file_properties.xml"));

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JWNLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String[] tokenize(String text, boolean removeStopwords) {
        String[] tokens = tokenizer.tokenize(text);

//        String[] tokens = text.split(" ");


        //Skloni ovo ***********************
//        System.out.println("Afnoter tokenization: ");
//        for (int i = 0; i < tokens.length ; i++) {
//            System.out.print(tokens[i] + " ");
//        }

        if (removeStopwords) {
            return removeStopWords(tokens);
        }
        return tokens;

    }

    public String POSTag(String text) {
        String[] tokens = tokenize(text, true);
        
        //Skloni ovo ***********************
//        System.out.println("");
//        System.out.println("After stopword removal: ");
//        for (int i = 0; i < tokens.length; i++) {
//            System.out.print(tokens[i] + " ");
//        }
//        System.out.println("");
//        System.out.println("Final: ");
        //Obrisi ovo gore ***********************
        
        String[] tags = tagger.tag(tokens);
        for (int i = 0; i < tags.length; i++) {

            POS pos = null;

            //care only about verbs,nouns,adjectives and adverbs.
            if (tags[i].startsWith("VB")) {
                pos = POS.VERB;
            } else if (tags[i].startsWith("NN")) {
                pos = POS.NOUN;
            } else if (tags[i].startsWith("JJ")) {
                pos = POS.ADJECTIVE;
            } else if (tags[i].startsWith("RB")) {
                pos = POS.ADVERB;
            }

            if (pos != null) {
                try {
//                    System.out.println(tokens[i]);
//                    System.out.println(pos.getLabel());
                    Dictionary dict = Dictionary.getInstance();
                    IndexWord indexWord = dict.lookupIndexWord(pos, tokens[i]);
                    if (indexWord != null) {
                        tokens[i] = indexWord.getLemma();
                    }
                    tokens[i] += "_" + pos.getLabel();
                } catch (JWNLException ex) {
                    ex.printStackTrace();
                }
            }

        }
//        System.out.println("*******Resut **********");
//        for (int i = 0; i < tokens.length; i++) {
//            System.out.println(tokens[i]);
//        }
        return convertArrayToString(tokens);
//        POSSample sample = new POSSample(tokens, tags);
//        return sample.toString();
    }

    public void lemmatization(String text) {
        //Complete this.
    }

    public String[] removeStopWords(String[] tokens) {
        if (this.stopwords == null || this.stopwords.isEmpty()) {
            loadStopwordsFromFile();
        }
        ArrayList<String> tokenList = new ArrayList<>(Arrays.asList(tokens));
        for (int i = 0; i < tokenList.size(); i++) {
            if (this.stopwords.contains(tokenList.get(i))) {
                tokenList.remove(tokenList.remove(i));
                i--;
            }
        }
        return (String[]) tokenList.toArray(new String[tokenList.size()]);
    }

    private void loadStopwordsFromFile() {
        this.stopwords = new ArrayList<>();
        try {
            BufferedReader f = new BufferedReader(new FileReader("data/stopwords/stop-words.txt"));
            String line;
            while ((line = f.readLine()) != null) {
                this.stopwords.add(line);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Stopwords file not loaded! Error: " + ex.getMessage());
        }
    }

    private String convertArrayToString(String[] tokens) {
        String result = "";
        for (String s : tokens) {
            result += s + " ";
        }
        return result;
    }

}
