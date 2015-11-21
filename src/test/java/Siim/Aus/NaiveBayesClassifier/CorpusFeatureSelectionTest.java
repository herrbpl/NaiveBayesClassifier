package Siim.Aus.NaiveBayesClassifier;

import java.util.Map;

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
		double chisquareScore;
		int observationCount = c.getDocumentCount();
		System.out.println(c.getDocumentCount());
		// iterate over all features in corpus
		for (Feature feature : c.getVocabulary()) {
			//System.out.println(feature);
			// number of documents that have the feature
			//System.out.println(c.getDocumentCount(feature.getFeature()));	
			int N1dot = c.getDocumentCount(feature.getFeature());
			int N0dot = c.getDocumentCount() - N1dot;
			
			// iterate over categories
			for (Map.Entry<String, Category> entry : c.getCategories().entrySet()) {
				String category = entry.getKey();
				Category cat = entry.getValue();
				//System.out.println(String.format("%s:%d \n", category, cat.getDocumentCount()));
				// documents that have feature and belong to category
				int N11 = cat.getDocumentCount(feature.getFeature());
				//System.out.println(N11);
				// documents that belong to category but do not have the feature
				int N01 = cat.getDocumentCount()-N11; 
				//System.out.println(N01);
				
				//N00 counts the number of documents that don't have the feature and don't belong to the specific category
				int N00 = N0dot - N01;
				
				//N10 counts the number of documents that have the feature and don't belong to the specific category
				int N10 = N1dot - N11;
				
				chisquareScore = observationCount*Math.pow(N11*N00-N10*N01, 2)/((N11+N01)*(N11+N10)*(N10+N00)*(N01+N00));
				String formula = String.format("(%d * POWER( ((%d * %d) - (%d * %d)),2) ) / ((%d+%d)*(%d+%d)*(%d+%d)*(%d+%d))\n", 
						observationCount, N11, N00, N10, N01, N11, N01, N11, N10, N10,N00, N01, N00);
				System.out.println(formula);
				System.out.println(String.format("%s:%s:%f\n", feature.getFeature(), cat.getCategory(), chisquareScore));
			}
			
		}
		
		
		fail("Not implemented yet!");
	}
	
}
