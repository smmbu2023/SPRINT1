package edu.brown.cs320.student.csv.rowcreators;
import java.util.List;
import java.util.ArrayList;

public class FactoryFailureException extends Exception {
    final List<String> row;
    
    public FactoryFailureException(String message, List<String> row) {
        super(message);
        this.row = new ArrayList<>(row);
        }
    }