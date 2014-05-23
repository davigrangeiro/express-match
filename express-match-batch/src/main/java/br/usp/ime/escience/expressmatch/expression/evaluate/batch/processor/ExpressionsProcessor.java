package br.usp.ime.escience.expressmatch.expression.evaluate.batch.processor;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.usp.ime.escience.expressmatch.model.Expression;
import br.usp.ime.escience.expressmatch.service.expressions.evaluate.EvaluateExpressionsServiceProvider;

@Component
public class ExpressionsProcessor implements ItemProcessor<Expression, Expression>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExpressionsProcessor.class);
	
	@Autowired
	private EvaluateExpressionsServiceProvider evaluateExpressionsServiceProvider;
	
	@Override
	public Expression process(Expression arg0) throws Exception {
		LOGGER.info(MessageFormat.format("Starting processor for expression: {0}", arg0.getId()));
		
		arg0 = evaluateExpressionsServiceProvider.evaluateExpression(arg0);
		
		LOGGER.info(MessageFormat.format("Finishing processor for expression: {0}", arg0.getId()));
		return arg0;
	}

}
