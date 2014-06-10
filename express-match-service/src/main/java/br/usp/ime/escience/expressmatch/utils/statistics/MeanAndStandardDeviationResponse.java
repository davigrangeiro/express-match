package br.usp.ime.escience.expressmatch.utils.statistics;

public class MeanAndStandardDeviationResponse {

	private double mean;
	private double StandardDeviation;
	
	
	public MeanAndStandardDeviationResponse(double mean, double standardDeviation) {
		super();
		this.mean = mean;
		StandardDeviation = standardDeviation;
	}


	public double getMean() {
		return mean;
	}


	public double getStandardDeviation() {
		return StandardDeviation;
	}
	
	
}