package br.usp.ime.escience.expressmatch.batch.symbol.classifying.processor;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.usp.ime.escience.expressmatch.model.Symbol;
import br.usp.ime.escience.expressmatch.model.SymbolClass;
import br.usp.ime.escience.expressmatch.service.symbol.classifier.SymbolClassifierRequest;
import br.usp.ime.escience.expressmatch.service.symbol.classifier.SymbolClassifierService;
import br.usp.ime.escience.expressmatch.utils.statistics.MeanAndStandardDeviationResponse;
import br.usp.ime.escience.expressmatch.utils.statistics.StatisticsUtil;

@Component
public class SymboClassResultsProcessor implements ItemProcessor<List<Symbol>, SymbolClass> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SymboClassResultsProcessor.class);

	@Autowired
	@Qualifier("shapeContexSymbolClassifierServiceProvider")
	private SymbolClassifierService symbolClassifier;
	
	@Override
	public SymbolClass process(List<Symbol> arg0) throws Exception {
		SymbolClass res = new SymbolClass();

		Symbol instanceModelSymbol = arg0.get(0);
		
		List<MeanAndStandardDeviationResponse> meansForSymbol = testClassifierForLabel(arg0);
		
		LOGGER.info("Starting calculation of pooled mean for data set");
		MeanAndStandardDeviationResponse pooledMean = StatisticsUtil.getMeanAndStandardDeviationForSeveralInstances(meansForSymbol);
		
		res.setLabel(instanceModelSymbol.getLabel());
		res.setInstanceSize(pooledMean.getInstanceSize() * 1l);
		res.setMean(new BigDecimal(pooledMean.getMean()));
		res.setSd(new BigDecimal(pooledMean.getStandardDeviation()));

		return res;
	}
	
	public List<MeanAndStandardDeviationResponse> testClassifierForLabel(List<Symbol> symbols){
		List<MeanAndStandardDeviationResponse> res = new ArrayList<>();
		List<Double> values = new ArrayList<>();		
		
		for (int i = 0; i < symbols.size(); i++) {
			values.clear();
			Symbol instanceModelSymbol = symbols.get(i);
			
			for (int j = 0; j < symbols.size(); j++) {
				
				if (i != j) {
					
					SymbolClassifierRequest<Symbol> req = new SymbolClassifierRequest<>();
					req.setsModel(instanceModelSymbol);
					req.setsTranscription(symbols.get(j));
					
					values.add((double)symbolClassifier.matchSymbol(req).getCost());

				}
			}

			MeanAndStandardDeviationResponse meanForInstance = StatisticsUtil.getMeanAndStandardDeviation(values);
			LOGGER.info(MessageFormat.format("Using symbol id {0} as model of label {1}; results: Mean: {2}, variation: {3}, sd: {4}, instancesSize: {5}", 
					instanceModelSymbol.getId(), 
					instanceModelSymbol.getLabel(),
					meanForInstance.getMean(), 
					meanForInstance.getVariation(),
					meanForInstance.getStandardDeviation(), 
					meanForInstance.getInstanceSize())
					);
			
			res.add(meanForInstance);
				
		}
	
		return res;
	}

}
