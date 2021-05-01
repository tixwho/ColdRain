package exception;

public class DatabaseException extends ColdRainException{


    /**
     * 
     */
    private static final long serialVersionUID = 502832247746226950L;
    public Integer errorCode;
    
    public DatabaseException(String message) {
        super(message);
    }
    
    public DatabaseException(String message,Throwable cause) {
        super(message,cause);
    }
    
    public DatabaseException(String message, ErrorCodes errorCode) {
        super(message,errorCode);
    }
    
    public DatabaseException(String message,Throwable cause, ErrorCodes errorCode) {
        super(message,cause,errorCode);
    }
    
    public Integer getErrorCode() {
        return errorCode;
    }
    

}
