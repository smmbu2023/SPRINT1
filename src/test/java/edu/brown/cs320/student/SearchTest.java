package edu.brown.cs320.student;
import java.util.List;

import org.junit.Test;

import edu.brown.cs320.student.csv.Parser;
import edu.brown.cs320.student.csv.Searcher;
//import edu.brown.cs320.student.csv.rowcreators.FactoryFailureException;
import edu.brown.cs320.student.csv.rowcreators.DefaultCreatorFromRow;
//import edu.brown.cs320.student.csv.rowcreators.TabSeparatedFieldsFromRow;

import static org.junit.Assert.assertTrue;
import java.io.StringReader;


public class SearchTest {

    /*
     * Ensure Constructor throws an exception when records = null
     */
    @Test(expected = IllegalArgumentException.class)
    public void exceptionOnNullArgument() {
        new Searcher(null);
    }
    

    /**
     * Test default findString
     */
    @Test
    public void searchThroughAllColumns() {

        StringReader stringReader = new StringReader("FirstName,LastName,City Name\nFN1,LN1,CT1\nFN2,LN2,CT2\n");

        DefaultCreatorFromRow defaultFormatter = new DefaultCreatorFromRow();
        Parser<List<String>> parser = new Parser<List<String>>(stringReader, defaultFormatter,
            true, ",");

        try {
            parser.Parse();
        } catch (Exception e) {
            assertTrue("Parsing exception: " + e.getMessage(), false);
        }

        List<List<String>> csvData = parser.getRawRecords();

        Searcher search = new Searcher(csvData);

        List<List<String>> searchResults;


        // Searching for LN should yield two rows
        searchResults = search.findString("LN");
        assertTrue(searchResults.size() == 2);

        // Searching for LN1 should yield one row
        searchResults = search.findString("LN1");
        assertTrue(searchResults.size() == 1);
        
        // Searching for a non-existing string should yield zero rows
        searchResults = search.findString("UNKNOWN");
        assertTrue((searchResults == null) || (searchResults.size() == 0));

        // Searching for LN in column 0 should yield zero rows
        searchResults = search.findString("LN", 0);
        assertTrue((searchResults == null) || (searchResults.size() == 0));

        // Searching for LN in column 1 should yield two rows
        searchResults = search.findString("LN", 1);
        assertTrue(searchResults.size() == 2);

        // Searching for LN in column 2 should yield zero rows
        searchResults = search.findString("LN", 2);
        assertTrue((searchResults == null) || (searchResults.size() == 0));

        // Searching for LN in column 3 (non-existent) should yield zero rows
        searchResults = search.findString("LN", 3);
        assertTrue((searchResults == null) || (searchResults.size() == 0));

        // Searching for LN in column LastName should yield two rows
        searchResults = search.findString("LN", "LastName");
        assertTrue(searchResults.size() == 2);
        
        // Searching for LN in a non existant should yield zero rows
        searchResults = search.findString("LN", "BadColumnName");
        assertTrue((searchResults == null) || (searchResults.size() == 0));

        // Searching for LN in the wrong column should yield zero rows
        searchResults = search.findString("LN", "FirstName");
        assertTrue((searchResults == null) || (searchResults.size() == 0));


    }
}

