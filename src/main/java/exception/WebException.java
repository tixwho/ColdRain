package exception;

public class WebException extends ColdRainException{


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
        super(message,errorCode);
    }
    
    public WebException(String message,Throwable cause, ErrorCodes errorCode) {
        super(message,cause,errorCode);
    }
    
    public Integer getErrorCode() {
        return errorCode;
    }
    

}
