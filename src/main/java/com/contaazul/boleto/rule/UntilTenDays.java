package com.contaazul.boleto.rule;

public class UntilTenDays implements Tax {

	@Override
	public double calculateTax(int totalInCents) {
		return totalInCents * 0.005;
	}

	
}
