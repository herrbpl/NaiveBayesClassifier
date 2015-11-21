package siimaus.tokenizer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import siimaus.naivebayesclassifier.Document;
import siimaus.naivebayesclassifier.Vocabulary;

/**
 * Purpose of this class is to take input in form of text and turn it into
 * Vocabulary
 * 
 * @author siimaus
 *
 */
public class TextTokenizer implements ITokenizer {

	
	public Vocabulary stopWords = null;
	
	public TextTokenizer() {
		// try to load stopwords
		File stopfile = new File("./stopwords.en.txt");
		if (stopfile.exists()) {
			this.stopWords = tokenizeWords(Document.fromFileText("./stopwords.en.txt"));
		}		
	}
	
	public TextTokenizer(String stopFilePath) {
		// try to load stopwords
		if (stopFilePath != null) {
			File stopfile = new File(stopFilePath);
			if (stopfile.exists()) {
				this.stopWords = tokenizeWords(Document.fromFileText(stopFilePath));
			}
		}
	}
	
	/**
	 * Prepare input string
	 * 
	 * @param input
	 *            - input string
	 * @return prepared string
	 */
	protected String prepareInput(String input) {
		// to lowercase
		// http://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html

		if (input == "") {
			return input;
		}		
		input = input.replaceAll("\\r", " "). // line feeds				
				replaceAll("\\n", " "). // line feeds				
				replaceAll("\\W+", " "). // all non-word characters
				replaceAll("\\s+", " "). // all whitespace		
				replaceAll("\\p{P}", ""). // all punctiation				
				replaceAll("^\\s*(\\d)+\\s+", " "). // all numbers in beginning
				replaceAll("\\s+(\\d)+\\s+", " "). // all numbers in middle
				replaceAll("\\s+(\\d)+$", " "). // all numbers in ending
				replaceAll("^(\\d)+$", " "). // all numbers empty								
				replaceAll("\\s+(\\d)+(\\s*\\d*)*\\s+", " "). // all numbers in ending
				
		trim().toLowerCase(Locale.getDefault());
		
		return input;
	}

	/**
	 * Splits input into a words and returns in form of a HashMap
	 * 
	 * @param input
	 * @return
	 */
	public Vocabulary tokenizeWords(String input) {

		Vocabulary result = new Vocabulary();

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
			if (this.stopWords == null || this.stopWords.getFeature(word) == null) {
				result.addFeature(word);
			}
		}

		return result;
	}

	/**
	 * Turns string into ngrams 
	 * @author aioobe
	 * @see <a href="http://stackoverflow.com/a/3656824">http://stackoverflow.com/a/3656824</a>
	 *  
	 * @param n
	 * @param str
	 * @return
	 */
	public static List<String> ngrams(int n, String str) {
		List<String> ngrams = new ArrayList<String>();
		String[] words = str.split(" ");
		for (int i = 0; i < words.length - n + 1; i++)
			ngrams.add(concat(words, i, i + n));
		return ngrams;
	}

	/**
	 * Concats words together
	 * @author aioobe
	 * 
	 * @see <a href="http://stackoverflow.com/a/3656824">http://stackoverflow.com/a/3656824</a> 
	 * @param words
	 * @param start
	 * @param end
	 * @return
	 */
	public static String concat(String[] words, int start, int end) {
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < end; i++)
			sb.append((i > start ? " " : "") + words[i]);
		return sb.toString();
	}

	public Vocabulary tokenizeNGrams(String input, int ngramSize) {
		Vocabulary result = new Vocabulary();

		// prepare input
		input = prepareInput(input);

		// if no input, return empty set
		if (input == "") {
			return result;
		}
		
		for(String ngram: TextTokenizer.ngrams(ngramSize, input) ) {
			result.addFeature(ngram);
		}
		
		return result;
	}

	/**
	 * Method to tokenize a string.
	 * 
	 * @param input
	 *            - text to be tokenized. Tokens can be a words or ngrams or
	 *            phrases
	 * @return Vocabulary
	 */
	@Override
	public Vocabulary tokenize(String input) {
		Vocabulary result = new Vocabulary();
		result.addVocabulary(this.tokenizeWords(input));		
		//result.addVocabulary(this.tokenizeNGrams(input,3));
		return result;
	}
}
