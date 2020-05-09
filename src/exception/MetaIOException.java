package exception;

public class MetaIOException extends Exception{

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
    
    public MetaIOException(String message,Throwable cause, ErrorCodes errorCode) {
        super(message,cause);
        this.errorCode = errorCode.getCode();
    }
    
    public Integer getErrorCode() {
        return errorCode;
    }
    

}
