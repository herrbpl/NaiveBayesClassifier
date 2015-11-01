package Siim.Aus.NaiveBayesClassifier;

public class Classifier {
	// total number of documents
    // this is used for prior probabilities calculation       
    public int documentCount = 0;
    
    // total number of observations
    public int observationCount = 0;
    
    // total number of classes/categories
    public int categoryCount = 0;
    
    
    
    public Classifier() {
		// TODO Auto-generated constructor stub
    	
	}
    
    // Corpus is container of documents
    public Corpus corpus;
    
    /** 
     * Trains classifier
     * We expect that we have number of features that make up vocabulary
     * We expect to have number of documents in corpus, each labeled with category 
     */
    public void train() {
    	
    }
    
}
