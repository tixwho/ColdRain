package com.coldrain.exception;

public class ColdRainRuntimeException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = -7525565091891983129L;
    public Integer errorCode;

    public ColdRainRuntimeException(String message) {
        super(message);
    }

    public ColdRainRuntimeException(String message,Throwable cause) {
        super(message,cause);


    }

    public ColdRainRuntimeException(String message,ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode.getCode();
    }

    public ColdRainRuntimeException(String message,Throwable cause, ErrorCodes errorCode) {
        super(message,cause);
        this.errorCode = errorCode.getCode();
    }
    
    public Integer getErrorCode() {
        return errorCode;
    }
    

}
