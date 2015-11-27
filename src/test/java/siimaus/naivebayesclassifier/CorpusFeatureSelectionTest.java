package siimaus.naivebayesclassifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import junit.framework.TestCase;
import siimaus.classifier.IClassifier;
import siimaus.classifier.NaiveBayes;
import siimaus.corpus.Corpus;
import siimaus.corpus.Document;
import siimaus.corpus.ICorpus;
import siimaus.corpus.score.ChiSquareScore;
import siimaus.corpus.score.DummyScore;
import siimaus.corpus.score.IFeatureScore;
import siimaus.tokenizer.BaseTokenizer;
import siimaus.tokenizer.BasicPreprocessor;
import siimaus.tokenizer.IPreprocessor;
import siimaus.tokenizer.ITokenizer;
import siimaus.tokenizer.NGramTokenizer;
import siimaus.util.FileUtils;

public class CorpusFeatureSelectionTest extends TestCase {

	public CorpusFeatureSelectionTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testChiSquareCalculation() {
		ICorpus c = new Corpus();
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
		ITokenizer tokenizer = new BaseTokenizer(new BasicPreprocessor());
		
		
		Corpus corpus = new Corpus(tokenizer);
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

		IClassifier cl = new NaiveBayes(corpus);
		
		cl.train();

		Document d = new Document("Ma armastan Sind!", corpus.getTokenizer());		

		String ca = cl.predict(d);
		System.out.println(ca);
		
		d = new Document("Ich Liebe Dich!", corpus.getTokenizer());		

		ca = cl.predict(d);
		System.out.println(ca);

	}

	public void testVeryLargeDataset() {
		ITokenizer tokenizer = new NGramTokenizer(new BasicPreprocessor(), 3);
						
		
		Corpus corpus = new Corpus(tokenizer);
		
		
		//siimaus.corpus.defaultTokenizer.stopWords = null;
		// pos
		String folderPos = "./aclImdb/test/pos";
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
					
					Document doc = new Document(FileUtils.fromFileText(fileName), corpus.getTokenizer());
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
		folderPos = "./aclImdb/test/neg";
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
					Document doc = new Document(FileUtils.fromFileText(fileName), corpus.getTokenizer());
					
					System.out.println(fileName);
					corpus.addDocument("neg", doc);
					currentObservations++;
					if (currentObservations > maxObservations) {
						break;
					}
				}
			}
		}

		IClassifier cl = new NaiveBayes(corpus);
		cl.train();
				
		
		/*
		 * Directory iteration code from http://stackoverflow.com/a/4917347
		 */
		
		
		folderPos = "./aclImdb/train/neg";
		maxObservations = 100;
		fileName = "";
		currentObservations = 0;
		int totalObservations = 0;
		dir = new File(folderPos);
		directoryListing = dir.listFiles();
		int correctCount = 0;
		if (directoryListing != null) {
			for (File child : directoryListing) {
				if (child.isFile()) {
					fileName = folderPos + File.separatorChar + child.getName();					
					Document doc = new Document(FileUtils.fromFileText(fileName), corpus.getTokenizer());
										

					String ca = cl.predict(doc);
					if (ca.equals("neg")) { 
						correctCount++;
					} else {
						System.out.println(fileName);
						System.out.println(ca);											
					}
					  
					
					currentObservations++;
					if (currentObservations > maxObservations) {
						break;
					}
				}
			}
		}
	
		totalObservations += currentObservations;
		
		
		folderPos = "./aclImdb/train/pos";
		maxObservations = 100;
		fileName = "";
		currentObservations = 0;
		dir = new File(folderPos);
		directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				if (child.isFile()) {
					fileName = folderPos + File.separatorChar + child.getName();					
					Document doc = new Document(FileUtils.fromFileText(fileName), corpus.getTokenizer());
					
					

					String ca = cl.predict(doc);
					if (ca.equals("pos")) { 
						correctCount++;
					} else {
						System.out.println(fileName);
						System.out.println(ca);											
					}
					  
					
					currentObservations++;
					if (currentObservations >= maxObservations) {
						break;
					}
				}
			}
		}
		
		totalObservations += currentObservations;
		
		System.out.println(String.format("Correctly guessed %d/%d (%f%%)", correctCount, totalObservations, (100.0*(correctCount/(totalObservations*1.0)))));
	}

}
