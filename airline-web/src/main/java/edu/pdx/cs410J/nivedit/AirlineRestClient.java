package edu.pdx.cs410J.nivedit;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send key/value pairs.
 */
public class AirlineRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";

    private final String url;


    /**
     * Creates a client to the airline REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public AirlineRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }

    /**
     * Returns the value for the given key
     */
    public String getValue(String airlineName, String source, String destination) throws IOException {
      Response response = get(this.url, "name", airlineName, "src", source, "dest", destination);
      throwExceptionIfNotOkayHttpStatus(response);
      String content = response.getContent();
      System.out.println(content);
      return content;
    }

    @VisibleForTesting
    Response postToMyURL(String... keysAndValues) throws IOException {
      return post(this.url, keysAndValues);
    }

    public void removeAllMappings() throws IOException {
      Response response = delete(this.url);
      throwExceptionIfNotOkayHttpStatus(response);
    }

    private Response throwExceptionIfNotOkayHttpStatus(Response response) {
      int code = response.getCode();
      if (code != HTTP_OK) {
        throw new AppointmentBookRestException(code);
      }
      return response;
    }

    public void addAirline(Airline airline, Flight flight) throws IOException {
        Response response = postToMyURL("name",airline.getName(),
                "flightNumber", Integer.toString(flight.getNumber()),
                "src", flight.getSource(),
                "departTime", flight.getDepartureString().toLowerCase(),
                "dest",flight.getDestination(),
                "arriveTime", flight.getArrivalString().toLowerCase());
        throwExceptionIfNotOkayHttpStatus(response);
    }

    private class AppointmentBookRestException extends RuntimeException {
      public AppointmentBookRestException(int httpStatusCode) {
        super("Got an HTTP Status Code of " + httpStatusCode);
      }
    }
}
