package baseApps;

import toolkit.Timer;

public class BaseTestApp {
    private Timer testTimer;
    public BaseTestApp(){
        this.testTimer = new Timer();
    }
    
    public void taskStart() {
        testTimer.timerStart();
    }
    
    public void taskCheckPoint() {
        testTimer.timerPeriod();
    }
    
    public void taskEnd() {
        testTimer.timerEnd();
    }

}
