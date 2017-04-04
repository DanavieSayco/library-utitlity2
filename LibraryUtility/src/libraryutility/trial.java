package libraryutility;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pc
 */
public class trial {
    public static void main(String args[]) throws Exception {
        Access.setCon(); // connect to database via DbData class
        String input = "stud1";
        String filter = "ID";
        DefaultTableModel dfm = new DefaultTableModel();
        if (filter.equals("ID")) {
            Student s = Access.findStudentById(input);
            System.out.println(s.getFirstName());
        } else if (filter.equals("Last Name")) {
            Student s = Access.findStudentByLast(input);
        } else {
            Student s = Access.findStudentByFirst(input);
        }
    }
}
