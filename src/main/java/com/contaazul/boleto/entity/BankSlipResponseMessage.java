package com.contaazul.boleto.entity;

import java.io.Serializable;

import com.contaazul.boleto.controller.BankSlipController;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 *	Entidade armazena o status e a mensagem do bankslip do controlador {@link BankSlipController}
 * 
 * @author Cléverson Hasse
 * @version 1.0.0
 * 
 */

@JsonPropertyOrder({"status", "message"})
public class BankSlipResponseMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String status;
	
	private String message;
	
	public BankSlipResponseMessage(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
