package edu.pdx.cs410J.nivedit.client;

import com.google.gwt.user.client.ui.*;
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

    private TextArea textArea;

    /**
     * Creates a new PrettyPrinter that writes to a file
     * in a human readable format. If a file does not exist,
     * it is created.
     * @param textArea
     *        The destination to be pretty printed to.
     */
    public PrettyPrinter(TextArea textArea){
        this.textArea = textArea;
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        stringBuilder.append("Airline Information");
        stringBuilder.append("\n");
        stringBuilder.append("===================");
        stringBuilder.append("\n");
        stringBuilder.append("Airline Name:" + " " + airline.getName());
        stringBuilder.append("\n\n");
        int i = 1;
        Iterator iterator = flightSortedSet.iterator();
        while (iterator.hasNext()){
            Flight flight = (Flight)iterator.next();
            long flightDuration = flight.getArrival().getTime()-flight.getDeparture().getTime();
            long flightDurationInHours = flightDuration/(60*60*1000);
            long flightDurationInMinutes = flightDuration/(60*1000)%60;
            stringBuilder.append("Flight " + i + " " + "Details:");
            stringBuilder.append("\n");
            stringBuilder.append("=================");
            stringBuilder.append("\n");
            stringBuilder.append("Flight Number:" +" "+ flight.getNumber());
            stringBuilder.append("\n");
            stringBuilder.append("Source Airport:" + " "+ flight.getSource().toUpperCase()+"(" +
                    AirportNames.getName(flight.getSource().toUpperCase()) + ")");
            stringBuilder.append("\n");
            stringBuilder.append("Departure Time:" + " "+ flight.getDepartureString());
            stringBuilder.append("\n");
            stringBuilder.append("Destination Airport:" + " "+ flight.getDestination().toUpperCase() + "("+
                    AirportNames.getName(flight.getDestination().toUpperCase()) + ")");
            stringBuilder.append("\n");
            stringBuilder.append("Arrival Time:" + " "+ flight.getArrivalString());
            stringBuilder.append("\n");
            stringBuilder.append("Flight Duration:" + flightDurationInHours + " hours and " + flightDurationInMinutes + " minutes");
            stringBuilder.append("\n\n");
            i +=1;
        }
        this.textArea.setText(stringBuilder.toString());
    }
}

