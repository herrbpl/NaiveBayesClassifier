package Siim.Aus.NaiveBayesClassifier;

/**
 * Feature class. This should not be used without Vocabulary?
 * @author siaus
 *
 */
public class Feature {		
	public int count = 0;	
	private Vocabulary vocabulary = null;
	FeatureType type = FeatureType.WORDS;
	
	/**
	 * Static method for creating or reusing Feature; If feature already exists in vocabulary, increase count of feature in vocabulary  
	 * @param s
	 * @param f
	 * @param vocabulary
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Feature addFeature(String s, FeatureType f, Vocabulary vocabulary) throws IllegalArgumentException {
		if (vocabulary == null) {
			throw new IllegalArgumentException("Vocabulary cannot be null");
		}
		
		// TODO: Capsulate vocabulary to getter/setter=?
		Feature feature = vocabulary.features.get(s);
		// if feature does not exists in vocabulary, add feat
		if (feature == null) {
			feature = new Feature(vocabulary);
			feature.type = f;
			vocabulary.features.put(s,feature);
		} else {
			feature.count++;
		}
		return feature;
			
	}
		
	
	public Feature(Vocabulary vocabulary) {
		// TODO Auto-generated constructor stub
		this.vocabulary = vocabulary;
		this.count = 1;
	}	
	
	public Vocabulary getVocabulary() {
		return vocabulary;
	}

	public void setVocabulary(Vocabulary vocabulary) {
		this.vocabulary = vocabulary;
	}

	public FeatureType getType() {
		return type;
	}

	public void setType(FeatureType type) {
		this.type = type;
	}
}
