package edu.pdx.cs410J.nivedit;

/**
 * Created by Niveditha on 7/8/2017.
 */

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class AirlineTest {

    @Test
    public void getNameReturnsNameOfAirline(){
        Flight flight = new Flight();
        Collection<Flight> flights = new ArrayList<>();
        flights.add(flight);
        Airline airline = new Airline("CS410J Air Express",flights);
        assertThat(airline.getName(), equalTo("CS410J Air Express"));
    }
}
