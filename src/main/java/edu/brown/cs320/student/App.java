/**
 * @author: Safae Merigh
 * @version: 1.0.0
 */

package edu.brown.cs320.student;
import java.io.Reader;
import java.util.List;

import edu.brown.cs320.student.csv.Parser;
import edu.brown.cs320.student.csv.Searcher;
import edu.brown.cs320.student.csv.rowcreators.DefaultCreatorFromRow;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Main application.  This is where the interactive application starts.
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // Process the command line arguments into a structure that encapsulates the meaning
        // of each argument and checks that all arguments are specified
        //
        Options options = new Options(args);


        // If some required arguments are missing, print an error message and exit.  Otherwise, print a
        // summary of the task that will be performed during this call
        //
        if (!options.validCommandLine()) 
            options.printUsageAndStop();
        else
            options.printTaskDescription();

        // Create a Reader instance associated with the CSV file
        //
        Reader reader = null;
        try {
            reader = new BufferedReader(new FileReader(options.getFileName()));

        } catch (Exception e) {
            System.err.println("Terminating because of fatal error while reading the CSV file: "
                + e.getMessage());
            System.exit(1);
        }

        // This is an instance of a default row formatter which leaves the row intact (in = out)
        DefaultCreatorFromRow defaultFormatter = new DefaultCreatorFromRow();

        Parser<List<String>> parser = new Parser<List<String>>(reader, defaultFormatter,
            options.getIsHeaderPresent(), ",");

        try {
            parser.Parse();
        } catch (Exception e) {
            System.err.println("Terminating because of fatal error while parsing data: " + e.getMessage());
            System.exit(1);
        }
        
        // Search results
        //
        List<List<String>> searchResults = null;

        // Create a class to search through the parsed records
        Searcher searcher = new Searcher(parser.getRawRecords());

        // Check if we need to restrict the search to a specific column.
        Integer searchColumnIndex = options.getSearchColumnIndex();
        String searchColumnName = options.getSearchColumnName();

        if (searchColumnIndex >= 0 ) {   // user specified valid column index
            searchResults = searcher.findString(options.getSearchText(), searchColumnIndex);
        
        } else if (searchColumnName != null) {  // user specified valid column name
            searchResults = searcher.findString(options.getSearchText(), searchColumnName);

        } else {  // No column is specified
            searchResults = searcher.findString(options.getSearchText());
        }

        // Print the results of the search
        if ((searchResults != null) && (searchResults.size() > 0)) {
            for (List<String> row : searchResults) {
                System.out.println(defaultFormatter.create(row));
            }
        } else {
            System.out.println("No records found.");
        } 
    }
}
