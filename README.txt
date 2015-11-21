Purpose of project:

1) Learn java
2) Learn git
3) Learn Maven
4) Learn JUnit
5) Learn something about Naive Bayes
6) Fullfill school home project criterias

Naive Bayes text classification requirements

I used this lecture:
	* https://class.coursera.org/nlp/lecture/28 
to understand more about requirements for algorithm.

Also, some information about Naive Bayes calculation was studied on:
	* http://blog.datumbox.com/developing-a-naive-bayes-text-classifier-in-java/
	* http://blog.datumbox.com/machine-learning-tutorial-the-naive-bayes-text-classifier/
	
ChiSquare calculation tutorial
	* http://www.ling.upenn.edu/~clight/chisquared.htm
	* http://math.hws.edu/javamath/ryan/ChiSquare.html	

* Need list of classes/ categories
* Need to read data from input
* Need to simplify/preprocess input
* Need to find size of all vocabulary
* Need to find and store priors or probabilities of a class/category by document
* Need to find and store conditional probabilities of each word for each document
* Need to know count all words in category/class
* Each keyword is Feature
* It is probably wise to separate classifier it self and its configuration as classes
* Input texts are documents
* Documents make up corpus
* Need to store category information for each feature
	* count of features in category

* Text preprocessing
	* Load data / text from file
	* lowercase
	* remove whitespace and punctuation
	* tokenize
	* remove stopwords
	*
	
Learned additionally

* Implementing Iterator and Iterable  

Dataset(s)

	* http://ai.stanford.edu/~amaas/data/sentiment/ - Large Movie Review Dataset