package siimaus.naivebayesclassifier;

import java.text.Format;

/**
 * Feature class. This is just a data structure for simplified input/output to/from Vocabulary
 * @author siaus
 *
 */
public class Feature {
	private String feature;
	public int count = 0;
	public double loglikelihood = 0.0;
	public Vocabulary vocabulary = null;
	public FeatureType type = FeatureType.WORDS;
	
	public Feature(String feature, FeatureType type, int count, double loglikelihood, Vocabulary vocabulary) {
		// TODO Auto-generated constructor stub
		this.feature = feature;
		this.vocabulary = vocabulary;
		this.count = count;
		this.loglikelihood = loglikelihood;
		this.type = type;
	}

	public String getFeature() {
		return feature;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("[\"%s\", %d, \"%s\", %f]", this.feature, count, type.toString(), loglikelihood);
	}

	public String toJSON() {
		return String.format("{\"feature\": \"%s\", \"count\": %d, \"type\": \"%s\", \"likelihood\": %f}", this.feature.replaceAll("\"",  "&quot;"), count, type.toString(), loglikelihood);
	}
}
