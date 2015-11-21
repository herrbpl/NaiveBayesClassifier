package siimaus.naivebayesclassifier;

import java.util.HashMap;
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
	private Map<String, Double> priors = null;

	public Corpus() {
		allfeatures = new HashMap<>();
		categories = new HashMap<>();
		priors = new HashMap<>();
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
	 * 
	 * @return
	 */
	public int getVocabularySize() {
		return getVocabulary().distinctCount();
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
	 * 
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
	 * 
	 * @param category
	 *            - category
	 * @param doc
	 *            - Document
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
	 * 
	 * @param category
	 *            - category
	 * @param input
	 *            - Doument body as string
	 * @return this
	 */
	public Corpus addDocument(String category, String input) {
		Document doc = new Document(input);
		return this.addDocument(category, doc);
	}

	/**
	 * Gets total documents count in corpus
	 * @return
	 */
	public int getDocumentCount() {
		// TODO Auto-generated method stub
		int r = 0;
		for (Category cat : this.categories.values()) {
			r += cat.getDocumentCount();
		}
		return r;
	}
	
	/**
	 * Gets number of documents that contain this feature
	 * @param feature
	 * @return
	 */
	public int getDocumentCount(String feature) {
		
		int r = 0;
		for (Category cat : this.categories.values()) {
			r += cat.getDocumentCount(feature);
		}
		return r;
	}

	
	/**
	 * Trains corpus using entered observations
	 * What to do with feature selection? Should feature selection return new trimmed corpus or should corpus rather work with subset of selected features?
	 * Because feature selection affects feature counting for training, this must be decided.
	 * @return
	 */
	public Corpus train() {
		// TODO Auto-generated method stub

		this.syncrhonizeVocabularies();

		priors.clear();
		Vocabulary v;
		
		// this only works if vocabulary is large and there are enough documents
		IFeatureScore scoreEngine = new ChiSquareScore();
		Map<String, Double> score = scoreEngine.score(this, 10.83);
		if (score.size() < 10) {
			// get all features
			score = scoreEngine.score(this);
		}				
				
		//int vc = this.getVocabularySize();
		
		int vc = score.size();
		
		System.out.println(String.format("Original features count: %d, selected features count: %s", this.getVocabularySize(), vc));
		
		
		for (Category cat : this.getCategories().values()) {
			
			priors.put(cat.getCategory(), Math.log(cat.getDocumentCount() * 1.0 / this.getDocumentCount()));

			
			v = this.getVocabulary(cat.getCategory());
			// Get vcnt that is contained in selections					
			int vcnt = 0;
			
			for (Feature feature : v) {
				if (score.containsKey(feature.getFeature())) {
					vcnt++;
				}
			}
			

			for (Feature feature : v) {
				if (score.containsKey(feature.getFeature())) { 
					double lh = Math.log(1.0 + feature.count) / (vcnt + vc);
					v.likelihoods.put(feature.getFeature(), lh);
				} else {
					v.likelihoods.put(feature.getFeature(), Double.NaN);
				}
			}			

		}

		return this;
	}

	public Map<String, Double> getPriors() {
		return priors;
	}

	public void setPriors(Map<String, Double> priors) {
		this.priors = priors;
	}

	/**
	 * Predicts category for document
	 * 
	 * @param d
	 *            Document
	 * @return
	 */

	public Map<String, Double> getPredictions(Document d) {
		// TODO Auto-generated method stub

		Map<String, Double> predictions = new HashMap<>();
		Vocabulary v;
		
		for (Category cat : this.getCategories().values()) {
			v = this.getVocabulary(cat.getCategory());
			double lh = this.getPriors().get(cat.getCategory());
			for (Feature feature : d) {
				if (v.likelihoods.containsKey(feature.getFeature())
						&& !v.likelihoods.get(feature.getFeature()).isNaN()) {					
					lh += (((v.likelihoods.get(feature.getFeature())) * feature.count));
				}
			}
			predictions.put(cat.getCategory(), lh);
		}

		return predictions;
	}

	public String predict(Document d) {
		// TODO Auto-generated method stub
		String result = "";
		double max = Double.NEGATIVE_INFINITY;
		Map<String, Double> predictions = this.getPredictions(d);
		for (Map.Entry<String, Double> ent : predictions.entrySet()) {
			if (ent.getValue() > max) {
				max = ent.getValue();
				result = ent.getKey();
			}
		}
		
		return result;
	}
}
