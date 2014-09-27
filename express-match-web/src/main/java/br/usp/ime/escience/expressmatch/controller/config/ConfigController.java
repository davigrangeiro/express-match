package br.usp.ime.escience.expressmatch.controller.config;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.usp.ime.escience.expressmatch.service.expressions.importable.InkmlImportServiceProvider;
import br.usp.ime.escience.expressmatch.service.expressions.result.ExpressionMatchResultExtractor;
import br.usp.ime.escience.expressmatch.service.symbol.classifier.SymbolClassifierService;

@Component
@ManagedBean
@RequestScoped
public class ConfigController implements Serializable{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);
	
	@Autowired
	private InkmlImportServiceProvider importServiceProvider;
	
	@Autowired
	private SymbolClassifierService symbolClassifier;
	
	@Autowired
	private ExpressionMatchResultExtractor extractor;
	
	public String importDataBase(){
		logger.debug("Starting dataset import");
		importServiceProvider.importDataSet();
		return "";
	}
	
	public String testShapeSymbol(){
		
		return "";
	}
	
	
	public String extractExpressionResults(){
		extractor.extractExpressionDatabaseResults();
		return "";
	}

}
