package oss.marunowskia.datamining.transactionalcausalinference.utilities.transactions;

import java.util.Hashtable;

import oss.marunowskia.datamining.transactionalcausalinference.utilities.statistics.WelfordStatistics;

/**
 * Simple utility which 
 * @author amarunowski
 *
 */
public class OutlierDetector {

	// Simple wrapper class to hide the implementation detail of the statistical methods..
	
	private Hashtable<Long, WelfordStatistics> statisticsForType;
	public OutlierDetector() {
		statisticsForType = new Hashtable<Long, WelfordStatistics>();
	}
	
	/**
	 * Records the duration statistics for this entry. Returns true iff value is greater than the mean + 2 * standard deviation
	 * @param typeId
	 * @param value
	 * @return
	 */
	public boolean recordObservation(long typeId, double value) {
		WelfordStatistics welfordStatistics;
		if(statisticsForType.containsKey(typeId)) {
			welfordStatistics = statisticsForType.get(typeId);
			welfordStatistics.recordObservedValue(value);
		}
		else {
			welfordStatistics = new WelfordStatistics();
			statisticsForType.put(typeId, welfordStatistics);
			welfordStatistics.recordObservedValue(value);
		}
		return welfordStatistics.getMean() + welfordStatistics.getStandardDeviation()*2 < value;
	}	
}
