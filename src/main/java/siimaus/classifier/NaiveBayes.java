package siimaus.classifier;

import java.util.HashMap;
import java.util.Map;

import siimaus.corpus.Category;
import siimaus.corpus.Document;
import siimaus.corpus.Feature;
import siimaus.corpus.ICorpus;
import siimaus.corpus.Vocabulary;
import siimaus.corpus.score.ChiSquareScore;
import siimaus.corpus.score.IFeatureScore;

public class NaiveBayes implements IClassifier {

	public ICorpus corpus;
	public IFeatureScore scoreEngine = null;
	private Map<String, Double> priors = null;
	// From encapsulation perspective, it would make sense to put likelihood to
	// classifer
	// but if vocabulary is big and categories are many, this doubles memory
	// usage
	// so for now, I'm keeping this information with corpus.
	// maybe a referenced map for training time and copies map for predicting
	// time
	// private Map<String, Map<String, Double >> likelihoods = null;

	public NaiveBayes(ICorpus corpus) {
		// TODO Auto-generated constructor stub
		this.corpus = corpus;
		priors = new HashMap<>();
	}

	/**
	 * Trains corpus using entered observations Naive Bayes algorithm is used.
	 * Feature selection is used before training
	 * 
	 * @return
	 */
	@Override
	public void train() {

		// Sycnronize categories
		corpus.syncrhonizeVocabularies();

		// select features
		// this only works if vocabulary is large and there are enough documents
		if (scoreEngine == null) {
			scoreEngine = new ChiSquareScore();
		}
		Map<String, Double> score = scoreEngine.score(corpus, 20.83);
		if (score.size() < 10) {
			// get all features
			score = scoreEngine.score(corpus);
		}

		// prepare
		priors.clear();
		Vocabulary v;

		int vc = score.size();

		System.out.println(String.format("Original features count: %d, selected features count: %s",
				corpus.getVocabularySize(), vc));

		// train
		for (Category cat : corpus.getCategories().values()) {

			priors.put(cat.getCategory(), Math.log(cat.getDocumentCount() * 1.0 / corpus.getDocumentCount()));

			v = corpus.getVocabulary(cat.getCategory());
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
					v.getLikelihoods().put(feature.getFeature(), lh);
				} else {
					v.getLikelihoods().put(feature.getFeature(), Double.NaN);
				}
			}

		}

	}

	public Map<String, Double> getPredictions(Document d) {
		// TODO Auto-generated method stub

		Map<String, Double> predictions = new HashMap<>();
		Vocabulary v;

		for (Category cat : corpus.getCategories().values()) {
			v = corpus.getVocabulary(cat.getCategory());
			double lh = this.priors.get(cat.getCategory());
			for (Feature feature : d) {
				if (v.getLikelihoods().containsKey(feature.getFeature())
						&& !v.getLikelihoods().get(feature.getFeature()).isNaN()) {
					lh += (((v.getLikelihoods().get(feature.getFeature())) * feature.count));
				}
			}
			predictions.put(cat.getCategory(), lh);
		}

		return predictions;
	}

	@Override
	public String predict(String document) {
		Document d = new Document(document, corpus.getTokenizer());
		return predict(d);
	}

	@Override
	public String predict(Document document) {
		
		String result = "";
		double max = Double.NEGATIVE_INFINITY;
		Map<String, Double> predictions = this.getPredictions(document);
		//System.out.println(predictions);
		for (Map.Entry<String, Double> ent : predictions.entrySet()) {
			if (ent.getValue() > max) {
				max = ent.getValue();
				result = ent.getKey();
			}
		}

		return result;
	}

}
