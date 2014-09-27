package br.usp.ime.escience.expressmatch.service.graph.utils;

import java.util.Collection;
import java.util.List;

import br.usp.ime.escience.expressmatch.model.Point;
import br.usp.ime.escience.expressmatch.model.Stroke;
import br.usp.ime.escience.expressmatch.model.Symbol;
import br.usp.ime.escience.expressmatch.model.graph.Graph;
import br.usp.ime.escience.expressmatch.model.graph.Node;
import br.usp.ime.escience.expressmatch.model.graph.Vertex;

public class GraphUtils {
	
	private GraphUtils(){}
	
	public static Graph createGraphByTranscription(Collection<Symbol> symbolList){
		Graph responseGraph = new Graph();
		
		int i=0;
		for (Symbol symbol : symbolList) {
			for (Stroke stroke : symbol.getStrokes()) {
			
				Point representant = stroke.getRepresentantPointOfStroke();
				responseGraph.addVertex(i++, stroke.getStrokeId(), representant.getX(), representant.getY());
				
			}
		}
		
		return responseGraph;
	}
	
	public static Graph scaleModelGraphByTranscription(Graph model, Graph transcription) {
		double xScale = transcription.getWidth() / model.getWidth();
		double yScale = transcription.getHeight() / model.getHeight();
		double usedScale = yScale > xScale ? yScale : xScale;
		
		model = scaleGraphByFactor(model, xScale, yScale);
		
		return model;
	}
	
	public static Graph scaleGraphByFactor(Graph in, double xScale, double yScale) {
		for (Vertex v : in.getIndexedVertexes()) {
			v.setX(v.getX() * xScale);
			v.setY(v.getY() * yScale);
		}
		return in;
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
