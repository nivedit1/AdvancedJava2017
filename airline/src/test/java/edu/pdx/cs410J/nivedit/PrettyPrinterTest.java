package edu.pdx.cs410J.nivedit;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for {@link Flight}class
 * @author Niveditha Venugopal
 */
public class PrettyPrinterTest {

    /*@Test
    public void testPrettyPrinterDumpMethod() throws IOException{
        String expectedDatePattern = "MM/dd/yyyy HH:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(expectedDatePattern);
        formatter.setLenient(false);
        Date departureTimeInDate1 = new Date();
        Date departureTimeInDate2 = new Date();
        Date departureTimeInDate3 = new Date();
        try {
            departureTimeInDate1 = formatter.parse("06/07/2017 09:45");
            departureTimeInDate2 = formatter.parse("07/07/2017 09:45");
            departureTimeInDate3 = formatter.parse("05/11/2014 03:45");
        }
        catch (ParseException e){
            System.err.println("Cannot parse");
        }
        Flight flight1 = new Flight(101,"PDX",departureTimeInDate2, "HOU",new Date());
        Flight flight2 = new Flight(101,"PDX",departureTimeInDate1, "HOU",new Date());
        Flight flight3 = new Flight (103, "PDX", departureTimeInDate3, "LAX", new Date());
        Collection<Flight> flights = new ArrayList<>();
        Airline airline = new Airline("airline1", flights);
        airline.addFlight(flight1);
        airline.addFlight(flight2);
        airline.addFlight(flight3);
        PrettyPrinter pp = new PrettyPrinter("file.txt");
        pp.dump(airline);
    }*/
}
