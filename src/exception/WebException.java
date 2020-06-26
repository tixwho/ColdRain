package exception;

public class WebException extends Exception{


    /**
     * 
     */
    private static final long serialVersionUID = 4552361256405287388L;
    /**
     * 
     */
    public Integer errorCode;
    
    public WebException(String message) {
        super(message);
    }
    
    public WebException(String message,Throwable cause) {
        super(message,cause);
    }
    public WebException(String message,ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode.getCode();
    }
    
    public WebException(String message,Throwable cause, ErrorCodes errorCode) {
        super(message,cause);
        this.errorCode = errorCode.getCode();
    }
    
    public Integer getErrorCode() {
        return errorCode;
    }
    

}
