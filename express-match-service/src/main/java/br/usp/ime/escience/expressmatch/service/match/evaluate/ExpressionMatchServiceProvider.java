package br.usp.ime.escience.expressmatch.service.match.evaluate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.usp.ime.escience.expressmatch.exception.ExpressMatchException;
import br.usp.ime.escience.expressmatch.model.Expression;
import br.usp.ime.escience.expressmatch.model.graph.Graph;
import br.usp.ime.escience.expressmatch.model.graph.Node;
import br.usp.ime.escience.expressmatch.service.combinatorial.CombinatorialGeneratorServiceProvider;
import br.usp.ime.escience.expressmatch.service.graph.mst.PrimMST;
import br.usp.ime.escience.expressmatch.service.graph.mst.general.MinimumSpanningTree;
import br.usp.ime.escience.expressmatch.service.graph.utils.GraphUtils;
import br.usp.ime.escience.expressmatch.service.match.ExpressionMatchService;


@Service
@Transactional
public class ExpressionMatchServiceProvider implements ExpressionMatchService{

	@Autowired
	private CombinatorialGeneratorServiceProvider combinatorialGeneratorService;
	
	@Override
	public Expression matchExpression(Expression transcription)
			throws ExpressMatchException {

		MinimumSpanningTree mst = new PrimMST();
		
		Graph baseGraph = GraphUtils.createGraphByTranscription(transcription.getSymbols());
		Node[] nodeList = mst.getMST(baseGraph);
		
		walkAmongTheTree(GraphUtils.getTreeRoot(nodeList), transcription, transcription.getExpressionType().getExpression());
		
		return transcription;
	}
	
	
	private void walkAmongTheTree(Node root, Expression transcription, Expression model){
		
		root.setVisited(Boolean.TRUE);
		
		
		//Find correspondences for the root tree node and mark them with the model expression correspondence.
		//combinatorialGeneratorService.getPermutationsResult(values, permutationSize, symbolClassifier)

		for (Node currentNode: root.getNext()) {
			if(!currentNode.isVisited()){
				walkAmongTheTree(currentNode, transcription, model);
			}
		}
	}

}
