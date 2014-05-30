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
import br.usp.ime.escience.expressmatch.model.status.ExpressionStatusEnum;
import br.usp.ime.escience.expressmatch.service.expressions.ExpressionServiceProvider;

@Component
public class ExpressionsReader implements ItemReader<Expression>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExpressionsReader.class);
	
	@Autowired
	private ExpressionServiceProvider expressionServiceProvider;
	
	private List<Expression> expressionsToProcess; 
	
	private Iterator<Expression> expressionsIterator;
	
	private ExpressionStatusEnum expressionStatus;
	
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
			
			loadExpressionsByStatus();
			
			this.expressionsIterator = this.expressionsToProcess.iterator();
			
			LOGGER.info(MessageFormat.format("Fetched {0} expressions with status ({1})", this.expressionsToProcess.size(), this.expressionStatus.toString()));
		}
	}

	private void loadExpressionsByStatus() {
		if (ExpressionStatusEnum.EXPRESSION_TRANSCRIBED.equals(expressionStatus)) {
			this.expressionsToProcess = expressionServiceProvider.findTranscribedExpressions();
		} else if (ExpressionStatusEnum.EXPRESSION_VALIDATED.equals(expressionStatus)){
			this.expressionsToProcess = expressionServiceProvider.findValidatedExpressions();
		}
	}
	
	private boolean thereIsAnyExpressionToProcess() {
		return (null != this.expressionsIterator && this.expressionsIterator.hasNext());
	}
	
	private Expression getNextExpressionToProcess() {
		Expression res =  this.expressionsIterator.next();

		LOGGER.info(MessageFormat.format("Returning expression with id {0}", res.getId()));
		return res;
	}

	public ExpressionStatusEnum getExpressionStatus() {
		return expressionStatus;
	}

	public void setExpressionStatus(ExpressionStatusEnum expressionStatus) {
		this.expressionStatus = expressionStatus;
	}

}
