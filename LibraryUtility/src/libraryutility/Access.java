package libraryutility;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException;
import java.sql.Statement; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author pc
 */
public class Access {
    private static Connection con;
    
    private Access() {
        
    }
    public static void setCon() {         
        try {             
            Class.forName("com.mysql.jdbc.Driver");             
            String conStr = "jdbc:mysql://localhost:3306/library_schema?user=root&password=";             
            con = DriverManager.getConnection(conStr);         
        } catch (Exception e) { 
        }     
    }

    public static DefaultTableModel getStudents() throws Exception {
        DefaultTableModel log = new DefaultTableModel(new Object[] {"ID", "Last Name", "First Name", "Year", "Gender", "Status", "Hrs Rendered"}, 0);
        String sq = "SELECT * FROM students ORDER BY stud_id";
        Statement st = con.createStatement(ResultSet.CONCUR_UPDATABLE, ResultSet.TYPE_SCROLL_SENSITIVE);
        ResultSet rs = st.executeQuery(sq);
        rs.beforeFirst();
        while (rs.next()) {
            log.addRow(new Object[] {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(4), rs.getString(8), rs.getDouble(7)});
        }
        return log;
    }
    
    public static void addStudent(Student newS) {
        PreparedStatement ps;
        String st = "INSERT INTO contacts(stud_id, last_name, first_name, gender, year, password) “ + “VALUES (?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(st);
            ps.setString(1, newS.id);
            ps.setString(2, newS.lastName);
            ps.setString(3, newS.firstName);
            ps.setString(4, newS.gender);
            ps.setString(5, newS.year);
            ps.setString(6, newS.password);
            ps.execute();
            ps.close();
        } catch (Exception e) { }
    }
    
    public static Student findStudentById(String id) {
        Student s = null;
        PreparedStatement ps;
        String sq = "SELECT * FROM students WHERE stud_id = ?";
        try {
            ps = con.prepareStatement(sq);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                s = new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getString(8));
            }
            ps.close();
        } catch (Exception e) { }
        return s;
    }
    
    public static Student findStudentByLast(String lastName) {
        Student s = null;
        PreparedStatement ps;
        String sq = "SELECT * FROM students WHERE LastName = ?";
        try {
            ps = con.prepareStatement(sq);
            ps.setString(1, lastName);
            ResultSet rs = ps.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                s = new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getString(8));
            }
            ps.close();
        } catch (Exception e) { }
        return s;
    }
    
    public static Student findStudentByFirst(String firstName) {
        Student s = null;
        PreparedStatement ps;
        String sq = "SELECT * FROM students WHERE FirstName = ?";
        try {
            ps = con.prepareStatement(sq);
            ps.setString(1, firstName);
            ResultSet rs = ps.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                s = new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getString(8));
            }
            ps.close();
        } catch (Exception e) { }
        return s;
    }
    
    public static DefaultListModel getOnline() throws Exception {
        DefaultListModel lstudents = new DefaultListModel();
        String sq = "SELECT * FROM students WHERE status = 'active' ORDER BY stud_id";
        Statement st = con.createStatement(ResultSet.CONCUR_UPDATABLE, ResultSet.TYPE_SCROLL_SENSITIVE);
        ResultSet rs = st.executeQuery(sq);
        rs.beforeFirst();
        while (rs.next()) {
            String s = rs.getString(2) + ", " + rs.getString(3);
            lstudents.addElement(s);
        }
        return lstudents;
    }
    
    public static DefaultTableModel getLog() throws Exception {
        DefaultTableModel log = new DefaultTableModel(new Object[] {"Student ID", "Date", "Time In", "Time Out"}, 0);
        String sq = "SELECT * FROM log";
        Statement st = con.createStatement(ResultSet.CONCUR_UPDATABLE, ResultSet.TYPE_SCROLL_SENSITIVE);
        ResultSet rs = st.executeQuery(sq);
        rs.beforeFirst();
        while (rs.next()) {
            log.addRow(new Object[] {rs.getString(5), rs.getDate(2), rs.getTime(3), rs.getTime(4)});
        }
        return log;
    }
    
    public static DefaultTableModel getTop() throws Exception {
        int counter = 1;
        DefaultTableModel log = new DefaultTableModel(new Object[] {"#", "Last Name", "First Name", "Hours"}, 0);
        String sq = "SELECT * FROM students ORDER BY hrsRendered DESC LIMIT 10";
        Statement st = con.createStatement(ResultSet.CONCUR_UPDATABLE, ResultSet.TYPE_SCROLL_SENSITIVE);
        ResultSet rs = st.executeQuery(sq);
        rs.beforeFirst();
        while (rs.next()) {
            log.addRow(new Object[] {counter, rs.getString(2), rs.getString(3), rs.getDouble(7)});
            counter++;
        }
        return log;
    }
    
    public static void changeStatus(String id, String status) {
        PreparedStatement ps;
        String sq = "UPDATE students SET status = ? WHERE stud_id = ?";
        try {
            ps = con.prepareStatement(sq);
            ps.setString(1, status);
            ps.setString(2, id);
            ps.execute();
            ps.close();
        } catch (Exception e) { }
    }
    
    public static void addLog(String id) {
        int tid = -1;
        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);
        java.sql.Time timeIn = new java.sql.Time(new java.util.Date().getTime());
        PreparedStatement ps;
        String st = "INSERT INTO log(log_id, date, time_in, stud_id) VALUES (?,?,?,?)";
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(st);
            rs = ps.executeQuery("SELECT LAST_INSERT_ID()");
            if (rs.next()) {
                tid = rs.getInt(1);
            }
            ps.setInt(1, tid);
            ps.setDate(2, date);
            ps.setTime(3, timeIn);
            ps.setString(4, id);
            ps.execute();
            ps.close();
        } catch (Exception e) {
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    
                }
            }
        }
    }
    
    public static void setTimeOut(String id) {
        java.sql.Time timeOut = new java.sql.Time(new java.util.Date().getTime());
        PreparedStatement ps;
        String sq = "UPDATE log SET time_out = ? WHERE stud_id = ? AND time_out IS NULL";
        try {
            ps = con.prepareStatement(sq);
            ps.setTime(1, timeOut);
            ps.setString(2, id);
            ps.execute();
            ps.close();
        } catch (Exception e) { }
    }
}
