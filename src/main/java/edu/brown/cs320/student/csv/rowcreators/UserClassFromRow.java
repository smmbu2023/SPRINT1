package edu.brown.cs320.student.csv.rowcreators;
import java.util.List;

public class UserClassFromRow implements CreatorFromRow<User>{

    public  User create(List<String> row) throws FactoryFailureException {

        try {
            return new User(row);
        
        } catch (Exception e) {
            throw new FactoryFailureException(e.getMessage(), row);
        }
    }
}
