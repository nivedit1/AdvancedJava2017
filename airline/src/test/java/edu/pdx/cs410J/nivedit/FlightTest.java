package edu.pdx.cs410J.nivedit;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Flight} class.
 */
public class FlightTest {
  
 /* @Test(expected = UnsupportedOperationException.class)
  public void getArrivalStringNeedsToBeImplemented() {
    Flight flight = new Flight(42, "ABC", "12/03/2017 07:30", "CDE", "12/03/2017 10:30");
    flight.getArrivalString();
  }
*/
 /* @Test
  public void initiallyAllFlightsHaveTheSameNumber() {
    Flight flight = new Flight();
    assertThat(flight.getNumber(), equalTo(42));
  }*/

 @Test
 public void getNumberReturnsFlightNumber(){

   Flight flight = new Flight (100, "ABC", "12/03/2017 07:30", "CDE", "12/03/2017 10:30");
   assertThat(flight.getNumber(), equalTo(100));
 }

  @Test
  public void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
    Flight flight = new Flight();
    assertThat(flight.getDeparture(), is(nullValue()));
  }

  @Test
  public void sourceShouldBeThreeCharacters(){
    Flight flight = new Flight(42, "ABC", "12/03/2017 07:30", "CDE", "12/03/2017 10:30");
    assertThat(flight.getSource().length(), equalTo(3));
  }

  @Test
  public void destinationShouldBeThreeCharacters(){
    Flight flight = new Flight(42, "ABC", "12/03/2017 07:30", "CDE", "12/03/2017 10:30");
    assertThat(flight.getDestination().length(), equalTo(3));
  }



}