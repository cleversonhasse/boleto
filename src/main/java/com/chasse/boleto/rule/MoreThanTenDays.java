package com.chasse.boleto.rule;

public class MoreThanTenDays implements Tax {

	@Override
	public double calculateTax(int totalInCents) {
		return totalInCents * 0.01;
	}

	
}
