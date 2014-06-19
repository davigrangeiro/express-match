package br.usp.ime.escience.expressmatch.service.graph.mst.general;

import br.usp.ime.escience.expressmatch.model.graph.Graph;
import br.usp.ime.escience.expressmatch.model.graph.Node;
import br.usp.ime.escience.expressmatch.model.graph.Vertex;
import br.usp.ime.escience.expressmatch.service.graph.cost.Cost;

public interface MinimumSpanningTree {

	Node[] getMST(Graph in, Cost<Vertex, Double> cost);
	Node[] getMST(Graph in);	
}
