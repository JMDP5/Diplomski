/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocessing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import net.didion.jwnl.data.IndexWord;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
 *
 * @author aleksandar
 */
public class OpenNLPProcessing {

    Tokenizer tokenizer;
    POSTagger tagger;

    public OpenNLPProcessing() {
        InputStream is = null;
        try {
            is = new FileInputStream("data/en-token.bin");
            TokenizerModel model = new TokenizerModel(is);
            this.tokenizer = new TokenizerME(model);

            POSModel posModel = new POSModelLoader().load(new File("data/en-pos-maxent.bin"));
            this.tagger = new POSTaggerME(posModel);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String[] tokenize(String text) {
        return tokenizer.tokenize(text);
    }

    public String POSTag(String text) {
        String[] tokens = tokenize(text);
        String[] tags = tagger.tag(tokens);
        POSSample sample = new POSSample(tokens, tags);
        return sample.toString();
    }
    
    public void lemmatization(String text) {
        //Complete this.
    }
    
    

}
