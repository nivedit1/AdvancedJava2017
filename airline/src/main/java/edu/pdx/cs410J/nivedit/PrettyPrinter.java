package edu.pdx.cs410J.nivedit;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * This class dumps an <code>airline</code> to a text file
 * or standard out in a human readable format.
 * @author Niveditha Venugopal
 */
public class PrettyPrinter implements AirlineDumper {

    private String destinationFilename;

    /**
     * Creates a new PrettyPrinter that writes to a file
     * in a human readable format. If a file does not exist,
     * it is created
     * @param destinationFilename
     *        The name of the file to be pretty printed to
     */
    public PrettyPrinter(String destinationFilename){

        this.destinationFilename = destinationFilename;
    }

    public static class Comparators {
        public static Comparator<Flight> sourceAirportAndDepartureTime = new Comparator<Flight>() {
            @Override
            public int compare(Flight flight1, Flight flight2) {
                return flight1.compareTo(flight2);
            }
        };
    }

    /**
     * Dumps the contents of the <code>airline</code> in a
     * human readable format to the desired destination
     * @param airline
     *        the <code>airline</code> to be dumped
     * @throws IOException
     */
    @Override
    public void dump(AbstractAirline airline) throws IOException {
        Collection<Flight> flights = airline.getFlights();
        SortedSet<Flight> flightSortedSet = new TreeSet<Flight>(Comparators.sourceAirportAndDepartureTime);
        flightSortedSet.addAll(flights);
        //System.out.println(flightSortedSet);
        File file = new File(this.destinationFilename);
        FileOutputStream out = new FileOutputStream(file,true);
        PrintWriter writer = new PrintWriter(out);
        writer.println("Airline Name:" + " " + airline.getName());
        int i = 1;
        while (flightSortedSet.iterator().hasNext()){
            Flight flight = flightSortedSet.iterator().next();
            long flightDuration = flight.getArrival().getTime()-flight.getDeparture().getTime();
            writer.println("Flight" + i + " " + "Details:");
            writer.println("Flight Number:" +" "+ flight.getNumber());
            writer.println("Flight Source:" + " "+ flight.getSource());
            writer.println("Departure Time:" + " "+ flight.getDepartureString());
            writer.println("Flight Destination:" + " "+ flight.getDestination());
            writer.println("Arrival Time:" + " "+ flight.getArrivalString());
            writer.println("Flight Duration:" + flightDuration);
            i +=1;
        }
        writer.close();
    }
}

