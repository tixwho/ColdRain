package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackTest extends LogAncestorTest{
    private static final Logger log = LoggerFactory.getLogger(LogbackTest.class);

    protected Logger getLogger() {
         return log;
    }
    public void doSomethingElse() {
          log.info("log somethingElse");
    }
    
    public static void doStatic() {
        log.info("I'm static!");
    }
    public static void main(String[] args) {
        log.error("SHIT");
    }

    
    

}
