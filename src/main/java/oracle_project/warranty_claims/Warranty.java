package oracle_project.warranty_claims;

import java.time.LocalDate;

public class Warranty {
	
	private Integer productId,
					warrantyNumber;
	
	private String serialNumber;
	
	private LocalDate openDate,
					  expiryDate;
	
	private boolean isValidClaim;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getWarrantyNumber() {
		return warrantyNumber;
	}

	public void setWarrantyNumber(Integer warrantyNumber) {
		this.warrantyNumber = warrantyNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public LocalDate getOpenDate() {
		return openDate;
	}

	public void setOpenDate(LocalDate openDate) {
		this.openDate = openDate;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public boolean isValidClaim() {
		return isValidClaim;
	}

	public void setValidClaim(boolean isValidClaim) {
		this.isValidClaim = isValidClaim;
	}

	@Override
	public String toString() {
		return "Warranty [productId=" + productId + ", warrantyNumber=" + warrantyNumber + ", serialNumber="
				+ serialNumber + ", openDate=" + openDate + ", expiryDate=" + expiryDate + ", isValidClaim="
				+ isValidClaim + "]";
	}
	
	
	

}
