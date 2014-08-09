/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aleksandar
 */
public class CSVParser {
    
    BufferedReader reader;
    String file;
    String line;
    String separator;
    
    public CSVParser(String f) {
        this.file = f;
        this.line = "";
        this.separator = "\",\"";
    }
    
    public void printCsv() {
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                String[] tweet = line.split(separator);
                System.out.println(tweet[5].charAt(tweet[5].length() - 1));
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
