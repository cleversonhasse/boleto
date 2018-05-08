package com.contaazul.boleto.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *	Classe de entidade representando a tabela BankSplit 
 * 
 * @author Cléverson Hasse
 * @version 1.0.0
 * 
 */

@Entity
@Table(name = "BANKSLIP")
public class BankSlip implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	@JsonProperty("id")
	private UUID id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "due_date")
	@JsonProperty("due_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dueDate;
	
	@Column(name = "total_in_cents")
	@JsonProperty("total_in_cents")
	private int totalInCents;
	
	@Column(name = "customer")
	@JsonProperty("customer")
	private String customer;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	@JsonProperty("status")
	private BankSlipStatus status;

	/**
	 * Método Construtor passando todos os parâmetros
	 * 
	 * @param id
	 * @param dueDate
	 * @param total_in_cents
	 * @param customer
	 * @param status
	 */
	public BankSlip(UUID id, Date dueDate, int totalInCents, String customer, BankSlipStatus status) {
		super();
		this.id = id;
		this.dueDate = dueDate;
		this.totalInCents = totalInCents;
		this.customer = customer;
		this.status = status;
	}
	
	/**
	 * Método Construtor sem parâmetros
	 * 
	 */
	public BankSlip() {
		super();
	}


	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getTotalInCents() {
		return totalInCents;
	}

	public void setTotalInCents(int totalInCents) {
		this.totalInCents = totalInCents;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public BankSlipStatus getStatus() {
		return status;
	}

	public void setStatus(BankSlipStatus status) {
		this.status = status;
	}

}
