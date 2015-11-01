package Siim.Aus.NaiveBayesClassifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import junit.framework.TestCase;

public class CorpusTest extends TestCase {

	private Corpus corpus;
	
	public CorpusTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
	}

	public void testGetAllFeatureCount() {
		
		corpus = new Corpus();
		
		int f;
		f = corpus.getAllFeatureCount();
		assertEquals(0, f);
		corpus.addInput("test", "one two three four ");
		
		f = corpus.getAllFeatureCount();
		assertEquals(4, f);
	}
	
	public void testGetFeatureCountString() {
		corpus = new Corpus();
		corpus.addInput("test", "one two three four ");
		int f;
		f = corpus.getFeatureCount("test");
		assertEquals(4, f);
		
		corpus.addInput("test2", "one two three four ");
		f = corpus.getFeatureCount("test2");
		assertEquals(4, f);
		
		f = corpus.getAllFeatureCount();
		assertEquals(4, f);
		
		corpus.addInput("test2", "quick brown fox jumped over lazy dog");
		f = corpus.getFeatureCount("test2");
		assertEquals(11, f);
		
		f = corpus.getAllFeatureCount();
		assertEquals(11, f);
		
		System.out.println(corpus);
		
		corpus.addInput("test", "one third in cash in advance ");
		f = corpus.getAllFeatureCount();
		assertEquals(15, f);
		
		System.out.println(corpus);
		
		
		
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
        corpus.addInput("eng", text);
        System.out.println(corpus.getAllFeatureCount());
        System.out.println(corpus.getFeatureCount("eng"));
        //System.out.println(corpus);
        
	}
	
	

}
