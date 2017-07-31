package edu.pdx.cs410J.nivedit;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;
import edu.pdx.cs410J.AirportNames;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * This class dumps an <code>airline</code> to a text file
 * or standard out in a human readable format.
 * @author Niveditha Venugopal
 */
public class PrettyPrinter implements AirlineDumper{

    private HttpServletResponse response;

    /**
     * Creates a new PrettyPrinter that writes to a file
     * in a human readable format. If a file does not exist,
     * it is created.
     * @param response
     *        The name of the file to be pretty printed to.
     */
    public PrettyPrinter(HttpServletResponse response){

        this.response = response;
    }

    /**
     * This class defines a comparator for pretty printer class.
     * @author Niveditha Venugopal
     */
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
     * human readable format to the desired destination.
     * @param airline
     *        the <code>airline</code> to be dumped.
     * @throws IOException
     *        Signals that an I/O exception of some sort has occurred.
     *        This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    @Override
    public void dump(AbstractAirline airline) throws IOException {
        Collection<Flight> flights = airline.getFlights();
        SortedSet<Flight> flightSortedSet = new TreeSet<Flight>(Comparators.sourceAirportAndDepartureTime);
        flightSortedSet.addAll(flights);
        PrintWriter writer = response.getWriter();
        writer.println();
        writer.println("Airline Information");
        writer.println("===================");
        writer.println("Airline Name:" + " " + airline.getName());
        writer.println();
        int i = 1;
        Iterator iterator = flightSortedSet.iterator();
        while (iterator.hasNext()){
            Flight flight = (Flight)iterator.next();
            long flightDuration = flight.getArrival().getTime()-flight.getDeparture().getTime();
            long flightDurationInHours = flightDuration/(60*60*1000);
            long flightDurationInMinutes = flightDuration/(60*1000)%60;
            writer.println("Flight " + i + " " + "Details:");
            writer.println("=================");
            writer.println("Flight Number:" +" "+ flight.getNumber());
            writer.println("Source Airport:" + " "+ flight.getSource().toUpperCase()+"(" +
                    AirportNames.getName(flight.getSource().toUpperCase()) + ")");
            writer.println("Departure Time:" + " "+ flight.getDepartureString());
            writer.println("Destination Airport:" + " "+ flight.getDestination().toUpperCase() + "("+
                    AirportNames.getName(flight.getDestination().toUpperCase()) + ")");
            writer.println("Arrival Time:" + " "+ flight.getArrivalString());
            writer.println("Flight Duration:" + flightDurationInHours + " hours and " + flightDurationInMinutes + " minutes");
            writer.println();
            i +=1;
        }
        writer.close();
    }
}

