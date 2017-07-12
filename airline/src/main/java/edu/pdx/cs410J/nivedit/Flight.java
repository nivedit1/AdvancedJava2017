package edu.pdx.cs410J.nivedit;

import edu.pdx.cs410J.AbstractFlight;

/**
 * This class represents a <code>Flight</code>.
 */
public class Flight extends AbstractFlight{

    private int flightNumber;
    private String sourceAirport;
    private String destinationAirport;
    private String departureTime;
    private String arrivalTime;

    public Flight(){
    super();
  }

    /**
     * Creates a new <code>Flight</code>
     *
     * @param flightNumber
     *        The flight number
     * @param sourceAirport
     *        The source airport code of the flight
     * @param destinationAirport
     *        The destination airport code of the flight
     * @param departureTime
     *        The departure time of the flight - Format: mm/dd/yyyy hh:mm
     * @param arrivalTime
     *        The arrival time of the flight - Format: mm/dd/yyyy hh:mm
     */

    public Flight(int flightNumber, String sourceAirport, String departureTime, String destinationAirport, String arrivalTime){
    this.flightNumber = flightNumber;
    this.sourceAirport = sourceAirport;
    this.departureTime = departureTime;
    this.destinationAirport = destinationAirport;
    this.arrivalTime = arrivalTime;
    }

    /**
     * Returns the <code>flightNumber</code>
     * that describes the flight.
     * @return flightNumber of the flight
     */
    @Override
    public int getNumber() {
        return this.flightNumber;
    }

    /**
     * Returns the <code>sourceAirport</code>
     * of the flight
     * @return source code of the airport
     */
    @Override
    public String getSource() {
        return this.sourceAirport;
    }

    /**
     * Returns the <code>departureTime</code>
     * of the flight
     * @return departure time of the flight
     */
    @Override
    public String getDepartureString() {
        return this.departureTime;
    }

    /**
     * Returns the <code>destinationAirport</code>
     * of the flight
     * @return destination code of the airport
     */
    @Override
    public String getDestination() {
        return this.destinationAirport;
    }

    /**
     * Returns the <code>arrivalTime</code>
     * of the flight
     * @return arrival time of the flight
     */
    @Override
    public String getArrivalString() {
        return this.arrivalTime;
    }
}
