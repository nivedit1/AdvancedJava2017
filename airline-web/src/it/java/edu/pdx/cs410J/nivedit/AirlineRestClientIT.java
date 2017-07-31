package edu.pdx.cs410J.nivedit;

import edu.pdx.cs410J.web.HttpRequestHelper.Response;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Integration test that tests the REST calls made by {@link AirlineRestClient}
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AirlineRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private AirlineRestClient newAirlineRestClient() {
    int port = Integer.parseInt(PORT);
    return new AirlineRestClient(HOSTNAME, port);
  }

  @Ignore
  @Test
  public void test0RemoveAllMappings() throws IOException {
    AirlineRestClient client = newAirlineRestClient();
    client.removeAllMappings();
  }

  @Ignore
  @Test
  public void test1EmptyServerContainsNoMappings() throws IOException {
    AirlineRestClient client = newAirlineRestClient();
    //Map<String, String> allKeysAndValues = client.getAllKeysAndValues();
    //assertThat(allKeysAndValues.size(), equalTo(0));
  }

  @Ignore
  @Test
  public void test2AddOneKeyValuePair() throws IOException {
    AirlineRestClient client = newAirlineRestClient();
    String testKey = "TEST KEY";
    String testValue = "TEST VALUE";
    //client.addKeyValuePair(testKey, testValue);

    //String value = client.getValue(testKey);
    //assertThat(value, equalTo(testValue));
  }

  @Ignore
  @Test
  public void test4MissingRequiredParameterReturnsPreconditionFailed() throws IOException {
    AirlineRestClient client = newAirlineRestClient();
    Response response = client.postToMyURL();
    //assertThat(response.getContent(), containsString(Messages.missingRequiredParameter("key")));
    assertThat(response.getCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));
  }
}
