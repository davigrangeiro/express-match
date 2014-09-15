package br.usp.ime.escience.expressmatch.service.symbol.classifier;

import br.usp.ime.escience.expressmatch.model.Symbol;

public class SymbolClassifierResponse {

	private float cost;
	private Symbol usedSymbol;
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

	public Symbol getUsedSymbol() {
		return usedSymbol;
	}

	public void setUsedSymbol(Symbol usedSymbol) {
		this.usedSymbol = usedSymbol;
	}
	
}
