# <center> CS 320 SPRING 2023 </center>
## <center> SPRINT 1: CSV - PROJECT </center>

# Project details
* Project started: Monday, Jan 30, 2023
* Project submitted: Friday, Feb 3, 2023
* Effort: about 3 hours per day
* Github repo: smmbu2023
<br>
<br>

# Source code structure
The source code includes:
* A package named __edu.brown.cs320.student__ which contains the following classes:
    * __App__ class which contains the _main()_ method.
    * __Options__ class which encapsulates the command-line argument processing logic.
* A package named __edu.brown.cs320.student.csv__ which contains the following classes:
    * __Parser__ class which parses data provided in a _Reader_ into a list of rows where each row is a list of columns. 
    * __Searcher__ class which searches for text within the 'parsed' data.
* A package named __edu.brown.cs320.student.csv.rowcreators__ which contains various classes that implement the CreatorFormRow interface.
* JUnit tests for __Parser__, __Searcher__ (two classes, one for right sizing, one for right output), and __Options__ classes.  The row-processor/factory classes get tested indirectly through these same tests.
<br>
<br>

# User Story 1: (End user stakeholder)
The _main()_ function in the __App__ class performs the following steps:
* Creates an instance of the __Options__ class using the command-line arguments used to invoke the utility. The filename, search string, column index/name are available through methods of __Options__ class instead of local variables in the _main()_ program. The acceptable command line arguments format is<br> 
    <center>-f FilePath -s SearchText [-i SearchColumnIndex | -c SearchColumnName] [-h]  </center>

    * From this, we see that the CSV filepath and the search text are required command-line arguments.
    * Search can be restricted to a specific column by providing the column index after the _-i_ switch/flag/option, or by providing the column name after the _-c_ switch/flag/option.  If the user provides both the column index and name, the index prevails.
    * The _-h_ switch/flag/option tells the utility that data contains a header line (the header is first _non-blank_ line in the document).  Note: using the _-c_ switch implicity sets _-h_.
    * The __Options__ class provides a method named _validCommandLine_ which returns _true_ if the required parameters are provided.  _main()_ prints a _usage_ message and stops if the call to this method returns _false_.
    * Trying to set the value after the _-i_ to a non-numeric value, or to a negative number, will stop the utility after printing an error message.
    * The user cannot, for instance, have a command such as _-f -s MYSEARCH_ since a filename must be provided after the _-f_ switch.  The same applies to _-s, -i_ and _-c_ flags.  In other words, these switches require values.  The _-h_ switch does not require a value; its presence sets the header present flag to _true_.
* Once the command-line arguments are validated by the __Options__ class, _main()_ opens the file specified in the command line, 'loads' its content into a __Reader__ objects, and then creates an instance of the __Parser__ class. If an I/O error occurs, it will be handled by an exception handler.
* The output of the call to the _Parse()_ method on the __Parser__ class is a list of rows where each row contains a certain number of columns.
*  The list of rows is then used to create an instance of the __Searcher__ class, which implements an overloaded function called _findString()_.  There are 3 ways to call this function: one for the case where the column is not specified, one for a column that is specified by index, and one for a column specified by name.
* The search results of the call are then displayed to the user through a _System.out.println()_ call.
* Note: the instance of the __Parse__  class used in this part is constructed with an instance of __DefaultCreatorFromRow__ which is a processor that leaves rows intact.

<br>
<br>

# User Story 2: (Developper stakeholder)
* The constructor of the __Parser__ class takes an instance of a __Reader__ as a parameter.
* As shown in the JUnit test script, the __Parser__ class works also when the data to be parsed is given by an instance of __StringReader__.
* To get records one line at a time while traversing the provided instance of __Reader__, the _Parse()_ creates a new instance __BufferedReader__ based on the provided __Reader__.  One would have to revisit this approach if the amount of CSV data to be processed is huge.
<br>
<br>

# User Story 3: (Developper stakeholder)
* For our tests, we created four implementations of the __CreatorFromRow__ interface:
    * __DefaultCreatorFromRow__: The _create()_ method returns the same row we call it with.  In other words, this implementation does not make any change to the rows.
    * __TabSeparatedFieldsFromRow__: The _create()_ method joints all columns into one single string.
    * __FillMissingColumnsInRow__: It is possible that a user wants to process a CSV file where some of the last columns are not given.  This processor basically creates the same number of columns in all rows.
    * __UserClassFromRow__: A processor which creates an instance of a custom User class for each row.  A test function in the __ParserTest__ class shows how CSV file lines are transformed into instances of the __User__ class. 
<br>
<br>

# User Story 4: (Developper stakeholder)
* Except for inadvertent omissions, all methods in all classes have a JavaDoc.  I assumed there is no need to copy that documentation to the README.  However, the following are some additional notes about the __Parser__ and __Search__ classes.
* __Parser__:
    * This class two constructors.  One constructor takes, as parameters, an instance of __Reader__ and instance of a class that implements __CreatorFromRow__.  These class attributes are declared as _final_ in the class, and no get/set accessors are provided.  The second constructor uses two additional parameters that specify the string separator (some foreign-language CSV files use semicolon as the separator.  Also, one could specify a regular expression to trim the spaces between columns).
    * The constructors throw a __IllegalArgumentException__ if either of the required parameters is _null_.
    * The _Parse()_ method, which is provided in two overloaded versions, does the parsing.  Some assumptions were made for this method:
        * The method builds two lists: one that contains the "raw" records, and the other other list contains records that were processed by the _create()_ method of  __CreatorFromRow__.  The methods _getRawRecords()_ and _getFactoryRecords()_ provide the raw/unprocessed records and the processed records, respectively.
        * The _Parse()_ re-throws an exception when it encounters an error. A JUnit test in __ParserTest__ shows that a __FactoryFailuerException__ is thrown when one column could not be transformed into a date as a expected.
        * Data returned by _getRawRecords()_ contains the header record if present.  However, _getFactoryRecords()_ does not return the header row even if present.
        * The class also provides some additional methods to access the number of records, number of columms, and the names of the columns (if a header is present).
* __Searcher__ class:
    * This class as one constructor which takes the raw, parsed data returned by _parser.getRawRecords()_, i.e., the rows before processing by __CreatorFromRow__.
    * The class contains a _findString()_ in three overloaded forms, depending on whether we want to specify a column index, column name, or none.
    * _findString()_ returns the list of records that contain the desired data in the specified column.
    * **Important**: the class does not search through the processed rows because the processor does not necessarily keep data in the form of columns.

