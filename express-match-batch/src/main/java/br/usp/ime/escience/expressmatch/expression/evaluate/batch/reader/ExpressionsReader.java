package br.usp.ime.escience.expressmatch.expression.evaluate.batch.reader;

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

import br.usp.ime.escience.expressmatch.model.Expression;
import br.usp.ime.escience.expressmatch.service.expressions.ExpressionServiceProvider;

@Component
public class ExpressionsReader implements ItemReader<Expression>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExpressionsReader.class);
	
	@Autowired
	private ExpressionServiceProvider expressionServiceProvider;
	
	private List<Expression> expressionsToProcess; 
	
	private Iterator<Expression> expressionsIterator;
	
	@Override
	public Expression read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {
		Expression res = null;
		
		loadExpressionsData();
		
		if (thereIsAnyExpressionToProcess()){
			res = getNextExpressionToProcess();
		} else {
			cleanOldData();
		}
		
		return res;
	}

	private void cleanOldData() {
		this.expressionsIterator = null;
		this.expressionsToProcess = null;
	}

	private void loadExpressionsData() {
		if(null == expressionsToProcess) {
			
			this.expressionsToProcess = expressionServiceProvider.findTranscribedExpressions();
			this.expressionsIterator = this.expressionsToProcess.iterator();
			
			LOGGER.info(MessageFormat.format("Fetched {0} expressions", this.expressionsToProcess.size()));
		}
	}
	
	private boolean thereIsAnyExpressionToProcess() {
		return null != this.expressionsIterator && this.expressionsIterator.hasNext();
	}
	
	private Expression getNextExpressionToProcess() {
		Expression res =  this.expressionsIterator.next();

		LOGGER.info(MessageFormat.format("Returning expression with id {0}", res.getId()));
		return res;
	}

}
