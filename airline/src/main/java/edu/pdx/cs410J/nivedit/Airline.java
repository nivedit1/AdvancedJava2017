package edu.pdx.cs410J.nivedit;

import edu.pdx.cs410J.AbstractAirline;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class is represents an <code>Airline</code>.
 */
public class Airline extends AbstractAirline<Flight>{

    /**
     * Creates a new <code>Airline</code>
     *
     * @param name
     *        The Airline's name
     * @param flights
     *        The flights belonging to the airline.
     */

    public String name;
    public Collection<Flight> flights = new ArrayList<>();

    public Airline(String name) {

        this.name = name;
    }

    /**
     * Returns a <code>name</code> that describes
     * this airline.
     */
    @Override
    public String getName(){
        return this.name;
    }

    /**
     * Adds <code>Flight</code> object airline
     * @param flight
     */
    @Override
    public void addFlight(Flight flight) {
        this.flights.add(flight);
    }

    /**
     * Returns collection of flights for the airline.
     * @return
     */
    @Override
    public Collection<Flight> getFlights() {
        return this.flights;
    }

}
