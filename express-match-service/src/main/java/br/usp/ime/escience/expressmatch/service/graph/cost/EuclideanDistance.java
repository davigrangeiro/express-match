package br.usp.ime.escience.expressmatch.service.graph.cost;

import br.usp.ime.escience.expressmatch.model.graph.Node;

public class EuclideanDistance implements Cost<Node, Double> {

	@Override
	public Double getCost(Node ti, Node tj) {
		double xDif = ti.getX() - tj.getX(),
			   yDif = ti.getY() - tj.getY();
		
		Double res = Math.sqrt((xDif*xDif)+(yDif*yDif));
		return res;
	}

}
