package siimaus.app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import siimaus.corpus.Category;
import siimaus.corpus.Corpus;
import siimaus.corpus.CorpusBuilder;
import siimaus.corpus.Document;
import siimaus.corpus.ICorpus;
import siimaus.tokenizer.BaseTokenizer;
import siimaus.tokenizer.BasicPreprocessor;
import siimaus.tokenizer.ITokenizer;
import siimaus.util.FileUtils;

public class ExampleAclImdbBuilder extends CorpusBuilder<CatDoc> {

	ITokenizer tokenizer = null;
	protected String folderPath = "";
	int maxObservations = 1000;

	public ExampleAclImdbBuilder(String folderPath, ITokenizer tokenizer) {
		
		// TODO Auto-generated constructor stub
		if (tokenizer == null) {
			this.tokenizer = new BaseTokenizer(new BasicPreprocessor());
		}
		this.folderPath = folderPath;
		load();		
		splitTestAndTrain();
	}

	public List<CatDoc> getFilesListEx(String p1, String p2) {
		System.out.println(String.format("This folderpath is %s", this.folderPath));
		List<CatDoc> result = new ArrayList<>();
		String folderPos = this.folderPath + File.separatorChar + p1 +File.separatorChar + p2;
		String fileName = "";
		System.out.println(folderPos);
		File dir = new File(folderPos);
		File[] directoryListing = dir.listFiles();
		
		int maxSize = 1000;
		
		int curr = 0;
		
		if (directoryListing != null) {

			for (File child : directoryListing) {
				if (child.isFile()) {
					fileName = folderPos + File.separatorChar + child.getName();
					CatDoc c = new CatDoc();
					c.cat = p2;
					c.path = fileName;
					
					
					result.add(c);
					
					curr++;
					
					if (curr > maxSize) {
						break;
					}
				}
			}
		}
		
		
		return result;
	}
	
	public List<CatDoc> getFilesList() {
		List<CatDoc> result = new ArrayList<>();
		result.addAll(getFilesListEx("test", "pos"));
		result.addAll(getFilesListEx("test", "neg"));
		result.addAll(getFilesListEx("train", "pos"));
		result.addAll(getFilesListEx("train", "neg"));
		return result;
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		this.allObservations.addAll(getFilesList());
		
	}

	@Override
	public ICorpus build() {
		// TODO Auto-generated method stub
		this.splitTestAndTrain();
		
		List<CatDoc> samples = this.sample(this.trainings, 1000);
		
		ICorpus c = new Corpus(tokenizer);

		for (CatDoc catDoc : samples) {
			String s = FileUtils.fromFileText(catDoc.path);
			
			c.addDocument(catDoc.cat, s);
		}
		return c;
	}

}
