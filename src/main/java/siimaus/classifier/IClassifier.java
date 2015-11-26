package siimaus.classifier;

import siimaus.corpus.Document;

public interface IClassifier {
	/**
	 * Trains classifier
	 */
	public void train();
	/**
	 * Returns predicted category string of input
	 * @param document - Input string
	 * @return Predicted category
	 */
	public String predict(String document);
	public String predict(Document document);
	
}
