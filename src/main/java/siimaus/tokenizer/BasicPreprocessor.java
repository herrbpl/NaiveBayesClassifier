package siimaus.tokenizer;

import java.util.Locale;

public class BasicPreprocessor implements IPreprocessor {

	@Override
	public String preProcess(String input) {
		// to lowercase
		// http://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html

		if (input == "") {
			return input;
		}
		input = input.replaceAll("\\r", " "). // line feeds
				replaceAll("\\n", " "). // line feeds
				//replaceAll("\\W+", " "). // all non-word characters
				replaceAll("\\s+", " "). // all whitespace
				replaceAll("\\p{P}+", ""). // all punctiation
				replaceAll("\\s+", " "). // all whitespace
				replaceAll("\\p{P}+", ""). // all punctiation
				replaceAll("^\\s*(\\d)+\\s+", " "). // all numbers in beginning
				replaceAll("\\s+(\\d)+\\s+", " "). // all numbers in middle
				replaceAll("\\s+(\\d)+$", " "). // all numbers in ending
				replaceAll("^(\\d)+$", " "). // all numbers empty
				replaceAll("\\s+(\\d)+(\\s*\\d*)*\\s+", " "). // all numbers in
																// ending

		trim().toLowerCase(Locale.getDefault());
		return input;
	}

}
