package br.usp.ime.escience.expressmatch.utils.statistics;

import java.util.List;

public class StatisticsUtil {

	public static MeanAndStandardDeviationResponse getMeanAndStandardDeviation(List<Double> instances) {
		double mean = 0.0,
			   accumulatedSquaredVariation = 0.0,
			   standardDeviation = 0.0;
		
		for (double d : instances) {
			mean += d;
		}
		mean /= instances.size();
		
		for (double d : instances) {
			accumulatedSquaredVariation += Math.pow((d-mean), 2.0);
		} 
		
		accumulatedSquaredVariation /= (instances.size() - 1);
		standardDeviation = Math.sqrt(accumulatedSquaredVariation);
		
		return new MeanAndStandardDeviationResponse(mean, standardDeviation, accumulatedSquaredVariation, instances.size());
	}
	
	
	public static MeanAndStandardDeviationResponse getMeanAndStandardDeviationForSeveralInstances(List<MeanAndStandardDeviationResponse> severalSamples) {
		//Pooled_variance	
	
		double mean = 0.0,
			   accumulatedSquaredVariation = 0.0,
			   standardDeviation = 0.0;
		int sumOfSetsSize = 0;
				
		
		for (MeanAndStandardDeviationResponse meanI : severalSamples) {
			mean += meanI.getMean();
			sumOfSetsSize += meanI.getInstanceSize() - 1;
			accumulatedSquaredVariation += meanI.getVariation() * (meanI.getInstanceSize() - 1); // Sum (ni-1 * iSetVariation)
		}
		
		mean /= severalSamples.size();
		
		accumulatedSquaredVariation /= sumOfSetsSize; // size = (n-1)*n = n^2-n
		
		standardDeviation = Math.sqrt(accumulatedSquaredVariation);
		
		return new MeanAndStandardDeviationResponse(mean, standardDeviation, accumulatedSquaredVariation, severalSamples.size());
	}
	
}
