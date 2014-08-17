package oss.marunowskia.datamining.transactionalcausalinference.demo;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

public class MarkovModelDemo {

	// Demo presets:
	private static final int OBSERVATION_TYPES = 10;
	private static final int NUMBER_OF_VERTICES = 50;
	private static final int EDGES_PER_VERTEX = 4;
	private static final int NUMBER_OF_TARGET_VERTICES = 5;
	private static final int ITERATION_LIMIT = 1000000;
	
	Logger logger = Logger.getLogger(MarkovModelDemo.class);
	public void runDemo() {
		logger.warn("MARKOV_MODEL_DEMO is not yet complete, methinks...");
		List<MarkovModelVertex> graphVertices = initializeMarkovModel();
		feedEventSequenceIntoSequenceAnalyzer(graphVertices);
	}


	private void feedEventSequenceIntoSequenceAnalyzer(List<MarkovModelVertex> markovModel) {
		MarkovModelVertex currentNode = markovModel.get(0);
		for(int iteration = 0; iteration < ITERATION_LIMIT; iteration++) {
			double randomValue = Math.random();
			
			long observedEmittedValue = currentNode.getEmittedValue(randomValue); 
			boolean targetState = currentNode.getTargetFlag();
			currentNode = currentNode.getNextState(randomValue);
		}
	}

	private List<MarkovModelVertex> initializeMarkovModel() {
		List<MarkovModelVertex>graphVertices = new ArrayList<MarkovModelVertex>();
		for(int a=0; a<NUMBER_OF_VERTICES; a++) {
			MarkovModelVertex newVertex = new MarkovModelVertex();
			graphVertices.add(newVertex);
		}
		for(int a=0; a<NUMBER_OF_VERTICES; a++) {
			MarkovModelVertex vertexToConnect = graphVertices.get(a);
			for(int b=0; b<EDGES_PER_VERTEX; b++) {
				int randomVertexIndex = (int)(Math.random()*graphVertices.size());
				long randomEmissionValue = (long)(Math.random()*OBSERVATION_TYPES);
				MarkovModelVertex destination = graphVertices.get(randomVertexIndex);
				vertexToConnect.addEdge(destination, randomEmissionValue);
			}
		}
		return graphVertices;
	}
	
	
	private static class MarkovModelVertex {
		
		private ArrayList<MarkovModelVertex> edges = new ArrayList<MarkovModelVertex>();
		private ArrayList<Long> emissions = new ArrayList<Long>();
		private boolean targetFlag;
		
		public void addEdge(MarkovModelVertex destination, Long emissionType) {
			edges.add(destination);
			emissions.add(emissionType);
		}
		
		public MarkovModelVertex getNextState(double randomInput) {
			if(randomInput < 0 || randomInput > 1) {
				throw new IllegalArgumentException("Random input must be a number between 0 and 1 (inclusive)");
			}
			return edges.get((int)(edges.size() * randomInput));
		}
		
		public Long getEmittedValue(double randomInput) {
			if(randomInput < 0 || randomInput > 1) {
				throw new IllegalArgumentException("Random input must be a number between 0 and 1 (inclusive)");
			}
			return emissions.get((int)(emissions.size() * randomInput));
		}

		public boolean getTargetFlag() {
			return targetFlag;
		}

		public void setTargetFlag(boolean targetFlag) {
			this.targetFlag = targetFlag;
		}
		
	}
}
