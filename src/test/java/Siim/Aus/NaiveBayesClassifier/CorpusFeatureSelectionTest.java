package Siim.Aus.NaiveBayesClassifier;

import junit.framework.TestCase;

public class CorpusFeatureSelectionTest extends TestCase {

	public CorpusFeatureSelectionTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testChiSquareCalculation() {
		Corpus c = new Corpus();
		c.addDocument("c", "Chinese Beijing Chinese");
		c.addDocument("c", "Chinese Chinese Shangai");
		c.addDocument("c", "Chinese Macao");
		c.addDocument("j", "Tokyo Japan Chinese");
		c.addDocument("j", "Tokyo Hiroshima Japan");
		
		System.out.println(c.getDocumentCount());
		// iterate over all features in corpus
		for (Feature feature : c.getVocabulary()) {
			System.out.println(feature);
			// number of documents that have the feature
			System.out.println(c.getDocumentCount(feature.getFeature()));	
			
		}
		
		
		fail("Not implemented yet!");
	}
	
}
