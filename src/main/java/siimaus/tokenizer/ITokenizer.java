package siimaus.tokenizer;

import siimaus.corpus.Vocabulary;

/**
 * Tokenizes input
 * @author siimaus
 *
 */
public interface ITokenizer {
	public Vocabulary tokenize(String input);	
	//public String[] tokenize(String input);
}
