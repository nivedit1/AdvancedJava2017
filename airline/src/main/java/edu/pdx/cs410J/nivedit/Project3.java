package edu.pdx.cs410J.nivedit;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirportNames;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The main class for the CS410J airline Project.
 * @author Niveditha Venugopal
 */
public class Project3 {

    /**
     *Main program that parses the command line,
     * validates the arguments provided by the user and
     * creates an <code>Airline</code> and <code>Flight</code>,
     * and optionally prints a description of the flight to
     * standard out by invoking the <code>toString</code> method of <code>Flight</code>
     * optionally adds flight information to the specified destination file and optionally
     * pretty prints <code>airline</code> information to file or standard out.
     * @param args
     *        Command line arguments passed by the user.
     */

    static Date departureTimeInDate;
    static Date arrivalTimeInDate;
    public static void main(String[] args) {
        Boolean printFlag = false;
        Boolean textFileFlag = false;
        Boolean prettyFlag = false;
        String[] argumentArray = new String[100];
        String airlineName = "";
        int flightNumber = 0;
        String sourceAirport = "";
        String departureTime = "";
        String destinationAirport = "";
        String arrivalTime = "";
        Collection<Flight> flights = new ArrayList<>();
        String textDumperFileName = "";
        String prettyFileName = "";
        Boolean fileExists = false;
        Boolean writeFlag = false;
        if(args.length > 0){
            if(args.length < 6){
                for(String arg : args){
                    if(arg.equals("-README")){
                        System.out.println(getReadme());
                        System.exit(0);
                    } else {
                        System.err.println("Missing command line arguments!");
                        System.exit(1);
                    }
                }
            } else {
                    for (int i = 0; i < 6; i++) {
                        if (args[i].equals("-README")) {
                            System.out.println(getReadme());
                            System.exit(0);
                        } else if (args[i].equals("-print")) {
                            printFlag = true;
                        } else if (args[i].equals("-textFile")) {
                            textFileFlag = true;
                            textDumperFileName = args[(Arrays.asList(args).indexOf("-textFile")) + 1];
                        } else if (args[i].equals("-pretty")) {
                            prettyFlag = true;
                            prettyFileName = args[(Arrays.asList(args).indexOf("-pretty")) + 1];
                        } else {
                            continue;
                        }
                    }
            }
            if((args.length <10 && printFlag == false && prettyFlag == false && textFileFlag == false)
                    || (printFlag == true && prettyFlag == true && textFileFlag == true && args.length < 15)
                    || (printFlag == true && prettyFlag == true && textFileFlag == false && args.length < 13)
                    || (printFlag == true && textFileFlag == true && prettyFlag == false && args.length < 13)
                    || (prettyFlag == true && textFileFlag == true && printFlag == false && args.length < 14)
                    || (printFlag == true && prettyFlag == false && textFileFlag == false && args.length < 11)
                    || (prettyFlag == true && textFileFlag == false && printFlag == false && args.length < 12)
                    || (textFileFlag == true && printFlag == false && prettyFlag == false && args.length < 12)){
                System.err.println("Missing command line arguments!");
                System.exit(1);
            } else if((args.length > 10 && printFlag == false && prettyFlag == false && textFileFlag == false)
                    || (printFlag == true && prettyFlag == true && textFileFlag == true && args.length > 15)
                    || (printFlag == true && prettyFlag == true && textFileFlag == false && args.length > 13)
                    || (printFlag == true && textFileFlag == true && prettyFlag == false && args.length > 13)
                    || (prettyFlag == true && textFileFlag == true && printFlag == false && args.length > 14)
                    || (printFlag == true && prettyFlag == false && textFileFlag == false && args.length > 11)
                    || (prettyFlag == true && textFileFlag == false && printFlag == false && args.length > 12)
                    || (textFileFlag == true && printFlag == false && prettyFlag == false && args.length > 12)){
                System.err.println("Too many arguments!");
                System.exit(1);
            } else {
                if(printFlag == true && prettyFlag == true && textFileFlag == true){
                    for(int i = 5; i < args.length; i++){
                        argumentArray[i-5] = args[i];
                    }
                } else if((printFlag == true && prettyFlag == true && textFileFlag == false)
                        || (printFlag == true && textFileFlag == true && prettyFlag == false)){
                    for(int i = 3; i <args.length; i++){
                        argumentArray[i-3] = args[i];
                    }
                } else if(prettyFlag == true && textFileFlag == true && printFlag == false){
                    for(int i = 4; i < args.length; i++){
                        argumentArray[i-4] = args[i];
                    }
                } else if(printFlag == true && textFileFlag == false && prettyFlag == false){
                    for(int i = 1; i <args.length; i++){
                        argumentArray[i-1] = args[i];
                    }
                } else if((textFileFlag == true && prettyFlag == false && printFlag == false)
                        || (prettyFlag == true && textFileFlag == false && printFlag == false)){
                    for(int i = 2; i < args.length; i++){
                        argumentArray[i-2] = args[i];
                    }
                } else {
                    for(int i = 0; i < args.length; i++){
                        argumentArray[i] = args[i];
                    }
                }
                if(validateArguments(argumentArray) == true){
                    airlineName = argumentArray[0];
                    flightNumber = Integer.parseInt(argumentArray[1]);
                    sourceAirport = argumentArray[2].toUpperCase();
                    departureTime = argumentArray[3] + " " + argumentArray[4] + " " + argumentArray[5];
                    destinationAirport = argumentArray[6].toUpperCase();
                    arrivalTime = argumentArray[7] + " " + argumentArray[8] + " " + argumentArray[9];
                    Flight flight = new Flight(flightNumber, sourceAirport, departureTimeInDate, destinationAirport, arrivalTimeInDate);
                    Airline airline = new Airline(airlineName, flights);
                    airline.addFlight(flight);
                    if (printFlag == true) {
                        System.out.println();
                        System.out.println(flight.toString());
                        System.out.println();
                    }
                    TextParser parser = new TextParser(textDumperFileName);
                    TextDumper dumper = new TextDumper(textDumperFileName);
                    PrettyPrinter prettyPrinter = new PrettyPrinter(prettyFileName);
                    AbstractAirline airline1;
                    if(textFileFlag == true){
                        File textFile = new File(textDumperFileName);
                        fileExists = textFile.exists();
                        if(fileExists == true) {
                            try{
                                FileReader reader = new FileReader(textDumperFileName);
                                BufferedReader bufferedReader = new BufferedReader(reader);
                                if(bufferedReader.readLine() != null){
                                    airline1 = parser.parse();
                                    if (airline1.getName().equals(airline.getName())) {
                                        writeFlag = true;
                                        airline.flights.addAll(airline1.getFlights());
                                    } else {
                                        System.err.println("This file belongs to airline -> " + airline1.getName());
                                        System.err.println("But the airline provided in the command line -> " + airline.getName());
                                        System.exit(1);
                                    }
                                } else {
                                    System.err.println("Empty File should not be passed");
                                    System.exit(1);
                                }
                            }
                            catch (IOException e){
                                System.err.println("IO Exception in textFile");
                            }
                        }else {
                            writeFlag = true;
                        }
                        if(writeFlag == true){
                            try {
                                textFile.createNewFile();
                                dumper.dump(airline);
                                if(prettyFlag == true){
                                    File prettyFile = new File(prettyFileName);
                                    airline = (Airline) parser.parse();
                                    prettyPrinter.dump(airline);
                                }
                            } catch (IOException e){
                                System.err.println("IO Exception in pretty file");
                                System.exit(1);
                            }
                        }
                    }else if(prettyFlag == true){
                        try {
                            prettyPrinter.dump(airline);
                        } catch (IOException e){
                            System.err.println("IO Exception");
                            System.exit(1);
                        }
                    }
                }
            }
        } else {
            System.err.println("No command line arguments!");
            System.exit(1);
        }
    }

    /*This method returns a README for the application.
    *@return README Text for the application.
    * */
    private static String getReadme() {

        String title = "\nName of the project:\n" +
                "====================\n" +
                "CS410J Project 3: Pretty Printing the Airline";
        String applicationDescription = "\n\nApplication Description:\n" +
                "========================\n" +
                "This application parses the command line arguments provided by the user " +
                "and assigns the values to the airline and the respective flight stated by the user.\n" +
                "It also checks the validity of flight number, airport codes, and " +
                "arrival and departure times.\n"+
                "It optionally adds the airline information to a file specified on the command line.\n"
                +"It optionally pretty prints the airline information.\n";
        String argumentList = "\nArguments List:" +
                "\n===============\n" +
                "Pass arguments to this program as follows:\n" +
                "  name -> The name of the airline  [Format: String]\n" +
                "  flightNumber -> The Flight Number which identifies the flight  [Format: Numeric Code]\n" +
                "  src -> Code of Departure airport  [Format: \"AAA\" (3 Letter Code)]\n" +
                "  departTime -> Departure date and time am/pm  [Format: mm/dd/yyyy hh:mm am/pm]\n" +
                "  dest -> Code of Arrival airport  [Format: \"AAA\" (3 Letter Code)]\n" +
                "  arriveTime -> Arrival date and time am/pm  [Format: mm/dd/yyyy hh:mm am/pm]\n\n";
        String options = "Options:" +
                "\n========\n" +
                "Options are as follows:\n" +
                "  -print -> Prints a description of the new flight\n" +
                "  -README -> Prints a README for this project and exits\n" +
                "  -textFile file -> Where to read/write the airline info\n" +
                "  -pretty file -> Pretty prints the airline's flight to a text file or standard out\n";
        String executionInstruction = "\nExecution Instruction:" +
                "\n======================\n" +
                "Run the program with -> java edu.pdx.cs410J.nivedit.Project3 [options] <args>";
        String developedBy = "\n\nDeveloped By:\n" +
                "=============" +
                "\n-Niveditha Venugopal" +
                "\n-Email ID: nivedit@pdx.edu" +
                "\n-PSU ID:   978073102";
        String readMeText = title + applicationDescription + argumentList + options + executionInstruction + developedBy;
        return readMeText;
    }

    /**
     * This method validates the arguments provided in the command line.
     * @param argumentArray
     *        The arguments passed in the command line.
     * @return true if all the arguments have passed the test.
     */
    public static boolean validateArguments(String[] argumentArray) {
        String airlineName = "";
        int flightNumber = 0;
        String sourceAirport = "";
        String userInputDepartureTime = "";
        String destinationAirport = "";
        String userInputArrivalTime = "";
        String expectedDatePattern = "MM/dd/yyyy h:mm a";
        SimpleDateFormat formatter = new SimpleDateFormat(expectedDatePattern, Locale.US);
        formatter.setLenient(false);
        airlineName = argumentArray[0];
        try {
            flightNumber = Integer.parseInt(argumentArray[1]);
            if (flightNumber < 0) {
                System.err.println("Flight number cannot be negative " + flightNumber);
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Flight Number is not Integer " + argumentArray[1]);
            System.exit(1);
        }
        if (argumentArray[2].matches("^[a-zA-Z][a-zA-Z][a-zA-Z]") == true) {
            if(AirportNames.getName((argumentArray[2]).toUpperCase()) != null){
                sourceAirport = argumentArray[2].toUpperCase();
            }
            else {
                System.err.println("Not a valid Airport Code - " + argumentArray[2]);
                System.exit(1);
            }
        } else {
            System.err.println("Source Airport Code is not a 3 letter code - " + argumentArray[2]);
            System.out.println("Airport code should be of the format \"AAA\"");
            System.exit(1);
        }
        try {
            if(argumentArray[5].equals("am") || argumentArray[5].equals("pm")) {
                userInputDepartureTime = argumentArray[3] + " " + argumentArray[4] + " " + argumentArray[5];
                departureTimeInDate = formatter.parse(userInputDepartureTime);
            }
            else {
                System.err.println("\"am/pm\" should not be capitalized - " + argumentArray[3] + " " + argumentArray[4] + " " + argumentArray[5]);
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("The Departure time does not match the format \"MM/DD/YY HH:MM\" - " + userInputDepartureTime);
            System.exit(1);
        }
        if (argumentArray[6].matches("^[a-zA-Z][a-zA-Z][a-zA-Z]") == true) {
            if(AirportNames.getName((argumentArray[6]).toUpperCase()) != null){
                destinationAirport = argumentArray[6].toUpperCase();
            }
            else {
                System.err.println("Not a valid Airport Code - " + argumentArray[6]);
                System.exit(1);
            }
        } else {
            System.err.println("Destination Airport Code is not a 3 letter code - " + argumentArray[6]);
            System.out.println("Airport code should be of the format \"AAA\"");
            System.exit(1);
        }
        try {
            if(argumentArray[9].equals("am") || argumentArray[9].equals("pm")){
                userInputArrivalTime = argumentArray[7] + " " + argumentArray[8] + " " + argumentArray[9];
                arrivalTimeInDate = formatter.parse(userInputArrivalTime);
            } else {
                System.err.println("\"am/pm\" should not be capitalized - " + argumentArray[7] + " " + argumentArray[8] + " " + argumentArray[9]);
                System.exit(1);
            }

        } catch (Exception e) {
            System.err.println("The Arrival time does not match the format \"MM/DD/YYYY HH:MM\" - " + userInputArrivalTime);
            System.exit(1);
        }
        return true;
    }
}