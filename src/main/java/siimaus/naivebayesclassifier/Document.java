package siimaus.naivebayesclassifier;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import siimaus.tokenizer.TextTokenizer;

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
	
	
	/**
	 * Temporary method to load file quickly. 
	 * @param filename
	 * @return
	 */
	public static String fromFileText(String fileName)  {
		String line = null;
		String text = "";		
		try {
			FileReader fileReader = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				text += line+"\n";
			}

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			return null;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
			//System.out.println("Error reading file '" + fileName + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
				
		return text;
	}
	
	/**
	 * Temporary method to load file quickly. 
	 * @param filename
	 * @return
	 */
	public static Document fromFile(String fileName)  {		
		Document doc = new Document(fromFileText(fileName));
		return doc;
	}
	
	protected void Tokenize(String input) {
		if (this.tokenizer != null) {
			Vocabulary v = this.tokenizer.tokenize(input);
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
