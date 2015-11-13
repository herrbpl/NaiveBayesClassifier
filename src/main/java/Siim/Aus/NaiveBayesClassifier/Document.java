package Siim.Aus.NaiveBayesClassifier;

import java.util.HashMap;
import java.util.Map;

/**
 * Document is set of features with their count of occurrence in document Its
 * basically data structure.
 * 
 * Should each document have distinct ID for storing and retrival to/from database?
 * Should each document preseve its original input?
 * 
 * @author siimaus Core of this is from datumbox code, I just rewrote it for
 *         myself line by line.
 * @see <a href=
 *      "https://github.com/datumbox/NaiveBayesClassifier/blob/master/src/com/datumbox/opensource/dataobjects/Document.java">
 *      https://github.com/datumbox/NaiveBayesClassifier/blob/master/src/com/
 *      datumbox/opensource/dataobjects/Document.java</a>
 */
public class Document extends Vocabulary {
			
	public String category;
	private String originalContent = "";
	public TextTokenizer tokenizer = null;
	public boolean saveContent = false;

	public Document() {
		// TODO Auto-generated constructor stub
		super();		
		saveContent = false;
	}
	
	protected void Tokenize(String input) {
		if (this.tokenizer != null) {
			Vocabulary v = this.tokenizer.tokenizeWords(input);
			this.clear();
			this.addVocabulary(v);
		}
	}
	
	public Document(String input) {
		// TODO Auto-generated constructor stub
		super();		
		this.setOriginalContent(input);
	}

	public String getOriginalContent() {
		
		return originalContent;
	}

	public void setOriginalContent(String originalContent) {
		
		if (originalContent == this.originalContent && saveContent) {
			return;
		}
		
		if (saveContent) {
			this.originalContent = originalContent;
		}
		
		this.clear();
		this.Tokenize(originalContent);
	}
	
	
	
}
