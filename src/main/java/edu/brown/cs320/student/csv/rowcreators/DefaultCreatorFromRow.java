package edu.brown.cs320.student.csv.rowcreators;
import java.util.List;

public class DefaultCreatorFromRow implements CreatorFromRow<List<String>>{
    public List<String> create(List<String> row) {
        return row;
    }
}
