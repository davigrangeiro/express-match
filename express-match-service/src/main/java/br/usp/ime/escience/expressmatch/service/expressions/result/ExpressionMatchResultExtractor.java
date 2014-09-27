package br.usp.ime.escience.expressmatch.service.expressions.result;

import java.util.List;

import javassist.compiler.ast.Symbol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.usp.ime.escience.expressmatch.model.Expression;
import br.usp.ime.escience.expressmatch.model.ExpressionType;
import br.usp.ime.escience.expressmatch.model.repository.ExpressionTypeRepository;
import br.usp.ime.escience.expressmatch.service.match.ExpressionMatchService;
import br.usp.ime.escience.expressmatch.service.match.evaluate.ExpressionMatchServiceProvider;

@Service
public class ExpressionMatchResultExtractor {

	@Autowired
	private ExpressionTypeRepository expressionTypeRepository;
	
	@Autowired 
	@Qualifier("expressionMatchServiceProvider")
	private ExpressionMatchService expressionMatchService;
	
	@Async
	public void extractExpressionDatabaseResults(){
		try {
			Thread.sleep(10000l);
			System.out.println("pronto");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void extractExpressionResultByType(ExpressionType type) {
		for (Expression expression : type.getExpressions()) {
			if(null != expression && null != type.getExpression() && 
			   expression.getId().longValue() != type.getExpression().getId() && 
			   expression.getExpressionStatus().intValue() == ExpressionStatusEnum)  {
				List<Symbol> symbols = expressionMatchService.matchExpression(expression);
				
			}
		}
		
		
	}
	
	
	
}
