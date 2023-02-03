/**
 * @author: Safae Merigh
 * @version: 1.0.0
 */

package edu.brown.cs320.student.csv;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that implements the search through the CSV data
 */
public class Searcher {
    
    private final List<List<String>> records;                 

    /**
     * Constructor
     * @param records the records/rows to seach through
     */
    public Searcher(List<List<String>> records) {

        if (records == null) {
            throw new IllegalArgumentException("Records/rows parameter cannot be null.");
        }

        this.records = records;
    }

    /**
     * Find a string (looks through all columns).
     * @param searchText: the string to search for.
     * @return The list of rows containing the desired string.
     */
    public List<List<String>> findString(String searchText) {
        return findString(searchText, -1);
    }

    /**
     * Find a string in a column specified by name. Assume names are in the first row.
     * @param searchText: the string to search for
     * @param columnName: name of the column to search in
     * @return The list of rows containing the desired string. Null if nothing is found
     */
    public List<List<String>> findString(String searchText, String columnName) {

        if (this.records.size() < 1)
            return null;

        // Find the index of the column with the given name.
        //
        Integer columnIndex;
        List<String> headerRow = this.records.get(0);
        for (columnIndex = 0; columnIndex < headerRow.size(); columnIndex++) {
            if (headerRow.get(columnIndex).equals(columnName)) {
                break;
            }
        }

        // If the column was identified, we can perform the search.
        //
        if (columnIndex < headerRow.size())
            return findString(searchText, columnIndex);
        else
            return null;
    }


    /**
     * Find a string in a specific column given by the column index
     * @param searchText: the string to search for
     * @return The list of rows containing the desired string
     */
    public List<List<String>> findString(String searchText, Integer columnIndex) {

        List<List<String>> searchResults = new ArrayList<>();

        for (List<String> row : this.records) {
            
            if (columnIndex > row.size() - 1) {
                // Do nothing
                }
            else if ((columnIndex >= 0) && row.get(columnIndex).contains(searchText)) {
                    searchResults.add(row);

            } else if (columnIndex < 0) {
                for (String column : row) {
                    if (column.contains(searchText)) {
                        searchResults.add(row);      
                    }
                }
            }
        }

        return searchResults;
    }
}
