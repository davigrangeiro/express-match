package br.usp.ime.escience.expressmatch.service.symbol.classifier;


public interface SymbolClassifier {

	SymbolClassifierResponse match(SymbolClassifierRequest request);
	
}
