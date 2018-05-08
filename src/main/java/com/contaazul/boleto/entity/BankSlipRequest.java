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
	@NotNull(message = "Total in cents must not be null")
	@Positive(message = "Total in cents must be a positive number, greater than 0")
    private int totalInCents;
	
    @JsonProperty("due_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Due date must not be null, insert some date in format yyyy-MM-dd")
    private Date dueDate;
    
    @JsonProperty("customer")
    @NotEmpty(message = "Customer name must not be null nor empty")
    @Length(max = 256, message = "Customer name must not be greater than 256 characters")
    private String customer;
    
    @JsonProperty("status")
    @NotNull(message = "Status must not be null, choice between PENDING, PAID or CANCELED")
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
