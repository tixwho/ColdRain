package exception;

public class PlaylistIOException extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = 9148548583398231345L;
    public Integer errorCode;
    
    public PlaylistIOException(String message) {
        super(message);
    }
    public PlaylistIOException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode.getCode();
    }
    
    public PlaylistIOException(String message,Throwable cause) {
        super(message,cause);
    }
    
    public PlaylistIOException(String message,Throwable cause, ErrorCodes errorCode) {
        super(message,cause);
        this.errorCode = errorCode.getCode();
    }
    
    public Integer getErrorCode() {
        return errorCode;
    }
    

}
