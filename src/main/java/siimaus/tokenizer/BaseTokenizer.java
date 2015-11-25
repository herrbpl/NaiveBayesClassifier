package siimaus.tokenizer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import siimaus.naivebayesclassifier.Vocabulary;
import siimaus.util.FileUtils;

/**
 * Purpose of this class is to take input in form of text and turn it into
 * Vocabulary
 * 
 * @author siimaus
 *
 */
public class BaseTokenizer implements ITokenizer {

	protected List<String> stopWords = new ArrayList<>();
	protected List<IPreprocessor> preprocessors = new ArrayList<>();

	public BaseTokenizer() {
	}

	public BaseTokenizer addPreprocessor(IPreprocessor proc) {
		this.preprocessors.add(proc);
		return this;
	}

	public List<IPreprocessor> getPreProcessors() {
		return this.preprocessors;
	}

	public List<String> getStopWords() {
		return this.stopWords;
	}
	
	/**
	 * Prepare input string using IPreprocessor
	 * 
	 * @param input
	 *            - input string
	 * 
	 * @return prepared string
	 */
	protected String prepareInput(String input) {
		for (IPreprocessor textProc : preprocessors) {
			if (textProc != null) {
				input = textProc.preProcess(input);
			}
		}
		return input;
	}

	protected List<String> doTokenize(String input) {
		String[] s = input.split(" ");
		return Arrays.asList(s);
	}
	
	/**
	 * Method to tokenize a string.
	 * 
	 * @param input
	 *            text to be tokenized.
	 * 
	 * 
	 * @return Vocabulary
	 */
	@Override
	public Vocabulary tokenize(String input) {
		Vocabulary result = new Vocabulary();
		input = prepareInput(input);

		// if no input, return empty set
		if (input == "") {
			return result;
		}

		// split by space
		List<String> s = this.doTokenize(input);
		
		// iterate over array
		Integer c = 0;
		for (String word : s) {
			if (this.stopWords == null || this.stopWords.contains(word) ) {
				result.addFeature(word);
			}
		}

		return result;
	}
}
