package siimaus.corpus.score;

import java.util.HashMap;
import java.util.Map;

import siimaus.corpus.Category;
import siimaus.corpus.Feature;
import siimaus.corpus.ICorpus;

public class ChiSquareScore implements IFeatureScore {

	/**
	 * This Feature Scoring uses ChiSquare method to calculate scores for Features
	 * Following implementation was used as calculation template:
	 * https://github.com/datumbox/NaiveBayesClassifier/blob/master/src/com/datumbox/opensource/features/FeatureExtraction.java
	 * 
	 * If only one category is present, no features are selected.. or all features should be selected?
	 * 
	 * @see <a href="https://github.com/datumbox/NaiveBayesClassifier/blob/master/src/com/datumbox/opensource/features/FeatureExtraction.java">https://github.com/datumbox/NaiveBayesClassifier/blob/master/src/com/datumbox/opensource/features/FeatureExtraction.java</a> 
	 */
	@Override
	public Map<String, Double> score(ICorpus corpus, Double cutOff) {
		// TODO Auto-generated method stub
		Map<String, Double> result = new HashMap<>();
		
		Double chisquareScore, previousScore;
		int observationCount = corpus.getDocumentCount();
		
		// iterate over all features in corpus
		for (Feature feature : corpus.getVocabulary()) {
			
			String featureName = feature.getFeature();
			// number of documents that have the feature				
			int N1dot = corpus.getDocumentCount(feature.getFeature());
			
			// number of documents that do not have feature
			int N0dot = observationCount - N1dot;
			
			
			// iterate over categories
			for (Map.Entry<String, Category> entry : corpus.getCategories().entrySet()) {				
				Category cat = entry.getValue();
				
				// documents that have feature and belong to category
				int N11 = cat.getDocumentCount(featureName);
				
				// documents that belong to category but do not have the feature
				int N01 = cat.getDocumentCount()-N11; 
				//System.out.println(N01);
				
				//N00 counts the number of documents that don't have the feature and don't belong to the specific category
				int N00 = N0dot - N01;
				
				//N10 counts the number of documents that have the feature and don't belong to the specific category
				int N10 = N1dot - N11;
				
				// calculate chi score
				chisquareScore = (1.0*observationCount)*Math.pow(N11*N00-N10*N01, 2)/((N11+N01)*(N11+N10)*(N10+N00)*(N01+N00));
				/*
				String formula = String.format("(%d * POWER( ((%d * %d) - (%d * %d)),2) ) / ((%d+%d)*(%d+%d)*(%d+%d)*(%d+%d))\n", 
						observationCount, N11, N00, N10, N01, N11, N01, N11, N10, N10,N00, N01, N00);
				System.out.println(formula);
				*/

				// only if value is > cutoff value
				if (chisquareScore.isNaN()  || chisquareScore < cutOff) {
					continue;
				}

				// add to map
				previousScore = result.get(featureName);
				if (previousScore == null || previousScore < chisquareScore) {
					result.put(featureName, chisquareScore);
				}
			}
			
		}
		
		return result;
	}

	/**
	 * Calculate score for features and do not apply cutoff
	 */
	@Override
	public Map<String, Double> score(ICorpus corpus) {
		return this.score(corpus, Double.NEGATIVE_INFINITY);
	}

}
