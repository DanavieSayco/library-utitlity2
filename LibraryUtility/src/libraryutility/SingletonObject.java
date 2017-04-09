package libraryutility;

/**
 *
 * @author pc
 */
public class SingletonObject {
    private static boolean change;
    
    private static volatile SingletonObject instance;
    private SingletonObject() {

    }
    public static SingletonObject getInstance() {
        if (instance == null) {
            instance = new SingletonObject();
        }
        return instance;
    }
    public boolean getChange() {
        return change;
    }
    public void setChange(boolean change) {
        this.change = change;
    }
}
