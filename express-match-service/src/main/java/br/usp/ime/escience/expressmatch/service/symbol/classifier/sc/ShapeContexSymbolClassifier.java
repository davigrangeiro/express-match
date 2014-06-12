package br.usp.ime.escience.expressmatch.service.symbol.classifier.sc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.usp.ime.escience.expressmatch.model.Stroke;
import br.usp.ime.escience.expressmatch.model.Symbol;
import br.usp.ime.escience.expressmatch.model.graph.Vertex;
import br.usp.ime.escience.expressmatch.model.repository.SymbolRepository;
import br.usp.ime.escience.expressmatch.service.graph.cost.CostShapeContextSymbolMatch;
import br.usp.ime.escience.expressmatch.service.graph.cost.ShapeContextCost;
import br.usp.ime.escience.expressmatch.service.graph.cost.ShapeContextServiceProvider;
import br.usp.ime.escience.expressmatch.service.symbol.classifier.SymbolClassifierServiceProvider;
import br.usp.ime.escience.expressmatch.service.symbol.classifier.SymbolClassifierRequest;
import br.usp.ime.escience.expressmatch.service.symbol.classifier.SymbolClassifierResponse;

@Service
@Transactional
public class ShapeContexSymbolClassifier implements SymbolClassifierServiceProvider {

	@Autowired
	private ShapeContextServiceProvider shapeContextServiceProvider;
	
	@Autowired
	private SymbolRepository symbolRepository;
	
	
	
	@Override
	public SymbolClassifierResponse matchTranscription(SymbolClassifierRequest<List<Stroke>> request) {
		SymbolClassifierResponse res = new SymbolClassifierResponse();
		
		Vertex vi = new Vertex(shapeContextServiceProvider.getShapeContextDescriptor(request.getsModel())),
			   vm = new Vertex(shapeContextServiceProvider.getShapeContextDescriptor(request.getsTranscription()));
		
		ShapeContextCost cost = new CostShapeContextSymbolMatch();
		res.setCost(cost.getCost(vm, vi));
		
		return res;
	}


	@Override
	public SymbolClassifierResponse matchSymbol(SymbolClassifierRequest<Symbol> request) {
		SymbolClassifierResponse res = new SymbolClassifierResponse();
		
		Vertex vi = new Vertex(shapeContextServiceProvider.getShapeContextDescriptor(request.getsModel())),
			   vm = new Vertex(shapeContextServiceProvider.getShapeContextDescriptor(request.getsTranscription()));
		
		ShapeContextCost cost = new CostShapeContextSymbolMatch();
		res.setCost(cost.getCost(vm, vi));
		
		return res;
	}

}
