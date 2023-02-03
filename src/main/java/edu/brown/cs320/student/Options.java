/**
 * @author: Safae Merigh
 * @version: 1.0.0
 */

package edu.brown.cs320.student;

public class Options {
    private String fileName = null;
    private String searchText = null;
    private Integer searchColumnIndex = -1;
    private String searchColumnName = null;
    private Boolean isHeaderPresent = false;


    /**
     * Create an instance of the Options class from the command arguments string array.
     * 
     * @param args: array of command-line arguments
     */
    public Options(String[] args) {

        // Do nothing if null argument array is provided
        if (args == null) 
            return;

        for (int argIndex = 0; argIndex < args.length;) {
            argIndex = add(argIndex, args);
        }
    }

    /**
     * Process the next argument pointed to by argIndex and advance argIndex
     * 
     * @param argIndex: index of the command-line argument to be processed.
     * @param args: string array of the command-line arguments.
     * @return new value of argIndex to be used in the next call.
     */
    private Integer add(Integer argIndex, String[] args) {

        String option = args[argIndex];
        String value = (argIndex+1 < args.length)? args[argIndex+1]: null;
        Boolean valueRequired = true;
        Integer nextArgIndex = 0;

        switch(option) {
            case "-f":
                setFileName(value);
                nextArgIndex =  argIndex + 2;
                break;
            case "-s":
                setSearchText(value);
                nextArgIndex =  argIndex + 2;
                break;
            case "-i":
                setSearchColumnIndex(value);
                nextArgIndex =  argIndex + 2;
                break;
            case "-c":
                setSearchColumnName(value);
                nextArgIndex =  argIndex + 2;
                break;
            case "-h":
                setIsHeaderPresent(true);
                valueRequired = false;
                nextArgIndex =  argIndex + 1;
                break;
            default:
                System.err.println("Unrecognized option: " + option);
                printUsageAndStop();
        }

        if (valueRequired && ((value == null) || value.startsWith("-") || (value.length() == 0))) {
            System.err.println("Invalid value for flag: " + option);
            printUsageAndStop();
        }

        return nextArgIndex;
    }

    /**
     * Set the file name/path
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Get the file name/path
     * @return File name/path
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * Set the search text
     * @param searchText: Text to be found
     */
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    /**
     * Get the search text.
     * @return Text to be found
     */
    public String getSearchText() {
        return this.searchText;
    }

    /**
     * Set the index of the column index to be used in the search
     * @param column
     */
    public void setSearchColumnIndex(String column) {
        try { // Integer Index
            this.searchColumnIndex =  Integer.parseInt(column);

        } catch (Exception e) {
            this.searchColumnIndex =  -1;
        }

        if (this.searchColumnIndex < 0) { // Negative Index
            System.err.println("Column index (" + column + ") must be a number >= 0.");
            System.exit(1);    
        }
    }

    /**
     * Get the index of the column index to be used in the search
     * @return column index to be used in the search
     */
    public Integer getSearchColumnIndex() {
        return this.searchColumnIndex;
    }

    /**
     * Set the name of the column index to be used in the search
     * @param column
     */
    public void setSearchColumnName(String column) {
        this.isHeaderPresent = true;
        this.searchColumnName = column;
    }

    /**
     * Get the name of the column index to be used in the search
     * @return
     */
    public String getSearchColumnName() {
        return this.searchColumnName;
    }

    /**
     * Tell whether the first line is a header
     * @param value
     */
    public void setIsHeaderPresent(Boolean value) {
        this.isHeaderPresent = value;
    }

    /**
     * Tell whether the first line is a header
     * @return true if the first line is a header
     */
    public Boolean getIsHeaderPresent() {
        return this.isHeaderPresent;
    }

    /**
     * Check if the required parameters have been specified.
     * @return true is all required parameters are provided.
     */
    public Boolean validCommandLine() {

        return  (fileName != null) &&
                (searchText != null) &&
                (fileName.length() > 0) &&
                (searchText.length() > 0);
    }

    /**
     * Print valid usage.
     */
    public void printUsageAndStop() {
        System.err.println("usage: -f FilePath -s SearchText [-i SearchColumnIndex | -c SearchColumnName]"
            + " [-h]");
        System.exit(1);
    }

    /** 
     * Print a description of the task that will be performed by the utility based on command-line inputs
     */
    public void printTaskDescription() {
        
        String column = "";
        String header = "";

        if (this.searchColumnIndex > -1)
            column = ". Search limited to column with index " + this.searchColumnIndex;
        else if (this.searchColumnName != null)
            column = ". Search limited to column named '" + this.searchColumnName + "'";

        if (this.isHeaderPresent)
            header = ". File has a header line." ;

        System.out.println("Searching for '" + this.searchText + "' within file "
            + this.fileName + column + header);

    }
}
