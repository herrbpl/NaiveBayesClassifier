package siimaus.app;

import java.util.List;

import junit.framework.TestCase;
import siimaus.classifier.IClassifier;
import siimaus.classifier.NaiveBayes;
import siimaus.corpus.Document;
import siimaus.corpus.ICorpus;
import siimaus.util.FileUtils;

public class ExampleAclImdbBuilderTest extends TestCase {

	public ExampleAclImdbBuilderTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testBuilder() {
		ExampleAclImdbBuilder a = new ExampleAclImdbBuilder("./aclImdb", null);
		
		ICorpus c = a.build();
		System.out.println(c.getDocumentCount());
		
		IClassifier cl = new NaiveBayes(c);
		cl.train();
		
		int correct = 0;
		int incorrect = 0;
		List<CatDoc> test = a.sample(a.getTests(), 200);
		
		for (CatDoc catDoc : test) {
			String s = FileUtils.fromFileText(catDoc.path);
			
			String cat = cl.predict(s);
			if (cat == catDoc.cat) {
				correct++;
			} else {
				incorrect++;
			}
			
		}
		System.out.println(String.format("Training set size: %d, test set size: %d, correct: %d, incorrect: %d, %f%%",
				c.getDocumentCount(), 200, correct, incorrect, (correct*1.0/200.0)*100.0));
		
	}
	
}
