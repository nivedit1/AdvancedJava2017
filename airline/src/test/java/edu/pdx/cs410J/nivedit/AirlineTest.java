package edu.pdx.cs410J.nivedit;

/**
 * Created by Niveditha on 7/8/2017.
 */

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class AirlineTest {

    @Test
    public void getNameReturnsNameOfAirline(){
        Airline airline = new Airline("CS410J Air Express");
        assertThat(airline.getName(), equalTo("CS410J Air Express"));
    }
}
