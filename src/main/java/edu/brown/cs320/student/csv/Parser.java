/**
 * @author: Safae Merigh
 * @version: 1.0.0
 */

package edu.brown.cs320.student.csv;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs320.student.csv.rowcreators.CreatorFromRow;
import edu.brown.cs320.student.csv.rowcreators.FactoryFailureException;

import java.util.Arrays;
import java.io.Reader;
import java.io.BufferedReader;


public class Parser<T> {
    
    private final Reader reader;
    private final CreatorFromRow<T> creator;

    private Boolean isHeaderPresent = false;
    private String delimString = ",";
    private String[] columnNames = null;
    private Integer columnCount =0;
    private List<List<String>> rows = null;                     // Raw data from the Reader
    private List<T> rowsProcessed = null;                       // Rows processed by CreatorFromRow
    /**
     * Class constructor
     * @param reader: Reader source of the data
     * @param creatorFromRow: A row formatter
     */
    public Parser(Reader reader, CreatorFromRow<T> creatorFromRow) {

        if (creatorFromRow == null) {
            throw new IllegalArgumentException("Creator strategy cannot be null.");
        }

        if (reader == null) {
            throw new IllegalArgumentException("Reader cannot be null.");
        }

        this.reader = reader;
        this.creator = creatorFromRow;
    }

    /**
     * 
     * Class constructor
     * @param reader: Reader source of the data
     * @param isHeaderPresent: Indicates whether the data to be parsed has a header line
     * @param delimString: String delimiter
     * @param creatorFromRow: A row formatter
     */
    public Parser(Reader reader,  CreatorFromRow<T> creatorFromRow, Boolean isHeaderPresent, String delimString) {

        if (creatorFromRow == null) {
            throw new IllegalArgumentException("Creator strategy cannot be null.");
        }

        if (reader == null) {
            throw new IllegalArgumentException("Reader cannot be null.");
        }

        this.reader = reader;
        this.creator = creatorFromRow;
        this.isHeaderPresent = isHeaderPresent;
        this.delimString = delimString;
    }

    /**
     * Main Parse method using default parameters.
     * @throws Exception
     */
    public List<T>  Parse() throws Exception{
        return Parse(this.isHeaderPresent, this.delimString);
    }

    /**
     * Main Parse method with call-specific parameters. 
     * @param isHeaderPresent: Indicates whether the data to be parsed has a header line
     * @param delimString: String delimiter
     * @throws Exception
     */
    public List<T> Parse(Boolean isHeaderPresent, String delimString) throws Exception, FactoryFailureException{

        this.columnNames = null;                        // Column names
        this.rows = new ArrayList<>();                  // Rows read from the Reader object
        this.rowsProcessed = new ArrayList<T>();
        this.columnCount = 0;                           // Maximum number of columns encountered in all rows.
        
        Boolean firstLine = true;                       // Flag to process the header line if present.

        BufferedReader bufReader = new BufferedReader(reader);

        String line = "";
        try {
            while((line = bufReader.readLine()) != null) {                
                if (line.length() > 0) {
                    if (firstLine && isHeaderPresent ) {
                        columnNames = line.split(delimString);
                        rows.add(Arrays.asList(columnNames));        // We include the header row in the raw output
                        this.columnCount = columnNames.length;
                    } else {
                        addRow(line, delimString);
                    }
                    firstLine = false;
                }
            }        
        } catch (FactoryFailureException e) {
            throw e;

        } catch (Exception e) {
            throw e;
        }

        return rowsProcessed;
    }

    /**
     * Splits the row/line read from the Reader into columns and gets them processed by the creator object.
     * @param line: input row to be split
     * @param delimString: string delimiter
     */
    private void addRow(String line, String delimString) throws FactoryFailureException {

        List<String> columnValues = Arrays.asList(line.split(delimString));

        rows.add(columnValues);
        rowsProcessed.add(this.creator.create(columnValues));

        // Update the columnCount in case this row has more columns.
        //
        this.columnCount = (this.columnCount < columnValues.size()) ? columnValues.size(): this.columnCount;
    }

    /**
     * Returns the records that were not processed by CreatorFromRow processor.
     * @return List rows where each row is a list of columns.
     */
    public List<List<String>> getRawRecords() {
        return this.rows;
    }

    /**
     * Get the processed records
     * @return
     */
    public List<T> getFactoryRecords() {
        return this.rowsProcessed;
    }

    /**
     * Get number of rows
     * @return Number of rows.
     */
    public Integer getRecordCount() {
        return this.rows.size() - (this.isHeaderPresent? 1: 0);
    }

    /**
     * Get max number of columns
     * @return Number of columns
     */
    public Integer getColumnCount() {
        return this.columnCount;
    }

    /**
     * Get column names (from the header row)
     * @return Array of column name
     */
    public String[] getColumnNames() {
        return this.columnNames;
    }

}
