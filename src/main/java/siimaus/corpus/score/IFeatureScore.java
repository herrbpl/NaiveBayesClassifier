package siimaus.corpus.score;

import java.util.Map;

import siimaus.corpus.Corpus;

public interface IFeatureScore {
	public Map<String, Double> score(Corpus corpus);
	public Map<String, Double> score(Corpus corpus, Double cutOff);
}
