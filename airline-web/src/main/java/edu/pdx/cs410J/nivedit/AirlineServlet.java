package edu.pdx.cs410J.nivedit;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple key/value pairs.
 */
public class AirlineServlet extends HttpServlet {
  private final Map<String, Flight> data = new HashMap<>();

  /**
   * Handles an HTTP GET request from a client by writing the value of the key
   * specified in the "key" HTTP parameter to the HTTP response.  If the "key"
   * parameter is not specified, all of the key/value pairs are written to the
   * HTTP response.
   */
  @Override
  protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
  {
      response.setContentType( "text/plain" );
      String airlineName = getParameter("name", request);
      if(airlineName != null){
          PrintWriter pw1 = response.getWriter();
          pw1.println(airlineName);

          pw1.println();
          //writeValue(airlineName, response);
      } else {
          writeAllMappings(response);
      }

      /*String key = getParameter( "key", request );
      if (key != null) {
          writeValue(key, response);

      } else {
          writeAllMappings(response);
      }*/
  }

  /**
   * Handles an HTTP POST request by storing the key/value pair specified by the
   * "key" and "value" request parameters.  It writes the key/value pair to the
   * HTTP response.
   */
  @Override
  protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
  {
      response.setContentType( "text/plain" );

      String airlineName = getParameter("name", request);
      String flightNumberInString = getParameter("flightNumber", request);
      String sourceAirport = getParameter("src", request);
      String departureTimeInString = getParameter("departTime", request);
      String destinationAirport = getParameter("dest", request);
      String arrivalTimeInString = getParameter("arriveTime", request);
      int flightNumber = -1;
      Collection<Flight> flights = new ArrayList<>();
      Date departureTimeInDate = new Date();
      Date arrivalTimeInDate = new Date();
      String expectedDatePattern = "MM/dd/yyyy h:mm a";
      SimpleDateFormat formatter = new SimpleDateFormat(expectedDatePattern);
      formatter.setLenient(false);
      try {
          flightNumber = Integer.parseInt(flightNumberInString);
      } catch (Exception e){
          PrintWriter pw1 = response.getWriter();
          pw1.println("Flight Number not integer" + flightNumberInString);
          System.exit(1);
      }
      try {
          departureTimeInDate = formatter.parse(departureTimeInString);
      } catch (Exception e){
          System.err.println("Date not matching");
          System.exit(1);
      }
      try {
          arrivalTimeInDate = formatter.parse(arrivalTimeInString);
      } catch (Exception e){
          System.err.println("Date not matching");
          System.exit(1);
      }
      Airline airline = new Airline(airlineName, flights);
      Flight flight = new Flight(flightNumber, sourceAirport, departureTimeInDate, destinationAirport, arrivalTimeInDate);
      airline.addFlight(flight);
      this.data.put(airlineName,flight);

      PrintWriter pw = response.getWriter();
      pw.println();
      pw.println("Mapped the airline to flight");
      pw.println(airline.getName());
      pw.println(flight.getNumber());
      pw.println(flight.getSource());
      pw.println(flight.getDeparture());
      pw.println(flight.getDestination());
      pw.println(flight.getDeparture());
      pw.flush();

      response.setStatus( HttpServletResponse.SC_OK);

      /*String key = getParameter( "key", request );
      if (key == null) {
          missingRequiredParameter(response, "key");
          return;
      }

      String value = getParameter( "value", request );
      if ( value == null) {
          missingRequiredParameter( response, "value" );
          return;
      }

      this.data.put(key, value);
*/
  }

  /**
   * Handles an HTTP DELETE request by removing all key/value pairs.  This
   * behavior is exposed for testing purposes only.  It's probably not
   * something that you'd want a real application to expose.
   */
  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/plain");

      this.data.clear();

      PrintWriter pw = response.getWriter();
      //pw.println(Messages.allMappingsDeleted());
      pw.flush();

      response.setStatus(HttpServletResponse.SC_OK);

  }

  /**
   * Writes an error message about a missing parameter to the HTTP response.
   *
   * The text of the error message is created by {@link //Messages#missingRequiredParameter(String)}
   */
  private void missingRequiredParameter( HttpServletResponse response, String parameterName )
      throws IOException
  {
      //String message = Messages.missingRequiredParameter(parameterName);
      //response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
  }

  /**
   * Writes the value of the given key to the HTTP response.
   *
   * The text of the message is formatted with
   * {@link //Messages#formatKeyValuePair(String, String)}
   */
  private void writeValue( String key, HttpServletResponse response ) throws IOException {
      Flight value = this.data.get(key);

      PrintWriter pw = response.getWriter();
      //pw.println(Messages.formatKeyValuePair(key, value));

      pw.flush();

      response.setStatus( HttpServletResponse.SC_OK );
  }

  /**
   * Writes all of the key/value pairs to the HTTP response.
   *
   * The text of the message is formatted with
   * {@link //Messages#formatKeyValuePair(String, String)}
   */
  private void writeAllMappings( HttpServletResponse response ) throws IOException {
      PrintWriter pw = response.getWriter();
      //Messages.formatKeyValueMap(pw, data);

      pw.flush();

      response.setStatus( HttpServletResponse.SC_OK );
  }

  /**
   * Returns the value of the HTTP request parameter with the given name.
   *
   * @return <code>null</code> if the value of the parameter is
   *         <code>null</code> or is the empty string
   */
  private String getParameter(String name, HttpServletRequest request) {
    String value = request.getParameter(name);
    if (value == null || "".equals(value)) {
      return null;

    } else {
      return value;
    }
  }

  @VisibleForTesting
  void setValueForKey(String key, Flight value) {
      this.data.put(key, value);
  }

  @VisibleForTesting
  Flight getValueForKey(String key) {
      return this.data.get(key);
  }
}
