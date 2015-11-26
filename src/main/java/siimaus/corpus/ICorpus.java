package siimaus.corpus;

import java.util.Map;

import siimaus.tokenizer.ITokenizer;

public interface ICorpus {

	/**
	 * Adds category to corpus
	 * 
	 * @param category
	 * @return
	 */
	ICorpus addCategory(String category);
	/**
	 * Adds category to corpus 
	 * @param cat - Category object
	 * @return
	 */

	ICorpus addCategory(Category cat);
	
	/**
	 * Returns count of all features in corpus
	 * 
	 * @return
	 */
	int getVocabularySize();

	/**
	 * Returns category object
	 * @param category
	 * @return Category
	 */
	Category getCategory(String category);

	
	
	Vocabulary getVocabulary(String category);

	/**
	 * Returns Category objects in HashMap
	 */
	Map<String, Category> getCategories();

	/**
	 * Gets all features as vocabulary
	 * 
	 * @return Vocabulary
	 */
	Vocabulary getVocabulary();

	/**
	 * Adds document to corpus category
	 * 
	 * @param category
	 *            - category
	 * @param doc
	 *            - Document
	 * @return this
	 */
	ICorpus addDocument(String category, Document doc);

	/**
	 * Adds document to corpus category;
	 * 
	 * @param category
	 *            - category
	 * @param input
	 *            - Doument body as string
	 * @return this
	 */
	ICorpus addDocument(String category, String input);

	/**
	 * Gets total documents count in corpus
	 * @return
	 */
	int getDocumentCount();

	/**
	 * Gets number of documents that contain this feature
	 * @param feature
	 * @return
	 */
	int getDocumentCount(String feature);
	
	/**
	 * Sets corpus default tokenizer; 
	 * @param tokenizer
	 * @return
	 */
	ICorpus setTokenizer(ITokenizer tokenizer);
	ITokenizer getTokenizer();

	/**
	 * Syncronizes vocabularies between categories
	 * Removes sparseness
	 * @return
	 */
	ICorpus syncrhonizeVocabularies();
	
}