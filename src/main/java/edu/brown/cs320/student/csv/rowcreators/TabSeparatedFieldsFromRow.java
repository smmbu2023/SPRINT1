package edu.brown.cs320.student.csv.rowcreators;
import java.util.List;
import java.util.StringJoiner;

public class TabSeparatedFieldsFromRow implements CreatorFromRow<String>{
    
    public String create(List<String> row) {
        return joinColumns(row, "\t" );
    }

    private String joinColumns(List<String> record, String stringDelim) {

        StringJoiner joiner = new StringJoiner(stringDelim);
        for (String column : record) {
            joiner.add(column);
        }
        return joiner.toString();

    }

}
