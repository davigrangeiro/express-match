package br.usp.ime.escience.expressmatch.service.expressions.evaluate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.usp.ime.escience.expressmatch.model.Expression;
import br.usp.ime.escience.expressmatch.model.Stroke;
import br.usp.ime.escience.expressmatch.model.Symbol;
import br.usp.ime.escience.expressmatch.model.graph.Graph;
import br.usp.ime.escience.expressmatch.model.graph.Vertex;
import br.usp.ime.escience.expressmatch.service.graph.cost.ShapeContextCost;
import br.usp.ime.escience.expressmatch.service.symbol.match.PartialGraphMatching;

public class PartialExpressionMatch extends PartialGraphMatching {


	public PartialExpressionMatch(Graph model, Graph input) {
		super(model, input, new ShapeContextCost());
	}

	public Set<Symbol> getMoreProbableSymbolsForId(int id, Expression model) {
		List<Vertex> probableIds = this.getMoreProbableModelVertexForInputVertex(id, 6);
		Set<Symbol> res = new HashSet<>();
		
		VERTEX_LOOP: for (Vertex vertex : probableIds) {
			for (Symbol symbol : model.getSymbols()) {
				for (Stroke stroke : symbol.getStrokes()) {
					if (vertex.getStrokeId() == stroke.getStrokeId().intValue()) {
						res.add(symbol);
						continue VERTEX_LOOP;
					}
				}
			}
		}
		
		return res;
	}
	
}
