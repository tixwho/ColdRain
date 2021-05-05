package com.coldrain.test;

import com.coldrain.toolkit.LogMaker;
import com.coldrain.toolkit.Timer;

public class TestReplace {
    public static void main(String[] args) {
        Timer tim = new Timer();
        String before = "Hello me";
        String after = before.replaceAll("me", "you");
        tim.timerStart();
        LogMaker.logs("reference: "+(before==after));
        tim.timerEnd();
        tim.timerStart();
        LogMaker.logs("value true?: "+(before.compareTo(after)==0));
        tim.timerEnd();
        //com.coldrain.test 2 if using reference will be faster.
        String before1 = "Hello me";
        String after1 = before.replaceAll("hi", "you");
        tim.timerStart();
        LogMaker.logs("reference: "+(before1==after1));
        tim.timerEnd();
        tim.timerStart();
        LogMaker.logs("value: "+before1.compareTo(after1));
        tim.timerEnd();
        

    }
    

}
