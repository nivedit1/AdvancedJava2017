package edu.pdx.cs410J.nivedit;

import edu.pdx.cs410J.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Niveditha on 7/7/2017.
 */
public class Airline extends AbstractAirline<Flight>{

    public String name;
    public Collection<Flight> flights = new ArrayList<>();

    public Airline(String name) {

        this.name = name;
    }


    @Override
    public String getName(){
        return this.name;
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
