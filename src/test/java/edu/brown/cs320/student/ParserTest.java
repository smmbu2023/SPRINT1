/**
 * @author: Safae Merigh
 * @version: 1.0.0
 */

package edu.brown.cs320.student;
import java.util.List;

import org.junit.Test;

import edu.brown.cs320.student.csv.Parser;
import edu.brown.cs320.student.csv.rowcreators.FactoryFailureException;
import edu.brown.cs320.student.csv.rowcreators.FillMissingColumnsInRow;
import edu.brown.cs320.student.csv.rowcreators.TabSeparatedFieldsFromRow;
import edu.brown.cs320.student.csv.rowcreators.UserClassFromRow;
import edu.brown.cs320.student.csv.rowcreators.User;

import static org.junit.Assert.assertTrue;
import java.io.StringReader;

/**
 * Unit test for simple App.
 */
public class ParserTest 
{
    /**
     * HeaderAndTwoLines
     */
    @Test
    public void HeaderAndTwoLines()
    {
                // This is an instance of one row formatter.  And parse the data provided by the Reader.
        //
        TabSeparatedFieldsFromRow tsfFormatter = new TabSeparatedFieldsFromRow();

        StringReader stringReader = new StringReader("FirstName,LastName,City Name\nFN1,LN1,CT1\nFN2,LN2,CT2\n");

        Parser<String> parser = new Parser<String>(stringReader, tsfFormatter, true, ",");

        try {
            parser.Parse();
        } catch (Exception e) {

        }
        assertTrue( parser.getColumnCount() == 3 );
        assertTrue( parser.getRecordCount() == 2 );

        String[] colNames = parser.getColumnNames();

        assertTrue( colNames[0].equals("FirstName")) ;
        assertTrue( colNames[1].equals("LastName"));
        assertTrue( colNames[2].equals("City Name"));

        List<List<String>> records = parser.getRawRecords();

        assertTrue( records.get(1).get(0).equals("FN1")) ;
        assertTrue( records.get(1).get(1).equals("LN1")) ;
        assertTrue( records.get(1).get(2).equals("CT1")) ;

        assertTrue( records.get(2).get(0).equals("FN2")) ;
        assertTrue( records.get(2).get(1).equals("LN2")) ;
        assertTrue( records.get(2).get(2).equals("CT2")) ;
    }

    /**
     * Does not interpret the first row as a header
     */
    @Test
    public void noHeaderAndTwoLines()
    {
                // This is an instance of one row formatter.  And parse the data provided by the Reader.
        //
        TabSeparatedFieldsFromRow userFormatter = new TabSeparatedFieldsFromRow();

        StringReader stringReader = new StringReader("FirstName,LastName,City Name\nFN1,LN1,CT1\nFN2,LN2,CT2\n");

        Parser<String> parser = new Parser<String>(stringReader, userFormatter, false, ",");

        try {
            parser.Parse();

        } catch (FactoryFailureException e) {
            assertTrue("Factory Failure Exception: " + e.getMessage(), false);

        } catch (Exception e) {
            assertTrue(e.getMessage(), false);
        }

        assertTrue( parser.getColumnCount() == 3 );
        assertTrue( parser.getRecordCount() == 3 );

        String[] colNames = parser.getColumnNames();

        assertTrue(colNames == null) ;

        List<List<String>> records = parser.getRawRecords();

        assertTrue( records.get(0).get(0).equals("FirstName")) ;
        assertTrue( records.get(0).get(1).equals("LastName")) ;
        assertTrue( records.get(0).get(2).equals("City Name")) ;

        assertTrue( records.get(1).get(0).equals("FN1")) ;
        assertTrue( records.get(1).get(1).equals("LN1")) ;
        assertTrue( records.get(1).get(2).equals("CT1")) ;

        assertTrue( records.get(2).get(0).equals("FN2")) ;
        assertTrue( records.get(2).get(1).equals("LN2")) ;
        assertTrue( records.get(2).get(2).equals("CT2")) ;
    }

    /**
     * Missing column values
     */
  //  @Test
    public void missingColumnValuesWithCommas()
    {
                // This is an instance of one row formatter.  And parse the data provided by the Reader.
        //
        FillMissingColumnsInRow shapeCorrection = new FillMissingColumnsInRow();

        StringReader stringReader = new StringReader("FirstName,LastName,City Name\nFN1,,CT1\nFN2,LN2,\n");

        Parser<List<String>> parser = new Parser<List<String>>(stringReader, shapeCorrection, true, ",");

        try {
            parser.Parse();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            assertTrue(false);

        }

        shapeCorrection.expandToColumnCount(parser.getColumnCount());

        assertTrue( parser.getColumnCount() == 3 );
        assertTrue( parser.getRecordCount() == 2 );

        String[] colNames = parser.getColumnNames();

        assertTrue( colNames[0].equals("FirstName")) ;
        assertTrue( colNames[1].equals("LastName"));
        assertTrue( colNames[2].equals("City Name"));

        List<List<String>> records = null;

       records = parser.getFactoryRecords();
       
        assertTrue( records.get(0).get(0).equals("FN1")) ;
        assertTrue( records.get(0).get(1).equals("")) ;
        assertTrue( records.get(0).get(2).equals("CT1")) ;

        assertTrue( records.get(1).get(0).equals("FN2")) ;
        assertTrue( records.get(1).get(1).equals("LN2")) ;
        assertTrue( records.get(1).get(2).equals("")) ;
    }


    // Parse to a User class with good column data
    @Test
    public void parseToUserClassGoodData()
    {
                // This is an instance of one row formatter.  And parse the data provided by the Reader.
        //
        UserClassFromRow userFormatter = new UserClassFromRow();

        StringReader stringReader = new StringReader("FirstName,LastName,Birth Date\nFN1,LN1,13-Dec-2000\nFN2,LN2,16-Jan-2010\n");

        Parser<User> parser = new Parser<User>(stringReader, userFormatter, true, ",");

        List<User> users = null;

        try {
            users = parser.Parse();

        } catch (FactoryFailureException e) {
            System.out.println(e.getMessage());                
    
        } catch (Exception e) {
            System.out.println(e.getMessage());                
        }

        for (User user : users) {
            System.out.println(user.toString());
        }

    }


    // Parse to a User class with bad column data
    @Test
    public void parseToUserClassBadData()
    {
        Boolean expectedExceptionRaised = false;
        // This is an instance of one row formatter.  And parse the data provided by the Reader.
        //
        UserClassFromRow userFormatter = new UserClassFromRow();

        StringReader stringReader = new StringReader("FirstName,LastName,Birth Date\nFN1,LN1,13-Dec-2000\nFN2,LN2,12-ABC-2010\n");

        Parser<User> parser = new Parser<User>(stringReader, userFormatter, true, ",");

        try {
            parser.Parse();

        } catch (FactoryFailureException e) {
             System.out.println(e.getMessage());  
             expectedExceptionRaised = true;

         } catch (Exception e) {
             System.out.println(e.getMessage());                
         }

        assertTrue("FactoryFailureException was not raised", expectedExceptionRaised);
    }

}
