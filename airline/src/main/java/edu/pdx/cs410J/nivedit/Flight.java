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

    /**
     * Returns the <code>departureTime</code>
     * of the flight
     * @return the departure time in Date Format
     */
    @Override
    public Date getDeparture() {
        return this.departureTime;
    }

    /**
     * Returns the <code>arrival time</code>
     * of the flight
     * @return the arrival time in Date format
     */
    @Override
    public  Date getArrival(){
        return this.arrivalTime;
    }

    /**
     * This method compares two Flight Objects based on their source Airport
     * and DepartureTime and returns an integer value.
     * @param other flight to be compared
     * @return integer value of the compare result.
     */
    @Override
    public int compareTo(Flight other) {
        int compareValue;
        compareValue = this.sourceAirport.compareTo(other.getSource());
        if(compareValue !=0){
            return compareValue;
        }
        compareValue = this.getDeparture().compareTo(other.getDeparture());
        if(compareValue != 0){
            return compareValue;
        }
        return 0;
    }
}