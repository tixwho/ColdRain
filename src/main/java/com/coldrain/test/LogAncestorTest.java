package com.coldrain.test;

import org.slf4j.Logger;

import java.util.ArrayList;

public abstract class LogAncestorTest {
    
    protected ArrayList<String> testArrList;
    protected abstract Logger getLogger();

    public LogAncestorTest() {
         getLogger().info("initialized");
    }
    

        
      
    

}
