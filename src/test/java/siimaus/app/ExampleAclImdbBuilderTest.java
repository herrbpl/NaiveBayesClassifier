package siimaus.app;

import java.util.List;

import junit.framework.TestCase;
import siimaus.corpus.ICorpus;

public class ExampleAclImdbBuilderTest extends TestCase {

	public ExampleAclImdbBuilderTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testBuilder() {
		ExampleAclImdbBuilder a = new ExampleAclImdbBuilder(".", null);
		ICorpus c = a.build();
		System.out.println(c.getDocumentCount());
		List<CatDoc> aa = a.getTrainings();
		for (CatDoc catDoc : aa) {
			System.out.println(catDoc.doc);
		}
		
	}
	
}
