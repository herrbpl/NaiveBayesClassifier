package siimaus.corpus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class CorpusBuilder<T> {

	protected List<T> allObservations; 
	protected List<T> tests;
	protected List<T> trainings;
	
	public CorpusBuilder() {
		// TODO Auto-generated constructor stub
		allObservations = new ArrayList<>();
		tests = new ArrayList<>();
		trainings = new ArrayList<>();
		load();		
		splitTestAndTrain();
	}
	/**
	 * Splits data between test and train
	 */
	protected void splitTestAndTrain() {
		// TODO Auto-generated method stub
		tests.clear();
		trainings.clear();
		int trainingsCount  =(int) Math.round( 0.5*allObservations.size());
		System.out.println(trainingsCount);
		trainings.addAll(sample(this.allObservations, trainingsCount));
		allObservations.removeAll(trainings);
		tests.addAll(allObservations);
		allObservations.addAll(tests);
		allObservations.addAll(trainings);
	}

	public List<T> sample(List<T> source, int sampleCount) {
		// TODO Auto-generated method stub
		if (source == null) {
			return null;
		}
		if (sampleCount > source.size()) {
			sampleCount = source.size();
		}
		
		Collections.shuffle(source);
		
		System.out.println(String.format("Source size:%d", source.size()));
		List<T> result = source.subList(0, sampleCount);
		System.out.println(String.format("Sampöle size:%d", result.size()));
		
		return result;
	
	}
	/**
	 * Loads dataset
	 */
	public abstract void load();
	
	public List<T> getTests() {
		return tests;
	}
	
	public List<T> getTrainings() {
		return trainings;
	}
	
	public int getObservationCount() {
		// TODO Auto-generated method stub
		return allObservations.size();
	}

	public abstract ICorpus build(); 
	
}
