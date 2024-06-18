package oracle_project.warranty_claims;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import oracle_project.warranty_claims.WarrantyClaims.Status;

public class DatabaseHandler {

	Connection c = null;
	LoadProperties p;
	String dbDriver, dbConnURL, dbUser, dbPass;

	public DatabaseHandler() {
		p = new LoadProperties();
		dbDriver = p.getDBDriver();
		dbConnURL = p.getDBconnURL();
		dbUser = p.getDBUser();
		dbPass = p.getDBPass();
	}

	public void connectDB() throws Exception {

		Class.forName(dbDriver);
		c = DriverManager.getConnection(dbConnURL, dbUser, dbPass);
		System.out.println("Connected to database");
	}

	public void insertClaim(WarrantyClaims claim) throws Exception {

		String selectQuery = "SELECT CLAIM_ID FROM claim WHERE CLAIM_DATE = ? AND CUSTOMER_ID = ? AND PRODUCT_ID = ?";

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate claimDate = claim.getClaim().getClaimDate();
		String formattedDate = claimDate.format(formatter);
		Date sqlDate = Date.valueOf(formattedDate);

		try (PreparedStatement selectStatement = c.prepareStatement(selectQuery);
				PreparedStatement insertStatement = c.prepareStatement("INSERT INTO claim ("
						+ "CLAIM_ID, CUSTOMER_ID, CUSTOMER_FIRSTNAME, CUSTOMER_LASTNAME, CUSTOMER_EMAIL, "
						+ "PRODUCT_ID, PRODUCT_NAME, SERIAL_NUMBER, WARRANTY_NUMBER, COUNTRY_CODE, "
						+ "COUNTRY_REGION, CLAIM_DATE, STATUS, SUBJECT, SUMMARY) "
						+ "VALUES (CLAIM_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
			selectStatement.setDate(1, sqlDate);
			selectStatement.setInt(2, claim.getClaim().getCustomerId());
			selectStatement.setInt(3, claim.getClaim().getProductId());

			ResultSet resultSet = selectStatement.executeQuery();

			if (!resultSet.next()) { // No duplicate found, so insert the claim
				insertStatement.setInt(1, claim.getClaim().getCustomerId());
				insertStatement.setString(2, claim.getClaim().getCustomerFirstName());
				insertStatement.setString(3, claim.getClaim().getCustomerLastName());
				insertStatement.setString(4, claim.getClaim().getCustomerEmail());
				insertStatement.setInt(5, claim.getClaim().getProductId());
				insertStatement.setString(6, claim.getClaim().getProductName());
				insertStatement.setString(7, claim.getClaim().getSerialNumber());
				insertStatement.setInt(8, claim.getWarranty().getWarrantyNumber());
				insertStatement.setString(9, claim.getCountry().getCca2());
				insertStatement.setString(10, claim.getCountry().getRegion());
				insertStatement.setDate(11, sqlDate); // Set the formatted SQL date
				insertStatement.setString(12, claim.getStat().toString());
				insertStatement.setString(13, claim.getClaim().getSubject());
				insertStatement.setString(14, claim.getClaim().getSummary());

				insertStatement.executeUpdate();
			} else {
				// Handle duplicate claim insertion (optional)
				System.out.println("Duplicate claim found. Skipping insertion.");
			}
		}
	}

	public List<Claims> getValidWarrantyClaims(List<Claims> claims) {

		List<Claims> validClaims = claims.stream().filter(claim -> {

			try {
				return isValidClaim(claim);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;

		}).collect(Collectors.toList());

		return validClaims;
	}

	public List<Claims> getInvalidWarrantyClaims(List<Claims> claims) {

		List<Claims> invalidClaims = claims.stream().filter(claim -> {

			try {
				return !isValidClaim(claim);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;

		}).collect(Collectors.toList());

		return invalidClaims;
	}

	private boolean isValidClaim(Claims claim) throws SQLException {
		return checkWarrantyTable(claim.getProductId(), claim.getSerialNumber());
	}

	public boolean checkWarrantyTable(int productId, String serialNumber) {
		String findQuery = "SELECT EXPIRY_DATE FROM WARRANTY WHERE PRODUCT_ID = ? AND SERIAL_NUMBER = ?";

		try (PreparedStatement ps = c.prepareStatement(findQuery)) {
			ps.setInt(1, productId);
			ps.setString(2, serialNumber);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					LocalDate expDate = rs.getDate("EXPIRY_DATE").toLocalDate();
					// If the warranty has expired, return true
					return expDate.isBefore(LocalDate.now());
				} else {
					// No such warranty record found
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Warranty getRespectiveWarranty(int productId, String serialNumber) {
		String findQuery = "SELECT PRODUCT_ID, SERIAL_NUMBER, WARRANTY_NUMBER, EXPIRY_DATE, DATE_OPENED FROM WARRANTY WHERE PRODUCT_ID = ? AND SERIAL_NUMBER = ?";

		try (PreparedStatement ps = c.prepareStatement(findQuery)) {
			ps.setInt(1, productId);
			ps.setString(2, serialNumber);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {

					Warranty w = new Warranty();

					w.setExpiryDate(toLocalDate(rs.getDate("EXPIRY_DATE")));
					w.setOpenDate(toLocalDate(rs.getDate("DATE_OPENED")));
					w.setProductId(rs.getInt("PRODUCT_ID"));
					w.setSerialNumber(rs.getString("SERIAL_NUMBER"));
					w.setWarrantyNumber(rs.getInt("WARRANTY_NUMBER"));

					return w;
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private LocalDate toLocalDate(Date date) {
		// TODO Auto-generated method stub
		return date.toLocalDate();
	}

	public Status validateStatus(LocalDate claimDate) {
		LocalDate currentDate = LocalDate.now();
		if (claimDate.isBefore(currentDate)) {
			return Status.NEW;
		} else {
			return Status.EXPIRED;
		}
	}

}
