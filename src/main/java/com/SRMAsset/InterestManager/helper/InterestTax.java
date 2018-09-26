package com.SRMAsset.InterestManager.helper;

public enum InterestTax {
	A(null), B(0.10), C(0.20);

	private Double tax;

	private InterestTax(Double tax) {
		this.setTax(tax);
	}

	public Double getTax() {
		return tax;
	}

	private void setTax(Double tax) {
		this.tax = tax;
	}

}
