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
import java.util.Map.Entry;

import junit.framework.TestCase;
import siimaus.classifier.IClassifier;
import siimaus.classifier.NaiveBayes;
import siimaus.corpus.Category;
import siimaus.corpus.Corpus;
import siimaus.corpus.Document;
import siimaus.corpus.ICorpus;
import siimaus.corpus.Vocabulary;
import siimaus.tokenizer.BaseTokenizer;
import siimaus.tokenizer.BasicPreprocessor;
import siimaus.tokenizer.ITokenizer;

public class CorpusTest extends TestCase {

	private ICorpus corpus;
	
	public CorpusTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
	}

	
	
	public void testCorpusBasics() {
		ITokenizer tokenizer = new BaseTokenizer(new BasicPreprocessor());
		Corpus c = new Corpus(tokenizer);
		c.addCategory("A").addCategory("B");
		
		
		
		c.addDocument("A", new Document("juhuu huhu", tokenizer));
		c.addDocument("B", new Document("BLAH", tokenizer));
		c.addDocument("C", new Document("mööö", tokenizer));
		c.addDocument("C", "proov");
		c.addDocument("B","proov");
		c.addDocument("D","proov2");		
		
		Vocabulary v;
		for (Entry<String, Category> ent : c.getCategories().entrySet()) {
			v = c.getVocabulary(ent.getKey());
			System.out.println(ent.getValue());						
			assertTrue(v.equals(ent.getValue().getVocabulary()));			
		}
		
		
		
	}
	
	public void testCorpusCalculation() {
		
		ITokenizer tokenizer = new BaseTokenizer(new BasicPreprocessor());
		Corpus c = new Corpus(tokenizer);
		// lets try real calculation		
		c.addDocument("c", "Chinese Beijing Chinese");
		c.addDocument("c", "Chinese Chinese Shangai");
		c.addDocument("c", "Chinese Macao");
		c.addDocument("j", "Tokyo Japan Chinese");
		c.addDocument("j", "Tokyo Hiroshima Japan");
		
		IClassifier cl = new NaiveBayes(c);
		
		// train
		cl.train();
				
		// predict
		Document d = new Document("Tokyo", c.getTokenizer());
		
		
		//Map<String, Double> predictions = cl.getPredictions(d);
		
		String ca = cl.predict(d);
		System.out.println(ca);
		
		/*System.out.println(Math.exp(predictions.get("c")));
		System.out.println(Math.exp(predictions.get("j")));
		System.out.println(predictions);
		*/

	}
	

}
