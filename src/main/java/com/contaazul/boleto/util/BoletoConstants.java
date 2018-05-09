package com.contaazul.boleto.util;

public final class BoletoConstants {

	public final static String ERROR_MSG_CUSTOMER_NOT_NULL = "Customer name must not be null nor empty";
	
	public final static String ERROR_MSG_CUSTOMER_GREATER_THAN = "Customer name must not be greater than 256 characters";
	
	public final static String ERROR_MSG_TOTAL_IN_CENTS_NOT_NULL = "Total in cents must not be null";
	
	public final static String ERROR_MSG_TOTAL_IN_CENTS_POSITIVE_NUMBER = "Total in cents must be a positive number, greater than 0";
	
	public final static String ERROR_MSG_DUE_DATE_NOT_NULL = "Due date must not be null, insert some date in format yyyy-MM-dd";
	
	public final static String ERROR_MSG_STATUS_NOT_NULL = "Status must not be null, choice between PENDING, PAID or CANCELED";
	
}
