package edu.pdx.cs410J.nivedit;

/**
 * This class dumps an <code>airline</code> to a text file
 * or standard out in a human readable format.
 * @author Niveditha Venugopal
 */
public class PrettyPrinter {

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
}
