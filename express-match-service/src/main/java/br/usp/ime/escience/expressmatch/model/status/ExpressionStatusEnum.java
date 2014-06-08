package br.usp.ime.escience.expressmatch.model.status;



public enum ExpressionStatusEnum {

	EXPRESSION_TRANSCRIBED(1),
	EXPRESSION_EVALUATED(2),
	EXPRESSION_VALIDATED(3),
	EXPRESSION_REJECTED(4);
	
	
	private final Integer value;

	
	private ExpressionStatusEnum(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	
}
