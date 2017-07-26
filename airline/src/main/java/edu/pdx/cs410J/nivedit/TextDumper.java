package edu.pdx.cs410J.nivedit;

import edu.pdx.cs410J.AbstractAirline;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class dumps an <code>airline</code> to a destination.
 * @author Niveditha Venugopal
 */
public class TextDumper implements edu.pdx.cs410J.AirlineDumper{

    private String destinationFilename;

    /**
     * Creates a new text dumper that dumps to a file of a given name
     * If the file does not exist, it is created.
     * @param destination_filename
     *        The name of the file to be dumped to.
     */
    public  TextDumper(String destination_filename) {

        this.destinationFilename = destination_filename;
    }

    /**
     * Dumps the contents of the airline to the desired destination.
     * @param airline
     *        the <code>airline</code> to be dumped.
     */
    public void dump(AbstractAirline airline) {
        String airlineName = airline.getName();
        Collection<Flight>flights = airline.getFlights();
        Iterator iterator = flights.iterator();
        try {
            File file = new File (this.destinationFilename);
            FileWriter out = new FileWriter(file, false);
            PrintWriter writer = new PrintWriter(out);
            while (iterator.hasNext()){
                Flight flight = (Flight)iterator.next();
                int flightNumber = flight.getNumber();
                String sourceAirport = flight.getSource();
                String departureTime = flight.getDepartureString();
                String destinationAirport = flight.getDestination();
                String arrivalTime = flight.getArrivalString();
                writer.println("<record>");
                writer.println("<airlineName>" + airlineName + "</airlineName>");
                writer.println("<flightNumber>" + flightNumber +"</flightNumber>");
                writer.println("<sourceAirport>" + sourceAirport + "</sourceAirport>");
                writer.println("<departureTime>" + departureTime + "</departureTime>");
                writer.println("<destinationAirport>" + destinationAirport + "</destinationAirport>");
                writer.println("<arrivalTime>" + arrivalTime + "</arrivalTime>");
                writer.println("</record>");
            }
            writer.close();
        } catch (IOException e){
                System.err.println("File does not exist");
        }
    }
}

