package edu.pdx.cs410J.nivedit;

import edu.pdx.cs410J.AbstractAirline;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents an <code>Airline</code>.
 * @author Niveditha Venugopal
 */
public class Airline extends AbstractAirline<Flight>{

    public String name;
    public Collection<Flight> flights = new ArrayList<>();

    /**
     * Creates a new <code>Airline</code>
     *
     * @param name
     *        The Airline's name.
     * @param flights
     *        The flights belonging to the airline.
     */

    public Airline(String name, Collection<Flight> flights) {
        this.name = name;
        this.flights = flights;
    }

    /**
     * Returns a <code>name</code> that describes
     * this airline.
     * @return name of the airline.
     */
    @Override
    public String getName(){
        return this.name;
    }

    /**
     * Adds <code>Flight</code> to an airline.
     * @param flight that you want to add to the airline.
     */
    @Override
    public void addFlight(Flight flight) {
        this.flights.add(flight);
    }

    /**
     *Returns a collection of flights for the airline.
     * @return Collection of flights belonging to the airline.
     */
    @Override
    public Collection<Flight> getFlights() {
        return this.flights;
    }

}
