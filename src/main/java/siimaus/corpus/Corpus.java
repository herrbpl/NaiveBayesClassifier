package siimaus.corpus;

import java.util.HashMap;
import java.util.Map;

import siimaus.corpus.score.ChiSquareScore;
import siimaus.corpus.score.IFeatureScore;
import siimaus.tokenizer.ITokenizer;

/**
 * Corpus holds data about documents and their classifications. Also information
 * about features available in corpus text. On other hand, it is like Vocabulary
 * with multiple categories Current implementation is very inefficient in terms
 * of memory :(
 * 
 * @author siimaus
 *
 */
public class Corpus implements ICorpus {

	/*
	 * likelihoods all : features, category, likelihood
	 * 
	 * feature | category 1 count | category 1 likelihood | category 2 |
	 * category 3
	 * 
	 */

	// containing all features, by category.
	private Map<String, Vocabulary> allfeatures = null;
	private Map<String, Category> categories = null;
	private Map<String, Double> priors = null;
	private ITokenizer tokenizer = null;

	public Corpus() {
		allfeatures = new HashMap<>();
		categories = new HashMap<>();
		priors = new HashMap<>();
	}
	
	public Corpus(ITokenizer tokenizer) {
		allfeatures = new HashMap<>();
		categories = new HashMap<>();
		priors = new HashMap<>();
		this.setTokenizer(tokenizer);
	}
	
	

	/* (non-Javadoc)
	 * @see siimaus.corpus.ICorpus#addCategory(java.lang.String)
	 */
	@Override
	public ICorpus addCategory(String category) {
		
		if (getCategory(category) == null || getVocabulary(category) == null) {			
			addCategory(new Category(category, this));
		}
		
		return this;
	}
	
	@Override	
	public ICorpus addCategory(Category cat) {
		
		if (cat == null) {			
			return this;
		}
		
		String category = cat.getCategory();
		
		// add features vocabulary
		if (getVocabulary(category) == null) {			
			Vocabulary v = new Vocabulary();
			allfeatures.put(category, v);			
		}		
		
		// set and build vocabulary for feature 
		cat.setVocabulary(
			cat.buildVocabulary(
					this.getVocabulary(category)
					)
				);
		
		// put category
		categories.put(category, cat);
				
		return this;
	}

	/* (non-Javadoc)
	 * @see siimaus.corpus.ICorpus#getVocabularySize()
	 */
	@Override
	public int getVocabularySize() {
		return getVocabulary().distinctCount();
	}

	/* (non-Javadoc)
	 * @see siimaus.corpus.ICorpus#getCategory(java.lang.String)
	 */
	@Override
	public Category getCategory(String category) {
		return this.categories.get(category);
	}

	/* (non-Javadoc)
	 * @see siimaus.corpus.ICorpus#getVocabulary(java.lang.String)
	 */
	@Override
	public Vocabulary getVocabulary(String category) {
		return this.allfeatures.get(category);
	}

	/* (non-Javadoc)
	 * @see siimaus.corpus.ICorpus#getCategories()
	 */
	@Override
	public Map<String, Category> getCategories() {
		return categories;
	}

	/* (non-Javadoc)
	 * @see siimaus.corpus.ICorpus#getVocabulary()
	 */
	@Override
	public Vocabulary getVocabulary() {
		Vocabulary v = new Vocabulary();

		// get all features
		for (Map.Entry<String, Vocabulary> voc : allfeatures.entrySet()) {
			v.addVocabulary(voc.getValue());
		}

		return v;
	}

	/**
	 * SYnchronizes features across categories
	 * 
	 * @return
	 */
	@Override
	public ICorpus syncrhonizeVocabularies() {
		Vocabulary v = getVocabulary();
		for (Feature feature : v) {
			for (Map.Entry<String, Vocabulary> voc : allfeatures.entrySet()) {
				voc.getValue().addFeature(feature.getFeature(), 0);
			}
		}
		return this;
	}

	/* (non-Javadoc)
	 * @see siimaus.corpus.ICorpus#addDocument(java.lang.String, siimaus.corpus.Document)
	 */
	@Override
	public ICorpus addDocument(String category, Document doc) {

		Category cat = this.addCategory(category).getCategory(category);
				
		// add document to category
		cat.addDocument(doc);
		
		// add features to category vocabulary.
		// this now gets added by category.addDocument as they should share vocabulary object
		//this.getVocabulary(category).addVocabulary(doc);
		//syncrhonizeVocabularies();
		/*
		// add zero count feature for other categories
		for (Feature feature : doc) {
			for (Map.Entry<String, Vocabulary> voc : this.allfeatures.entrySet()) {
				if (voc.getKey() != category) {
					voc.getValue().addFeature(feature.getFeature(), 0);
				}
			}
		}
		*/
		return this;
	}

	/* (non-Javadoc)
	 * @see siimaus.corpus.ICorpus#addDocument(java.lang.String, java.lang.String)
	 */
	@Override
	public ICorpus addDocument(String category, String input) {
		Document doc = new Document(input, this.getTokenizer());
		return this.addDocument(category, doc);
	}

	/* (non-Javadoc)
	 * @see siimaus.corpus.ICorpus#getDocumentCount()
	 */
	@Override
	public int getDocumentCount() {
		// TODO Auto-generated method stub
		int r = 0;
		for (Category cat : this.categories.values()) {
			r += cat.getDocumentCount();
		}
		return r;
	}
	
	/* (non-Javadoc)
	 * @see siimaus.corpus.ICorpus#getDocumentCount(java.lang.String)
	 */
	@Override
	public int getDocumentCount(String feature) {
		
		int r = 0;
		for (Category cat : this.categories.values()) {
			r += cat.getDocumentCount(feature);
		}
		return r;
	}

	@Override
	public ICorpus setTokenizer(ITokenizer tokenizer) {
		// TODO Auto-generated method stub
		this.tokenizer = tokenizer;
		return this;
	}
	
	@Override
	public ITokenizer getTokenizer() {
		// TODO Auto-generated method stub
		return this.tokenizer;
	}
	
	
}
