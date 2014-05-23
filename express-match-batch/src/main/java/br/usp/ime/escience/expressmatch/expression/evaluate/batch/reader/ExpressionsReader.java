package br.usp.ime.escience.expressmatch.expression.evaluate.batch.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import br.usp.ime.escience.expressmatch.model.Expression;

@Component
public class ExpressionsReader implements ItemReader<Expression>{

	@Override
	public Expression read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {
		// TODO Auto-generated method stub
		return null;
	}

}
