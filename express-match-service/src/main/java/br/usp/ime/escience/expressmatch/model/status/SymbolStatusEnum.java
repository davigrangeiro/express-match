package br.usp.ime.escience.expressmatch.model.status;

public enum SymbolStatusEnum {

	SYMBOL_TRANSCRIBED(1),
	SYMBOL_EVALUATED(2),
	SYMBOL_VALIDATED(3),
	SYMBOL_REJECTED(4);
	
	private final Integer value;

	
	private SymbolStatusEnum(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

}
