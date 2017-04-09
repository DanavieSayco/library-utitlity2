package libraryutility;
import java.sql.Time;
public class Schedule {
    Time startTime;
    Time endTime;
    String day;
    
    public Schedule() {
        
    }
    public Schedule(Time startTime, Time endTime, String day) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }
    public Time getSTime() {
        return this.startTime;
    }
    public Time getETime() {
        return this.endTime;
    }
    public String getDay() {
        return this.day;
    }
}
