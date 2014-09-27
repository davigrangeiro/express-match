package br.usp.ime.escience.expressmatch.service.symbol.match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.usp.ime.escience.expressmatch.model.Point;
import br.usp.ime.escience.expressmatch.model.graph.Graph;
import br.usp.ime.escience.expressmatch.model.graph.Vertex;
import br.usp.ime.escience.expressmatch.service.graph.cost.ShapeContextCost;

public class PartialGraphMatching extends GraphMatching {

	protected Map<Integer, Integer> inputMap = new HashMap<Integer, Integer>(); 
	protected Map<Integer, Integer> modelMap = new HashMap<Integer, Integer>(); 
	
	protected float[][] costMatrix = null;
	
	@Override
	/**
	 * NOT IMPLEMENTED
	 */
	public int[][] getMatch() {
		return null;
	}

	public PartialGraphMatching() {
		super();
	}

	public PartialGraphMatching(Graph model, Graph input, ShapeContextCost cost) {
		super(model, input);
		this.setCost(cost);
		
		if (model != null && input != null) {
			this.costMatrix = this.getCostMatrix();
		
			for (int i = 0; i < inputVertex.length; i++) {
				inputMap.put(inputVertex[i].getStrokeId(), i);
			}
			for (int i = 0; i < modelVertex.length; i++) {
				modelMap.put(modelVertex[i].getStrokeId(), i);
			}
		}
	}
	
	protected float[] getMoreProbableModelVertexForInputVertex(Integer inputVertexId) {
		float[] line = this.costMatrix[this.inputMap.get(inputVertexId)];
		return line;
	}
	
	
	

}
