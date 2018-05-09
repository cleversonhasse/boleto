package com.contaazul.boleto.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import com.contaazul.boleto.controller.BankSlipController;
import com.contaazul.boleto.model.BankSlip;
import com.contaazul.boleto.model.BankSlipStatus;
import com.contaazul.boleto.util.BoletoConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *	Entidade armazena as informações da requisição POST do bankslip do controlador {@link BankSlipController}
 * 
 * @author Cléverson Hasse
 * @version 1.0.0
 * 
 */

public class BankSlipRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("total_in_cents")
	@NotNull(message = BoletoConstants.ERROR_MSG_TOTAL_IN_CENTS_NOT_NULL)
	@Positive(message = BoletoConstants.ERROR_MSG_TOTAL_IN_CENTS_POSITIVE_NUMBER)
    private int totalInCents;
	
    @JsonProperty("due_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = BoletoConstants.ERROR_MSG_DUE_DATE_NOT_NULL)
    private Date dueDate;
    
    @JsonProperty("customer")
    @NotEmpty(message = BoletoConstants.ERROR_MSG_CUSTOMER_NOT_NULL)
    @Length(max = 256, message = BoletoConstants.ERROR_MSG_CUSTOMER_GREATER_THAN)
    private String customer;
    
    @JsonProperty("status")
    @NotNull(message = BoletoConstants.ERROR_MSG_STATUS_NOT_NULL)
    private BankSlipStatus status;

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

    public BankSlipStatus getStatus() {
        return status;
    }

    public void setStatus(BankSlipStatus status) {
        this.status = status;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public BankSlip createBankSlip() {
        BankSlip bankSlip = new BankSlip();
        bankSlip.setId(UUID.randomUUID());
        bankSlip.setDueDate(dueDate);
        bankSlip.setTotalInCents(totalInCents);
        bankSlip.setCustomer(customer);
        bankSlip.setStatus(status);
        return bankSlip;
    }
}
