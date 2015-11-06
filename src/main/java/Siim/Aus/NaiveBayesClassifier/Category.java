package Siim.Aus.NaiveBayesClassifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class contains list of documents in category, also list of features in category
 * @author siaus
 *
 */
public class Category {
	private List<Document> documents;

	private String category="";
	
	public Category(String category) {
		// TODO Auto-generated constructor stub
		this.category = category;
		this.documents = new ArrayList<Document>();		
	}
	
	
	
	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	

}
