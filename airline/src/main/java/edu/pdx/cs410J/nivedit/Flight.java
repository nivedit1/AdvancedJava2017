package edu.pdx.cs410J.nivedit;

import edu.pdx.cs410J.AbstractFlight;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class represents a <code>Flight</code>.
 * @author Niveditha Venugopal
 */
public class Flight extends AbstractFlight implements Comparable<Flight> {

    private int flightNumber;
    private String sourceAirport;
    private String destinationAirport;
    private Date departureTime;
    private Date arrivalTime;
    public static final DateFormat FORMATTER = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT, Locale.getDefault());

    public Flight() {
        super();
    }

    /**
     * Creates a new <code>Flight</code>
     *
     * @param flightNumber       The flight number
     * @param sourceAirport      The source airport code of the flight
     * @param destinationAirport The destination airport code of the flight
     * @param departureTime      The departure time of the flight - Format: mm/dd/yyyy hh:mm
     * @param arrivalTime        The arrival time of the flight - Format: mm/dd/yyyy hh:mm
     */

    public Flight(int flightNumber, String sourceAirport, Date departureTime, String destinationAirport, Date arrivalTime) {
        this.flightNumber = flightNumber;
        this.sourceAirport = sourceAirport;
        this.departureTime = departureTime;
        this.destinationAirport = destinationAirport;
        this.arrivalTime = arrivalTime;
    }

    /**
     * Returns the <code>flightNumber</code>
     * that describes the flight.
     *
     * @return flightNumber of the flight
     */
    @Override
    public int getNumber() {

        return this.flightNumber;
    }

    /**
     * Returns the <code>sourceAirport</code>
     * of the flight
     *
     * @return source code of the airport
     */
    @Override
    public String getSource() {

        return this.sourceAirport;
    }

    /**
     * Returns the <code>departureTime</code>
     * of the flight
     *
     * @return departure time of the flight
     */
    @Override
    public String getDepartureString() {
        String departureTimeInString = FORMATTER.format(this.getDeparture());
        return departureTimeInString;
    }

    /**
     * Returns the <code>destinationAirport</code>
     * of the flight
     *
     * @return destination code of the airport
     */
    @Override
    public String getDestination() {

        return this.destinationAirport;
    }

    /**
     * Returns the <code>arrivalTime</code>
     * of the flight
     *
     * @return arrival time of the flight
     */
    @Override
    public String getArrivalString() {
        String arrivalTimeInString = FORMATTER.format(this.getDeparture());
        return arrivalTimeInString;
    }

    @Override
    public Date getDeparture() {
        return this.departureTime;
    }

    @Override
    public  Date getArrival(){
        return this.arrivalTime;
    }

    @Override
    public int compareTo(Flight o) {
        return 0;
    }
}