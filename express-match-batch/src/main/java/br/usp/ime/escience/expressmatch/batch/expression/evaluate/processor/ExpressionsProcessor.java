package br.usp.ime.escience.expressmatch.batch.expression.evaluate.processor;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.usp.ime.escience.expressmatch.model.Expression;
import br.usp.ime.escience.expressmatch.service.match.ExpressionMatchService;

@Component
public class ExpressionsProcessor implements ItemProcessor<Expression, Expression>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExpressionsProcessor.class);
	
	@Autowired
	@Qualifier("expressionMatchServiceProvider")
	private ExpressionMatchService expressionMatchService;
	
	
	@Override
	public Expression process(Expression arg0) throws Exception {
		LOGGER.info(MessageFormat.format("Starting processor for expression: {0}", arg0.getId()));
		
		arg0 = expressionMatchService.matchExpression(arg0);
		
		LOGGER.info(MessageFormat.format("Finishing processor for expression: {0}", arg0.getId()));
		return arg0;
	}

}

