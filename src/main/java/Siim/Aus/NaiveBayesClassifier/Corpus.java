package Siim.Aus.NaiveBayesClassifier;

import java.util.ArrayList;
import java.util.List;

public class Corpus {
	// list of documents
	// list of features
	// list of categories
	
	public List<Document> documents;
	public TextTokenizer tokenizer;
	
	public Corpus() {
		// TODO Auto-generated constructor stub
		this.documents = new ArrayList<Document>();	
		this.tokenizer = new TextTokenizer();
	}
	
	
	
}
