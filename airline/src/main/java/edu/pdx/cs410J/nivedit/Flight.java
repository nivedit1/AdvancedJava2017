package edu.pdx.cs410J.nivedit;

import edu.pdx.cs410J.AbstractFlight;

public class Flight extends AbstractFlight {

  private int number;
  private String source;
  private String destination;
  private String departure;
  private String arrival;

  public Flight(){
    super();
  }

  public Flight(int Number, String source, String departure, String destination, String arrival){

    this.number = Number;
    this.source = source;
    this.departure = departure;
    this.destination = destination;
    this.arrival = arrival;

  }

  @Override
  public int getNumber() {

    return this.number;
  }

  @Override
  public String getSource() {

    if(this.source.length() == 3){

        return this.source;
    }
    else
      {
        throw new IllegalStateException("Source Airport Code is not 3 characters! " + this.source);
      }
  }

  @Override
  public String getDepartureString() {

    if(this.departure.
            matches("^([0][1-9]|[1][012]|[1-9])/([1-9]|([012][0-9])|[3][01])/\\d\\d\\d\\d [012]{0,1}[0-9]:[0-5][0-9]"))
    {

      return this.departure;

    }
    else
    {
      throw new IllegalStateException("Date Format Wrong !" + " " + this.departure);
    }
  }

  @Override
  public String getDestination() {

    if(this.destination.length() == 3)
      {
        return this.destination;
      }
    else
    {
      throw new IllegalStateException("Destination Airport Code is not 3 characters! " + this.destination);
    }

  }

  @Override
  public String getArrivalString() {

    if (this.arrival.
            matches("^([0][1-9]|[1][012]|[1-9])/([1-9]|([012][0-9])|[3][01])/\\d\\d\\d\\d [012]{0,1}[0-9]:[0-5][0-9]"))
    {

      return this.arrival;

    }
    else {
      throw new IllegalStateException("Date Format Wrong !" + " " + this.arrival);
    }

  }


}
