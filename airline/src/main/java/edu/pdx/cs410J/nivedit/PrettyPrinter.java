package edu.pdx.cs410J.nivedit;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

import java.io.IOException;

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

    /**
     * Dumps the contents of the <code>airline</code> in a
     * human readable format to the desired destination
     * @param airline
     *        the <code>airline</code> to be dumped
     * @throws IOException
     */
    @Override
    public void dump(AbstractAirline airline) throws IOException {

    }
}
