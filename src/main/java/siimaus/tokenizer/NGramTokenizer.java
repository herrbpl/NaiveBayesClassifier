package siimaus.tokenizer;

import java.util.ArrayList;
import java.util.List;

import siimaus.corpus.Vocabulary;

public class NGramTokenizer extends BaseTokenizer {
	
	protected int nGramSize = 1;
	
	public NGramTokenizer(IPreprocessor proc) {
		// TODO Auto-generated constructor stub
		addPreprocessor(proc);
	}
	public NGramTokenizer(IPreprocessor proc, int nGramSize) {
		addPreprocessor(proc);
		if (nGramSize > 0 ) {
			this.nGramSize = nGramSize;
		}
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
	
	@Override
	protected List<String> doTokenize(String input) {
		// TODO Auto-generated method stub
		return ngrams(this.nGramSize, input);
	}
	
	
}
