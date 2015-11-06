package Siim.Aus.NaiveBayesClassifier;

import java.util.HashMap;
import java.util.Map;

/**
 * Vocabulary, contains Features
 * @author siaus
 *
 */
public class Vocabulary {
	
	// default vocabulary
	public static Vocabulary defaultVocabulary;
	
	protected Map<String, Feature> features;	
	public Vocabulary() {
		// TODO Auto-generated constructor stub
		this.features = new HashMap<>();		
	}
	
	public Vocabulary(Map<String, Feature> features) {
		this.features = features;
	}
	
	public Feature addFeature(String s, FeatureType f) {		
		return Feature.addFeature(s, f, this);
	}
	
	/**
	 * Size of vocabulary
	 * @return
	 */
	
	public int size() {
		return features.size();
	}
	
	/**
	 * Adds two vocabularies together 
	 * @param otherVocabulary
	 * @return
	 */
	public Vocabulary addVocabulary(Vocabulary otherVocabulary) {
		
		Vocabulary v = new Vocabulary();
		
		// add my features to new vocabulary
		for (Map.Entry<String, Feature> fl : this.features.entrySet()) {						
			Feature f = v.addFeature(fl.getKey(), fl.getValue().getType());
			f.count = fl.getValue().count;			
		}
		
		// add features from other Vocabulary
		for (Map.Entry<String, Feature> fl : otherVocabulary.features.entrySet()) {			
			Feature f = v.getFeature(fl.getKey());			
			
			// Feature does not exist, add
			if (f == null) {
				f = v.addFeature(fl.getKey(), fl.getValue().getType());
				f.count = fl.getValue().count;	
			} else {
				f.count += fl.getValue().count;
			}						
						
		}
		// Return added vocabulary;
		return v;
	}
	
	private Feature getFeature(String key) {
		// TODO Auto-generated method stub
		return this.features.get(key);		
	}

	/**
	 * Get default Vocabulary
	 * @return
	 */
	public static Vocabulary getDefaultVocabulary() {
		if (Vocabulary.defaultVocabulary == null) {
			Vocabulary.defaultVocabulary = new Vocabulary();
		}
		return Vocabulary.defaultVocabulary;
	}
	
}
