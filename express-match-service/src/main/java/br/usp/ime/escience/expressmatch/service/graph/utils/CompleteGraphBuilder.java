package br.usp.ime.escience.expressmatch.service.graph.utils;

import java.util.ArrayList;

import br.usp.ime.escience.expressmatch.model.UserParameter;
import br.usp.ime.escience.expressmatch.model.graph.Edge;
import br.usp.ime.escience.expressmatch.model.graph.Graph;
import br.usp.ime.escience.expressmatch.model.graph.Node;
import br.usp.ime.escience.expressmatch.model.graph.Vertex;
import br.usp.ime.escience.expressmatch.service.graph.cost.Cost;


public class CompleteGraphBuilder {
	

	@SuppressWarnings("unchecked")
	public static ArrayList<Edge>[] getCompleteGraphEdgesBasedOnVetexSize(Graph in, Cost<Vertex, Double> cost, UserParameter uParameter) { 
		ArrayList<Edge>[] neighbours = new ArrayList[in.getVertexSize()];
		
		for (int i = 0; i < neighbours.length; i++) {
			neighbours[i] = new ArrayList<>();
		}
		
		Vertex[] vertexList = in.getIndexedVertexes();
		
		for (int j = 0; j < vertexList.length - 1; j++) {

			double maxRadius = uParameter.getStrokeSizeMultiplier() * vertexList[j].getVertexSize();
			for (int k = j+1; k < vertexList.length; k++) {
				double costD = cost.getCost(vertexList[j], vertexList[k]);
				
				if (costD <= maxRadius) {
					neighbours[j].add(new Edge(vertexList[j], vertexList[k], costD));
				}
			}
		}
		return neighbours;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Edge>[] getCompleteGraphEdges(Node[] nodes, Cost<Vertex, Double> cost) { 
		ArrayList<Edge>[] neighbours = new ArrayList[nodes.length];
		
		for (int i = 0; i < neighbours.length; i++) {
			neighbours[i] = new ArrayList<>();
		}
		
		for (int j = 0; j < nodes.length - 1; j++) {
			for (int k = j+1; k < nodes.length; k++) {
				double costD = cost.getCost(nodes[j], nodes[k]);
				neighbours[j].add(new Edge(nodes[j], nodes[k], costD));
				neighbours[k].add(new Edge(nodes[k], nodes[j], costD));
			}
		}
		return neighbours;
	}

}
