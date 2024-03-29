package com.coldrain.database.generic;

import com.coldrain.toolkit.Timer;

public class BaseDatabaseTestingClass extends BaseDatabaseLoggingClass {
    public static Timer tim = new Timer();
    public BaseDatabaseTestingClass() {
        printWelcome();
    }
    
    protected void printWelcome() {
        logger.error("TESTING CLASS INITIALIZED: All logging system set to Error");
        logger.error("Supported logger: self,pojo,audioDBService,playlistDBService");
        logger.error("TIMER INITILIZED: call tim");
        logger.error("------SETUP COMPLETE------");
    }
}
