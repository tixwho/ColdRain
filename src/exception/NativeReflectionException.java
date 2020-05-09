package exception;

public class NativeReflectionException extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = 8576748298171961802L;
    public Integer errorCode;
    
    public NativeReflectionException(String message) {
        super(message);
    }
    
    public NativeReflectionException(String message,Throwable cause) {
        super(message,cause);
    }
    
    public NativeReflectionException(String message,Throwable cause, ErrorCodes errorCode) {
        super(message,cause);
        this.errorCode = errorCode.getCode();
    }
    
    public Integer getErrorCode() {
        return errorCode;
    }
    

}
