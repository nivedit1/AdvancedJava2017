package edu.pdx.cs410J.nivedit;

import edu.pdx.cs410J.AbstractFlight;

/**
 * This class is represents a <code>Flight</code>.
 */
public class Flight extends AbstractFlight{
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
    private int flightNumber;
    private String sourceAirport;
    private String destinationAirport;
    private String departureTime;
    private String arrivalTime;

    public Flight(){

    super();
  }

    public Flight(int Number, String source, String departure, String destination, String arrival){
    this.flightNumber = Number;
    this.sourceAirport = source;
    this.departureTime = departure;
    this.destinationAirport = destination;
    this.arrivalTime = arrival;
    }

    /**
     * Returns the <code>flightNumber</code>
     * that describes the flight.
     * @return
     */
    @Override
    public int getNumber() {
        return this.flightNumber;
    }

    /**
     * Returns the <code>sourceAirport</code>
     * of the flight
     * @return
     */
    @Override
    public String getSource() {
        return this.sourceAirport;
    }

    /**
     * Returns the <code>departureTime</code>
     * of the flight
     * @return
     */
    @Override
    public String getDepartureString() {
        return this.departureTime;
    }

    /**
     * Returns the <code>destinationAirport</code>
     * of the flight
     * @return
     */
    @Override
    public String getDestination() {
        return this.destinationAirport;
    }

    /**
     * Returns the <code>arrivalTime</code>
     * of the flight
     * @return
     */
    @Override
    public String getArrivalString() {
        return this.arrivalTime;
    }
}
