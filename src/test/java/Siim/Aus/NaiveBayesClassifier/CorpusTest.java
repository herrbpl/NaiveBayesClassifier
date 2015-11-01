package Siim.Aus.NaiveBayesClassifier;

import junit.framework.TestCase;

public class CorpusTest extends TestCase {

	private Corpus corpus;
	
	public CorpusTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
	}

	public void testGetFeatureCount() {
		
		corpus = new Corpus();
		
		int f;
		f = corpus.getFeatureCount();
		assertEquals(0, f);
		corpus.addInput("test", "one two three four ");
		
		f = corpus.getFeatureCount();
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
		
		f = corpus.getFeatureCount();
		assertEquals(4, f);
		
		corpus.addInput("test2", "quick brown fox jumped over lazy dog");
		f = corpus.getFeatureCount("test2");
		assertEquals(11, f);
		
		f = corpus.getFeatureCount();
		assertEquals(11, f);
		
	}
	
	

}
