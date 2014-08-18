package br.usp.ime.escience.expressmatch.service.match;

import br.usp.ime.escience.expressmatch.exception.ExpressMatchException;
import br.usp.ime.escience.expressmatch.model.Expression;

public interface ExpressionMatchService {

	Expression matchExpression(Expression arg0) throws ExpressMatchException;
	
}
