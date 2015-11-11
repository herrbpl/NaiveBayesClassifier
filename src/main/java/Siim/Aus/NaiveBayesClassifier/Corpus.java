package Siim.Aus.NaiveBayesClassifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Corpus holds data about documents and their classifications. Also information
 * about features available in corpus text. Should each document have an id for
 * specific example retrival?
 * 
 * @author siimaus
 *
 */
public class Corpus {
	// list of documents
	// list of features
	// list of categories

	// public List<Document> documents;
	public TextTokenizer tokenizer; // tokenizer engine
	public Map<String, Map<String, Feature>> allFeatures; // list of all
															// features and
															// their count in
															// categories
	public Map<String, List<Document>> categories; // map of categories, each
													// containing list of
													// documents
	
	public Map<String, Category> categories2;
	
	// TODO: create feature class
	
	/*
	 * Feature Category
	 * Likelihood word->category->(count,likelihood)
	 * 				  ->count
	 * 
	 */

	public Corpus() {
		// TODO Auto-generated constructor stub
		// this.documents = new ArrayList<Document>();
		this.tokenizer = new TextTokenizer();
		// Holds all features (words) and their count in categories
		this.allFeatures = new HashMap<String, Map<String, Feature>>();
		this.categories = new HashMap<String, List<Document>>();
		this.categories2 = new HashMap<String, Category>();
		
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub

		String s = "";

		String comma = "";

		for (Map.Entry<String, List<Document>> cat : categories.entrySet()) {
			s += comma + String.format("{category:\"%s\",featureCount:%d, documentCount:%d, documents:{\n", cat.getKey(), getFeatureCount(cat.getKey()),
					cat.getValue().size());
			comma = ",";
			String comma2 = "";
			for (Document document : cat.getValue()) {
				s += comma2 + String.format("{%s}\n", document.toString());
				comma2 = ",";
			}
			s += " }}\n";
		}

		return String.format("{features:%d, categories:{\n%s}}\n", getAllFeatureCount(), s);
	}

	/**
	 * Gets count of all features
	 * 
	 * @return count of all features
	 */
	public Integer getAllFeatureCount() {
		return allFeatures.size();
	}

	/**
	 * Gets count of features in category
	 * 
	 * @param category
	 *            - category to return count for
	 * @return count of features in category
	 * @throws IllegalArgumentException
	 */

	public Integer getFeatureCount(String category) throws IllegalArgumentException {
		List<Document> dl;

		dl = categories.get(category);
		// add category if it does not exists
		if (dl == null) {
			throw new IllegalArgumentException("Invalid category");
		}

		// count all features in category. Probably it is wiser to actually save
		// it somewhere on adding
		Integer count = 0;
		for (Document document : dl) {
			// iterate over document words
			for (Map.Entry<String, Integer> wl : document.words.entrySet()) {
				count += wl.getValue();
			}
		}

		return count;
	}

	/**
	 * Adds input to corpus.
	 * 
	 * @param category
	 *            - category to which text belongs. Should there be possibility
	 *            of having multiple categories for one text?
	 * @param text
	 *            - text itself.
	 */

	public void addInput(String category, String text) {

		List<Document> dl;

		dl = categories.get(category);

		// add category if it does not exists
		if (dl == null) {
			dl = new ArrayList<Document>();
			categories.put(category, dl);
		}

		// tokenize document and add to list;
		Document d = new Document(tokenizer.tokenizeWords(text));
		d.category = category;
		dl.add(d);

		
		
		// update features counts for classes. iterate over document word list.
		for (Map.Entry<String, Integer> wl : d.words.entrySet()) {
			// retrieve feature info
			Map<String,Feature> fcc = allFeatures.get(wl.getKey());

			// if feature does not exists in map, add it
			if (fcc == null) {
				fcc = new HashMap<String, Feature>();
				allFeatures.put(wl.getKey(), fcc);
			}
			/*

			// add to feature
			Integer countFeatures;
			Feature f;
			f = fcc.get(category);
			
			if (f == null) {
				f = new Feature();
				f.count=0;
				fcc.put(category, f);
			} else {
				f.count++;				
			}
			*/
		}

	}

}
