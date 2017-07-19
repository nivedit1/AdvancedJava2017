package edu.pdx.cs410J.nivedit;

import edu.pdx.cs410J.AbstractAirline;

import java.io.*;
import java.util.Collection;

/**
 * Created by Niveditha Venugopal on 7/14/2017.
 */
public class TextDumper implements edu.pdx.cs410J.AirlineDumper{

    private String destinationFilename;

    public  TextDumper(String destination_filename) {
        this.destinationFilename = destination_filename;
    }

    public void dump(AbstractAirline airline){
        String airlineName = airline.getName();
        Collection<Flight>flights = airline.getFlights();
        Flight flight = flights.iterator().next();
        int flightNumber = flight.getNumber();
        String sourceAirport = flight.getSource();
        String departureTime = flight.getDepartureString();
        String destinationAirport = flight.getDestination();
        String arrivalTime = flight.getArrivalString();
        Boolean writeFlag = false;
        AbstractAirline airline1;
        try {
            File file = new File (this.destinationFilename);
            FileOutputStream out = new FileOutputStream(file, true);
            PrintWriter writer = new PrintWriter(out);
            TextParser parser = new TextParser(this.destinationFilename);
            FileReader reader = new FileReader(this.destinationFilename);
            BufferedReader bufferedReader = new BufferedReader(reader);
            //System.out.println(file.length());
            if(file.exists()) {
                if(bufferedReader.readLine() != null){
                    airline1 = parser.parse();
                    if(airlineName.equals(airline1.getName())){
                        writeFlag = true;
                    }
                    else {
                        System.err.println("This file belongs to airline -> " + airline1.getName());
                        System.err.println("But the airline provided in the command line -> " + airline.getName());
                        System.exit(1);
                    }
                }
                else {
                    System.err.println("Empty File should not be passed");
                    System.exit(1);
                }
            }
            else {
                writeFlag = true;
            }
            if(writeFlag == true){
                writer.println("<record>");
                writer.println("<airlineName>" + airlineName + "</airlineName>");
                writer.println("<flightNumber>" + flightNumber +"</flightNumber>");
                writer.println("<sourceAirport>" + sourceAirport + "</sourceAirport>");
                writer.println("<departureTime>" + departureTime + "</departureTime>");
                writer.println("<destinationAirport>" + destinationAirport + "</destinationAirport>");
                writer.println("<arrivalTime>" + arrivalTime + "</arrivalTime>");
                writer.println("</record>");
                writer.close();
            }
        }
        catch (IOException e){
            System.err.println("File does not exist");
        }
    }
}