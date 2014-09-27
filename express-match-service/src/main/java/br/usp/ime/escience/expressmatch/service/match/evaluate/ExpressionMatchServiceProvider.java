package br.usp.ime.escience.expressmatch.service.match.evaluate;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.usp.ime.escience.expressmatch.exception.ExpressMatchException;
import br.usp.ime.escience.expressmatch.model.Expression;
import br.usp.ime.escience.expressmatch.model.Stroke;
import br.usp.ime.escience.expressmatch.model.Symbol;
import br.usp.ime.escience.expressmatch.model.UserParameter;
import br.usp.ime.escience.expressmatch.model.graph.Edge;
import br.usp.ime.escience.expressmatch.model.graph.Graph;
import br.usp.ime.escience.expressmatch.model.graph.Node;
import br.usp.ime.escience.expressmatch.model.repository.SymbolClassRepository;
import br.usp.ime.escience.expressmatch.model.repository.UserParameterRepository;
import br.usp.ime.escience.expressmatch.model.status.SymbolStatusEnum;
import br.usp.ime.escience.expressmatch.service.combinatorial.CombinatorialGeneratorServiceProvider;
import br.usp.ime.escience.expressmatch.service.expressions.evaluate.PartialExpressionMatch;
import br.usp.ime.escience.expressmatch.service.graph.mst.PrimMST;
import br.usp.ime.escience.expressmatch.service.graph.mst.general.MinimumSpanningTree;
import br.usp.ime.escience.expressmatch.service.graph.utils.GraphUtils;
import br.usp.ime.escience.expressmatch.service.match.ExpressionMatchService;
import br.usp.ime.escience.expressmatch.service.symbol.classifier.SymbolClassifierResponse;
import br.usp.ime.escience.expressmatch.service.symbol.classifier.SymbolClassifierService;
import br.usp.ime.escience.expressmatch.utils.ArrayUtils;


/**
 * @author davi
 *
 */
@Service
@Transactional
public class ExpressionMatchServiceProvider implements ExpressionMatchService{

	private static final double ALPHA = 0.85;

	public static final float[] RECOGNITION_THRESHOLD = {0.15f, 0.32f, 0.34f, 0.30f, 0.28f};

	private static final Logger LOGGER = LoggerFactory.getLogger(ExpressionMatchServiceProvider.class);
	
	@Autowired
	private CombinatorialGeneratorServiceProvider combinatorialGeneratorService;
	
	@Autowired
	private SymbolClassRepository symbolClassRepository;
	
	@Autowired
	@Qualifier("shapeContexSymbolClassifierServiceProvider")
	private SymbolClassifierService symbolClassifierService;
	
	@Autowired
	private UserParameterRepository userParameterRepository;
	
	private final double STROKE_SCALE = 1.5;
	private final int    STROKE_SET_MAX_SIZE = 5;
	
	@Override
	public List<Symbol> matchExpression(Expression transcription)
			throws ExpressMatchException {

		MinimumSpanningTree mst = new PrimMST();
		UserParameter userParameter = userParameterRepository.getUserParameterByRoot(true);
		
		Graph inputGraph = GraphUtils.createGraphByTranscription(transcription.getSymbols());
		Graph modelGraph = GraphUtils.createGraphByTranscription(transcription.getExpressionType().getExpression().getSymbols());

		modelGraph = GraphUtils.scaleModelGraphByTranscription(modelGraph, inputGraph);
		
		inputGraph.updateShapeContextExpression(userParameter);
		modelGraph.updateShapeContextExpression(userParameter);
		
		Node[] nodeList = mst.getMST(inputGraph);
		Map<Integer, Node> nodeMap = new HashMap<Integer, Node>();
		for (Node node : nodeList) {
			node.setVisited(Boolean.FALSE);
			nodeMap.put(node.getNodeId(), node);
		}
		 
		Map<Integer, Stroke> strokeMap = this.getStrokeMappedById(transcription);
		
		//Getting the most probable symbols that will be used to comparison with the stroke set permutations
		PartialExpressionMatch partialExpressionMatch = new PartialExpressionMatch(modelGraph, inputGraph);
		
		Map<Integer, Set<Symbol>> moreProbableSymbolsMap = new HashMap<Integer, Set<Symbol>>();
		
		for (Symbol s : transcription.getSymbols()) {
			for (Stroke stroke : s.getStrokes()) {
				moreProbableSymbolsMap.put(stroke.getStrokeId(), partialExpressionMatch.getMoreProbableSymbolsForId(stroke.getStrokeId(), transcription.getExpressionType().getExpression()));
			}
		}
		
		List<Symbol> recognizedSymbols = new ArrayList<Symbol>();
		walkAmongTheTree(GraphUtils.getTreeRoot(nodeList), nodeMap, transcription, transcription.getExpressionType().getExpression(), inputGraph, modelGraph, strokeMap, recognizedSymbols, moreProbableSymbolsMap);
		
		return recognizedSymbols;
	}
	
	
	private void walkAmongTheTree(Node root, Map<Integer, Node> nodeMap, Expression transcription, Expression model, 
								  Graph inputGraph, Graph modelGraph, Map<Integer, Stroke> strokeMap, 
								  List<Symbol> recognizedSymbols, Map<Integer, Set<Symbol>> moreProbableSymbolsMap){
		
		if (!root.isAccepted()) {
			
			Stroke currentStrokeNode = strokeMap.get(root.getNodeId());

//			double strokeScaledBoundingBoxSize = currentStrokeNode.getStrokeDiagonalSize() * STROKE_SCALE;
			double strokeScaledBoundingBoxSize = inputGraph.getExpressionPartSize() * STROKE_SCALE;

			Set<Integer> distanceFilteredStrokes = new HashSet<>();
			distanceFilteredStrokes.add(currentStrokeNode.getStrokeId());
			
			for (Edge edge : inputGraph.getEdges()) {
				//testing if the currentNode (root) is source of the edge and if the cost of the edge is lower than the strokeScaledBoundingBoxSize (diagonal)
				// and the 'to' node has not accepted yet.

				Node from = (Node) edge.getFrom(),
					 to   = (Node) edge.getTo();
				
				if ( root.getNodeId() == from.getNodeId() && 
					 edge.getCost() <= strokeScaledBoundingBoxSize &&
					!nodeMap.get(to.getNodeId()).isAccepted()) {
					
					distanceFilteredStrokes.add(to.getNodeId());
				}
			}
			
			int[] valuesForPermutation = new int[distanceFilteredStrokes.size()];
			int index = 0;
			for (Iterator<Integer> iterator = distanceFilteredStrokes.iterator(); iterator.hasNext();) {
				valuesForPermutation[index++] = iterator.next();
			}
			

			SymbolClassifierResponse min = null;
			double minValue = Double.MAX_VALUE;
			//Find correspondences for the root tree node and mark them with the model expression correspondence.
			for (int i = STROKE_SET_MAX_SIZE; i >= 1; i--) {
				
				if(i <= valuesForPermutation.length) {
					
					List<SymbolClassifierResponse> responses = combinatorialGeneratorService.getPermutationsResult(valuesForPermutation, i, symbolClassifierService, strokeMap, moreProbableSymbolsMap.get(root.getNodeId()), currentStrokeNode);
					
					
					for (SymbolClassifierResponse instance : responses) {
						
						if (!instance.getUsedSymbol().isUsedInRecognition()) {
						
//							SymbolClass symbolClass = symbolClassRepository.findByLabel(instance.getUsedSymbol().getLabel());
//							float diference = StatisticsUtil.getDistanceOfMean(instance.getCost(), symbolClass.getMean().floatValue(), symbolClass.getSd().floatValue());
							double diference = instance.getCost() * ExpressionMatchServiceProvider.ALPHA + (1-ExpressionMatchServiceProvider.ALPHA) * instance.getUsedSymbol().getCurrentCost();
							//get absolute value of diference
//							diference = Math.abs(diference);
							
							if (minValue > diference) {
								minValue = diference;
								min = instance;
								
							}
							LOGGER.info(MessageFormat.format("Permutation: {0}, symbol: {1}, symbol cost: {2}, expression cost: {3}, total cost: {4}",
											ArrayUtils.printPermutation(instance.getPermutation()),
											instance.getUsedSymbol().getHref(),
											instance.getCost(),
											instance.getUsedSymbol().getCurrentCost(),
											diference));
						}
					}
					
					if (minValue <= ExpressionMatchServiceProvider.RECOGNITION_THRESHOLD[min.getPermutation().length]) {
						break;
					}
				}
			}
			
			min.getUsedSymbol().setUsedInRecognition(Boolean.TRUE);
			
			Symbol newSymbol = new Symbol(transcription);
			newSymbol.setHref(min.getUsedSymbol().getHref());
			newSymbol.setLabel(min.getUsedSymbol().getLabel());
			newSymbol.setSymbolStatus(SymbolStatusEnum.SYMBOL_EVALUATED.getValue());
			newSymbol.setStrokes(new ArrayList<Stroke>());
			
			LOGGER.info(MessageFormat.format("Symbol: {0}, permutation: {1}, cost: {2}",
							min.getUsedSymbol().getHref(),
							ArrayUtils.printPermutation(min.getPermutation()),
							minValue));
			
			for (int id : min.getPermutation()) {
				Stroke stroke = strokeMap.get(id);
				
//				stroke.setSymbol(newSymbol);
				newSymbol.getStrokes().add(stroke);
				
				for (Entry<Integer, Node> node : nodeMap.entrySet()) {
					if(node.getValue().getNodeId() == id) {
						node.getValue().setAccepted(Boolean.TRUE);
						break;
					}
				}
			}
			
			recognizedSymbols.add(newSymbol);
		}
		
		
		
		// Walk among the tree if the node hasn't visited yet.
		if (!root.isVisited()) {
			root.setVisited(Boolean.TRUE);
			for (Node currentNode: root.getNext()) {
				if(!currentNode.isVisited()){

					walkAmongTheTree(currentNode, nodeMap, transcription, model, inputGraph, modelGraph, strokeMap, recognizedSymbols, moreProbableSymbolsMap);

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
