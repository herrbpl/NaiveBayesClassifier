package siimaus.naivebayesclassifier;

import java.util.HashMap;
import java.util.Map;

public class DummyScore implements IFeatureScore {

	@Override
	public Map<String, Double> score(Corpus corpus) {
		// TODO Auto-generated method stub
		return this.score(corpus, Double.NEGATIVE_INFINITY);
	}

	@Override
	public Map<String, Double> score(Corpus corpus, Double cutOff) {
		// TODO Auto-generated method stub
		Map<String, Double> result = new HashMap<>();
		for (Feature feature : corpus.getVocabulary()) {
			result.put(feature.getFeature(), 0.0);
		}
		return result;
	}

}
