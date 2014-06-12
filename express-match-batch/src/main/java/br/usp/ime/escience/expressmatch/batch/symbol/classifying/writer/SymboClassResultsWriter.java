package br.usp.ime.escience.expressmatch.batch.symbol.classifying.writer;

import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.usp.ime.escience.expressmatch.model.SymbolClass;
import br.usp.ime.escience.expressmatch.model.repository.SymbolClassRepository;

@Component
public class SymboClassResultsWriter implements ItemWriter<SymbolClass> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SymboClassResultsWriter.class);
	
	@Autowired
	private SymbolClassRepository symbolClassRepository;

	@Override
	public void write(List<? extends SymbolClass> arg0) throws Exception {
		
		for (SymbolClass symbolClass : arg0) {
			symbolClassRepository.save(symbolClass);
			
			LOGGER.info(MessageFormat.format("Saved symbol class with id {0} of label {1} ", symbolClass, symbolClass));
		}
		
	}

}
