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
public class Document {
	// words in document
	public Map<String, Integer> words;
	

	public String category;

	public Document() {
		// TODO Auto-generated constructor stub
		this.words = new HashMap<String, Integer>();	
	}
	
	public Document( Map<String, Integer> words) {
		this.words = words;
	}

}
