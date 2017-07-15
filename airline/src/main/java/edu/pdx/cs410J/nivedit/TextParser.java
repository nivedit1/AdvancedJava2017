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
        String line;
        Collection<Flight> flights = new ArrayList<>();

        try{
            BufferedReader reader = new BufferedReader(new FileReader(this.sourceFilename));
            try{
                line = reader.readLine();
                System.out.println(line);
            }
            catch (IOException e){
                System.err.println("IO Failed");
            }
        }
        catch (FileNotFoundException f){
            System.err.println("File Not Found!");
        }
        Airline airline = new Airline("Name",flights);
        return airline;
    }
}
