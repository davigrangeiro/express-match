package br.usp.ime.escience.expressmatch.service.match;

import java.util.List;

import br.usp.ime.escience.expressmatch.exception.ExpressMatchException;
import br.usp.ime.escience.expressmatch.model.Expression;
import br.usp.ime.escience.expressmatch.model.Symbol;

public interface ExpressionMatchService {

	List<Symbol> matchExpression(Expression arg0) throws ExpressMatchException;
	
}
