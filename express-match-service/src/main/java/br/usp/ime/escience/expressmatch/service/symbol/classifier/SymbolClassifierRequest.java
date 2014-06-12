package br.usp.ime.escience.expressmatch.service.symbol.classifier;

import br.usp.ime.escience.expressmatch.model.Symbol;

public class SymbolClassifierRequest<SymbolOrStrokeList>{

	private Symbol sModel;
	private SymbolOrStrokeList sTranscription;
	
	public Symbol getsModel() {
		return sModel;
	}
	public void setsModel(Symbol sModel) {
		this.sModel = sModel;
	}
	public SymbolOrStrokeList getsTranscription() {
		return sTranscription;
	}
	public void setsTranscription(SymbolOrStrokeList sTranscription) {
		this.sTranscription = sTranscription;
	}
	
}
