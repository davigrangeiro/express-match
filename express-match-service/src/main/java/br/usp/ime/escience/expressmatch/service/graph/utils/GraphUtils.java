package br.usp.ime.escience.expressmatch.service.graph.utils;

import java.util.List;

import br.usp.ime.escience.expressmatch.model.Point;
import br.usp.ime.escience.expressmatch.model.Stroke;
import br.usp.ime.escience.expressmatch.model.Symbol;
import br.usp.ime.escience.expressmatch.model.graph.Graph;
import br.usp.ime.escience.expressmatch.model.graph.Node;

public class GraphUtils {
	
	private GraphUtils(){}
	
	public static Graph createGraphByTranscription(List<Symbol> symbolList){
		Graph responseGraph = new Graph();
		
		for (Symbol symbol : symbolList) {
			for (Stroke stroke : symbol.getStrokes()) {
			
				Point representant = stroke.getRepresentantPointOfStroke();
				responseGraph.addVertex(stroke.getStrokeId(), representant.getX(), representant.getY());
				
			}
		}
		
		return responseGraph;
	}
	
	
	public static Node getTreeRoot(Node[] nodeList){
		Node res = null;
		
		for (int i = 0; i < nodeList.length && null == res; i++) {
			if(null == nodeList[i].getPrevious()) {
				res = nodeList[i];
			}
		}
		
		return res;
	}
	
}
