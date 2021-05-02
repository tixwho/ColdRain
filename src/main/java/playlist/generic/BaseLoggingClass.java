package playlist.generic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseLoggingClass {
    public static Logger logger = LoggerFactory.getLogger(BaseLoggingClass.class);
    public LoggerCtrl selfCtrl = new LoggerCtrl(BaseLoggingClass.class);
    
    public void setAllLevel(String lvl) {
        selfCtrl.setLevel(lvl);
    }

}
