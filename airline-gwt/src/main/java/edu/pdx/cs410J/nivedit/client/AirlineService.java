package edu.pdx.cs410J.nivedit.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * A GWT remote service that returns an airline
 */
@RemoteServiceRelativePath("airline")
public interface AirlineService extends RemoteService {

  /**
   * Returns the flight based on the search parameters
   * @param name
   *          name of the airline to be seached
   * @param source
   *         source airport code of the flight
   * @param destination
   *         destination airport code of the flight
   * @return
   *       airline object that matches the search criteria
   */
  Airline getAirline(String name, String source, String destination);

  /**
   * Always throws an undeclared exception so that we can see GWT handles it.
   */
  void throwUndeclaredException();

  /**
   * Always throws a declared exception so that we can see GWT handles it.
   */
  void throwDeclaredException() throws IllegalStateException;

  /**
   * Posts an airline to the server
   * @param name
   *         name of the airline
   * @param flight
   *         the flight object that has to be added to the airline
   * @return
   *      String whether successful or not
   */
  String postAirline(String name, Flight flight);
}
