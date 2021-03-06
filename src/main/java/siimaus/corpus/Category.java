package siimaus.corpus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import siimaus.tokenizer.ITokenizer;

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
	
	// corpus reference
	private ICorpus corpus = null;
	
	// tokenizer reference
	private ITokenizer tokenizer = null;

	public Category(String category) {
		// TODO Auto-generated constructor stub
		this.setCategory(category);
		this.documents = new ArrayList<Document>();
		this.setVocabulary(new Vocabulary());
	}
	
	public Category(String category, ICorpus corpus) {
		// TODO Auto-generated constructor stub
		this.corpus = corpus;
		this.setCategory(category);
		this.documents = new ArrayList<Document>();
		if (this.corpus == null) {
			this.setVocabulary(new Vocabulary());
		} else {
			this.setVocabulary(corpus.getVocabulary(category));
		}
	}
	

	/**
	 * Adds document to category;
	 * 
	 * @param doc
	 * @return this
	 */
	public Category addDocument(Document doc) {		
		this.getDocuments().add(doc);
		this.getVocabulary().addVocabulary(doc);
		return this;
	}

	public Category addDocument(String input) {
		Document doc = new Document(input);
		return this.addDocument(doc);		
	}

	/**
	 * If instance is specific, it gets removed, else, first matching document gets removed. Loglikelihoods are not estimated for comparison
	 * @param doc
	 * @return
	 */
	public Category removeDocument(Document doc) {
		if (this.documents.contains(doc)) {	
			
			this.vocabulary.removeVocabulary(doc);
			this.documents.remove(doc);
			return this;
		} else {
			
			for (Iterator<Document> iterator = documents.iterator(); iterator.hasNext();) {
				Document document = (Document) iterator.next();
				if (doc.equals(document)) {
					this.vocabulary.removeVocabulary(document);
					this.documents.remove(document);
					break;
				}
			}
			return this;
		}				
	}
	
	/**
	 * Removes first instance of document equaling tokenized input
	 * @param input
	 * @return
	 */
	public Category removeDocument(String input) {		
		Document d = new Document(input);
		return this.removeDocument(d);
	}
	
	
	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
		this.buildVocabulary(this.vocabulary);
	}

	/** 
	 * Returns number of all documents belonging to Category
	 * @return
	 */
	public int getDocumentCount() {
		return this.documents.size();
	}
	
	/**
	 * Returns number of documents that contain this feature
	 * @param feature
	 * @return
	 */
	public int getDocumentCount(String feature) {

		int res = 0;
		for (Iterator<Document> iterator = documents.iterator(); iterator.hasNext();) {
			Document document = (Document) iterator.next();
			Feature f = document.getFeature(feature);
			if (f != null && f.count > 0) {
				res++;
			}
		}
		return res;
	}
	
	public int featureCount(String feature) {
		Feature f = this.vocabulary.getFeature(feature.toLowerCase().trim());
		if (f == null) {
			return 0;
		}
		return f.count;
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

	public Vocabulary getVocabulary() {
		return vocabulary;
	}

	protected void setVocabulary(Vocabulary vocabulary) {
		this.vocabulary = vocabulary;
	}

	/**
	 * Tokenizer of category. Uses corpus tokenizer if not set exclusively
	 * @return
	 */
	public ITokenizer getTokenizer() {
		if (this.tokenizer == null) {
			if (this.getCorpus() != null) {
				return this.getCorpus().getTokenizer();
			}
		}
		return this.tokenizer;
	}

	public void setTokenizer(ITokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}
	
	/**
	 * Gets associated corpus
	 * @return ICorpus
	 */
	public ICorpus getCorpus() {
		return this.corpus;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub		
		return String.format("%s:%d:%s", this.category, this.documents.size(), this.vocabulary.toString());
	}
	
	public String toJSON() {
		StringJoiner joiner = new StringJoiner(",");
		for (Iterator<Document> iterator = documents.iterator(); iterator.hasNext();) {
			Document document = (Document) iterator.next();
			joiner.add(document.toJSON()); 
		}
		return String.format("{\"name\":\"%s\", \"documentcount\":%d, \"documents\":[%s], \"vocabulary\":%s}", 
				this.category, this.getDocumentCount(), joiner.toString(), this.vocabulary.toJSON()
				);
				
	}

	
	
	
}
