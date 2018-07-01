package com.chasse.boleto.rule;

public class CalculateTaxes {

	public double calculate(int totalInCents, Tax tax) {
		return tax.calculateTax(totalInCents);
	}
	
}
