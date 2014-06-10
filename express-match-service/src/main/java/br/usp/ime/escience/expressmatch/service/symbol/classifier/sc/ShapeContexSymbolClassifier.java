package br.usp.ime.escience.expressmatch.service.symbol.classifier.sc;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import br.usp.ime.escience.expressmatch.utils.statistics.MeanAndStandardDeviationResponse;
import br.usp.ime.escience.expressmatch.utils.statistics.StatisticsUtil;

@Service
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS)
@Transactional
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
		List<Double> values = new ArrayList<>();		
		
		for (int i = 0; i < symbols.size(); i++) {
			SymbolClassifierRequest req = new SymbolClassifierRequest();
			req.setsModel(symbols.get(0));
			req.setsTranscription(new  ArrayList<Stroke>());
			for (Stroke stroke : symbols.get(i).getStrokes()) {
				req.getsTranscription().add(stroke);
			}
			values.add((double)this.match(req).getCost());
		}
		MeanAndStandardDeviationResponse res = StatisticsUtil.getMeanAndStandardDeviation(values);
		System.out.println(MessageFormat.format("Mean: {0}, sd: {1}, instancesSize: {2}",res.getMean(), res.getStandardDeviation(), values.size()));
}

}
