package edu.pdx.cs410J.nivedit;

import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Flight} class.
 */
public class FlightTest {

 @Test
 public void getNumberReturnsFlightNumber(){

   Flight flight = new Flight (100, "ABC",new Date(), "CDE", new Date());
   assertThat(flight.getNumber(), equalTo(100));
 }

  @Test
  public void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
    Flight flight = new Flight();
    assertThat(flight.getDeparture(), is(nullValue()));
  }

  @Test
  public void sourceShouldBeThreeCharacters(){
    Flight flight = new Flight(42, "ABC", new Date(), "CDE", new Date());
    assertThat(flight.getSource().length(), equalTo(3));
  }

  @Test
  public void destinationShouldBeThreeCharacters(){
    Flight flight = new Flight(42, "ABC", new Date(), "CDE", new Date());
    assertThat(flight.getDestination().length(), equalTo(3));
  }



}