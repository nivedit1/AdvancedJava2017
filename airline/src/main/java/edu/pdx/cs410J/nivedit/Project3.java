package edu.pdx.cs410J.nivedit;

import edu.pdx.cs410J.AbstractAirline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.ArrayList;

/**
 * The main class for the CS410J airline Project
 * @author Niveditha Venugopal
 */
public class Project3 {

    /**
     *Main program that parses the command line,
     * validates the arguments provided by the user and
     * creates an <code>Airline</code> and <code>Flight</code>,
     * and optionally prints a description of the flight to
     * standard out by invoking the <code>toString</code> method of <code>Flight</code>
     * optionally adds flight information to the specified destination file.
     * @param args
     *        Command line arguments passed by the user     *
     */

    static Date departureTimeInDate;
    static Date arrivalTimeInDate;
    public static void main(String[] args) {
        Boolean printFlag = false;
        Boolean textFileFlag = false;
        String[] argumentArray = new String[100];
        boolean validArguments = false;
        String airlineName = "";
        int flightNumber = 0;
        String sourceAirport = "";
        String departureTime = "";
        String destinationAirport = "";
        String arrivalTime = "";
        Collection<Flight> flights = new ArrayList<>();
        String fileName = "";
        Boolean fileExists = false;
        Boolean writeFlag = false;
        if(args.length > 0){
            if (args.length < 8) {
                if(args.length < 4){
                    for(String arg : args){
                        if(arg.equals("-README")){
                            System.out.println(getReadme());
                            System.exit(0);
                        } else if (arg.equals("-print")){
                            System.err.println("Not enough arguments to print!");
                            System.exit(1);
                        }
                        else if (arg.equals("-textFile")){
                            System.err.println("Not enough arguments to write to file");
                            System.exit(1);
                        }
                        else {
                            System.err.println("Missing command line arguments");
                            System.exit(1);
                        }
                    }
                }
                if (args[0].equals("-README") || args[1].equals("-README") || args[2].equals("-README") || args[3].equals("-README")) {
                    System.out.println(getReadme());
                    System.exit(0);
                } else if ((args[0].equals("-print") || args[1].equals("-print") || args[2].equals("-print"))) {
                    printFlag = true;
                    System.err.println("Not enough arguments to print!");
                    System.exit(1);
                } else if ((args[0].equals("-textFile") || args[1].equals("-textFile"))) {
                    System.err.println("Not enough arguments to write to file");
                    System.exit(1);
                } else {
                    System.err.println("Missing command line arguments");
                    System.exit(1);
                }
            } else if (args.length >= 8 && args.length <= 12) {
                if (args[0].equals("-README") || args[1].equals("-README") || args[2].equals("-README") || args[3].equals("-README")) {
                    System.out.println(getReadme());
                    System.exit(0);
                } else if ((args[0].equals("-print") || args[1].equals("-print") || args[2].equals("-print"))) {
                    printFlag = true;
                    if(args.length == 11 && (args[0].equals("-textFile") || args[1].equals("-textFile"))){
                        textFileFlag = true;
                        if(args[0].equals("-textFile")){
                            fileName = args[1];
                        } else {
                            fileName = args[2];
                        }
                        for (int i = 3; i<args.length; i++ ){
                            argumentArray[i-3] = args[i];
                        }
                    } else if (args.length == 10 && (args[0].equals("-textFile") || args[1].equals("-textFile"))){
                        textFileFlag = true;
                        if(args[0].equals("-textFile")){
                            fileName = args[1];
                        } else {
                            fileName = args[2];
                        }
                        for (int i = 3; i<args.length; i++ ){
                            argumentArray[i-3] = args[i];
                        }
                    } else if(args.length == 9){
                        for (int i = 1; i < args.length; i++) {
                            argumentArray[i - 1] = args[i];
                        }
                    } else if (args.length == 8){
                        System.err.println("Not enough arguments to print");
                        System.exit(1);
                    } else {
                        System.err.println("Too many arguments");
                        System.exit(1);
                    }
                } else if(args[0].equals("-textFile")){
                    if(args.length == 10){
                        textFileFlag = true;
                        fileName = args[1];
                        for(int i = 2; i < args.length; i++){
                            argumentArray[i-2] = args[i];
                        }
                    } else if(args.length < 10){
                        System.err.println("Not enough arguments to write to file");
                        System.exit(1);
                    } else{
                        System.err.println("Too many arguments to write to file");
                        System.exit(1);
                    }
                } else if (args.length == 8){
                    for (int i = 0; i < args.length; i++) {
                        argumentArray[i] = args[i];
                    }
                } else {
                    System.err.println("Too many arguments");
                    System.exit(1);
                }
            } else {
                if (args[0].equals("-README") || args[1].equals("-README") || args[2].equals("-README") || args[3].equals("-README") ) {
                    System.out.println(getReadme());
                    System.exit(0);
                } else if ((args[0].equals("-print") || args[1].equals("-print") || args[2].equals("-print"))) {
                    printFlag = true;
                    System.err.println("Too many arguments to print!");
                    System.exit(1);
                } else if ((args[0].equals("-textFile"))){
                    textFileFlag = true;
                    fileName = args[1];
                    System.err.println("Too many arguments to write to file");
                    System.exit(1);
                } else {
                    System.err.println("Too many arguments!");
                    System.exit(1);
                }
            }
            validArguments = validateArguments(argumentArray);
            if (validArguments == true){
                airlineName = argumentArray[0];
                flightNumber = Integer.parseInt(argumentArray[1]);
                sourceAirport = argumentArray[2];
                departureTime = argumentArray[3] + " " + argumentArray[4];
                destinationAirport = argumentArray[5];
                arrivalTime = argumentArray[6] + " " + argumentArray[7];
                Flight flight = new Flight(flightNumber, sourceAirport, departureTimeInDate, destinationAirport, arrivalTimeInDate);
                Airline airline = new Airline(airlineName, flights);
                airline.addFlight(flight);
                TextParser parser = new TextParser(fileName);
                TextDumper dumper = new TextDumper(fileName);
                PrettyPrinter prettyPrinter = new PrettyPrinter(fileName);
                AbstractAirline airline1;
                if(textFileFlag == true){
                    File file = new File(fileName);
                    fileExists = file.exists();
                    if(fileExists == true) {
                        try{
                            FileReader reader = new FileReader(fileName);
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
                            System.err.println("IO Exception");
                        }
                    }else {
                        writeFlag = true;
                    }
                    if(writeFlag == true){
                        try {
                            file.createNewFile();
                            dumper.dump(airline);
                            //prettyPrinter.dump(airline);
                        } catch (IOException e){
                            System.err.println("IO Exception");
                            System.exit(1);
                        }
                    }
                }
                if (printFlag == true) {
                    System.out.println(flight.toString());
                }
            }
        } else {
            System.err.println("No command line arguments");
            System.exit(1);
        }
    }

    /*This method returns a README for the application
    *@return README Text
    * */
    private static String getReadme() {

        String title = "\nName of the project:\n" +
                "====================\n" +
                "CS410J Project 1: Designing an Airline Application";
        String applicationDescription = "\n\nApplication Description:\n" +
                "========================\n" +
                "This application parses the command line arguments provided by the user " +
                "and assigns the values to the airline and the respective flight stated by the user.\n" +
                "It also checks the validity of flight number, airport codes, and " +
                "arrival and departure times.\n"+
                "It optionally adds the airline information to a file specified on the command line.\n";
        String argumentList = "\nArguments List:" +
                "\n===============\n" +
                "Pass arguments to this program as follows:\n" +
                "  name -> The name of the airline  [Format: String]\n" +
                "  flightNumber -> The Flight Number which identifies the flight  [Format: Numeric Code]\n" +
                "  src -> Code of Departure airport  [Format: \"AAA\" (3 Letter Code)]\n" +
                "  departTime -> Departure date and time (24-hour time)  [Format: mm/dd/yyyy hh:mm]\n" +
                "  dest -> Code of Arrival airport  [Format: \"AAA\" (3 Letter Code)]\n" +
                "  arriveTime -> Arrival date and time (24-hour time)  [Format: mm/dd/yyyy hh:mm]\n\n";
        String options = "Options:" +
                "\n========\n" +
                "Options are as follows:\n" +
                "  -print -> Prints a description of the new flight\n" +
                "  -README -> Prints a README for this project and exits\n" +
                "  -textFile file -> Where to read/write the airline info\n";
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
     * This function validates the arguments provided in the command line
     * @param argumentArray
     *        The arguments passed in the command line
     * @return true if all the arguments have passed the test
     */
    public static boolean validateArguments(String[] argumentArray) {
        String airlineName = "";
        int flightNumber = 0;
        String sourceAirport = "";
        String userInputDepartureTime = "";
        String destinationAirport = "";
        String userInputArrivalTime = "";
        String expectedDatePattern = "MM/dd/yyyy HH:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(expectedDatePattern);
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
            sourceAirport = argumentArray[2];
        } else {
            System.err.println("Source Airport Code is not a 3 letter code - " + argumentArray[2]);
            System.out.println("Airport code should be of the format \"AAA\"");
            System.exit(1);
        }
        try {
            userInputDepartureTime = argumentArray[3] + " " + argumentArray[4];
            departureTimeInDate = formatter.parse(userInputDepartureTime);
        } catch (Exception e) {
            System.err.println("The Departure time does not match the format \"MM/DD/YY HH:MM\" - " + userInputDepartureTime);
            System.exit(1);
        }
        if (argumentArray[5].matches("^[a-zA-Z][a-zA-Z][a-zA-Z]") == true) {
            destinationAirport = argumentArray[5];
        } else {
            System.err.println("Destination Airport Code is not a 3 letter code - " + argumentArray[5]);
            System.out.println("Airport code should be of the format \"AAA\"");
            System.exit(1);
        }
        try {
            userInputArrivalTime = argumentArray[6] + " " + argumentArray[7];
            arrivalTimeInDate = formatter.parse(userInputArrivalTime);
        } catch (Exception e) {
            System.err.println("The Arrival time does not match the format \"MM/DD/YY HH:MM\" - " + userInputArrivalTime);
            System.exit(1);
        }
        return true;
    }
}
