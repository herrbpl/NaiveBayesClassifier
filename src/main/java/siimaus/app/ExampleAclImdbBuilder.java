package siimaus.app;

import siimaus.corpus.Category;
import siimaus.corpus.Corpus;
import siimaus.corpus.CorpusBuilder;
import siimaus.corpus.Document;
import siimaus.corpus.ICorpus;
import siimaus.tokenizer.BaseTokenizer;
import siimaus.tokenizer.BasicPreprocessor;
import siimaus.tokenizer.ITokenizer;

public class ExampleAclImdbBuilder extends CorpusBuilder<CatDoc> {

	ITokenizer tokenizer = null;
	
	public ExampleAclImdbBuilder(String folderPath, ITokenizer tokenizer) {
		// TODO Auto-generated constructor stub
		if (tokenizer == null) {
			this.tokenizer = new BaseTokenizer(new BasicPreprocessor());
		}
	}
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		for (int i = 0; i<10; i++) {
			String s = String.format("Docno no%d", i);
			Document d = new Document(s, this.tokenizer);
			CatDoc c = new CatDoc();
			c.doc = d;
			if (i < 5) {
				c.cat = "a";
			} else {
				c.cat = "b";
			}
			this.allObservations.add(c);

		}
	}

	@Override
	public ICorpus build() {
		// TODO Auto-generated method stub
		this.splitTestAndTrain();
		ICorpus c = new Corpus(tokenizer);
		
		for (CatDoc catDoc : trainings) {
			c.addDocument(catDoc.cat, catDoc.doc);			
		}
		return c;
	}

}
