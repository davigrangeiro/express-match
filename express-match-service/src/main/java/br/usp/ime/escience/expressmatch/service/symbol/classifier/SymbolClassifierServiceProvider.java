package br.usp.ime.escience.expressmatch.service.symbol.classifier;

import java.util.List;

import br.usp.ime.escience.expressmatch.model.Stroke;
import br.usp.ime.escience.expressmatch.model.Symbol;


public interface SymbolClassifierServiceProvider {

	SymbolClassifierResponse matchTranscription(SymbolClassifierRequest<List<Stroke>> request);
	
	SymbolClassifierResponse matchSymbol(SymbolClassifierRequest<Symbol> request);
	
}
