package edu.pdx.cs410J.nivedit;

import com.sun.org.apache.xpath.internal.operations.Bool;
import edu.pdx.cs410J.AbstractAirline;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  public static void main(String[] args) {
    Class c = AbstractAirline.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    Boolean readmeFlag = false;
    Boolean printFlag = false;
    int printOptionArgumentNumber = 40;
    int parseCounter = 0;
    String airlineName = "";
    int flightNumber = 0;
    String sourceAirport = "";
    String departureTime = "";
    String destinationAirport = "";
    String arrivalTime = "";

    if(args.length < 8){
      for (String arg : args) {
        if(arg.equals("-README")){
          readmeFlag = true;
          System.out.println(getReadme());
          break;
        }
        else if(arg.equals("-print")){
          printFlag = true;
        }
        else{
          continue;
        }
      }
      if(readmeFlag == false && printFlag == true){
        System.err.println("Not enough arguments to print!");
      }
      if(readmeFlag == false && printFlag == false){
        System.err.println("Missing command line arguments");
        System.exit(1);
      }
    }
    else {

      for (String arg : args) {
        if(arg.equals("-README")){
          readmeFlag = true;
          System.out.println(getReadme());
          break;
        }
        else if(arg.equals("-print")){

            printFlag = true;
        }
        else{
          parseCounter += 1;
          switch(parseCounter){
              case 1:
                  airlineName = arg;
                  break;
              case 2:
                  flightNumber = Integer.parseInt(arg);
                  break;
              case 3:
                  sourceAirport = arg;
                  break;
              case 4:
                  departureTime = arg;
                  break;
              case 5:
                  departureTime += " " + arg;
                  break;
              case 6:
                  destinationAirport = arg;
                  break;
              case 7:
                  arrivalTime = arg;
                  break;
              case 8:
                  arrivalTime += " " + arg;
                  break;
        }
      }
      /*if(readmeFlag == false && printFlag == false){
        String airline_name = args[0];
        int flight_number = Integer.parseInt(args[1]);
        String flight_source = args[2];
        String departure_time = args[3] + " " + args[4];
        String flight_destination = args[5];
        String arrival_time = args[6] + " " + args[7];
        Airline airline = new Airline(airline_name);
        Flight flight = new Flight(flight_number,flight_source,departure_time,flight_destination,arrival_time);
        airline.addFlight(flight);
        System.out.println(airline.getFlights());
        System.out.println(flight.toString());
      }
      else if (printFlag == true){
          for(int i = 0; i < args.length; i++){
              if(args[i] == "-print"){
                  printOptionArgumentNumber = i;
              }
          }
          switch (printOptionArgumentNumber){
              case 0 :
                  String airline_name = args[1];
                  int flight_number = Integer.parseInt(args[2]);
                  String flight_source = args[3];
                  String departure_time = args[4] + " " + args[5];
                  String flight_destination = args[6];
                  String arrival_time = args[7] + " " + args[8];
                  break;
              case 1 :
                  airline_name = args[0];
                  flight_number = Integer.parseInt(args[2]);
                  flight_source = args[3];
                  departure_time = args[4] + " " + args[5];
                  flight_destination = args[6];
                  arrival_time = args[7] + " " + args[8];
                  break;

*/
          }

        Airline airline = new Airline(airlineName);
        Flight flight = new Flight(flightNumber,sourceAirport,departureTime,destinationAirport,arrivalTime);
        airline.addFlight(flight);
        if(printFlag == true){

            System.out.println(flight.toString());

          }
      }
    }

  private static String getReadme() {
    String readMeText = "This program parses the command line arguments provided by the user and assigns flight to an airline!";
    return readMeText;
  }

}