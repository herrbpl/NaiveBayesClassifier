package Siim.Aus.NaiveBayesClassifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Class contains list of documents in category, also list of features in
 * category
 * 
 * @author siaus
 *
 */
public class Category {
	private List<Document> documents;

	private String category = "";

	// Vocabulary of category
	private Vocabulary vocabulary = null;

	public Category(String category) {
		// TODO Auto-generated constructor stub
		this.setCategory(category);
		this.documents = new ArrayList<Document>();
		this.vocabulary = new Vocabulary();
	}

	/**
	 * Adds document to category;
	 * 
	 * @param doc
	 * @return this
	 */
	public Category addDocument(Document doc) {
		this.getDocuments().add(doc);
		this.vocabulary.addVocabulary(doc);
		return this;
	}

	public Category addDocument(String input) {
		Document doc = new Document(input);
		this.getDocuments().add(doc);
		this.vocabulary.addVocabulary(doc);		
		return this;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;

		this.vocabulary = this.buildVocabulary(this.vocabulary);
	}

	/**
	 * Builds category vocabulary from documents vocabulary.
	 * 
	 * @return
	 */

	public Vocabulary buildVocabulary(Vocabulary v) {

		if (v == null) {
			v = new Vocabulary();
		} else {
			v.clear();
		}

		for (Iterator<Document> iterator = documents.iterator(); iterator.hasNext();) {
			Document document = (Document) iterator.next();
			v.addVocabulary(document);
		}

		return v;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
