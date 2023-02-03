package edu.brown.cs320.student.csv.rowcreators;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class User {
    private final String firstName;
    private final String lastName;
    private final Date birthDate;

    public User(List<String> row) throws Exception {
        this.firstName = row.get(0);
        this.lastName = row.get(1);
        this.birthDate = new SimpleDateFormat("dd-MMM-yyyy").parse(row.get(2));        
    }

    @Override
    public String toString() {

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        
        String dob = simpleDateFormat.format(birthDate);

        return ("First Name:" + firstName + "\nLast Name: " + lastName + "\nDOB: " + dob + "\n");
    }

}
