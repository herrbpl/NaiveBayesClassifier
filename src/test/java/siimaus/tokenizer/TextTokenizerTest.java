package siimaus.tokenizer;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import siimaus.naivebayesclassifier.Feature;
import siimaus.naivebayesclassifier.Vocabulary;
import siimaus.tokenizer.TextTokenizer;

public class TextTokenizerTest extends TestCase {

	private TextTokenizer t;

	public TextTokenizerTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		t = new TextTokenizer();
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
		
	}

}
