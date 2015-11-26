package siimaus.corpus.score;

import java.util.HashMap;
import java.util.Map;

import siimaus.corpus.Feature;
import siimaus.corpus.ICorpus;

public class DummyScore implements IFeatureScore {

	@Override
	public Map<String, Double> score(ICorpus corpus) {
		// TODO Auto-generated method stub
		return this.score(corpus, Double.NEGATIVE_INFINITY);
	}

	@Override
	public Map<String, Double> score(ICorpus corpus, Double cutOff) {
		// TODO Auto-generated method stub
		Map<String, Double> result = new HashMap<>();
		for (Feature feature : corpus.getVocabulary()) {
			result.put(feature.getFeature(), 0.0);
		}
		return result;
	}

}
