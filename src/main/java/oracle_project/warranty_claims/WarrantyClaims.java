package oracle_project.warranty_claims;

public class WarrantyClaims {
	
	public enum Status{
		NEW, EXPIRED
	};

	Claims claim;
	Warranty warranty;
	Country country;
	Status stat;

	public Claims getClaim() {
		return claim;
	}

	public void setClaim(Claims claim) {
		this.claim = claim;
	}

	public Warranty getWarranty() {
		return warranty;
	}

	public Status getStat() {
		return stat;
	}

	public void setStat(Status stat) {
		this.stat = stat;
	}

	public void setWarranty(Warranty warranty) {
		this.warranty = warranty;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	public void setWarrantyClaim(Claims claim, Warranty warranty, Country country) {
		setClaim(claim);
		setWarranty(warranty);
		setCountry(country);
	}

	@Override
	public String toString() {
		return "WarrantyClaims [claim=" + claim + ", warranty=" + warranty + ", country=" + country + "]";
	}
	
	

}
