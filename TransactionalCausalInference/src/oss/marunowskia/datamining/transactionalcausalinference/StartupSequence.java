package oss.marunowskia.datamining.transactionalcausalinference;

import java.util.Hashtable;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import oss.marunowskia.datamining.transactionalcausalinference.demo.CausalInferenceDemo;
import oss.marunowskia.datamining.transactionalcausalinference.demo.FixedSequenceDemo;
import oss.marunowskia.datamining.transactionalcausalinference.utilities.*;
import oss.marunowskia.datamining.transactionalcausalinference.utilities.transactions.TransactionScanner;

@Singleton
@Startup
public class StartupSequence {

	private Logger logger = Logger.getLogger(StartupSequence.class);
	private TransactionScanner transactionScanner;
	private boolean running = false;
	@Inject
	DatabaseUtililty databaseUtility;
	
	@PostConstruct
	public void startupSequence() {
		logger.info("StartupSequence initiated");

		running = true;
		Hashtable<String, String> config = databaseUtility.getConfig();
		transactionScanner = new TransactionScanner(config);
		databaseUtility.fetchSortedTraceDataBatches(transactionScanner);
		transactionScanner.reportResults();
		running = false;
		
	}
	
	@Schedule(minute="0/5", hour="*", second="0", persistent=false)
	private void readNewTraceData() {
		// If the previous timer instance hasn't finished yet, this method won't even get called due to a timer lock!
		if(!running) {
			running = true;
			databaseUtility.fetchSortedTraceDataBatches(transactionScanner);
			transactionScanner.reportResults();
			running = false;
		}
	}
	
	// FOR DEMO PURPOSES ONLY!
	public static void main(String args[]) {
		new CausalInferenceDemo().runDemo();
	}
}
