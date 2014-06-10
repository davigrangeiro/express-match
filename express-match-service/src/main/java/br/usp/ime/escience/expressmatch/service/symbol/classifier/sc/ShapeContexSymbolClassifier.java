package br.usp.ime.escience.expressmatch.service.symbol.classifier.sc;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.usp.ime.escience.expressmatch.model.Stroke;
import br.usp.ime.escience.expressmatch.model.Symbol;
import br.usp.ime.escience.expressmatch.model.graph.Vertex;
import br.usp.ime.escience.expressmatch.model.repository.SymbolRepository;
import br.usp.ime.escience.expressmatch.service.graph.cost.CostShapeContextSymbolMatch;
import br.usp.ime.escience.expressmatch.service.graph.cost.ShapeContextCost;
import br.usp.ime.escience.expressmatch.service.graph.cost.ShapeContextServiceProvider;
import br.usp.ime.escience.expressmatch.service.symbol.classifier.SymbolClassifier;
import br.usp.ime.escience.expressmatch.service.symbol.classifier.SymbolClassifierRequest;
import br.usp.ime.escience.expressmatch.service.symbol.classifier.SymbolClassifierResponse;

@Service
public class ShapeContexSymbolClassifier implements SymbolClassifier {

	@Autowired
	private ShapeContextServiceProvider shapeContextServiceProvider;
	
	@Autowired
	private SymbolRepository symbolRepository;
	
	@Override
	public SymbolClassifierResponse match(SymbolClassifierRequest request) {
		SymbolClassifierResponse res = new SymbolClassifierResponse();
		
		Vertex vi = new Vertex(shapeContextServiceProvider.getShapeContextDescriptor(request.getsModel())),
			   vm = new Vertex(shapeContextServiceProvider.getShapeContextDescriptor(request.getsTranscription()));
		
		ShapeContextCost cost = new CostShapeContextSymbolMatch();
		res.setCost(cost.getCost(vm, vi));
		
		return res;
	}
	
	public void testClassifier(){
		List<Symbol> symbols = symbolRepository.findByLabel("2");
		
		SymbolClassifierRequest req = new SymbolClassifierRequest();
		req.setsModel(symbols.get(0));
		symbols.get(0).getShapeDescriptors();
		req.setsTranscription(new  ArrayList<Stroke>());
		for (int i = 1; i < 15; i++) {
			for (Stroke stroke : symbols.get(i).getStrokes()) {
				req.getsTranscription().add(stroke);
			}
			System.out.println(this.match(req).getCost());
		}
	}

}
