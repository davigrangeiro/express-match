package br.usp.ime.escience.expressmatch.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "symbol_match", catalog = "expressMatch")
public class SymbolMatch implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private ExpressionMatch expressionMatch;
	private Symbol symbol;
	private Symbol modelSymbol;
	private Boolean sameReference;
	private Boolean sameClass;
	
	public SymbolMatch() {
		super();
	}

	public SymbolMatch(Long id, ExpressionMatch expressionMatch, Symbol symbol,
			Symbol modelSymbol, Boolean sameReference, Boolean sameClass) {
		super();
		this.id = id;
		this.expressionMatch = expressionMatch;
		this.symbol = symbol;
		this.modelSymbol = modelSymbol;
		this.sameReference = sameReference;
		this.sameClass = sameClass;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expression_match_id", nullable = false)
	public ExpressionMatch getExpressionMatch() {
		return expressionMatch;
	}

	public void setExpressionMatch(ExpressionMatch expressionMatch) {
		this.expressionMatch = expressionMatch;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "symbol_id", nullable = false)
	public Symbol getSymbol() {
		return symbol;
	}

	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "model_symbol_id", nullable = false)
	public Symbol getModelSymbol() {
		return modelSymbol;
	}

	public void setModelSymbol(Symbol modelSymbol) {
		this.modelSymbol = modelSymbol;
	}

	@Column(name = "same_reference", nullable = false)
	public Boolean getSameReference() {
		return sameReference;
	}

	public void setSameReference(Boolean sameReference) {
		this.sameReference = sameReference;
	}

	@Column(name = "same_class", nullable = false)
	public Boolean getSameClass() {
		return sameClass;
	}

	public void setSameClass(Boolean sameClass) {
		this.sameClass = sameClass;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((expressionMatch == null) ? 0 : expressionMatch.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((modelSymbol == null) ? 0 : modelSymbol.hashCode());
		result = prime * result
				+ ((sameClass == null) ? 0 : sameClass.hashCode());
		result = prime * result
				+ ((sameReference == null) ? 0 : sameReference.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SymbolMatch other = (SymbolMatch) obj;
		if (expressionMatch == null) {
			if (other.expressionMatch != null)
				return false;
		} else if (!expressionMatch.equals(other.expressionMatch))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (modelSymbol == null) {
			if (other.modelSymbol != null)
				return false;
		} else if (!modelSymbol.equals(other.modelSymbol))
			return false;
		if (sameClass == null) {
			if (other.sameClass != null)
				return false;
		} else if (!sameClass.equals(other.sameClass))
			return false;
		if (sameReference == null) {
			if (other.sameReference != null)
				return false;
		} else if (!sameReference.equals(other.sameReference))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}
	
	
	
}
