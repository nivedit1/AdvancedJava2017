package edu.pdx.cs410J.nivedit;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AirportNames;

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
      /*String uri = request.getRequestURI();
      String lastPart = uri.substring(uri.lastIndexOf('/') + 1, uri.length());
      System.out.println(lastPart);*/
      String airlineName = getParameter("name", request);
      String sourceAirport = getParameter("src", request);
      String destinationAirport = getParameter("dest", request);
      PrettyPrinter prettyPrinter = new PrettyPrinter(response);
      PrintWriter pw = response.getWriter();
      if((airlineName == null && sourceAirport !=null && destinationAirport!=null)
              || (airlineName == null && sourceAirport !=null && destinationAirport == null)
              || (airlineName == null && sourceAirport ==null && destinationAirport !=null)){
          String message = "Airline Name not provided!";
          response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
          return;
      } else if(airlineName !=null && sourceAirport == null && destinationAirport !=null){
          String message = "Source Airport not provided!";
          response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
          return;
      } else if(airlineName !=null && sourceAirport !=null && destinationAirport == null) {
          String message = "Destination Airport not provided!";
          response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
          return;
      } else if (airlineName == null && sourceAirport == null && destinationAirport == null
               && this.data.isEmpty() == true){
          String message = "No airline found";
          pw.println(message);
          return;
      } else if(airlineName == null && sourceAirport == null && destinationAirport == null &&
              this.data.isEmpty() == false){
          for(Map.Entry<String, Collection<Flight>>entry: data.entrySet()){
              if(this.flights.equals(entry.getValue())){
                  airlineName = entry.getKey();
                  break;
              }
          }
          Airline airlineToPrint = new Airline(airlineName, this.flights);
          prettyPrinter.dump(airlineToPrint);
      } else if (airlineName !=null && sourceAirport == null && destinationAirport == null){
          if(this.data.containsKey(airlineName)){
              Airline airline = new Airline(airlineName, this.data.get(airlineName));
              prettyPrinter.dump(airline);
              return;
          } else {
              String message = "Airline not found!";
              pw.println(message);
              return;
          }
      }
      Collection<Flight> flightsToPrint = new ArrayList<>();
      String sourceAirportActual;
      String destinationAirportActual;
      if (sourceAirport.matches("^[a-zA-Z][a-zA-Z][a-zA-Z]") == true) {
          if(AirportNames.getName((sourceAirport).toUpperCase()) != null){
                  sourceAirportActual = sourceAirport.toUpperCase();
          }
          else {
              String message = "Not a valid Source Airport Code - " + sourceAirport;
              response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
              return;
          }
      } else {
          String message = "Source Airport Code is not a 3 letter code - " + sourceAirport;
          message = message + "\nAirport code should be of the format \"AAA\"";
          response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
          return;
      }
      if (destinationAirport.matches("^[a-zA-Z][a-zA-Z][a-zA-Z]") == true) {
          if(AirportNames.getName((destinationAirport).toUpperCase()) != null){
              destinationAirportActual = destinationAirport.toUpperCase();
          }
          else {
              String message = "Not a valid Destination Airport Code - " + destinationAirport;
              response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
              return;
          }
      } else {
          String message = "Destination Airport Code is not a 3 letter code - " + destinationAirport;
          message = message + "\nAirport code should be of the format \"AAA\"";
          response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
          return;
      }
      if(airlineName != null && sourceAirport !=null && destinationAirport !=null){
          if(data.containsKey(airlineName)){
              Collection<Flight> flightsInGet = data.get(airlineName);
              for(Flight flight:flightsInGet){
                  if(flight.getSource().equals(sourceAirport) && flight.getDestination().equals(destinationAirport)){
                      flightsToPrint.add(flight);
                  }
                  else {
                      continue;
                  }
              }
              if(flightsToPrint.size() > 0){
                  Airline airlineToPrint = new Airline(airlineName, flightsToPrint);
                  prettyPrinter.dump(airlineToPrint);
              } else {
                  String message = "Flights starting from " + sourceAirport + " landing in " + destinationAirport +
                          " not found !";
                  pw.println(message);
                  return;
              }
          } else {
              String message = "The airline that you're looking for is not found: "+ airlineName;
              pw.println(message);
              return;
          }
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
      String sourceAirportActual = "";
      String destinationAirportActual = "";
      Date departureTimeInDate = new Date();
      Date arrivalTimeInDate = new Date();
      String expectedDatePattern = "MM/dd/yyyy h:mm a";
      SimpleDateFormat formatter = new SimpleDateFormat(expectedDatePattern);
      formatter.setLenient(false);
      if(airlineName == null){
          missingRequiredParameter(response, "Airline Name");
          return;
      } else if(flightNumberInString == null){
          missingRequiredParameter(response, "Flight Number");
          return;
      } else if(sourceAirport == null){
          missingRequiredParameter(response, "Source Airport Code");
          return;
      } else if(departureTimeInString == null){
          missingRequiredParameter(response, "Departure Time");
          return;
      } else if(destinationAirport == null){
          missingRequiredParameter(response, "Destination Airport");
          return;
      } else if(arrivalTimeInString == null){
          missingRequiredParameter(response, "Arrival Time");
          return;
      }
      try {
          flightNumber = Integer.parseInt(flightNumberInString);
      } catch (Exception e){
          String message = "Flight Number is not integer: " + flightNumberInString;
          response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
          return;
      }
      if (sourceAirport.matches("^[a-zA-Z][a-zA-Z][a-zA-Z]") == true) {
          if(AirportNames.getName((sourceAirport).toUpperCase()) != null){
              sourceAirportActual = sourceAirport.toUpperCase();
          }
          else {
              String message = "Not a valid Source Airport Code - " + sourceAirport;
              response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
              return;
          }
      } else {
          String message = "Source Airport Code is not a 3 letter code - " + sourceAirport;
          message = message + "\nAirport code should be of the format \"AAA\"";
          response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
          return;
      }
      try {
          String [] dateSplit = departureTimeInString.split("\\s+");
          if(dateSplit[2].equals("am") || dateSplit[2].equals("pm")){
              departureTimeInDate = formatter.parse(departureTimeInString);
          }else {
              String message = "am/pm should not be capitalized in departure time - " + departureTimeInString;
              response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
              return;
          }

      } catch (Exception e){
          String message = "The Departure time does not match the format \"MM/DD/YY HH:MM\" - " + departureTimeInString;
          response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
          return;
      }
      if (destinationAirport.matches("^[a-zA-Z][a-zA-Z][a-zA-Z]") == true) {
          if(AirportNames.getName((destinationAirport).toUpperCase()) != null){
              destinationAirportActual = destinationAirport.toUpperCase();
          }
          else {
              String message = "Not a valid Destination Airport Code - " + destinationAirport;
              response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
              return;
          }
      } else {
          String message = "Destination Airport Code is not a 3 letter code - " + destinationAirport;
          message = message + "\nAirport code should be of the format \"AAA\"";
          response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
          return;
      }
      try {
          String [] dateSplit = arrivalTimeInString.split("\\s+");
          if(dateSplit[2].equals("am") || dateSplit[2].equals("pm")){
              arrivalTimeInDate = formatter.parse(arrivalTimeInString);
          }else {
              String message = "am/pm should not be capitalized in arrival time - " + arrivalTimeInString;
              response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
              return;
          }
      } catch (Exception e){
          String message = "The Arrival time does not match the format \"MM/DD/YY HH:MM\" - " + arrivalTimeInString;
          response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
          return;
      }
      Flight flight = new Flight(flightNumber, sourceAirport, departureTimeInDate, destinationAirport, arrivalTimeInDate);
      flights.add(flight);
      Airline airline = new Airline(airlineName, flights);
      airline.addFlight(flight);
      this.data.put(airlineName,flights);
      PrintWriter pw = response.getWriter();
      pw.println();
      pw.println("Mapped airline " + airlineName + " to flight " + flightNumber);
      pw.flush();
      response.setStatus( HttpServletResponse.SC_OK);
      return;
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
      String message = "Missing required parameter: " +parameterName ;
      response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
      return;
  }



  /**
   * Writes all of the key/value pairs to the HTTP response.
   *
   * The text of the message is formatted with
   * {@link //Messages#formatKeyValuePair(String, String)}
   */
  private void writeAllMappings( HttpServletResponse response ) throws IOException {
      PrintWriter pw = response.getWriter();
      pw.println(data);
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
