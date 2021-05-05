package com.coldrain.toolkit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogMaker {
    
    public static void logs(String logContent) {
        System.out.println("Log"+time()+": "+logContent);
    }
    
    public static String time() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = myDateObj.format(myFormatObj);
        return "("+formattedTime+")";
    }

}
