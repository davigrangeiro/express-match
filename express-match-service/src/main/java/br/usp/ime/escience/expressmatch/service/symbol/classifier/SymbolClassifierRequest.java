package br.usp.ime.escience.expressmatch.service.symbol.classifier;

import java.util.List;

import br.usp.ime.escience.expressmatch.model.Stroke;
import br.usp.ime.escience.expressmatch.model.Symbol;

public class SymbolClassifierRequest {

	private Symbol sModel;
	private List<Stroke> sTranscription;
	
	public Symbol getsModel() {
		return sModel;
	}
	public void setsModel(Symbol sModel) {
		this.sModel = sModel;
	}
	public List<Stroke> getsTranscription() {
		return sTranscription;
	}
	public void setsTranscription(List<Stroke> sTranscription) {
		this.sTranscription = sTranscription;
	}
	
}
