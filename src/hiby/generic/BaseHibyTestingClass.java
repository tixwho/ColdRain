package hiby.generic;

import toolkit.Timer;

public class BaseHibyTestingClass extends BaseHibyLoggingClass {

    public static Timer tim = new Timer();
    public BaseHibyTestingClass() {
        printWelcome();
    }
    
    protected void printWelcome() {
        logger.error("TESTING CLASS INITIALIZED: All logging system set to Error");
        logger.error("Supported logger: self,utf8Switcher");
        logger.error("TIMER INITILIZED: call tim");
        logger.error("------SETUP COMPLETE------");
    }
}
