

import it.geosolutions.geonetwork.GNClient;

import org.junit.Test;

public class GNClientTest {
	
    private static final String gnServiceURL = "http://localhost:8080/geonetwork";
    private static final String gnUsername = "admin";
    private static final String gnPassword = "admin";

	@Test
	public void testCreateName() {

	    // Create a GeoNetwork client pointing to the GeoNetwork service
	    GNClient client = new GNClient(gnServiceURL);

	    // Perform a login into GN
	    boolean logged = client.login(gnUsername, gnPassword);
	}

}
