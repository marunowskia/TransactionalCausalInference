package oss.marunowskia.datamining.transactionalcausalinference.utilities.transactions;

import java.util.Hashtable;

import oss.marunowskia.datamining.transactionalcausalinference.models.TransactionBoundary;
import oss.marunowskia.datamining.transactionalcausalinference.models.TransactionBoundary.EventType;
import oss.marunowskia.datamining.transactionalcausalinference.utilities.sequences.SequenceAnalyzer;

public class TransactionSequenceAnalyzer extends SequenceAnalyzer  {

	private OutlierDetector outlierDetector = new OutlierDetector();	
	
	public TransactionSequenceAnalyzer(Hashtable<String, String> config) {
		super(config);
	}
	
	public void analalyzeTransactionBoundary(TransactionBoundary event) {
		super.recordEvent(event, event.getTypeId());
	
		if(event.getEventType()==EventType.TRANSACTION_END) { 
			boolean eventIsOutlier = outlierDetector.recordObservation(event.getTypeId(), event.getTransaction().getDuration());
			super.analyzeEvent(event, eventIsOutlier);
			
			// Since we only have the duration of the transaction once the transaction finishes, 
			// we have to perform our analysis of the transaction's start event after the transaction has ended. 
			super.analyzeEvent(event.getTransaction().getStartBoundary(), eventIsOutlier);
		}
	}
}
