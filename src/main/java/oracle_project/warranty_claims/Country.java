package oracle_project.warranty_claims;

public class Country {
	private String cca2;
	private String region;

	// Getters and Setters
	public String getCca2() {
		return cca2;
	}

	public void setCca2(String cca2) {
		this.cca2 = cca2;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return "Country [cca2=" + cca2 + ", region=" + region + "]";
	}
}
