package com.coldrain.exception;

public class MetaIOException extends ColdRainException{

    /**
     * 
     */
    private static final long serialVersionUID = 9148548583398231345L;
    public Integer errorCode;
    
    public MetaIOException(String message) {
        super(message);
    }
    
    public MetaIOException(String message,Throwable cause) {
        super(message,cause);
    }
    
    public MetaIOException(String message, ErrorCodes errorCode) {
        super(message,errorCode);
    }
    
    public MetaIOException(String message,Throwable cause, ErrorCodes errorCode) {
        super(message,cause,errorCode);
    }
    
    public Integer getErrorCode() {
        return errorCode;
    }
    

}
