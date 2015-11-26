package siimaus.tokenizer;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import siimaus.corpus.Document;
import siimaus.corpus.Feature;
import siimaus.corpus.Vocabulary;
import siimaus.tokenizer.BaseTokenizer;

public class TextTokenizerTest extends TestCase {

	private BaseTokenizer t;

	public TextTokenizerTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		t = new BaseTokenizer();		
	}

	public void testPrepareInput() {
		// test against empty string
		String testString = "";
		String expected = "";
		assertEquals(expected, t.prepareInput(testString));

		// whitespace only
		testString = "  	";
		expected = "";
		assertEquals(expected, t.prepareInput(testString));

		// whitespace and punctuation only
		testString = "	 ., 	";
		expected = "";
		assertEquals(expected, t.prepareInput(testString));

		// trimming
		testString = " test ";
		expected = "test";
		assertEquals(expected, t.prepareInput(testString));

		// trimming
		testString = " test test ";
		expected = "test test";
		assertEquals(expected, t.prepareInput(testString));

		// trimming
		testString = " test  .	 test ";
		expected = "test test";
		assertEquals(expected, t.prepareInput(testString));

		// lowercasing
		testString = " Test ";
		expected = "test";
		assertEquals(expected, t.prepareInput(testString));

	}

	public void testTokenizeWords() {
		Map<String, Feature> result;
		Vocabulary x;

		// empty string
		String testString = "";
		x = t.tokenizeWords(testString);
		result = x.getFeatures();

		System.out.println(result.isEmpty());

		assertTrue(result.isEmpty());

		// two strings
		testString = "test test2 ";
		x = t.tokenizeWords(testString);
		result = x.getFeatures();
		assertTrue(!result.isEmpty());
		assertTrue(result.containsKey("test"));
		assertTrue(result.containsKey("test2"));
		assertEquals(2, result.size());

		int i;
		i = result.get("test").count;

		assertEquals(1, i);

		i = result.get("test2").count;
		assertEquals(1, i);

		// three strings
		testString = "test test2 test";
		x = t.tokenizeWords(testString);
		result = x.getFeatures();

		assertTrue(!result.isEmpty());
		assertTrue(result.containsKey("test"));
		assertTrue(result.containsKey("test2"));
		assertEquals(2, result.size());

		i = result.get("test").count;

		assertEquals(2, i);

		i = result.get("test2").count;
		assertEquals(1, i);

		testString = "test test2 test wordwith nokk 123\n\n 32345 this is my car";
		// three strings and numbers
		//testString = "1222 test test2 test wordwith> <nokk 1234\n\n 2345 this is my car";
		x = t.tokenizeWords(testString);
		result = x.getFeatures();
		System.out.println(result);
		assertTrue(!result.isEmpty());
		assertTrue(result.containsKey("test"));
		assertTrue(result.containsKey("test2"));
		assertTrue(!result.containsKey("1234"));
		assertTrue(!result.containsKey("2345"));
		
		assertEquals(5, result.size());								
	}
	public void testTokenizeNGrams() {
		Map<String, Feature> result;
		Vocabulary x;

		// empty string
		String testString = "";
		testString = "This is my car.";
		x = t.tokenizeNGrams(testString, 3);
		result = x.getFeatures();
		System.out.println(result);
		assertTrue(!result.isEmpty());
		assertTrue(result.containsKey("this is my"));
		assertTrue(result.containsKey("is my car"));
		
		testString = "This is my car.";
		x = t.tokenizeNGrams(testString, 2);
		result = x.getFeatures();
		System.out.println(result);
		assertTrue(!result.isEmpty());
		assertTrue(result.containsKey("this is"));
		assertTrue(result.containsKey("is my"));
		assertTrue(result.containsKey("my car"));
		
	}
}
