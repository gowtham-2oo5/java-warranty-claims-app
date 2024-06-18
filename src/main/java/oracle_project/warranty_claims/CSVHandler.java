package oracle_project.warranty_claims;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.text.MessageFormat;
import java.text.ParseException;

public class CSVHandler {

    File f = null;
    LoadProperties p = null;
    MessageFormat csvDataFormat = null;

    public CSVHandler() throws Exception {
        p = new LoadProperties();
        f = new File(p.getCSVpath());
        csvDataFormat = new MessageFormat(p.getCSVDataFormat());
    }

    public List<Claims> readCSV() {
        List<Claims> claimsList = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            br.readLine(); // Skip the header

            while ((line = br.readLine()) != null) {
                try {
                    // Preprocess the line to handle missing values and correct date format
                    String[] parts = line.split(",", -1);
                    if (parts.length != 10) {
                        throw new ParseException("Incorrect number of fields", 0);
                    }

                    // Replace null or empty strings with default values for mandatory fields
                    int customerId = parts[0] == null || parts[0].isEmpty() ? 0 : Integer.parseInt(parts[0]);
                    String customerFirstName = parts[1] == null ? "" : parts[1];
                    String customerLastName = parts[2] == null ? "" : parts[2];
                    String customerEmail = parts[3] == null ? "" : parts[3];
                    int productId = parts[4] == null || parts[4].isEmpty() ? 0 : Integer.parseInt(parts[4]);
                    String productName = parts[5] == null ? "" : parts[5];
                    String serialNumber = parts[6] == null ? "" : parts[6];
                    
                    LocalDate claimDate = null;
                    if (parts[7] != null && !parts[7].isEmpty()) {
                        try {
                            claimDate = LocalDate.parse(parts[7], dateFormatter);
                        } catch (DateTimeParseException e) {
                            System.err.println("Invalid date format: " + parts[7] + " in line: " + line);
                        }
                    }
                    
                    String subject = parts[8] == null ? "" : parts[8];
                    String summary = parts[9] == null ? "" : parts[9];

                    // Create a new ClaimsHandler object and add it to the list
                    Claims claim = new Claims(
                            customerId, 
                            customerFirstName, 
                            customerLastName, 
                            customerEmail, 
                            productId, 
                            productName, 
                            serialNumber, 
                            claimDate, 
                            subject, 
                            summary
                    );
                    claimsList.add(claim);

                } catch (ParseException | NumberFormatException e) {
                    System.err.println("Parse error: " + e.getMessage() + " in line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return claimsList;
    }
}
