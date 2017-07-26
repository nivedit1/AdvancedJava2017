package edu.pdx.cs410J.nivedit;

import edu.pdx.cs410J.AbstractAirline;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


/**
 * This class parses the text output that contains <code>airline</code>
 * information given to it.
 * @author Niveditha Venugopal
 */
public class TextParser implements edu.pdx.cs410J.AirlineParser{

    private String sourceFilename;

    /**
     * Creates a new text parser that reads its input from a file of a given name.
     * @param sourceFilename
     *        The name of the file to be parsed.
     */
    public TextParser(String sourceFilename){

        this.sourceFilename = sourceFilename;
    }

    /**
     * Parses the specified input source and from it creates an airline.
     * @return airline that was parsed from the file.
     */
    public AbstractAirline parse(){
        String line = null;
        StringBuffer stringBuffer = new StringBuffer("");
        String airlineName = "";
        int flightNumber = 0;
        String sourceAirport = "";
        String departureTime = "" ;
        Date departureTimeInDate = new Date();
        String destinationAirport = "";
        String arrivalTime = "";
        Date arrivalTimeInDate = new Date();
        Collection<Flight> flights = new ArrayList<>();
        Airline airline = new Airline("", flights);
        Flight flight = new Flight(0,"",departureTimeInDate,"",arrivalTimeInDate);
        String expectedDatePattern = "MM/dd/yyyy h:mm a";
        SimpleDateFormat formatter = new SimpleDateFormat(expectedDatePattern);
        formatter.setLenient(false);
        int recordComplete = 0;
        try{
            FileReader reader = new FileReader(this.sourceFilename);
            BufferedReader bufferedReader = new BufferedReader(reader);
            try{
                while ((line = bufferedReader.readLine())!= null){
                    stringBuffer.append(line);
                    stringBuffer.append("\n");
                }
                bufferedReader.close();
            }
            catch (IOException e){
                System.err.println("IO Failed");
            }
        }
        catch (FileNotFoundException f){
            System.err.println("File Not Found!");
        }
        String stringBuff = stringBuffer.toString();
        String currentLine = "";
        while((stringBuff.indexOf("\n"))> 0 ){
            currentLine = stringBuff.substring(0,(stringBuff.indexOf("\n")));
            if(currentLine.contains("<record>")){
                if(recordComplete != 0 && recordComplete != 8){
                    System.err.println("Flight information not complete in the file");
                    System.exit(1);
                }
                recordComplete = 0;
                recordComplete += 1;
                airlineName = "";
                flightNumber = -1;
                sourceAirport = "";
                departureTime = "";
                destinationAirport ="";
                arrivalTime ="";
            } else if(currentLine.contains("<airlineName>") && currentLine.contains("</airlineName>")){
                int startIndex = currentLine.indexOf("<airlineName>") + "<airlineName>".length();
                int endIndex = currentLine.indexOf("</airlineName>");
                airlineName = currentLine.substring(startIndex,endIndex);
                recordComplete += 1;
            } else if(currentLine.contains("<flightNumber>") && currentLine.contains("</flightNumber>")){
                int startIndex = currentLine.indexOf("<flightNumber>") + "<flightNumber>".length();
                int endIndex = currentLine.indexOf("</flightNumber>");
                try {
                    flightNumber = Integer.parseInt(currentLine.substring(startIndex,endIndex));
                    if (flightNumber < 0) {
                        System.err.println("Flight number in the file cannot be negative " + flightNumber);
                        System.exit(1);
                    }
                    recordComplete += 1;
                } catch (Exception e) {
                    System.err.println("Flight Number in the file is not Integer " + currentLine.substring(startIndex,endIndex));
                    System.exit(1);
                }
            } else if(currentLine.contains("<sourceAirport>") && currentLine.contains("</sourceAirport>")){
                int startIndex = currentLine.indexOf("<sourceAirport>") + "<sourceAirport>".length();
                int endIndex = currentLine.indexOf("</sourceAirport>");
                if (currentLine.substring(startIndex,endIndex).matches("^[a-zA-Z][a-zA-Z][a-zA-Z]") == true) {
                    sourceAirport = currentLine.substring(startIndex,endIndex);
                    recordComplete += 1;
                } else {
                    System.err.println("Source Airport Code in the file is not a 3 letter code - " + currentLine.substring(startIndex,endIndex));
                    System.out.println("Airport code should be of the format \"AAA\"");
                    System.exit(1);
                }
            } else if(currentLine.contains("<departureTime>") && currentLine.contains("</departureTime>")){
                int startIndex = currentLine.indexOf("<departureTime>") + "<departureTime>".length();
                int endIndex = currentLine.indexOf("</departureTime>");
                try {
                    departureTime = currentLine.substring(startIndex,endIndex);
                    departureTimeInDate = formatter.parse(departureTime);
                    recordComplete += 1;
                } catch (Exception e) {
                    System.err.println("The Departure time in the file does not match the format \"MM/DD/YY HH:MM\" - " + departureTime);
                    System.exit(1);
                }
            } else if(currentLine.contains("<destinationAirport>") && currentLine.contains("</destinationAirport>")){
                int startIndex = currentLine.indexOf("<destinationAirport>") + "<destinationAirport>".length();
                int endIndex = currentLine.indexOf("</destinationAirport>");
                if (currentLine.substring(startIndex,endIndex).matches("^[a-zA-Z][a-zA-Z][a-zA-Z]") == true) {
                    destinationAirport = currentLine.substring(startIndex,endIndex);
                    recordComplete += 1;
                } else {
                    System.err.println("Destination Airport Code in the file is not a 3 letter code - " + currentLine.substring(startIndex,endIndex));
                    System.out.println("Airport code should be of the format \"AAA\"");
                    System.exit(1);
                }
            } else if(currentLine.contains("<arrivalTime>") && currentLine.contains("</arrivalTime>")) {
                int startIndex = currentLine.indexOf("<arrivalTime>") + "<arrivalTime>".length();
                int endIndex = currentLine.indexOf("</arrivalTime>");
                try {
                    arrivalTime = currentLine.substring(startIndex,endIndex);
                    arrivalTimeInDate = formatter.parse(arrivalTime);
                    recordComplete += 1;
                } catch (Exception e) {
                    System.err.println("The Arrival time in the file does not match the format \"MM/DD/YY HH:MM\" - " + arrivalTime);
                    System.exit(1);
                }
            } else if (currentLine.contains("</record>")){
                recordComplete += 1;
                if(recordComplete == 8){
                    airline = new Airline(airlineName, flights);
                    flight = new Flight(flightNumber, sourceAirport, departureTimeInDate, destinationAirport, arrivalTimeInDate);
                    airline.addFlight(flight);
                } else {
                    System.err.println("Flight information not complete in the file");
                    System.exit(1);
                }
            } else {
                System.err.println("Malformatted File -> " + currentLine);
                System.exit(1);
            }
            stringBuff = stringBuff.substring((stringBuff.indexOf("\n")+1),stringBuff.length());
        }
        if(recordComplete != 8){
            System.err.println("Flight information not complete in the file");
            System.exit(1);
        }
        return airline;
    }
}
