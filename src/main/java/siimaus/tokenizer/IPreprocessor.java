package siimaus.tokenizer;

public interface IPreprocessor {
	/**
	 * Preprocess string and return it
	 * @param input
	 * @return processed string
	 */
	public String preProcess(String input);
}
