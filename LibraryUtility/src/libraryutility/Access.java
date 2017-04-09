package libraryutility;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException;
import java.sql.Statement; 
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
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

    public static DefaultTableModel getStudents(String sort) throws Exception {
        DefaultTableModel log = new DefaultTableModel(new Object[] {"ID", "Last Name", "First Name", "Year", "Gender", "Status", "Hrs Rendered"}, 0);
        String sq;
        if (sort.equals("HrsRendered")) {
            sq = "SELECT * FROM students ORDER BY " + sort + " DESC";
        } else {
            sq = "SELECT * FROM students ORDER BY " + sort;
        }
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sq);
        rs.beforeFirst();
        while (rs.next()) {
            log.addRow(new Object[] {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(4), rs.getString(8), rs.getTime(7)});
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
    
    public static void addHours(String id) {
        PreparedStatement ps;
        String st = "UPDATE students NATURAL JOIN log SET hrsRendered = ADDTIME(hrsRendered, hours) WHERE students.stud_id = ?";
        try {
            ps = con.prepareStatement(st);
            ps.setString(1, id);
            ps.execute();
            ps.close();
        } catch (Exception e) {
            
        }
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
                s = new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getTime(7), rs.getString(8));
            }
            ps.close();
        } catch (Exception e) { }
        return s;
    }
    
    public static DefaultTableModel findStudentByLast(String lastName) {
        DefaultTableModel log = new DefaultTableModel(new Object[] {"ID", "Last Name", "First Name", "Year", "Gender", "Status", "Hrs Rendered"}, 0);
        PreparedStatement ps;
        String sq = "SELECT * FROM students WHERE LastName = ?";
        try {
            ps = con.prepareStatement(sq);
            ps.setString(1, lastName);
            ResultSet rs = ps.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                log.addRow(new Object[] {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(4), rs.getString(8), rs.getTime(7)});
            }
            ps.close();
        } catch (Exception e) { }
        return log;
    }
    
    public static DefaultTableModel findStudentByFirst(String firstName) {
        DefaultTableModel log = new DefaultTableModel(new Object[] {"ID", "Last Name", "First Name", "Year", "Gender", "Status", "Hrs Rendered"}, 0);
        PreparedStatement ps;
        String sq = "SELECT * FROM students WHERE FirstName LIKE ?";
        try {
            ps = con.prepareStatement(sq);
            ps.setString(1, "%" + firstName + "%");
            ResultSet rs = ps.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                log.addRow(new Object[] {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(4), rs.getString(8), rs.getTime(7)});
            }
            ps.close();
        } catch (Exception e) { }
        return log;
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
    
    public static DefaultTableModel getNullOut() throws Exception {
        DefaultTableModel log = new DefaultTableModel(null, new String[] {"Date","Last Name", "First Name", "Time In", "Action"}) {
            public Class getColumnClass(int c) {
                switch (c) {
                    case 0: return String.class;
                    case 1: return String.class;
                    case 2: return String.class;
                    case 3: return String.class;
                    default: return Boolean.class;
                }
            }
        };
        String sq = "SELECT Date, LastName, FirstName, time_in FROM log NATURAL JOIN students WHERE time_out IS NULL";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sq);
        rs.beforeFirst();
        while (rs.next()) {
            log.addRow(new Object[] {rs.getDate(1).toString(), rs.getString(2), rs.getString(3), rs.getTime(4).toString(), false});
        }
        return log;
    }
    
    public static DefaultTableModel getLog() throws Exception {
        DefaultTableModel log = new DefaultTableModel(new Object[] {"Date", "Last Name", "First Name", "Time In", "Time Out", "Hours"}, 0);
        String sq = "SELECT Date, LastName, FirstName, time_in, time_out, Hours FROM log NATURAL JOIN students";
        Statement st = con.createStatement(ResultSet.CONCUR_UPDATABLE, ResultSet.TYPE_SCROLL_SENSITIVE);
        ResultSet rs = st.executeQuery(sq);
        rs.beforeFirst();
        while (rs.next()) {
            log.addRow(new Object[] {rs.getDate(1), rs.getString(2), rs.getString(3), rs.getTime(4), rs.getTime(5), rs.getTime(6)});
        }
        return log;
    }
    
    public static DefaultTableModel getTop(String limit) throws Exception {
        int counter = 1;
        String sq = "";
        DefaultTableModel log = new DefaultTableModel(new Object[] {"#", "Last Name", "First Name", "Hours"}, 0);
        if (limit.equals("All")) {
            sq = "SELECT * FROM students ORDER BY hrsRendered DESC, lastname";
        } else {
            sq = "SELECT * FROM students ORDER BY hrsRendered DESC, lastname LIMIT " + limit;
        }
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sq);
        rs.beforeFirst();
        while (rs.next()) {
            log.addRow(new Object[] {counter, rs.getString(2), rs.getString(3), rs.getTime(7)});
            counter++;
        }
        return log;
    }
    
    public static DefaultTableModel getStudSched() throws SQLException {
        DefaultTableModel log = new DefaultTableModel(new Object[] {"Last Name", "First Name", "Status"}, 0);
        String sq = "select lastname, firstname, starttime, endtime, status, day from students NATURAL JOIN schedule where day = dayname(curdate()) and starttime <= curtime() and endtime >= curtime()";
        Statement st = con.createStatement(ResultSet.CONCUR_UPDATABLE, ResultSet.TYPE_SCROLL_SENSITIVE);
        ResultSet rs = st.executeQuery(sq);
        rs.beforeFirst();
        while (rs.next()) {
            log.addRow(new Object[] {rs.getString(1), rs.getString(2), rs.getString(5)});
        }
        return log;
    }
    
    public static DefaultTableModel getSched(String id) throws Exception {
        DefaultTableModel log = new DefaultTableModel(null, new String[] {"Schedule ID","Start Time", "End Time", "Day", "Action"}) {
            public Class getColumnClass(int c) {
                switch (c) {
                    case 0: return String.class;
                    case 1: return String.class;
                    case 2: return String.class;
                    case 3: return String.class;
                    default: return Boolean.class;
                }
            }
        };
        String sq = "SELECT sched_id, starttime, endtime, day FROM students NATURAL JOIN schedule WHERE stud_id = ?";
        PreparedStatement ps;
        ps = con.prepareStatement(sq);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        rs.beforeFirst();
        while (rs.next()) {
            log.addRow(new Object[] {rs.getString(1), rs.getTime(2).toString(), rs.getTime(3).toString(), rs.getString(4), false});
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
    
    public static void changeStatSA(String id, String status) {
        PreparedStatement ps;
        String sq = "UPDATE studassistant SET status = ? WHERE id = ?";
        try {
            ps = con.prepareStatement(sq);
            ps.setString(1, status);
            ps.setString(2, id);
            ps.execute();
            ps.close();
        } catch (Exception e) {
            System.out.println("error");
        }
    }
    
    public static void addLog(String id) {
        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);
        java.sql.Time timeIn = new java.sql.Time(new java.util.Date().getTime());
        PreparedStatement ps;
        String st = "INSERT INTO log(log_id, date, time_in, stud_id) VALUES (NULL,?,?,?)";
        try {
            ps = con.prepareStatement(st);
            ps.setDate(1, date);
            ps.setTime(2, timeIn);
            ps.setString(3, id);
            ps.execute();
            ps.close();
        } catch (Exception e) {
        } 
    }
    
    public static DefaultTableModel getWeekLog(String term, String year, String gender) {
        DefaultTableModel log = new DefaultTableModel(new Object[] {"Last Name", "First Name", "Week1", "Week2", "Week3", "Week4"}, 0);
        String sq;
        if (gender.equalsIgnoreCase("All")) {
            sq = "SELECT lastname, firstname, weektime1, weektime2, weektime3, weektime4 FROM weeklog NATURAL JOIN students WHERE term = ? AND students.year = ?";
        } else {
            sq = "SELECT lastname, firstname, weektime1, weektime2, weektime3, weektime4 FROM weeklog NATURAL JOIN students WHERE term = ? AND students.year = ? AND gender = ?";
        }
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sq);
            ps.setString(1, term);
            ps.setString(2, year);
            if (!gender.equalsIgnoreCase("All")) {
                ps.setString(3, gender);
            }
            ResultSet rs = ps.executeQuery();
            rs.beforeFirst();
            while(rs.next()) {
                log.addRow(new Object[] {rs.getString(1), rs.getString(2), rs.getTime(3), rs.getTime(4), rs.getTime(5), rs.getTime(6)});
            }
        } catch (Exception e) {
            
        }  
        return log;
    }
    
    public static void addEx(String id, ArrayList<String> arr, String reason, String usr) throws Exception {
        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);
        PreparedStatement ps;
        for (int i = 0; i < arr.size(); i++) {
            String st = "INSERT INTO excuse(excuse_id, date, stud_id, sched_id, reason, sa_id) VALUES (NULL, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(st);
            ps.setDate(1, date);
            ps.setString(2, id);
            ps.setInt(3, Integer.parseInt(arr.get(i)));
            ps.setString(4, reason);
            ps.setString(5, usr);
            ps.execute();
            st = "UPDATE students NATURAL JOIN schedule SET hrsRendered = ADDTIME(hrsRendered, timediff(endtime, starttime)) WHERE students.stud_id = ? and schedule.sched_id = ?";
            ps = con.prepareStatement(st);
            ps.setString(1, id);
            ps.setInt(2, Integer.parseInt(arr.get(i)));
            ps.execute();
        } 
    }
    
    public static void setTimeOut(String id) {
        java.sql.Time timeOut = new java.sql.Time(new java.util.Date().getTime());
        PreparedStatement ps;
        String sq = "UPDATE log SET time_out = ?, hours = TIMEDIFF(time_out, time_in) WHERE stud_id = ? AND time_out IS NULL";
        try {
            ps = con.prepareStatement(sq);
            ps.setTime(1, timeOut);
            ps.setString(2, id);
            ps.execute();
            ps.close();
        } catch (Exception e) { }
    }
    
    public static void setTimeOut(ArrayList<String> stud, String usr) {
        java.sql.Time timeOut = new java.sql.Time(new java.util.Date().getTime());
        PreparedStatement ps;
        PreparedStatement ps2;
        String sq;
        for (int i = 0; i < stud.size(); i++) {
            sq = "UPDATE log NATURAL JOIN students SET time_out = ?, hours = TIMEDIFF(?, time_in), sa_id = ? WHERE firstname = ? AND time_out IS NULL";
            try {
                ps = con.prepareStatement(sq);
                ps.setTime(1, timeOut);
                ps.setTime(2, timeOut);
                ps.setString(3, usr);
                ps.setString(4, stud.get(i));
                ps.execute();
                ps.close();
            } catch (Exception e) { 
                System.out.println(e);
            }
            sq = "UPDATE students SET status = 'not active' WHERE firstname = ?";
            try {
                ps2 = con.prepareStatement(sq);
                ps2.setString(1, stud.get(i));
                ps2.execute();
                ps2.close();
            } catch (Exception e) { 
                System.out.println(e);
            }
        }
    }
    
    public static boolean validate(String id) {
        boolean result  = false;
        PreparedStatement ps;
        String sq = "SELECT TIMEDIFF(CURTIME(), time_in) FROM log NATURAL JOIN students WHERE stud_id = ? AND time_out IS NULL AND TIMEDIFF(CURTIME(), time_in) >= CAST('01:00:00' AS time)";
        try {
            ps = con.prepareStatement(sq);
            ps.setString(1, id);
            result = true;
        } catch (Exception e) {
            System.out.println("Error" + e);
            result = false;
        }
        return result;
    }
    
    public static boolean checkSched(String id) {
        boolean result = false;
        DayOfWeek today = DayOfWeek.from(LocalDate.now());
        String tod = today.toString();
        PreparedStatement ps;
        String sq = "SELECT * FROM schedule WHERE stud_id = ?";
        try {
            ps = con.prepareStatement(sq);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                if (rs.getString(4).equalsIgnoreCase(tod)) {
                    Time start = rs.getTime(2);
                    Time end = rs.getTime(3);
                    long startL = start.getTime();
                    long endL = end.getTime();
                    long now = System.currentTimeMillis();
                    
                    if (endL <= now) {
                        result = true;
                    } else {
                        PreparedStatement pss;
                        String sqq = "SELECT * FROM log WHERE stud_id = ? AND time_out IS NULL";
                        pss = con.prepareStatement(sqq);
                        pss.setString(1, id);
                        ResultSet rss = ps.executeQuery();
                        rss.beforeFirst();
                        while (rss.next()) {
                            Time in = rss.getTime(3);
                            long inL = in.getTime();
                            
                            if (inL < startL && startL < now) {
                                result = true;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {}
        return result;
    }
    
    public static boolean hasSched(String id) {
        boolean result = false;
        PreparedStatement ps;
        String sq = "SELECT * FROM schedule WHERE stud_id = ?";
        try {
            ps = con.prepareStatement(sq);
            ps.setString(1, id);
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
    
    public static LogIn findSA(String id) {
        LogIn l = null;
        PreparedStatement ps;
        String sq = "SELECT * FROM studassistant WHERE id = ?";
        try {
            ps = con.prepareStatement(sq);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                l = new LogIn(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5));
            }
            ps.close();
        } catch (Exception e) { 
            System.out.println("error here");
        }
        return l;
    }
    
    public static LogIn searchSA() {
        LogIn l = null;
        String sq = "SELECT * FROM studassistant WHERE status = 'active'";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sq);
            rs.beforeFirst();
            while (rs.next()) {
                l = new LogIn(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5));
            }
        } catch (Exception e) { }
        return l;        
    }
    
    public static void changePass(String id, String password) {
        PreparedStatement ps;
        String sq = "UPDATE students SET password = ? WHERE stud_id = ?";
        try {
            ps = con.prepareStatement(sq);
            ps.setString(1, password);
            ps.setString(2, id);
            ps.execute();
            ps.close();
        } catch (Exception e) { }
    }
}
