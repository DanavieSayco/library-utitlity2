package libraryutility;

import java.sql.Time;

/**
 *
 * @author pc
 */
public class Student {
    String id;
    String lastName;
    String firstName;
    String gender;
    String year;
    String password;
    String enrolled;
    String status;
    Time hrs;
    
    public Student() {
    }
    public Student(String id, String lastName, String firstName, String gender, String year, String password, Time hrs, String status) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.id = id;
        this.password = password;
        this.gender = gender;
        this.year = year;
        this.status = status;
        this.hrs = hrs;
    }
    public String getId() {
        return this.id;
    }
    public String getPassword() {
        return this.password;
    }
    public String getLastName() {
        return this.lastName;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public String getName() {
        return this.lastName + ", " + this.firstName;
    }
    public String getGender() {
        return this.gender;
    }
    public String getYear() {
        return this.year;
    }
    public String isEnrolled() {
        return this.enrolled;
    }
    public String getStatus() {
        return this.status;
    }
    public Time getHrs() {
        return this.hrs;
    }
}
