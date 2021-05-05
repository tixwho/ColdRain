package com.coldrain.exception;

public class ColdRainException extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = -7525565091891983129L;
    public Integer errorCode;
    
    public ColdRainException(String message) {
        super(message);
    }
    
    public ColdRainException(String message,Throwable cause) {
        super(message,cause);
        
        
    }
    
    public ColdRainException(String message,ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode.getCode();
    }
    
    public ColdRainException(String message,Throwable cause, ErrorCodes errorCode) {
        super(message,cause);
        this.errorCode = errorCode.getCode();
    }
    
    public Integer getErrorCode() {
        return errorCode;
    }
    

}
