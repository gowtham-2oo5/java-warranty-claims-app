package oracle_project.warranty_claims;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogClass {

	private static Logger log = Logger.getLogger(oracle_project.warranty_claims.App.class.getName());
	private static Logger logInvalidFormat = Logger.getLogger(oracle_project.warranty_claims.App.class.getName());
	private static Logger logInvalidWarranty = Logger.getLogger(oracle_project.warranty_claims.App.class.getName());

	static {

		try {
			LoadProperties p = new LoadProperties();

			FileHandler invalidFormatFileHandler = new FileHandler(p.getInvalidFormatLogFile());
			invalidFormatFileHandler.setFormatter(new SimpleFormatter());

			FileHandler invalidWarrantyFileHandler = new FileHandler(p.getInvalidWarrantyLogFile());
			invalidWarrantyFileHandler.setFormatter(new SimpleFormatter());

			logInvalidFormat.addHandler(invalidFormatFileHandler);
			logInvalidFormat.setUseParentHandlers(false);

			logInvalidWarranty.addHandler(invalidWarrantyFileHandler);
			logInvalidWarranty.setUseParentHandlers(false);

		} catch (Exception e) {
			log.log(Level.SEVERE, "Failed to initialize logger handler.", e);
		}

	}

	public boolean logInvalidFormatClaims(Claims claim) {
		System.out.println("\nGoing to log invalid format claims\n");
		logInvalidFormat.log(Level.INFO, claim.toString() + "\n");
		return true;
	}

	public void logInvalidWarrantyClaims(Claims claim) {
		logInvalidWarranty.log(Level.INFO, claim.toString());
	}

}
