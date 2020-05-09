package toolkit;

import java.util.ArrayList;

public class Timer {
    
    private long startTime=-1;   //获取开始时间  
    private long endTime=-1; //获取结束时间  
    private long passedTime = -1;
    private ArrayList<Long> checkPointList = new ArrayList<Long>();
    
    public Timer() {
        this.timerEnd();//flush
    }
    
    public void timerStart() {
        this.startTime = System.currentTimeMillis();
        //this.startTime = System.nanoTime();
    }
    
    public void timerPeriod() {
        if (this.startTime<0) {
            LogMaker.logs("Haven't start timing yet!");
            return;
        }
        this.endTime = System.currentTimeMillis();
        getPassedTime();
        checkPointList.add(this.passedTime);
    }
    
    public void timerEnd() {
        if (this.startTime<0) {
            LogMaker.logs("Haven't start timing yet!");
            return;
        }
        this.endTime = System.currentTimeMillis();
        getPassedTime();
        checkPointList.add(this.passedTime);
        printCheckPoint();
        reset();
        
    }
    public void getPassedTime() {
        this.passedTime = this.endTime - this.startTime;
        LogMaker.logs("passed: "+this.passedTime+" millis");
    }
    
    public void printCheckPoint() {
        long firstOne = 0;
        int chkPointCount = 1;
        for (Long chkPoint:checkPointList) {
            LogMaker.logs("CheckPoint"+chkPointCount+" :"+(chkPoint-firstOne)+"millis");
            firstOne = chkPoint;
            chkPointCount++;
        }
    }
    
    public void reset() {
        this.startTime = -1;
        this.endTime = -1;
        this.passedTime = -1;
    }

}
