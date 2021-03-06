package oss.marunowskia.datamining.transactionalcausalinference.demo;

import java.beans.EventSetDescriptor;

import org.apache.log4j.Logger;

import oss.marunowskia.datamining.transactionalcausalinference.utilities.sequences.SequenceAnalyzer;


// Apologies if this part is messy. I may have sacrificed sleep to sneak writing this into my schedule
public class FixedSequenceDemo {
	
	private SequenceAnalyzer sequenceAnalyzer = new SequenceAnalyzer();
	private double fixedSequenceProbability = 0.1;
	private double interruptionProbability = 0.1;
	private int numberOfEventTypes = 500;
	
	Logger logger = Logger.getLogger(FixedSequenceDemo.class);
	long[] fixedSequence = {4,4,4,4,4,4,4,1,2,3,4,5,6,7,8,9};
	
	/**
	 * Run a basic demo which contains both the expected sequence and unexpected sequence.
	 * The strongest association rule for this should be very close to 100% confident, 
	 * as there is a well defined pattern that it should be able to find.
	 * 
	 * @param demoSequenceLength
	 */
	public synchronized void runDemo(int demoSequenceLength) {
		
		for(int repeat = 0; repeat < demoSequenceLength/fixedSequence.length; repeat++) {
			if(Math.random()<fixedSequenceProbability) {
				// Simulates observing a defined sequence of events, and always getting the same result for the last event in that sequence
				playFixedSequence(sequenceAnalyzer);
			}
			else {
				playRandomSequence(sequenceAnalyzer);
			}
		}
		sequenceAnalyzer.reportAssociationRules();
	}
	
	public void optimizeConfiguration(int demoSequenceLength) {
		// Somewhat optimized parameters, based on our knowledge of the input data
		sequenceAnalyzer.setHistoryLimit((int)(fixedSequence.length*2*(1/(1-interruptionProbability))));
		sequenceAnalyzer.setRuleDepthLimit(fixedSequence.length);
		sequenceAnalyzer.setSupportThreshold((int)(demoSequenceLength/fixedSequence.length*fixedSequenceProbability*fixedSequenceProbability));
	}

	private int eventId = 0;
	private void playFixedSequence(SequenceAnalyzer sequenceAnalyzer) {
		for(int a=0; a<fixedSequence.length; a++) {
			long event = fixedSequence[a];

			// Insert interruptions into the sequence being analyzed
			if(Math.random()<interruptionProbability) {
				sequenceAnalyzer.recordEvent(++eventId, (long)(Math.random()*100));
				sequenceAnalyzer.analyzeEvent(eventId, Math.random()<0.01);
			}
			
			sequenceAnalyzer.recordEvent(++eventId, event);
			if(a!=fixedSequence.length-1) {
				// Randomize the class label of the non-target sequence termination events
				sequenceAnalyzer.analyzeEvent(eventId, Math.random()<0.1);
			}
			else {
				sequenceAnalyzer.analyzeEvent(eventId, true);
			}
		}
	}
	
	private void playRandomSequence(SequenceAnalyzer sequenceAnalyzer) {
		for(int a=0; a<fixedSequence.length; a++) {
			long event = (long)(Math.random()*numberOfEventTypes);
			sequenceAnalyzer.recordEvent(++eventId, event);
			sequenceAnalyzer.analyzeEvent(eventId, Math.random()<0.1);
		}
	}

	public SequenceAnalyzer getSequenceAnalyzer() {
		return sequenceAnalyzer;
	}

	public void setSequenceAnalyzer(SequenceAnalyzer sequenceAnalyzer) {
		this.sequenceAnalyzer = sequenceAnalyzer;
	}

	public double getFixedSequenceProbability() {
		return fixedSequenceProbability;
	}
	
	public double getInterruptionProbability() {
		return interruptionProbability;
	}

	public void setInterruptionProbability(double interruptionProbability) {
		this.interruptionProbability = interruptionProbability;
	}

	public void setFixedSequenceProbability(double fixedSequenceProbability) {
		this.fixedSequenceProbability = fixedSequenceProbability;
	}

	public int getNumberOfEventTypes() {
		return numberOfEventTypes;
	}

	public void setNumberOfEventTypes(int numberOfEventTypes) {
		this.numberOfEventTypes = numberOfEventTypes;
	}
	
}
