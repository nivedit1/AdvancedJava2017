package edu.pdx.cs410J.nivedit.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.nivedit.client.Airline;
import edu.pdx.cs410J.nivedit.client.Flight;
import edu.pdx.cs410J.nivedit.client.AirlineService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The server-side implementation of the Airline service
 */
public class AirlineServiceImpl extends RemoteServiceServlet implements AirlineService
{
  private final Map<String, Collection<Flight>> airlineFlightMap = new HashMap<>();
  @Override
  public Airline getAirline(String airlineName, String source, String destination) {
    Collection<Flight> flightsToPrint = new ArrayList<>();
    if(this.airlineFlightMap.containsKey(airlineName)){
      Collection<Flight> flightsInGet = airlineFlightMap.get(airlineName);
      for(Flight flight:flightsInGet){
        if(flight.getSource().equals(source) && flight.getDestination().equals(destination)){
          flightsToPrint.add(flight);
        }
        else {
          continue;
        }
      }
    }
    Airline airline = new Airline(airlineName, flightsToPrint);
    return airline;
  }

  @Override
  public void throwUndeclaredException() {
    throw new IllegalStateException("Expected undeclared exception");
  }

  @Override
  public void throwDeclaredException() throws IllegalStateException {
    throw new IllegalStateException("Expected declared exception");
  }

  @Override
  public String postAirline(String name, Flight flight) {
    String successMessage = null;
    if(this.airlineFlightMap.containsKey(name)){
      Collection<Flight> flights = this.airlineFlightMap.get(name);
      flights.add(flight);
      this.airlineFlightMap.put(name,flights);
      successMessage = "Success";
    } else {
      Collection<Flight> flights = new ArrayList<>();
      flights.add(flight);
      this.airlineFlightMap.put(name,flights);
      successMessage = "Success";
    }
    return successMessage;
  }

  /**
   * Log unhandled exceptions to standard error
   *
   * @param unhandled
   *        The exception that wasn't handled
   */
  @Override
  protected void doUnexpectedFailure(Throwable unhandled) {
    unhandled.printStackTrace(System.err);
    super.doUnexpectedFailure(unhandled);
  }
}
