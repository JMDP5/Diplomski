/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import preprocessing.TextPreprocessing;
import weka.core.Utils;

/**
 *
 * @author aleksandar
 */
public class CSVParser {

    BufferedReader reader;
    TextPreprocessing textPreprocessing;
    String file;
    String outFile;
    String line;
    String separator;
    FileWriter writer;

    public CSVParser(String f, String outFile) {
        this.file = f;
        this.line = "";
        this.separator = ",";
        textPreprocessing = new TextPreprocessing();
        this.outFile = outFile;
    }

    public void processCSV() {
        try {
            Set<String> uniqueWords = new HashSet<>();
            int wordCount = 0;
            reader = new BufferedReader(new FileReader(file));
            writer = new FileWriter(outFile);
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(separator);
                String processed = (textPreprocessing.preprocess(parts[1]));

                //
                String[] tokenized = textPreprocessing.expandText(processed);
                wordCount += tokenized.length;
                for (String string : tokenized) {
                    uniqueWords.add(string);
                }
                writer.append(parts[0].substring(1, parts[0].length() - 1));
                writer.append(',');
                
                //Sredi ovo, ne moze ovako glupavo..
                if (processed.charAt(processed.length() - 1) == ' ') {
                    processed = processed.substring(1, processed.length() - 1);
                } else if (processed.charAt(0) == ' ') {
                    processed = processed.substring(1);
                } 
                
                String escaped = Utils.quote(processed);
                writer.append(escaped);
                writer.append("\n");

//                writer.append("'");
//                if (processed.charAt(processed.length() - 1) == ' ') {
//                    writer.append(processed.substring(1, processed.length() - 1));
//                } else if (processed.charAt(0) == ' ') {
//                    writer.append(processed.substring(1));
//                } else {
//                    writer.append(processed);
//                }
//                writer.append("'");
//                writer.append("\n");
            }

            System.out.println("Words total: " + wordCount);
            System.out.println("Unique words: " + uniqueWords.size());

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void printCsv() {
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(separator);
                System.out.println(parts.length);
//                System.out.println("Tweet text: " + tweet[5].substring(0, tweet[5].length()-1));
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        System.out.println("DONE! Whole file printed!");
    }
}
