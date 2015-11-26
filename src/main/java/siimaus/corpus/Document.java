package siimaus.corpus;

import java.util.HashMap;
import java.util.Map;

import siimaus.tokenizer.BaseTokenizer;
import siimaus.tokenizer.BasicPreprocessor;
import siimaus.tokenizer.ITokenizer;
import siimaus.util.FileUtils;

/**
 * Document is set of features with their count of occurrence in document Its
 * basically data structure.
 * 
 * Should each document have distinct ID for storing and retrival to/from database?
 * Should each document preseve its original input?
 * Document = single separate Observation
 * 
 * @author siimaus Core of this is from datumbox code, I just rewrote it for
 *         myself line by line.
 * @see <a href=
 *      "https://github.com/datumbox/NaiveBayesClassifier/blob/master/src/com/datumbox/opensource/dataobjects/Document.java">
 *      https://github.com/datumbox/NaiveBayesClassifier/blob/master/src/com/
 *      datumbox/opensource/dataobjects/Document.java</a>
 */
public class Document extends Vocabulary {
				
	private String content = "";
	public ITokenizer tokenizer = null;	
	public boolean saveContent = false;

	public Document() {
		// TODO Auto-generated constructor stub
		super();		
		saveContent = false;		
	}
	
	public Document(String input, ITokenizer tokenizer) {
		super();
		this.saveContent = false;
		this.setTokenizer(tokenizer);
		this.setContent(input);
	}
	
	public Document setTokenizer(ITokenizer tokenizer) {
		this.tokenizer = tokenizer;
		return this;
	}
	
	/**
	 * Temporary method to load file quickly. 
	 * @param filename
	 * @return
	 */
	public static Document fromFile(String fileName)  {		
		Document doc = new Document(FileUtils.fromFileText(fileName));
		return doc;
	}
	
	/** 
	 * Tokenizes input, creates basic tokenizer if not set
	 * @param input
	 */
	protected void Tokenize(String input) {
		if (tokenizer == null) {
			this.setTokenizer(new BaseTokenizer(new BasicPreprocessor()));
		}
		
		this.clear();			
		this.addVocabulary(tokenizer.tokenize(input));						
		
	}
	
	public Document(String input) {
		// TODO Auto-generated constructor stub
		super();		
		saveContent = false;		
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
				
		this.Tokenize(originalContent);
	}
	
	
	
}
