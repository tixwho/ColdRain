package toolkit;

import java.util.ArrayList;

public class Timer {
    
    private long startTime=-1;   //获取开始时间  
    private long endTime=-1; //获取结束时间  
    private long passedTime = -1;
    private ArrayList<MsgBox> checkPointList = new ArrayList<MsgBox>();
    
    public Timer() {
        this.silentTimerEnd();//flush
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
        checkPointList.add(new MsgBox(this.passedTime));
    }
    
    public void timerPeriod(String msg) {
        if (this.startTime<0) {
            LogMaker.logs("Haven't start timing yet!");
            return;
        }
        this.endTime = System.currentTimeMillis();
        getPassedTime();
        checkPointList.add(new MsgBox(this.passedTime,msg));
    }
    
    public void timerEnd() {
        if (this.startTime<0) {
            LogMaker.logs("Haven't start timing yet!");
            return;
        }
        this.endTime = System.currentTimeMillis();
        getPassedTime();
        checkPointList.add(new MsgBox(this.passedTime));
        printCheckPoint();
        reset();
        
    }
    
    public void timerEnd(String info) {
        if (this.startTime<0) {
            LogMaker.logs("Haven't start timing yet!");
            return;
        }
        this.endTime = System.currentTimeMillis();
        getPassedTime();
        checkPointList.add(new MsgBox(this.passedTime,info));
        printCheckPoint();
        reset();
        
    }
    
    //used to flush initially without printing anything.
    public void silentTimerEnd() {
        if (this.startTime<0) {
            return;
        }
        this.endTime = System.currentTimeMillis();
        getPassedTime();
        checkPointList.add(new MsgBox(this.passedTime));
        printCheckPoint();
        reset();
        
    }
    public void getPassedTime() {
        this.passedTime = this.endTime - this.startTime;
        //LogMaker.logs("passed: "+this.passedTime+" millis");
    }
    
    public void printCheckPoint() {
        long firstOne = 0;
        int chkPointCount = 1;
        for (MsgBox chkPoint:checkPointList) {
            if(chkPoint.getInfo()==null) {
                LogMaker.logs("CheckPoint"+chkPointCount+": info:none: "+(chkPoint.getTime()-firstOne)+"millis");
            }else {
                LogMaker.logs("CheckPoint"+chkPointCount+": info:"+chkPoint.getInfo()+": "+(chkPoint.getTime()-firstOne)+"millis");
            }
           
            firstOne = chkPoint.getTime();
            chkPointCount++;
        }
    }
    
    public void reset() {
        this.startTime = -1;
        this.endTime = -1;
        this.passedTime = -1;
    }
    
    public class MsgBox{
        private long time;
        private String info;
        public MsgBox(long passedTime,String info) {
            this.info=info;
            this.time=passedTime;
        }
        
        public MsgBox(long passedTime) {
            this.info=null;
            this.time=passedTime;
        }
        
        public long getTime() {
            return this.time;
        }
        public String getInfo() {
            return this.info;
        }
    }

}
