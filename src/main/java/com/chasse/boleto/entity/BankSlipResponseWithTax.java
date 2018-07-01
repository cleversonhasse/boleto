package com.chasse.boleto.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.chasse.boleto.controller.BankSlipController;
import com.chasse.boleto.model.BankSlipStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 *	Entidade armazena o id e o status do bankslip do controlador {@link BankSlipController}
 * 
 * @author Cléverson Hasse
 * @version 1.0.0
 * 
 */

@JsonPropertyOrder({"id", "dueDate", "totalInCents", "customer", "status", "fine"})
public class BankSlipResponseWithTax implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID id;
	
	private Date dueDate;
	
	private int totalInCents;
	
	private String customer;
	
	private BankSlipStatus status;

	private int fine;
	
	/**
	 * Método Construtor passando todos os parâmetros
	 * 
	 * @param id
	 * @param dueDate
	 * @param total_in_cents
	 * @param customer
	 * @param status
	 */
	public BankSlipResponseWithTax(UUID id, Date dueDate, int totalInCents, String customer, BankSlipStatus status, int fine) {
		super();
		this.id = id;
		this.dueDate = dueDate;
		this.totalInCents = totalInCents;
		this.customer = customer;
		this.status = status;
		this.fine = fine;
	}
	
	/**
	 * Método Construtor sem parâmetros
	 * 
	 */
	public BankSlipResponseWithTax() {
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

	public int getFine() {
		return fine;
	}

	public void setFine(int fine) {
		this.fine = fine;
	}
	
}
