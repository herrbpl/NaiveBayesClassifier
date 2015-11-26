package siimaus.corpus.score;

import java.util.Map;

import siimaus.corpus.ICorpus;

public interface IFeatureScore {
	public Map<String, Double> score(ICorpus corpus);
	public Map<String, Double> score(ICorpus corpus, Double cutOff);
}
