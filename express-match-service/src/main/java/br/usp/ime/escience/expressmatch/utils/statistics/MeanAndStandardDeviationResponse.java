package br.usp.ime.escience.expressmatch.utils.statistics;

public class MeanAndStandardDeviationResponse {

	private double mean;
	private double variation;
	private double standardDeviation;
	private int    instanceSize;
	
	
	public MeanAndStandardDeviationResponse(double mean, double standardDeviation, double variation, int instanceSize) {
		super();
		this.mean = mean;
		this.standardDeviation = standardDeviation;
		this.variation = variation;
		this.instanceSize = instanceSize;
	}


	public double getMean() {
		return mean;
	}


	public double getStandardDeviation() {
		return standardDeviation;
	}


	public double getVariation() {
		return variation;
	}


	public int getInstanceSize() {
		return instanceSize;
	}
	
	
}