package edu.pdx.cs410J.nivedit;

import com.sun.org.apache.xpath.internal.operations.Bool;
import edu.pdx.cs410J.AbstractAirline;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

    public static void main(String[] args) {

        Class c = AbstractAirline.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath
        Boolean readmeFlag = false;
        Boolean printFlag = false;
        String[] argumentArray = new String[100];
        String airlineName = "";
        int flightNumber = 0;
        String sourceAirport = "";
        Date departureTime = new Date();
        String userInputDepartureTime = "";
        String destinationAirport = "";
        Date arrivalTime = new Date();
        String userInputArrivalTime = "";
        String expectedDatePattern = "MM/dd/yyyy HH:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(expectedDatePattern);
        formatter.setLenient(false);

        if (args.length == 1) {
            if (args[0].equals("-README")) {
                readmeFlag = true;
                System.out.println(getReadme());
                System.exit(0);
            } else if (args[0].equals("-print")) {
                printFlag = true;
                System.err.println("Not enough arguments to print");
                System.exit(1);
            } else {
                System.err.println("Missing command line arguments");
                System.exit(1);
            }

        }

        else if (args.length < 8) {

            if (args[0].equals("-README") || args[1].equals("-README")) {
                readmeFlag = true;
                System.out.println(getReadme());
                System.exit(0);
            } else if ((args[0].equals("-print") || args[1].equals("-print")) && readmeFlag == false) {
                printFlag = true;
                System.err.println("Not enough arguments to print!");
                System.exit(1);
            } else {
                System.err.println("Missing command line arguments");
                System.exit(1);
            }
        }

        else if(args.length >= 8 && args.length <= 10){
            if (args[0].equals("-README") || args[1].equals("-README")) {
                readmeFlag = true;
                System.out.println(getReadme());
            } else if (args[0].equals("-print")) {
                printFlag = true;
                for (int i = 1; i < args.length; i++) {
                    argumentArray[i - 1] = args[i];
                }
            } else if (args.length > 8){
                System.err.println("Too many arguments!");
                System.exit(1);
            }
            else {
                for (int i = 0; i < args.length; i++) {
                    argumentArray[i] = args[i];
                }
            }

            if (readmeFlag == false) {
                airlineName = argumentArray[0];
                try {
                    flightNumber = Integer.parseInt(argumentArray[1]);
                } catch (Exception e) {
                    System.err.println("Flight Number is not Integer " + argumentArray[1]);
                    System.exit(1);
                }
                if(argumentArray[2].matches("^[a-zA-Z][a-zA-Z][a-zA-Z]") == true) {

                  sourceAirport = argumentArray[2];
                }
                else {
                    System.err.println("Source Airport Code is not a 3 letter code - " + argumentArray[2]);
                    System.out.println("Airport code should be of the format \"AAA\"");
                    System.exit(1);
                }

                try {
                    userInputDepartureTime = argumentArray[3] + " " + argumentArray[4];
                    departureTime = formatter.parse(userInputDepartureTime);

                }
                catch(Exception e) {
                    System.err.println("The Departure time does not match the format \"MM/DD/YY HH:MM\" - " + userInputDepartureTime);
                    System.exit(1);
                }

                if(argumentArray[5].matches("^[a-zA-Z][a-zA-Z][a-zA-Z]") == true) {

                    destinationAirport = argumentArray[5];
                }
                else {
                    System.err.println("Destination Airport Code is not a 3 letter code - " + argumentArray[5]);
                    System.out.println("Airport code should be of the format \"AAA\"");
                    System.exit(1);
                }
                //arrivalTime = argumentArray[6] + " " + argumentArray[7];
                try {
                    userInputArrivalTime = argumentArray[6] + " " + argumentArray[7];
                    arrivalTime = formatter.parse(userInputArrivalTime);

                }
                catch(Exception e) {
                    System.err.println("The Arrival time does not match the format \"MM/DD/YY HH:MM\" - " + userInputArrivalTime);
                    System.exit(1);
                }

                Airline airline = new Airline(airlineName);

                Flight flight = new Flight(flightNumber, sourceAirport, userInputDepartureTime, destinationAirport, userInputArrivalTime);

                airline.addFlight(flight);

                if (printFlag == true) {

                    System.out.println(flight.toString());
                }

            }

        }
        else{
            if (args[0].equals("-README") || args[1].equals("-README")) {
                readmeFlag = true;
                System.out.println(getReadme());
                System.exit(0);
            } else if ((args[0].equals("-print") || args[1].equals("-print")) && readmeFlag == false) {
                printFlag = true;
                System.err.println("Too many arguments to print!");
                System.exit(1);
            } else {
                System.err.println("Too many arguments!");
                System.exit(1);
            }
        }

    }

    private static String getReadme() {
        String title = "\n\nName of the project:\n" +
                "====================\n\n"+
                "CS410J Project 1: Designing an Airline Application";
        String applicationDescription = "\n\nApplication Description:\n" +
                            "========================\n"+
                "\nThis application parses the command line arguments provided by the user "+
        "and assigns the values to the airline and the respective flight stated by the user.\n"+
                "It also checks the validity of flight number, airport codes, and "+
                "arrival and departure times.\n\n";
        String argumentList = "Arguments List:"+
                      "\n===============\n"+
                "\nPass arguments to this program as follows:\n\n"+
                "\tname -> The name of the airline\t\t\t\t\tFormat: String\n" +
                "\tflightNumber -> The Flight Number which identifies the flight\tFormat: Numeric Code\n" +
                "\tsrc -> Code of Departure airport\t\t\t\tFormat: \"AAA\" (3 Letter Code)\n" +
                "\tdepartTime -> Departure date and time (24-hour time)\t\tFormat: mm/dd/yyyy hh:mm\n" +
                "\tdest -> Code of Arrival airport\t\t\t\t\tFormat: \"AAA\" (3 Letter Code)\n" +
                "\tarriveTime -> Arrival date and time (24-hour time)\t\tFormat: mm/dd/yyyy hh:mm\n\n";
        String options = "Options:"+
                "\n========\n"+
                "\nOptions are as follows:\n\n" +
                "\t-print -> Prints a description of the new flight\n" +
                "\t-README -> Prints a README for this project and exits\n";
        String executionInstruction= "\nExecution Instruction:"+
                      "\n======================\n"+
                "\nRun the program with -> java edu.pdx.cs410J.nivedit.Project1 [options] <args>";
        String developedBy = "\n\nDeveloped By:\n" +
                      "============="+
                      "\n\n-Niveditha Venugopal"+
                      "\n-Email ID: nivedit@pdx.edu"+
                      "\n-PSU ID:   978073102";
        String readMeText = title + applicationDescription + argumentList + options + executionInstruction + developedBy;


        return readMeText;
    }

}

