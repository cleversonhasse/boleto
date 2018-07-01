package com.chasse.boleto.entity;

import java.io.Serializable;
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

@JsonPropertyOrder({"id", "name"})
public class BankSlipResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID id;
	
	private BankSlipStatus status;
	
	public BankSlipResponse(UUID id, BankSlipStatus status) {
		this.id = id;
		this.status = status;
	}
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public BankSlipStatus getStatus() {
		return status;
	}

	public void setStatus(BankSlipStatus status) {
		this.status = status;
	}
	
}
