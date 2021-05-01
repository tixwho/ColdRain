package exception;

public class PlaylistIOException extends ColdRainException{

    /**
     * 
     */
    private static final long serialVersionUID = 9148548583398231345L;
    public Integer errorCode;
    
    public PlaylistIOException(String message) {
        super(message);
    }
    public PlaylistIOException(String message, ErrorCodes errorCode) {
        super(message,errorCode);
    }
    
    public PlaylistIOException(String message,Throwable cause) {
        super(message,cause);
    }
    
    public PlaylistIOException(String message,Throwable cause, ErrorCodes errorCode) {
        super(message,cause,errorCode);
    }
    
    public Integer getErrorCode() {
        return errorCode;
    }
    

}
