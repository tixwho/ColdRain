package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackTest2 extends LogAncestorTest{
    private static final Logger log = LoggerFactory.getLogger(LogbackTest2.class);

    protected Logger getLogger() {
         return log;
    }
    public void doSomethingElse() {
          log.info("log somethingIn");
    }
    
    public static void doStatic() {
        log.info("I'm static!");
    }
    public static void main(String[] args) {
        log.error("SHITSHIT");
    }

    
    

}
