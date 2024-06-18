package oracle_project.warranty_claims;

import java.util.ArrayList;
import java.util.List;

public class App {
	public static void main(String[] args) throws Exception {

		LogClass log = new LogClass();

		// Receiving List from CSV file
		List<Claims> claims = new ArrayList<>();
		claims = new CSVHandler().readCSV();
		
		Claims ch = new Claims();

		// Get invalid format claims
		List<Claims> invalidFormatClaims = ch.getInvalidFormatClaims(claims);

		// Log Invalid Format Claims
		if (!invalidFormatClaims.isEmpty()) {
			invalidFormatClaims.forEach(claim -> log.logInvalidFormatClaims(claim));
		} else {
			System.out.println("No invalid format claims to log.");
		}
		
		// Get valid format claims
		List<Claims> validFormatClaims = ch.getValidFormatClaims(claims);
		DatabaseHandler db = new DatabaseHandler();
		db.connectDB();

		// Get invalid warranty claims
		List<Claims> invalidWarrantyClaims = db.getInvalidWarrantyClaims(validFormatClaims);

		//System.out.println("\nGoing to log invalid warranty claims\n");
		if (!invalidWarrantyClaims.isEmpty()) {
			invalidWarrantyClaims.forEach(claim -> log.logInvalidWarrantyClaims(claim));
		} else {
			System.out.println("No invalid warranty claims to log.");
		}
		
		
		// Get valid  warranty claims
		List<Claims> validWarrantyClaims = db.getValidWarrantyClaims(validFormatClaims);

		if (validWarrantyClaims.size() == 0 || validWarrantyClaims == null) {
			System.out.println("No valid claims");
		} else {
			LoadProperties p = new LoadProperties();
			RESTHandling rs = new RESTHandling(p.getRESTURL());

			System.out.println("Going to insert warranty claims into DB");
			// Augment claims
			validWarrantyClaims.forEach(claim -> {
				WarrantyClaims wc = new WarrantyClaims();

				wc.setClaim(claim);
				wc.setWarranty(db.getRespectiveWarranty(claim.getProductId(), claim.getSerialNumber()));
				wc.setCountry(rs.getCountry());
				wc.setStat(db.validateStatus(wc.claim.getClaimDate()));
				System.out.println("Inserting record");
				try {
					db.insertClaim(wc);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}

		System.out.println("End");

	}
}
