package br.usp.ime.escience.expressmatch.batch.symbol.classifying.reader;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.usp.ime.escience.expressmatch.model.Symbol;
import br.usp.ime.escience.expressmatch.model.repository.SymbolRepository;

@Component
public class SymboClassResultsReader implements ItemReader<List<Symbol>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SymboClassResultsReader.class);
	
	@Autowired
	private SymbolRepository symbolRepository;
	
	private List<String> distinctSymbolClasses;
	
	private Iterator<String> distinctSymbolClassesIterator;
	
	@Override
	public List<Symbol> read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {
		
		List<Symbol> res = null;
		
		loadSymbolClassesData();
		
		if (isThereAnySymbolClassToProcess()) {
			res = getNextSetOfSymbols();
		}
		return res;
	}


	private List<Symbol> getNextSetOfSymbols() {
		List<Symbol> res;
		String label = distinctSymbolClassesIterator.next();
		res = symbolRepository.findByLabel(label);
		
		LOGGER.info(MessageFormat.format("Returning {0} instances of symbol class {1}", res.size(), label));
		return res;
	}


	private boolean isThereAnySymbolClassToProcess() {
		return null != distinctSymbolClassesIterator && distinctSymbolClassesIterator.hasNext();
	}


	private void loadSymbolClassesData() {
		if (null == distinctSymbolClasses) {
			distinctSymbolClasses = symbolRepository.getDistinctSymbolClasses();
			distinctSymbolClassesIterator = distinctSymbolClasses.iterator();
			
			LOGGER.info(MessageFormat.format("Found {0} distinct symbol classes", distinctSymbolClasses.size()));
		}
	}
}
