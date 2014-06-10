package br.usp.ime.escience.expressmatch.batch.symbol.classifying.processor;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import br.usp.ime.escience.expressmatch.model.Symbol;
import br.usp.ime.escience.expressmatch.model.SymbolClass;

public class SymboClassResultsProcessor implements ItemProcessor<List<Symbol>, SymbolClass> {

	@Override
	public SymbolClass process(List<Symbol> arg0) throws Exception {
		return null;
	}

}
