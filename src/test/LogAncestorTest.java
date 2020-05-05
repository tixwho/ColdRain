package test;

import java.util.ArrayList;
import org.slf4j.Logger;

public abstract class LogAncestorTest {
    
    protected ArrayList<String> testArrList;
    protected abstract Logger getLogger();

    public LogAncestorTest() {
         getLogger().info("initialized");
    }
    

        
      
    

}
