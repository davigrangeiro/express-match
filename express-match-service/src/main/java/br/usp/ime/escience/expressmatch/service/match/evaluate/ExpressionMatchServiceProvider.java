package br.usp.ime.escience.expressmatch.service.match.evaluate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.usp.ime.escience.expressmatch.exception.ExpressMatchException;
import br.usp.ime.escience.expressmatch.model.Expression;
import br.usp.ime.escience.expressmatch.model.Stroke;
import br.usp.ime.escience.expressmatch.model.Symbol;
import br.usp.ime.escience.expressmatch.model.graph.Edge;
import br.usp.ime.escience.expressmatch.model.graph.Graph;
import br.usp.ime.escience.expressmatch.model.graph.Node;
import br.usp.ime.escience.expressmatch.service.combinatorial.CombinatorialGeneratorServiceProvider;
import br.usp.ime.escience.expressmatch.service.graph.mst.PrimMST;
import br.usp.ime.escience.expressmatch.service.graph.mst.general.MinimumSpanningTree;
import br.usp.ime.escience.expressmatch.service.graph.utils.GraphUtils;
import br.usp.ime.escience.expressmatch.service.match.ExpressionMatchService;


/**
 * @author davi
 *
 */
@Service
@Transactional
public class ExpressionMatchServiceProvider implements ExpressionMatchService{

	@Autowired
	private CombinatorialGeneratorServiceProvider combinatorialGeneratorService;
	
	private final double STROKE_SCALE = 1.5;
	private final int    STROKE_SET_MAX_SIZE = 5;
	
	@Override
	public Expression matchExpression(Expression transcription)
			throws ExpressMatchException {

		MinimumSpanningTree mst = new PrimMST();
		
		Graph baseGraph = GraphUtils.createGraphByTranscription(transcription.getSymbols());
		Node[] nodeList = mst.getMST(baseGraph);
		
		Map<Integer, Stroke> strokeMap = this.getStrokeMappedById(transcription);
		
		walkAmongTheTree(GraphUtils.getTreeRoot(nodeList), transcription, transcription.getExpressionType().getExpression(), baseGraph, strokeMap);
		
		return transcription;
	}
	
	
	private void walkAmongTheTree(Node root, Expression transcription, Expression model, Graph baseGraph, Map<Integer, Stroke> strokeMap){
		
		if (!root.isAccepted()) {
			
			Stroke currentStrokeNode = strokeMap.get(root.getId());
			double strokeScaledBoundingBoxSize = currentStrokeNode.getStrokeDiagonalSize() * STROKE_SCALE;
			Set<Integer> distanceFilteredStrokes = new HashSet<>();
			
			for (Edge edge : baseGraph.getEdges()) {
				//testing if the currentNode (root) is source of the edge and if the cost of the edge is lower than the strokeScaledBoundingBoxSize (diagonal)
				if (root.getId() == edge.getFrom().getId() && edge.getCost() <= strokeScaledBoundingBoxSize) {
					distanceFilteredStrokes.add(edge.getTo().getId());
				}
			}
			
			int[] valuesForPermutation = new int[distanceFilteredStrokes.size()];
			int index = 0;
			for (Iterator<Integer> iterator = distanceFilteredStrokes.iterator(); iterator.hasNext();) {
				valuesForPermutation[index++] = iterator.next();
			}
	
			//Find correspondences for the root tree node and mark them with the model expression correspondence.
			for (int i = STROKE_SET_MAX_SIZE; i >= 1; i--) {
				combinatorialGeneratorService.getPermutationsResult(valuesForPermutation, i, null);
			}
			
			root.setAccepted(Boolean.TRUE);
		}
		
		// Walk among the tree if the node hasn't visited yet.
		if (!root.isVisited()) {
			root.setVisited(Boolean.TRUE);
			for (Node currentNode: root.getNext()) {
				if(!currentNode.isVisited()){
					walkAmongTheTree(currentNode, transcription, model, baseGraph, strokeMap);
				}
			}
		}
		
	}


	private Map<Integer, Stroke> getStrokeMappedById(Expression transcription) {
		Map<Integer, Stroke> res = new HashMap<>();
		for (Symbol currentSymbol : transcription.getSymbols()) {
			for (Stroke symbolStroke : currentSymbol.getStrokes()) {
				res.put(symbolStroke.getStrokeId(), symbolStroke);
			}
		}
		return res;
	}

}
