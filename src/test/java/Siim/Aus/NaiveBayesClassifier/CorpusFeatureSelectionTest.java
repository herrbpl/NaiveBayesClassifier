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
				
		IFeatureScore scoreEngine = new ChiSquareScore();
		
		Map<String, Double> score = scoreEngine.score(c);
		System.out.println(score);
		
		assertEquals(5.0, score.get("tokyo"));
		assertEquals(1.875, score.get("chinese"));
		assertEquals(1.875, score.get("hiroshima"));
		assertEquals(5.0, score.get("japan"));
		
		score = scoreEngine.score(c, 10.91);
		System.out.println(score);
						
		assertEquals(0, score.size());		
		
		scoreEngine = new DummyScore();
		score = scoreEngine.score(c);
		System.out.println(score);
		assertEquals(0.0, score.get("tokyo"));
	}
	
}
