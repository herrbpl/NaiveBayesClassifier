package siimaus.tokenizer;

import siimaus.naivebayesclassifier.Vocabulary;

/**
 * Tokenizes input
 * @author siimaus
 *
 */
public interface ITokenizer {
	public Vocabulary tokenize(String input);
}
