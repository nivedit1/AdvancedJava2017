package edu.pdx.cs410J.nivedit;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * An integration test for the {@link Project3} main class.
 */
public class Project2IT extends InvokeMainTestCase {

    /*private static File airlineFile;

    @BeforeClass
    public static void createTempDirectoryForAirlineFile() throws IOException {
        File tmpDirectory = new File(System.getProperty("java.io.tmpdir"));
        airlineFile = new File(tmpDirectory, "airline.txt");
    }

    @AfterClass
    public static void deleteTempDirectoryForAirlineFile() {
        if (airlineFile.exists()) {
            assertTrue(airlineFile.delete());
        }
    }

    private MainMethodResult invokeProject2(String... args) {
        return invokeMain(Project3.class, args);
    }

    private String readFile(File file) throws IOException {

            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            Stream<String> lines = br.lines();
            lines.forEach(line -> {
                sb.append(line).append("\n");
            });
            br.close();
            return sb.toString();
    }

    @Test
    public void test1CreateNewAirlineFileWhenFileDoesNotExist() throws IOException {
        assertThat(airlineFile.exists(), equalTo(false));

        invokeProject2("-textFile", airlineFile.getAbsolutePath(), "MyAirline",
                "123", "PDX", "7/16/2017", "15:00", "LAX", "7/16/2017", "18:00");

        String fileContents = readFile(airlineFile);
        assertThat(fileContents, containsString("123"));
    }*/

   /* @Test
    public void test2AddFlightToExistingAirlineFile() throws IOException {
        assertThat(airlineFile.exists(), equalTo(true));

        invokeProject2("-textFile", airlineFile.getAbsolutePath(), "MyAirline",
                "234", "PDX", "7/17/2017", "15:00", "LAX", "7/17/2017", "18:00");

        String fileContents = readFile(airlineFile);
        assertThat(fileContents, containsString("123"));
        assertThat(fileContents, containsString("234"));
    }
*/
}