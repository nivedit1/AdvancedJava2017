package edu.pdx.cs410J.nivedit;

import com.gdevelop.gwt.syncrpc.SyncProxy;
import edu.pdx.cs410J.nivedit.client.Airline;
import edu.pdx.cs410J.nivedit.client.AirlineService;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class AirlineServiceSyncProxyIT extends HttpRequestHelper {

  private final int httpPort = Integer.getInteger("http.port", 8080);
  private String webAppUrl = "http://localhost:" + httpPort + "/airline";

  @Test
  public void gwtWebApplicationIsRunning() throws IOException {
    Response response = get(this.webAppUrl);
    assertEquals(200, response.getCode());
  }

  @Test
  public void canInvokeAirlineServiceWithGwtSyncProxy() {
    String moduleName = "airline";
    SyncProxy.setBaseURL(this.webAppUrl + "/" + moduleName + "/");

    AirlineService service = SyncProxy.createSync(AirlineService.class);
    Airline airline = service.getAirline("Air CS410J","PDX","HOU");
    assertEquals("Air CS410J", airline.getName());
    assertEquals(0, airline.getFlights().size());
  }

}
