package br.usp.ime.escience.expressmatch.service.graph.cost;

import br.usp.ime.escience.expressmatch.model.graph.Vertex;

public class EuclideanDistance implements Cost<Vertex, Double> {

	@Override
	public Double getCost(Vertex ti, Vertex tj) {
		double xDif = ti.getX() - tj.getX(),
			   yDif = ti.getY() - tj.getY();
		
		Double res = Math.sqrt((xDif*xDif)+(yDif*yDif));
		return res;
	}

}
