package oss.marunowskia.datamining.transactionalcausalinference.utilities.statistics;

public class WelfordStatistics {
	
	// This class uses the Welford method for computing a running standard deviation on a stream of real values.
	// http://www.johndcook.com/standard_deviation.html
	
	double sum;
	double sumOfSquares;
	
	double previousM;
	double nextM;
	double previousS;
	double nextS;
	
	long numberOfObservations;
	
	public void recordObservedValue(double value) {
		numberOfObservations++;
		sum += value;
		
		if(numberOfObservations==1) {
			previousM = nextM = value;
			previousS = 0;
		}
		else {
			nextM = previousM + (value - previousM) / numberOfObservations; // Modify the mean by the amount contributed by the new observation
			nextS = previousS + (value - previousM) * (value - nextM); // Modify the variance by the amount contributed by the new observation
		}
		
		previousM = nextM;
		previousS = nextS;
	}
	
	public double getStandardDeviation() {
		return Math.sqrt(getVariance());
	}
	
	public double getMean() {
		return sum/numberOfObservations;
	}
	
	public double getVariance() {
		return numberOfObservations > 1 ? nextS / (numberOfObservations - 1) : 0.0;
	}
	
}
