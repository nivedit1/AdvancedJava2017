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

    return this.source;
  }

  @Override
  public String getDepartureString() {

    return this.departure;
  }

  @Override
  public String getDestination() {

    return this.destination;

  }

  @Override
  public String getArrivalString() {

    return this.arrival;

  }


}
