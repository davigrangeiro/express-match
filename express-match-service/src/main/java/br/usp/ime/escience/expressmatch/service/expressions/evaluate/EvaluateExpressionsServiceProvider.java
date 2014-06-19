package br.usp.ime.escience.expressmatch.service.expressions.evaluate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.usp.ime.escience.expressmatch.model.Expression;

@Service
@Transactional
public class EvaluateExpressionsServiceProvider {

	public Expression evaluateExpression(Expression expressionToEvaluate){
		return expressionToEvaluate;
	}
	
}
