package Siim.Aus.NaiveBayesClassifier;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

/**
 * Vocabulary, contains Features Should this still contain strings->Integer maps
 * ? This would remove necessity to manage Feature references but requires
 * separate management of likelihood and feature does not know to what
 * vocabulary it belongs
 * 
 * @author siaus
 *
 */
public class Vocabulary implements Iterable<Feature> {

	// default vocabulary
	public static Vocabulary defaultVocabulary;

	// count of features
	protected Map<String, Integer> features;
	protected Map<String, FeatureType> featureTypes;
	protected Map<String, Double> likelihoods;

	public Vocabulary() {
		// TODO Auto-generated constructor stub
		this.features = new HashMap<>();
		this.featureTypes = new HashMap<>();
		this.likelihoods = new HashMap<>();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub

		StringJoiner joiner = new StringJoiner(",");
		for (Feature feature : this) {
			joiner.add(feature.toString());
		}
		return String.format("%d:%s\n", this.size(), joiner.toString());
	}

	public String toJSON() {
		// TODO Auto-generated method stub

		StringJoiner joiner = new StringJoiner(",");
		for (Feature feature : this) {
			joiner.add(feature.toJSON());
		}
		return String.format("{\"size\": %d, \"features\": [%s]}", this.size(), joiner.toString());
	}

	// ===========================================================================
	// Vocabulary management
	// ===========================================================================

	/**
	 * Size of vocabulary
	 * 
	 * @return
	 */

	public int size() {
		return features.size();
	}

	/**
	 * Clones Vocabulary
	 * 
	 * @return
	 */
	public Vocabulary copy() {
		Vocabulary v = new Vocabulary();

		// add my features to new vocabulary.
		v.features.putAll(this.features);
		v.featureTypes.putAll(this.featureTypes);
		v.likelihoods.putAll(this.likelihoods);

		return v;
	}

	/**
	 * Compares to another Vocabulary and returns true if features match
	 * 
	 * @param oobj
	 * @return true if equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Vocabulary)) {
			return false;
		}
		Vocabulary v = (Vocabulary) obj;

		return v.features.equals(this.features) && v.featureTypes.equals(this.featureTypes)
				&& v.likelihoods.equals(this.likelihoods);

	}

	/**
	 * Adds two vocabularies together and returns new instance NB! Original
	 * vocabularies are copied so original data is not changed
	 * 
	 * @param thisVocabulary
	 * @param otherVocabulary
	 * @return
	 */
	public static Vocabulary addVocabulary(Vocabulary thisVocabulary, Vocabulary otherVocabulary) {
		Vocabulary v = thisVocabulary.copy();
		return v.addVocabulary(otherVocabulary);
	}

	/**
	 * Adds another Vocabulary to this Vocabulary
	 * 
	 * @param otherVocabulary
	 * @return
	 */
	public Vocabulary addVocabulary(Vocabulary otherVocabulary) {

		// add features from other Vocabulary
		for (Feature feature : otherVocabulary) {
			this.addFeature(feature);
		}

		// Return added vocabulary;
		return this;
	}

	/**
	 * Removes Vocabulary from current vocabulary
	 * 
	 * @param otherVocabulary
	 * @return
	 */
	public Vocabulary removeVocabulary(Vocabulary otherVocabulary) {
		// add features from other Vocabulary
		for (Feature feature : otherVocabulary) {
			feature.count = -feature.count;			
			this.addFeature(feature);
		}

		// Return added vocabulary;
		return this;
	}

	/**
	 * Removes everything from current Vocabulary
	 * 
	 * @return emptied Vocabulary;
	 */
	public Vocabulary clear() {
		this.features.clear();
		this.featureTypes.clear();
		this.likelihoods.clear();
		return this;
	}

	// ===========================================================================
	// feature management
	// ===========================================================================

	/**
	 * Gets feature
	 * 
	 * @param key
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Feature getFeature(String key) throws IllegalArgumentException {			
		// TODO Auto-generated method stub
		if (!this.features.containsKey(key)) {
			return null;
			// throw new IllegalArgumentException(String.format("No such feature
			// %s", key));
		}
		return new Feature(key, this.featureTypes.get(key), this.features.get(key), this.likelihoods.get(key), this);
	}

	/**
	 * Add feature if it does not exist If feature exist, check if type is same.
	 * If not, illegal argument is thrown; If feature exist and type is same,
	 * increase feature count by feature count.
	 * 
	 * @param f
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vocabulary addFeature(Feature f) throws IllegalArgumentException {
		if (f == null) {
			// throw new IllegalArgumentException("Feature cannot be null");
			return this;
		}

		// no change because add or subtract 0;
		if (f.count == 0) {
			return this;
		}

		if (this.features.containsKey(f.getFeature())) { // feature exist
			// add count
			Feature f2 = this.getFeature(f.getFeature());
			if (f2.type != f.type) {
				throw new IllegalArgumentException("Feature already exist with different type");
			}

			f2.count += f.count;

			if (f2.count == 0) {
				// remove feature
				return this.removeFeature(f.getFeature());
			}

			this.features.put(f2.getFeature(), f2.count);
			// will not change likelihood.

			return this;

		} else { // feature does not exist
			// or just could silently return this
			if (f.count < 0) {
				// throw new IllegalArgumentException("Cannot add negative
				// count");
				return this;
			}
			this.features.put(f.getFeature(), f.count);
			this.featureTypes.put(f.getFeature(), f.type);
			this.likelihoods.put(f.getFeature(), f.loglikelihood);
			return this;
		}

	}

	public Vocabulary addFeature(String s, FeatureType type, int count, double likelihood) {
		return this.addFeature(new Feature(s, type, count, likelihood, this));
	}

	public Vocabulary addFeature(String s, FeatureType type) {
		return this.addFeature(s, type, 1, 0.0);
	}

	public Vocabulary addFeature(String s, int count) {
		return this.addFeature(s, FeatureType.WORDS, count, 0.0);
	}

	/**
	 * Adds feature with default type WORDS
	 * 
	 * @param s
	 * @return
	 */
	public Vocabulary addFeature(String s) {
		return this.addFeature(s, 1);
	}

	/**
	 * Sets Feature count. Setting it to zero removes feature. If feature does not exist, it is added.
	 * @param key
	 * @param count
	 * @return
	 */
	public Vocabulary setFeatureCount(String key, int count) {
		if (!this.features.containsKey(key)) {			
			return this.addFeature(key, count);
		} 
		if (count == 0) {
			return this.removeFeature(key);
		}
		this.features.put(key, count);
		return this;
	}
	
	/**
	 * Removes feature if exists.
	 * 
	 * @param key
	 * @return
	 */
	public Vocabulary removeFeature(String key) {
		if (!this.features.containsKey(key)) {
			return this;
		}
		this.features.remove(key);
		this.featureTypes.remove(key);
		this.likelihoods.remove(key);
		return this;
	}

	/**
	 * Removes feature if exists.
	 * 
	 * @param key
	 * @return
	 */
	public Vocabulary removeFeature(Feature feature) {
		feature.count = -feature.count;
		return this.addFeature(feature);
	}

	/**
	 * Get default Vocabulary
	 * 
	 * @return
	 */
	public static Vocabulary getDefaultVocabulary() {
		if (Vocabulary.defaultVocabulary == null) {
			Vocabulary.defaultVocabulary = new Vocabulary();
		}
		return Vocabulary.defaultVocabulary;
	}

	/**
	 * Return iterator for Features This thread was used for reference:
	 * http://codereview.stackexchange.com/questions/48109/simple-example-of-an-
	 * iterable-and-an-iterator-in-java
	 */
	@Override
	public Iterator<Feature> iterator() {
		// TODO Auto-generated method stub
		return new FeatureIterator();
	}

	/**
	 * This thread was used for reference:
	 * http://codereview.stackexchange.com/questions/48109/simple-example-of-an-
	 * iterable-and-an-iterator-in-java
	 * 
	 * @author Sahand on StackOverFlow / siaus
	 *
	 */

	private final class FeatureIterator implements Iterator<Feature> {
		private int cursor;
		private final String[] keys;

		public FeatureIterator() {
			this.keys = Vocabulary.this.features.keySet().toArray(new String[Vocabulary.this.size()]);
			this.cursor = 0;
		}

		public boolean hasNext() {
			return this.cursor < Vocabulary.this.size();
		}

		public Feature next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			}

			return Vocabulary.this.getFeature(this.keys[cursor++]);
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
