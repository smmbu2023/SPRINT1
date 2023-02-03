package edu.brown.cs320.student.csv.rowcreators;

import java.util.List;
import java.util.ArrayList;

public class FillMissingColumnsInRow implements CreatorFromRow<List<String>>{
    
    private Integer columnCount;

    public List<String> create(List<String> row) {

        // Ensure that all rows have the same number of cols.  Fill missing items with blanks.
        //
        if (row.size() == this.columnCount)
            return row;

        List<String> newRow = new ArrayList<String>();

        for(int col = 0; col < this.columnCount; col++) {
            if (col < row.size())
                newRow.add(row.get(col));                       // Copy existing column
            else
                newRow.add("");                                 // Append column with blank value
        } 

        return newRow;
    }

    /**
     * Set the number of columns to use for all rows.
     * @param columnCount Number of columns.
     */
    public void expandToColumnCount(Integer columnCount) {
        this.columnCount = columnCount;
    }

}
