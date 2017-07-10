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

  @Test(expected = IllegalStateException.class)
  public void invalidSourceThrowsException(){
    Flight flight = new Flight(42, "ABCD", "12/03/2017 07:30", "CDE", "12/03/2017 10:30");
    flight.getSource();
  }

  @Test(expected = IllegalStateException.class)
  public void invalidDestinationThrowsException(){
    Flight flight = new Flight(42, "ABC", "12/03/2017 07:30", "CDEF", "12/03/2017 10:30");
    flight.getDestination();
  }

  @Test
  public void departureDateIsInRightFormat(){
    Flight flight = new Flight(42, "ABC", "11/05/2017 09:45", "CDE", "12/31/2017 10:30");
    assertThat(flight.getDepartureString().
                    matches("^([0][1-9]|[1][012]|[1-9])/([1-9]|([012][0-9])|[3][01])/\\d\\d\\d\\d [012]{0,1}[0-9]:[0-5][0-9]"),
            equalTo(true));
  }

  @Test(expected = IllegalStateException.class)
  public void invalidDepartureDateThrowsException(){
    Flight flight = new Flight(42, "ABC", "12/38/2017 10:30", "CDE", "12/31/2017 10:30");
    flight.getDepartureString();

  }

  @Test
  public void arrivalDateIsInRightFormat(){
    Flight flight = new Flight(42, "ABC", "12/31/2017 7:30", "CDE", "02/01/2017 10:30");
    assertThat(flight.getArrivalString().
                    matches("^([0][1-9]|[1][012]|[1-9])/([1-9]|([012][0-9])|[3][01])/\\d\\d\\d\\d [012]{0,1}[0-9]:[0-5][0-9]"),
            equalTo(true));
  }

  @Test(expected = IllegalStateException.class)
  public void invalidArrivalDateThrowsException(){
    Flight flight = new Flight(42, "ABC", "12/31/2017 10:30", "CDE", "13/31/2017 10:30");
    flight.getArrivalString();

  }

 /* @Test
  public void dummyTest(){

    assertThat("01/".matches("^([0][1-9]|[1][012]|[1-9])/"),equalTo(true));
  }*/

}