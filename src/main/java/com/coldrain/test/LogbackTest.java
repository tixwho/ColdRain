package com.coldrain.test;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
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
        /*How to set log level*/
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();     
        ch.qos.logback.classic.Logger rootlogger = loggerContext.getLogger(LogbackTest.class);
        rootlogger.setLevel(Level.ERROR);
        
        log.info("here");
        log.error("SHIT");
    }

    
    

}
