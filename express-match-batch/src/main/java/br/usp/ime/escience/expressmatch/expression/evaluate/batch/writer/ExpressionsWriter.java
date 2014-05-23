package br.usp.ime.escience.expressmatch.expression.evaluate.batch.writer;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import br.usp.ime.escience.expressmatch.exception.ExpressMatchExpression;
import br.usp.ime.escience.expressmatch.model.Expression;
import br.usp.ime.escience.expressmatch.service.expressions.ExpressionServiceProvider;
import br.usp.ime.escience.expressmatch.utils.StringUtils;


@Component
public class ExpressionsWriter implements ItemWriter<Expression>{

	private static final Logger LOGGER = LoggerFactory.getLogger(ExpressionsWriter.class);
	
	private ExpressionServiceProvider expressionServiceProvider;
	
	@Override
	public void write(List<? extends Expression> arg0) throws Exception {
		String ids = StringUtils.getArrayContents(arg0);
		
		LOGGER.info(MessageFormat.format("Starting writing expressions with ids: ({0})", ids));
		
		try {
			saveExpressions(arg0);
		} catch (ExpressMatchExpression e){
			//This error is not blocking, if there is an exception while trying to save a expression, the process carries on.
			LOGGER.warn("An error occur while attempt to save expressions", e);
		} catch (Exception e) {
			LOGGER.error("An error occur while attempt to save expressions", e);
			throw e;
		}
		
		LOGGER.info(MessageFormat.format("Finishing writing expressions with ids: ({0})", ids));
		
	}

	private void saveExpressions(List<? extends Expression> arg0) throws ExpressMatchExpression{
		List<Expression> expressions = new ArrayList<>();
		expressions.addAll(arg0);
		
//		expressionServiceProvider.saveExpressions(expressions);
	}

}
