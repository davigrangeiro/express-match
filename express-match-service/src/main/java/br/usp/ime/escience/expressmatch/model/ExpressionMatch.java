package br.usp.ime.escience.expressmatch.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "expression_match", catalog = "expressMatch")
public class ExpressionMatch implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Expression expression;
	private ExpressionType expressionType;
	private UserParameter userParameter;
	private Boolean correctMatching;
	private Date insertDate;
	
	private List<SymbolMatch> symbolMatching;
	
	public ExpressionMatch() {
		super();
	}

	public ExpressionMatch(Long id, Expression expression,
			ExpressionType expressionType, UserParameter userParameter,
			Boolean correctMatching, Date insertDate) {
		super();
		this.id = id;
		this.expression = expression;
		this.expressionType = expressionType;
		this.userParameter = userParameter;
		this.correctMatching = correctMatching;
		this.insertDate = insertDate;
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
	@JoinColumn(name = "expression_id", nullable = false)
	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expression_type_id", nullable = false)
	public ExpressionType getExpressionType() {
		return expressionType;
	}

	public void setExpressionType(ExpressionType expressionType) {
		this.expressionType = expressionType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_parameter_id", nullable = false)
	public UserParameter getUserParameter() {
		return userParameter;
	}

	public void setUserParameter(UserParameter userParameter) {
		this.userParameter = userParameter;
	}

	@Column(name = "correct_matching", nullable = false)
	public Boolean getCorrectMatching() {
		return correctMatching;
	}

	public void setCorrectMatching(Boolean correctMatching) {
		this.correctMatching = correctMatching;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "expressionMatch")
	public List<SymbolMatch> getSymbolMatching() {
		return this.symbolMatching;
	}
	
	public void setSymbolMatching(List<SymbolMatch> symbolMatching) {
		this.symbolMatching = symbolMatching;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "insert_date", nullable = false)
	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((correctMatching == null) ? 0 : correctMatching.hashCode());
		result = prime * result
				+ ((expression == null) ? 0 : expression.hashCode());
		result = prime * result
				+ ((expressionType == null) ? 0 : expressionType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((insertDate == null) ? 0 : insertDate.hashCode());
		result = prime * result
				+ ((userParameter == null) ? 0 : userParameter.hashCode());
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
		ExpressionMatch other = (ExpressionMatch) obj;
		if (correctMatching == null) {
			if (other.correctMatching != null)
				return false;
		} else if (!correctMatching.equals(other.correctMatching))
			return false;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		if (expressionType == null) {
			if (other.expressionType != null)
				return false;
		} else if (!expressionType.equals(other.expressionType))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (insertDate == null) {
			if (other.insertDate != null)
				return false;
		} else if (!insertDate.equals(other.insertDate))
			return false;
		if (userParameter == null) {
			if (other.userParameter != null)
				return false;
		} else if (!userParameter.equals(other.userParameter))
			return false;
		return true;
	}
	
	
}
