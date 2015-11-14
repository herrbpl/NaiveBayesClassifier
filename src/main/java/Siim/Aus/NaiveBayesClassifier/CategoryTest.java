package Siim.Aus.NaiveBayesClassifier;

import junit.framework.TestCase;

public class CategoryTest extends TestCase {

	public CategoryTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testAddDocumentDocument() {
		Category c = new Category("TEST");
		Document d = new Document("Lammas all paremal nurgas");
		c.addDocument(d);
		assertEquals(1,  c.getDocuments().size());
	}

	public void testAddDocumentString() {
		Category c = new Category("TEST");		
		c.addDocument("Lammas all paremal nurgas");
		assertEquals(1,  c.getDocuments().size());
	}

}
