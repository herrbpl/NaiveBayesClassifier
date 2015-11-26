package siimaus.naivebayesclassifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import siimaus.corpus.Corpus;
import siimaus.corpus.Document;

public class CorpusTest extends TestCase {

	private Corpus corpus;
	
	public CorpusTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
	}

	
	
	public void testGetFeatureCountString() {
		/*
		corpus = new Corpus();
		
		
		
		
		String line = null;
		String text = "";
		String fileName = "./training.language.en.txt";
		
		System.out.println(fileName);
		try {
			FileReader fileReader = 
			        new FileReader(fileName);
				

        // Always wrap FileReader in BufferedReader.
        BufferedReader bufferedReader = 
            new BufferedReader(fileReader);

        while((line = bufferedReader.readLine()) != null) {
            text += line;
        }   

        // Always close files.
        bufferedReader.close(); 
		} catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" + 
                    fileName + "'");                
            }
		catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
        corpus = new Corpus();
        corpus.addDocument("eng", text);
        //corpus.addDocument("de", text);
        System.out.println(corpus.getVocabularySize());
        System.out.println(corpus.getCategories().keySet().toString());
        System.out.println(corpus.getCategory("eng").getDocumentCount());
        System.out.println(corpus.getCategory("eng").getVocabulary().size());
        //System.out.println(corpus);
        System.out.println(corpus.getVocabulary());
        
        // sorting output
        
        List<Feature> fbyCount = corpus.getVocabulary().getFeatures();
        
        Collections.sort(fbyCount, 
        	new Comparator<Feature>() {

				@Override
				public int compare(Feature o1, Feature o2) {
					// TODO Auto-generated method stub
					return o2.count - o1.count;
				}
        		
        	}
        );
        
        for (Feature feature : fbyCount) {
			System.out.println(feature);
		}
	*/
	}
	
	public void testCorpusCalculation() {
		// lets try real calculation
		Corpus c = new Corpus();
		c.addDocument("c", "Chinese Beijing Chinese");
		c.addDocument("c", "Chinese Chinese Shangai");
		c.addDocument("c", "Chinese Macao");
		c.addDocument("j", "Tokyo Japan Chinese");
		c.addDocument("j", "Tokyo Hiroshima Japan");
		
		System.out.println(c.getCategory("c").getVocabulary());
		System.out.println(c.getCategory("j").getVocabulary());
		
		System.out.println(c.getCategory("c").getDocumentCount());
		System.out.println(c.getCategory("c"));
		System.out.println(c.getCategory("c").toJSON());
		
		// train
		c.train();
				
		// predict
		Document d = new Document("Tokyo");
		
		
		Map<String, Double> predictions = c.getPredictions(d);
		
		String ca = c.predict(d);
		System.out.println(ca);
		System.out.println(Math.exp(predictions.get("c")));
		System.out.println(Math.exp(predictions.get("j")));
		System.out.println(predictions);
		//IMprove
	}
	

}
