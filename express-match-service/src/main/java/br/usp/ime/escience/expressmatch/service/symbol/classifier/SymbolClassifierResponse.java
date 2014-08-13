package br.usp.ime.escience.expressmatch.service.symbol.classifier;

public class SymbolClassifierResponse {

	private float cost;
	private int[] permutation;

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public int[] getPermutation() {
		return permutation;
	}

	public void setPermutation(int[] permutation) {
		this.permutation = permutation;
	}
	
}
