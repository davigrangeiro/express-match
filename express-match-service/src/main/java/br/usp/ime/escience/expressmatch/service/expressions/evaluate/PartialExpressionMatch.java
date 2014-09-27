package br.usp.ime.escience.expressmatch.service.expressions.evaluate;

import java.util.HashSet;
import java.util.Set;

import br.usp.ime.escience.expressmatch.model.Expression;
import br.usp.ime.escience.expressmatch.model.Stroke;
import br.usp.ime.escience.expressmatch.model.Symbol;
import br.usp.ime.escience.expressmatch.model.graph.Graph;
import br.usp.ime.escience.expressmatch.service.graph.cost.ShapeContextCost;
import br.usp.ime.escience.expressmatch.service.symbol.match.PartialGraphMatching;

public class PartialExpressionMatch extends PartialGraphMatching {


	public PartialExpressionMatch(Graph model, Graph input) {
		super(model, input, new ShapeContextCost());
	}

	public Set<Symbol> getMoreProbableSymbolsForId(int id, Expression model) {
		float[] costs = this.getMoreProbableModelVertexForInputVertex(id);
		Set<Symbol> res = new HashSet<>();
		
		for (Symbol symbol : model.getSymbols()) {
			symbol.setCurrentCost(Float.MAX_VALUE);
		}
		
		VERTEX_LOOP: for (int i = 0; i < costs.length; i++) {
			for (Symbol symbol : model.getSymbols()) {
				for (Stroke stroke : symbol.getStrokes()) {
					if (i == stroke.getStrokeId().intValue()) {
						if (costs[i] < symbol.getCurrentCost()) {
							symbol.setCurrentCost(costs[i]);
						}
						res.add(symbol);
						continue VERTEX_LOOP;
					}
				}
			}
		}
		
		return res;
	}
	
}
