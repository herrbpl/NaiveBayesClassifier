package siimaus.app;

import siimaus.corpus.Document;

public class CatDoc {
	String path;
	String cat;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s:%s", cat, path);
	}
}
