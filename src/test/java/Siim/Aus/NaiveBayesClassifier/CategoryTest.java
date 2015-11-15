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
	
	public void testRemoveDocument() {
		Category c = new Category("TEST");
		Document d = new Document("Lammas all paremal nurgas");
		c.addDocument(d);
		assertEquals(1,  c.getDocuments().size());
		c.removeDocument(d);
		assertEquals(0,  c.getDocuments().size());
		assertEquals(0,  c.getVocabulary().distinctCount());
	
		c.addDocument("Lammas all paremal nurgas");
		assertEquals(1,  c.getDocuments().size());
		Document e = new Document("Lammas all paremal nurgas");
		c.removeDocument(e);
		assertEquals(0,  c.getDocuments().size());
		assertEquals(0,  c.getVocabulary().distinctCount());
		
		c.addDocument("Lammas all paremal nurgas");
		assertEquals(1,  c.getDocuments().size());
		c.addDocument("Lammas all paremal nurgas");
		assertEquals(2,  c.getDocuments().size());
		c.removeDocument("Lammas all paremal nurgas");
		assertEquals(1,  c.getDocuments().size());
	}

	public void testGetFeatureCount() {
		System.out.println("Test begins");
		Category c = new Category("TEST");		
		c.addDocument("Lammas all paremal nurgas");
		assertEquals(1,  c.getDocuments().size());
		
		assertEquals(1, c.featureCount("lammas"));
		assertEquals(1, c.featureCount("Lammas"));
		
		c.addDocument("Lammas all paremal nurgas");
		
		assertEquals(2, c.featureCount("Lammas"));
		System.out.println(c.getVocabulary());
		
		c.addDocument("Lammas ülal vasakus nurgas");
		System.out.println(c.getVocabulary());
		assertEquals(3, c.featureCount("Lammas"));
	}
	
}
