package edu.pdx.cs410J.nivedit;

import edu.pdx.cs410J.InvokeMainTestCase;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project2} main class.
 */
public class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    /*private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project2.class, args );
    }


   *//* Tests that invoking the main method with no arguments issues an error
   *//*
  @Test
  public void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }*/
}