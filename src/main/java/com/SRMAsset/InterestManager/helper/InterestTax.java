package com.SRMAsset.InterestManager.helper;

public enum InterestTax {
	A(0.10), B(0.20), C(null);

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
