package siimaus.naivebayesclassifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import junit.framework.TestCase;
import siimaus.naivebayesclassifier.ChiSquareScore;
import siimaus.naivebayesclassifier.Corpus;
import siimaus.naivebayesclassifier.Document;
import siimaus.naivebayesclassifier.DummyScore;
import siimaus.naivebayesclassifier.IFeatureScore;

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

		IFeatureScore scoreEngine = new ChiSquareScore();

		Map<String, Double> score = scoreEngine.score(c);
		System.out.println(score);
		assertEquals(0, score.size());

		c.addDocument("j", "Tokyo Japan Chinese");
		c.addDocument("j", "Tokyo Hiroshima Japan");
		System.out.println(score);

		score = scoreEngine.score(c);

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

	public void testChiScoreCalculationLarge() {

		Corpus corpus = new Corpus();
		Document doc1 = Document.fromFile("./training.language.en.txt");
		Document doc2 = Document.fromFile("./training.language.de.txt");
		Document doc3 = Document.fromFile("./training.language.fr.txt");

		corpus.addDocument("en", doc1);
		corpus.addDocument("de", doc2);
		corpus.addDocument("fr", doc3);

		System.out.println(corpus.getDocumentCount());
		System.out.println(corpus.getVocabularySize());

		IFeatureScore scoreEngine = new ChiSquareScore();

		Map<String, Double> score = scoreEngine.score(corpus);
		System.out.println("Score 1");
/*
		for (Map.Entry<String, Double> scoreEntry : score.entrySet()) {
			System.out.println(String.format("%s -> %f", scoreEntry.getKey(), scoreEntry.getValue()));

		}
*/
		score = scoreEngine.score(corpus, 10.81);
		System.out.println("Score 2");
		for (Map.Entry<String, Double> scoreEntry : score.entrySet()) {
			System.out.println(String.format("%s -> %f", scoreEntry.getKey(), scoreEntry.getValue()));

		}

		corpus.train();

		Document d = new Document("Ma armastan Sind. I love you!");

		Map<String, Double> predictions = corpus.getPredictions(d);

		String ca = corpus.predict(d);
		System.out.println(ca);
		System.out.println(Math.exp(predictions.get("en")));
		System.out.println(Math.exp(predictions.get("fr")));
		System.out.println(Math.exp(predictions.get("de")));
		System.out.println(predictions);

	}

	public void testVeryLargeDataset() {
		Corpus corpus = new Corpus();

		// pos
		String folderPos = "./aclImdb/aclImdb/test/pos";
		String fileName = "";

		/*
		 * Directory iteration code from http://stackoverflow.com/a/4917347
		 */
		
		int maxObservations = 1000;
		
		int currentObservations;
		
		currentObservations = 0;
		File dir = new File(folderPos);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				if (child.isFile()) {
					fileName = folderPos + File.separatorChar + child.getName();
					Document doc = Document.fromFile(fileName);
					System.out.println(fileName);
					corpus.addDocument("pos", doc);
					currentObservations++;
					if (currentObservations > maxObservations) {
						break;
					}
				}
			}
		}

		System.out.println(corpus.getVocabularySize());
		
		// pos
		folderPos = "./aclImdb/aclImdb/test/neg";
		fileName = "";

		/*
		 * Directory iteration code from http://stackoverflow.com/a/4917347
		 */
		currentObservations = 0;
		dir = new File(folderPos);
		directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				if (child.isFile()) {
					fileName = folderPos + File.separatorChar + child.getName();
					Document doc = Document.fromFile(fileName);
					System.out.println(fileName);
					corpus.addDocument("neg", doc);
					currentObservations++;
					if (currentObservations > maxObservations) {
						break;
					}
				}
			}
		}

		corpus.train();
		
		/*
		 * Directory iteration code from http://stackoverflow.com/a/4917347
		 */
		
		
		folderPos = "./aclImdb/aclImdb/train/neg";
		maxObservations = 10;
		fileName = "";
		currentObservations = 0;
		dir = new File(folderPos);
		directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				if (child.isFile()) {
					fileName = folderPos + File.separatorChar + child.getName();
					Document doc = Document.fromFile(fileName);
					System.out.println(fileName);
					
					Map<String, Double> predictions = corpus.getPredictions(doc);

					String ca = corpus.predict(doc);
					System.out.println(ca);					
					System.out.println(predictions);
					
					currentObservations++;
					if (currentObservations > maxObservations) {
						break;
					}
				}
			}
		}
	
		
		folderPos = "./aclImdb/aclImdb/train/pos";
		maxObservations = 10;
		fileName = "";
		currentObservations = 0;
		dir = new File(folderPos);
		directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				if (child.isFile()) {
					fileName = folderPos + File.separatorChar + child.getName();
					Document doc = Document.fromFile(fileName);
					System.out.println(fileName);
					
					Map<String, Double> predictions = corpus.getPredictions(doc);

					String ca = corpus.predict(doc);
					System.out.println(ca);					
					System.out.println(predictions);
					
					currentObservations++;
					if (currentObservations >= maxObservations) {
						break;
					}
				}
			}
		}
		
	}

}