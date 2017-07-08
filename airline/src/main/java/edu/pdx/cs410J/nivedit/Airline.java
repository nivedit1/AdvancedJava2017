package edu.pdx.cs410J.nivedit;

import edu.pdx.cs410J.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Niveditha on 7/7/2017.
 */
public class Airline extends AbstractAirline<Flight>{

    private Collection<Flight> flights = new ArrayList<>();


    @Override
    public String getName(){
        return "Air CS410J";
    }


    @Override
    public void addFlight(Flight flight) {
        this.flights.add(flight);
    }

    @Override
    public Collection<Flight> getFlights() {
        return this.flights;
    }

}
