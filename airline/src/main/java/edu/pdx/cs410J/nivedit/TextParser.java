package edu.pdx.cs410J.nivedit;

import edu.pdx.cs410J.AbstractAirline;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by Niveditha Venugopal on 7/14/2017.
 */
public class TextParser implements edu.pdx.cs410J.AirlineParser{

    private String sourceFilename;

    public TextParser(String sourceFilename){
        this.sourceFilename = sourceFilename;
    }

    public AbstractAirline parse(){
        String line = null;
        StringBuffer stringBuffer = new StringBuffer("");
        String airlineName = "";
        int flightNumber = 0;
        String sourceAirport = "";
        String departureTime = "";
        String destinationAirport = "";
        String arrivalTime = "";
        Collection<Flight> flights = new ArrayList<>();
        Airline airline = new Airline("name", flights);
        Flight flight = new Flight(0,"","","","");

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
                airlineName = "";
                flightNumber = -1;
                sourceAirport = "";
                departureTime = "";
                destinationAirport ="";
                arrivalTime ="";
            }
            else if(currentLine.contains("<airlineName>")){
                int startIndex = currentLine.indexOf("<airlineName>") + "<airlineName>".length();
                int endIndex = currentLine.indexOf("</airlineName>");
                airlineName = currentLine.substring(startIndex,endIndex);
            }
            else if(currentLine.contains("<flightNumber>")){
                int startIndex = currentLine.indexOf("<flightNumber>") + "<flightNumber>".length();
                int endIndex = currentLine.indexOf("</flightNumber>");
                flightNumber = Integer.parseInt(currentLine.substring(startIndex,endIndex));
            }
            else if(currentLine.contains("<sourceAirport>")){
                int startIndex = currentLine.indexOf("<sourceAirport>") + "<sourceAirport>".length();
                int endIndex = currentLine.indexOf("</sourceAirport>");
                sourceAirport = currentLine.substring(startIndex,endIndex);
            }
            else if(currentLine.contains("<departureTime>")){
                int startIndex = currentLine.indexOf("<departureTime>") + "<departureTime>".length();
                int endIndex = currentLine.indexOf("</departureTime>");
                departureTime = currentLine.substring(startIndex,endIndex);
            }
            else if(currentLine.contains("<destinationAirport>")){
                int startIndex = currentLine.indexOf("<destinationAirport>") + "<destinationAirport>".length();
                int endIndex = currentLine.indexOf("</destinationAirport>");
                destinationAirport = currentLine.substring(startIndex,endIndex);
            }
            else if(currentLine.contains("<arrivalTime>")){
                int startIndex = currentLine.indexOf("<arrivalTime>") + "<arrivalTime>".length();
                int endIndex = currentLine.indexOf("</arrivalTime>");
                arrivalTime = currentLine.substring(startIndex,endIndex);
            }
            else if (currentLine.contains("</record>")){
                //To be implemented
                //Project2.validateArguments(argumentArray);
                airline = new Airline(airlineName, flights);
                flight = new Flight(flightNumber, sourceAirport, departureTime, destinationAirport, arrivalTime);
                airline.addFlight(flight);
            }
            else {
                System.err.println("Malformatted File -> " + currentLine);
                System.exit(1);
            }
            stringBuff = stringBuff.substring((stringBuff.indexOf("\n")+1),stringBuff.length());
        }
        return airline;
    }
}
