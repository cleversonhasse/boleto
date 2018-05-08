package com.contaazul.boleto.entity;

import java.io.Serializable;
import java.util.List;

import com.contaazul.boleto.controller.BankSlipController;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 *	Entidade contêm os parâmetros para resposta de erro do controlador {@link BankSlipController}
 * 
 * @author Cléverson Hasse
 * @version 1.0.0
 * 
 */

@JsonPropertyOrder({"error", "message", "errorList"})
public class BankSlipResponseWithError implements Serializable {

	private static final long serialVersionUID = 1L;

	private String error;
	
	private String message;
	
	private List<String> errorList;

	public BankSlipResponseWithError(String error, String message, List<String> errorList) {
		this.error = error;
		this.message = message;
		this.errorList = errorList;
	}
	
	public BankSlipResponseWithError(String error, String message) {
		this.error = error;
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	} 
	
}
