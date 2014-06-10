package br.usp.ime.escience.expressmatch.utils.statistics;

import java.util.List;

public class StatisticsUtil {

	public static MeanAndStandardDeviationResponse getMeanAndStandardDeviation(List<Double> instances){
		double mean = 0.0,
			   accumulatedSquaredDeviation = 0.0,
			   standardDeviation = 0.0;
		
		for (double d : instances) {
			mean += d;
		}
		mean /= instances.size();
		
		for (double d : instances) {
			accumulatedSquaredDeviation += Math.pow((d-mean), 2.0);
		} 
		
		accumulatedSquaredDeviation /= (instances.size() - 1);
		standardDeviation = Math.sqrt(accumulatedSquaredDeviation);
		
		return new MeanAndStandardDeviationResponse(mean, standardDeviation);
		
		//Mean: 0.356, sd: 0.062, instancesSize: 1,781
		//Mean: 0.356, sd: 0.062, instancesSize: 1,781
	}
	
}
