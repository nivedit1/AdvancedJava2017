package edu.pdx.cs410J.nivedit;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Flight} class.
 */
public class FlightTest {

    private Flight flight;

    @Before
    public void setUp() throws Exception {
        flight = new Flight(42, "ABC", new Date(), "CDE", new Date());
    }

    @Test
    public void getNumberReturnsFlightNumber(){
        assertThat(flight.getNumber(), equalTo(42));
    }

    @Test
    public void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
        Flight flight = new Flight();
        assertThat(flight.getDeparture(), is(nullValue()));
    }

    @Test
    public void sourceShouldBeThreeCharacters(){
        assertThat(flight.getSource().length(), equalTo(3));
    }

    @Test
    public void destinationShouldBeThreeCharacters(){
      assertThat(flight.getDestination().length(), equalTo(3));
    }

    @Test
    public void testFlightCompareToMethodForEqualDepartureDate(){
        String expectedDatePattern = "MM/dd/yyyy HH:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(expectedDatePattern);
        formatter.setLenient(false);
        Date departureTimeInDate1 = new Date();
        Date departureTimeInDate2 = new Date();
        try {
            departureTimeInDate1 = formatter.parse("07/07/2017 09:45");
            departureTimeInDate2 = formatter.parse("07/07/2017 09:45");
        }
        catch (ParseException e){
            System.err.println("Cannot parse");
        }
        Flight flight1 = new Flight(101,"PDX",departureTimeInDate1, "HOU",new Date());
        Flight flight2 = new Flight(102,"PDX",departureTimeInDate2,"HOU", new Date());
        assertThat(flight1.compareTo(flight2), equalTo(0));
    }

    @Test
    public void testFlightCompareToMethodForUnEqualDepartureDate(){
        String expectedDatePattern = "MM/dd/yyyy HH:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(expectedDatePattern);
        formatter.setLenient(false);
        Date departureTimeInDate1 = new Date();
        Date departureTimeInDate2 = new Date();
        try {
            departureTimeInDate1 = formatter.parse("07/07/2017 10:45");
            departureTimeInDate2 = formatter.parse("07/07/2017 09:45");
        }
        catch (ParseException e){
            System.err.println("Cannot parse");
        }
        Flight flight1 = new Flight(101,"PDX",departureTimeInDate1, "HOU",new Date());
        Flight flight2 = new Flight(102,"PDX",departureTimeInDate2,"HOU", new Date());
        assertThat(flight1.compareTo(flight2), equalTo(1));
    }

    @Test
    public void testFlightCompareToMethodForEqualSourceAirport(){
      Flight flight1 = new Flight(101,"PDX",new Date(), "HOU",new Date());
      Flight flight2 = new Flight(102,"PDX",new Date(),"HOU", new Date());
      assertThat(flight1.compareTo(flight2), equalTo(0));
    }

    @Test
    public void testFlightCompareToMethodForUnEqualSourceAirport(){
        Flight flight1 = new Flight(101,"LAX",new Date(), "HOU",new Date());
        Flight flight2 = new Flight(102,"PDX",new Date(),"HOU", new Date());
        assertThat(flight1.compareTo(flight2), equalTo(-4));
    }

}