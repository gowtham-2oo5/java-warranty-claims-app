package oracle_project.warranty_claims;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Claims {

	private int customerId, productId;
	private String customerFirstName, customerLastName, customerEmail, productName, serialNumber, subject, summary;
	private LocalDate claimDate;

	public Claims() {}

	protected Claims(int customerId, String customerFirstName, String customerLastName, String customerEmail,
			int productId, String productName, String serialNumber, LocalDate claimDate, String subject,
			String summary) {
		this.claimDate = claimDate;
		this.customerEmail = customerEmail;
		this.customerFirstName = customerFirstName;
		this.customerId = customerId;
		this.customerLastName = customerLastName;
		this.productId = productId;
		this.productName = productName;
		this.serialNumber = serialNumber;
		this.subject = subject;
		this.summary = summary;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public LocalDate getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(LocalDate claimDate) {
		this.claimDate = claimDate;
	}

	public Boolean isValidClaim(Claims claim) {
		// Check if any required fields are null or empty
		if (isEmpty(claim.customerFirstName) || isEmpty(claim.customerLastName) || isEmpty(claim.customerEmail)
				|| isEmpty(claim.productName) || isEmpty(claim.serialNumber) || isEmpty(claim.subject)
				|| isEmpty(claim.claimDate) || isEmpty(claim.summary)) {
			return false;
		}

		// Check if IDs are valid (assuming IDs should be positive)
		if (claim.customerId <= 0 || claim.productId <= 0) {
			return false;
		}

		return true;
	}

	private boolean isEmpty(LocalDate claimDate) {
		// TODO Auto-generated method stub
		return claimDate == null;
	}

	private boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}

	public List<Claims> getInvalidFormatClaims(List<Claims> claims) {
		System.out.println("Entered GET invalid format claims");
		List<Claims> invalidFormatClaims = claims.stream().filter(claim -> !isValidClaim(claim))
				.collect(Collectors.toList());

		return invalidFormatClaims;
	}

	public String toString() {
		String customer = String.format("Customer ID: %d\nCustomer Name: %s %s\nCustomer Email: %s\n", customerId,
				customerFirstName, customerLastName, customerEmail);
		String product = String.format("Product ID: %d\nProductName: %s\n", productId, productName);
		String claim = String.format("Serial number: %s\nSubject: %s\nSummary: %s\nDate claimed: %s", serialNumber,
				subject, summary, claimDate);
		return String.format("Customer Details:\n%s\n\nProduct Details:\n%s\nClaim Details:\n%s\nWarranty Details: s\n",
				customer, product, claim);
	}

	public List<Claims> getValidFormatClaims(List<Claims> claims) {
		
		List<Claims> invalidFormatClaims = claims.stream().filter(claim -> isValidClaim(claim))
				.collect(Collectors.toList());

		return invalidFormatClaims;
	}
}
