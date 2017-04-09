package libraryutility;

/**
 *
 * @author pc
 */
public class Update {
    private static boolean logChange;
    private static volatile Update instance;
    
    public Update(boolean change){
        this.logChange = false;
    }
    public static Update getInstance() {
        if (instance == null) {
            instance = new Update();
        }
    }

    private Update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void setLogChange(boolean logChange) {
        this.logChange = logChange;
    }
    public boolean getLogChange() {
        return logChange;
    }
}
