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
    if(args.length < 8){
      for (String arg : args) {
        if(arg.equals("-README")){
          readmeFlag = true;
          System.out.println(getReadme());
          break;
        }
        else if(arg.equals("-PRINT")){
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
        else{
          continue;
        }
      }
      if(readmeFlag == false){
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
    }
  }

  private static String getReadme() {
    String readMeText = "This program parses the command line arguments provided by the user and assigns flight to an airline!";
    return readMeText;
  }

}