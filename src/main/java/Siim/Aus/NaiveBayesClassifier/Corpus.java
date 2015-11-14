package Siim.Aus.NaiveBayesClassifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Corpus holds data about documents and their classifications. Also information
 * about features available in corpus text. On other hand, it is like Vocabulary
 * with multiple categories Current implementation is very inefficient in terms
 * of memory :(
 * 
 * @author siimaus
 *
 */
public class Corpus {

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

	public Corpus() {
		allfeatures = new HashMap<>();
		categories = new HashMap<>();
	}

	/**
	 * Adds category to corpus
	 * 
	 * @param category
	 * @return
	 */
	public Corpus addCategory(String category) {
		if (!categories.containsKey(category)) {
			Category c = new Category(category);
			categories.put(category, c);
		}
		if (!allfeatures.containsKey(category)) {
			Vocabulary v = new Vocabulary();
			allfeatures.put(category, v);
		}
		return this;
	}

	/**
	 * Returns count of all features in corpus
	 * @return
	 */
	public int getVocabularySize() {
		return getVocabulary().size();
	}

	public Category getCategory(String category) {
		return this.categories.get(category);
	}

	public Vocabulary getVocabulary(String category) {
		return this.allfeatures.get(category);
	}

	public Map<String, Category> getCategories() {
		return categories;
	}
	
	/**
	 * Gets all features as vocabulary
	 * @return
	 */
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
	protected Corpus syncrhonizeVocabularies() {
		Vocabulary v = getVocabulary();		
		for (Feature feature : v) {
			for (Map.Entry<String, Vocabulary> voc : allfeatures.entrySet()) {
				voc.getValue().addFeature(feature.getFeature(), 0);
			}
		}
		return this;
	}

	/**
	 * Adds document to corpus category
	 * @param category - category
	 * @param doc - Document
	 * @return this
	 */
	public Corpus addDocument(String category, Document doc) {

		// add document to category
		this.addCategory(category).getCategory(category).addDocument(doc);
		// add features to category vocabulary
		this.getVocabulary(category).addVocabulary(doc);

		// add zero count feature for other categories
		for (Feature feature : doc) {
			for (Map.Entry<String, Vocabulary> voc : this.allfeatures.entrySet()) {
				if (voc.getKey() != category) {
					voc.getValue().addFeature(feature.getFeature(), 0);
				}
			}
		}

		return this;
	}
	
	/**
	 * Adds document to corpus category;
	 * @param category - category
	 * @param input - Doument body as string
	 * @return this
	 */
	public Corpus addDocument(String category, String input) {
		Document doc = new Document(input);
		return this.addDocument(category, doc);
	}
}
