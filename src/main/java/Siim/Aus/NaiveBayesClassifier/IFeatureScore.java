package Siim.Aus.NaiveBayesClassifier;

import java.util.Map;

public interface IFeatureScore {
	public Map<String, Double> score(Corpus corpus);
	public Map<String, Double> score(Corpus corpus, Double cutOff);
}
