package oracle_project.warranty_claims;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.jackson.JacksonFeature;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RESTHandling {

	private String endpoint;

	public RESTHandling(String endpoint) throws Exception {
		if (endpoint != null)
			this.endpoint = endpoint;
		else
			throw new Exception("Error at setting endpoint");
	}

	public Country getCountry() {
		Client client = ClientBuilder.newClient();
		client.register(JacksonFeature.class);

		try {
			String response = client.target(endpoint).request(MediaType.APPLICATION_JSON).get(String.class);

			ObjectMapper mapper = new ObjectMapper();
			Country country = mapper.readValue(response, Country.class);

			return country;
		} catch (Exception e) {
			// Handle exceptions
			e.printStackTrace();
			return null;
		} finally {
			client.close();
		}
	}

	@Override
	public String toString() {
		return "RESTHandling [endpoint=" + endpoint + "]";
	}

}
