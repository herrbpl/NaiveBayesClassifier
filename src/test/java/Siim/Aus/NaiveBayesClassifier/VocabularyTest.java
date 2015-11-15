package Siim.Aus.NaiveBayesClassifier;

import junit.framework.TestCase;

public class VocabularyTest extends TestCase {
	public Vocabulary v;

	public VocabularyTest(String name) {
		super(name);

	}

	protected void setUp() throws Exception {
		super.setUp();
		v = new Vocabulary();

	}

	public void testIterator() {
		String s = "";
		
		for (Feature feature : v) {
			System.out.println(feature.getFeature());
			s += feature.getFeature();			
		}
		assertEquals("", s);
		
		v.addFeature("Test1").addFeature("Test2");
		s = "";		
		for (Feature feature : v) {
			
			s += feature.getFeature();			
		}
		assertEquals("Test1Test2", s);
	}
	
	public void testSize() {
		v.clear();
		assertEquals(0, v.distinctCount());
		v.addFeature("Test");
		assertEquals(1, v.distinctCount());
		v.addFeature("Test");
		assertEquals(1, v.distinctCount());
		v.addFeature("Test2");
		assertEquals(2, v.distinctCount());
		v.removeFeature("Test");
		assertEquals(1, v.distinctCount());						
	}

	public void testCopy() {
		Vocabulary a, b;
		a = new Vocabulary();
		b = a.copy();
		assertEquals(true, a.equals(b));
		assertEquals(true, !(a==b));
	}

	public void testEquals() {
		Vocabulary a, b;
		a = new Vocabulary();
		b = a;
		assertEquals(true, a.equals(b));
		b = new Vocabulary();
		assertEquals(true, a.equals(b));
		b = a.copy();
		assertEquals(true, a.equals(b));		
		b.addFeature("TEST");
		assertEquals(false, a.equals(b));
		a.addFeature("TEST");
		assertEquals(true, a.equals(b));
		a.likelihoods.put("TEST", 0.01);
		assertEquals(true, a.equals(b));
		assertEquals(false, a.equalsAll(b));
	}
	
	public void testAddFeature() {
		Vocabulary a, b;
		a = new Vocabulary();
		a.addFeature("A").addFeature("A").addFeature("B");
		assertEquals(2, a.getFeature("A").count);
		assertEquals(1, a.getFeature("B").count);
		a.addFeature("X",0);
		assertEquals(0, a.getFeature("X").count);
		a.addFeature("A",0);
		assertEquals(2, a.getFeature("A").count);
		System.out.println(a);
		
	}
	
	public void testAddVocabulary() {
		Vocabulary a, b;
		a = new Vocabulary();
		b = new Vocabulary();
		a.addFeature("A").addFeature("B");
		assertEquals(1, a.getFeature("A").count);
		assertEquals(1, a.getFeature("B").count);
		b.addFeature("A").addFeature("C");
		assertEquals(1, b.getFeature("A").count);
		assertEquals(1, b.getFeature("C").count);
		a.addVocabulary(b);
		assertEquals(2, a.getFeature("A").count);
		assertEquals(1, a.getFeature("B").count);
		assertEquals(1, a.getFeature("C").count);		
		a.removeVocabulary(b);		
		assertEquals(1, a.getFeature("A").count);
		assertEquals(1, a.getFeature("B").count);
		assertEquals(0, a.getFeature("C").count);
		a.removeVocabulary(b);				
		assertEquals(0, a.getFeature("A").count);
		assertEquals(1, a.getFeature("B").count);
		
		assertEquals(0, a.getFeature("C").count);
		System.out.println(a);
		System.out.println(b);
		b.removeVocabulary(b);
		System.out.println(b);
	}

	public void testSetFeatureCount() {
		Vocabulary a;
		a = new Vocabulary();
		a.addFeature("TEST");
		assertEquals(1, a.getFeature("TEST").count);
		a.setFeatureCount("TEST", 10);
		assertEquals(10, a.getFeature("TEST").count);
		a.setFeatureCount("TEST", 0);		
		assertEquals(0, a.getFeature("TEST").count);
		a.setFeatureCount("TEST", -10);
		assertEquals(0, a.getFeature("TEST").count);
		a.allowNegativeFeatures = true;
		a.setFeatureCount("TEST", -10);
		assertEquals(-10, a.getFeature("TEST").count);
		System.out.println(a);
	}
	
}
