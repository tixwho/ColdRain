package local.generic;

import toolkit.Timer;

public class BaseLocalTestingClass extends BaseLocalLoggingClass {
    public static Timer tim = new Timer();
    public BaseLocalTestingClass() {
        printWelcome();
    }
    
    protected void printWelcome() {
        logger.error("TESTING CLASS INITIALIZED: All logging system set to Error");
        logger.error("Supported logger: self,reader,table,writer");
        logger.error("TIMER INITILIZED: call tim");
        logger.error("------SETUP COMPLETE------");
    }
}
