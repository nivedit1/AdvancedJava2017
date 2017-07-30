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
  private final Map<String, Collection<Flight>> data = new HashMap<>();
  private Collection<Flight> flights = new ArrayList<>();

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
      String sourceAirport = getParameter("src", request);
      String destinationAirport = getParameter("dest", request);
      PrintWriter pw = response.getWriter();
      if(airlineName != null){
          if(data.containsKey(airlineName)){
              Collection<Flight> flightsInGet = data.get(airlineName);
              for(Flight flight:flightsInGet){
                  if(flight.getSource().equals(sourceAirport) && flight.getDestination().equals(destinationAirport)){
                      pw.println(flight);
                  }
                  else {
                      continue;
                  }
              }
          }
          else {
              pw.println(this.flights);
          }
      } else {
          writeAllMappings(response);
      }
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
      this.data.put(airlineName,flights);

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
      Collection<Flight> value = this.data.get(key);

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
  void setValueForKey(String key, Collection<Flight> value) {
      this.data.put(key, value);
  }

  @VisibleForTesting
  Collection <Flight> getValueForKey(String key) {
      return this.data.get(key);
  }
}
