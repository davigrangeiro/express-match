package br.usp.ime.escience.expressmatch.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "symbol_class", catalog = "expressMatch")
public class SymbolClass implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String label;
	private BigDecimal mean;
	private BigDecimal sd;
	private Long instanceSize;
	private Date insertDate;
	
	public SymbolClass(Long id, String label, BigDecimal mean, BigDecimal sd,
			Long instanceSize, Date insertDate) {
		super();
		this.id = id;
		this.label = label;
		this.mean = mean;
		this.sd = sd;
		this.instanceSize = instanceSize;
		this.insertDate = insertDate;
	}

	public SymbolClass() {
		super();
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

	@Column(name = "label", nullable = false, length = 100)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Column(name = "mean", nullable = false)
	public BigDecimal getMean() {
		return mean;
	}

	public void setMean(BigDecimal mean) {
		this.mean = mean;
	}

	@Column(name = "sd", nullable = false)
	public BigDecimal getSd() {
		return sd;
	}

	public void setSd(BigDecimal sd) {
		this.sd = sd;
	}

	@Column(name = "instance_size", nullable = false)
	public Long getInstanceSize() {
		return instanceSize;
	}

	public void setInstanceSize(Long instanceSize) {
		this.instanceSize = instanceSize;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((insertDate == null) ? 0 : insertDate.hashCode());
		result = prime * result
				+ ((instanceSize == null) ? 0 : instanceSize.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((mean == null) ? 0 : mean.hashCode());
		result = prime * result + ((sd == null) ? 0 : sd.hashCode());
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
		SymbolClass other = (SymbolClass) obj;
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
		if (instanceSize == null) {
			if (other.instanceSize != null)
				return false;
		} else if (!instanceSize.equals(other.instanceSize))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (mean == null) {
			if (other.mean != null)
				return false;
		} else if (!mean.equals(other.mean))
			return false;
		if (sd == null) {
			if (other.sd != null)
				return false;
		} else if (!sd.equals(other.sd))
			return false;
		return true;
	}
	
}
