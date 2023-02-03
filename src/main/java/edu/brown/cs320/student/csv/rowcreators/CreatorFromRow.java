package edu.brown.cs320.student.csv.rowcreators;
import java.util.List;

/**
 * This is formatter that gets passed to the parser.
 * After parsing a row, the parser pass the row
 * to the create method and returns its result.
 */
public interface CreatorFromRow<T> {

    //After parsing a row, the parser pass the row to the create method and return its result
    T create(List<String> row) throws FactoryFailureException;
}
