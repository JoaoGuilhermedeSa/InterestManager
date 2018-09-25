package com.SRMAsset.InterestManager.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "Client")
public class Client implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7410909663208955395L;
	@Id
	private String id;
	@NotNull
	private String name;
	@NotNull
	private Double creditLimite;
	@NotNull
	private String risk;
	@JsonIgnore
	private Double interestRate;
	@JsonIgnore
	private Date modified;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getCreditLimite() {
		return creditLimite;
	}

	public void setCreditLimite(Double creditLimite) {
		this.creditLimite = creditLimite;
	}

	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

}
