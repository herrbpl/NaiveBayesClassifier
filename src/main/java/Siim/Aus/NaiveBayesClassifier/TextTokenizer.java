package Siim.Aus.NaiveBayesClassifier;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/** 
 * Purpose of this class is to take input in form of text and turn it into Document
 * @author siimaus
 *
 */
public class TextTokenizer {

	/**
	 * Prepare input string
	 * @param input - input string
	 * @return prepared string
	 */
	protected String prepareInput(String input) {
		// to lowercase
		// http://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
		
		if (input == "") {
			return input; 
		}
		
		input = input.
				replaceAll("\\p{P}", ""). // all punctiation
				replaceAll("\\s+", " "). // all whitespace									
				trim().
				toLowerCase(Locale.getDefault());		
		
		return input;
	}
	
	/**
	 * Splits input into a words and returns in form of a HashMap
	 * @param input
	 * @return
	 */
	public Map<String, Integer> tokenizeWords(String input) {
		
		HashMap<String, Integer> result = new HashMap<>();
		
		// prepare input
		input = prepareInput(input);

		// if no input, return empty set
		if (input == "") {
			return result;
		}
		
		// split by space
		String[] s = input.split(" ");
		
		// iterate over array
		Integer c = 0;
		for (String word : s) {			
			c = result.get(word);
			
			// new word
			if (c == null) {
				result.put(word, 1);
			} else {
				c++;
				result.put(word, c);				
			}						
		}
		
		
		return result;		
	}
	/**
	 * Method to tokenize a string.
	 * @param input - text to be tokenized. Tokens can be a words or ngrams or phrases 
	 * @return Document
	 */
	@Deprecated 
	public Document tokenize(String input) {
		
		Document d = new Document();
		d.words = tokenizeWords(input);

		return d;
	}
}
