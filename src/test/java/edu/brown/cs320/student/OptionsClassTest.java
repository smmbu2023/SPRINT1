/**
 * @author: Safae Merigh
 * @version: 1.0.0
 */

package edu.brown.cs320.student;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

/**
 * Unit test for simple App.
 */
public class OptionsClassTest 
{

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();


    @Test
    public void invalidFlagArgument_A()
    {
        exit.expectSystemExitWithStatus(1);
        String[] args = new String[] {"-b"};
        new Options(args);
    }

    @Test
    public void invalidFlagArgument_B()
    {
        exit.expectSystemExitWithStatus(1);
        String[] args = new String[] {"x"};
        new Options(args);
    }

    @Test
    public void noFileNameArgument_A()
    {
        exit.expectSystemExitWithStatus(1);
        String[] args = new String[] {"-f"};
        new Options(args);
    }


    @Test
    public void noFileNameArgument_B()
    {
        exit.expectSystemExitWithStatus(1);
        String[] args = new String[] {"-f -c ASDF"};
        new Options(args);
    }

    @Test
    public void noSearchTextArgument_A()
    {
        exit.expectSystemExitWithStatus(1);
        String[] args = new String[] {"-s -c ASDF"};
        new Options(args);
    }

    @Test
    public void noSearchTextArgument_B()
    {
        exit.expectSystemExitWithStatus(1);
        String[] args = new String[] {"-s"};
        new Options(args);
    }

    //------------------------------------------------------------------------------------------------/
    @Test
    public void noColumnIndexValue_A()
    {
        exit.expectSystemExitWithStatus(1);
        String[] args = new String[] {"-f", "TEST.csv",  "-s", "Lorem", "-i"};
        new Options(args);
    }

    @Test
    public void badColumnIndexValue()
    {
        exit.expectSystemExitWithStatus(1);
        String[] args = new String[] {"-f", "TEST.csv",  "-s", "Lorem", "-i", "NOTNUMBER"};
        new Options(args);
    }

    @Test
    public void negativeColumnIndexValue()
    {
        exit.expectSystemExitWithStatus(1);
        String[] args = new String[] {"-f", "TEST.csv", "-s", "Lorem", "-i", "-3"};
        new Options(args);
    }

    //------------------------------------------------------------------------------------------------/
    @Test
    public void noColumnNameValue_A()
    {
        exit.expectSystemExitWithStatus(1);
        String[] args = new String[] {"-c"};
        new Options(args);
    }

    @Test
    public void noColumnNameValue_B()
    {
        exit.expectSystemExitWithStatus(1);
        String[] args = new String[] {"-c", ""};
        new Options(args);
    }

    //---------------------------------------------------------------------------------------------------//
    @Test
    public void testAccessors_A()
    {
        String[] args = new String[] {"-f", "TEST1.csv", "-c", "COL1", "-s", "Lorem"};
        Options options = new Options(args);

        assertTrue( options.getFileName() == "TEST1.csv" );
        assertTrue( options.getSearchText() == "Lorem" );
        assertTrue( options.getSearchColumnName() == "COL1" );
        assertTrue( options.getIsHeaderPresent() == true);
        assertTrue(options.validCommandLine() == true);

        // Filename and search are provided.
        args = new String[] {"-f", "TEST.csv", "-s", "Lorem"};
        options = new Options(args);
        assertTrue(options.validCommandLine() == true);

        // Filename is missing
        args = new String[] {"-s", "Lorem"};
        options = new Options(args);
        assertTrue(options.validCommandLine() == false);

        // Search Text is missing
        args = new String[] {"-f", "TEST1.csv"};
        options = new Options(args);
        assertTrue(options.validCommandLine() == false);

        // No header
        args = new String[] {"-f", "TEST.csv", "-s", "Lorem"};
        options = new Options(args);
        assertTrue(options.getIsHeaderPresent() == false);

        // Header flag at the begining
        args = new String[] {"-h", "-f", "TEST.csv", "-s", "Lorem"};
        options = new Options(args);
        assertTrue(options.getIsHeaderPresent() == true);

        // Header flag in the middle
        args = new String[] {"-f", "TEST.csv", "-h", "-s", "Lorem"};
        options = new Options(args);
        assertTrue(options.getIsHeaderPresent() == true);

        // Header flag at the end
        args = new String[] {"-f", "TEST.csv", "-s", "Lorem", "-h"};
        options = new Options(args);
        assertTrue(options.getIsHeaderPresent() == true);
        
    }

}
