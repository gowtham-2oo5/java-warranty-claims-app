package oracle_project.warranty_claims;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class LoadProperties {

	private Properties p = new Properties();
	private File f = null;

	public LoadProperties() {
		try {

			f = new File(
					"D:\\KLU\\23-24 Summer Accl\\EP\\eclipse-programs\\warranty-claims\\src\\main\\java\\configs\\config.properties");
			FileInputStream read = new FileInputStream(f);
			this.p.load(read);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDBconnURL() {
		return p.getProperty("db.connUrl");
	}

	public String getDBDriver() {
		return p.getProperty("db.driver");
	}

	public String getDBUser() {
		return p.getProperty("db.user");
	}

	public String getDBPass() {
		return p.getProperty("db.pass");
	}

	public String getRESTURL() {
		return p.getProperty("rest.endpoint");
	}

	public String getCSVpath() {
		return p.getProperty("csv.file.path");
	}

	public String getCSVDataFormat() {
		return p.getProperty("csv.file.data.format");
	}

	public String getInvalidFormatLogFile() {
		return p.getProperty("invalid.format.out");
	}

	public String getInvalidWarrantyLogFile() {
		return p.getProperty("invalid.warranty.out");
	}
}
