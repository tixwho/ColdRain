package test;

import org.slf4j.Logger;

public abstract class LogAncestorTest {
    protected abstract Logger getLogger();
    public LogAncestorTest() {
         getLogger().info("initialized");
    }
    

        
      
    

}
