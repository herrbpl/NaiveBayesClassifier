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
		assertEquals(0, v.size());
		v.addFeature("Test");
		assertEquals(1, v.size());
		v.addFeature("Test");
		assertEquals(1, v.size());
		v.addFeature("Test2");
		assertEquals(2, v.size());
		v.removeFeature("Test");
		assertEquals(1, v.size());						
	}

	public void testCopy() {
		
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
	}

}
