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
	private String content = "";
	public TextTokenizer tokenizer = null;
	// maybe this should be somewhere else
	public static TextTokenizer defaultTokenizer = new TextTokenizer();
	public boolean saveContent = false;

	public Document() {
		// TODO Auto-generated constructor stub
		super();		
		saveContent = false;
		this.tokenizer = Document.defaultTokenizer;
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
		saveContent = false;
		this.tokenizer = Document.defaultTokenizer;
		this.setContent(input);
	}

	public String getContent() {
		
		return content;
	}

	public void setContent(String originalContent) {
		
		if (originalContent == this.content && saveContent) {
			return;
		}
		
		if (saveContent) {
			this.content = originalContent;
		}
		
		this.clear();
		this.Tokenize(originalContent);
	}
	
	
	
}
